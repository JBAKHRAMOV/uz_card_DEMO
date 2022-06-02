package com.company.service;

import com.company.config.AuthorizationConfig;
import com.company.dto.request.CardFilterRequestDTO;
import com.company.dto.request.CardRequestDTO;
import com.company.dto.response.CardResponseDTO;
import com.company.entity.CardEntity;
import com.company.enums.StatusEnum;
import com.company.exeption.InsufficientFundsException;
import com.company.exeption.ItemNotFoundException;
import com.company.repository.custom.CardCustomRepository;
import com.company.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    private final int min = 1000;
    private final int max = 9999;
    @Autowired
    private CardCustomRepository cardCustomRepository;
    @Autowired
    private ClientService clientService;

    public CardResponseDTO create(CardRequestDTO requestDTO) {
        clientService.get(requestDTO.getClientId());
        String profileName = AuthorizationConfig.getCurrentProfileUserName();
        CardEntity entity = new CardEntity();
        entity.setNumber(getCardNumber());
        entity.setBalance(requestDTO.getBalance());
        entity.setClientId(requestDTO.getClientId());
        entity.setProfileName(profileName);
        LocalDate localDate = LocalDate.now();
        entity.setExpiryDate(localDate.plusYears(3));
        if (requestDTO.getBalance() > 0) {
            entity.setStatus(StatusEnum.ACTIVE);
        } else {
            entity.setStatus(StatusEnum.BLOCK);
        }
        cardRepository.save(entity);
        return toDTO(entity);
    }

    public CardResponseDTO getById(String id) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return toDTO(entity);
    }

    public CardEntity get(String id) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return entity;
    }

    public List<CardResponseDTO> getAll() {
        List<CardResponseDTO> responseDTOS = new LinkedList<>();
        cardRepository.findByStatus(StatusEnum.ACTIVE).forEach(entity -> {
            responseDTOS.add(toDTO(entity));
        });
        return responseDTOS;
    }

    public CardEntity get(String id, Long amount) {
        CardEntity entity = cardRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        if (entity.getBalance() < amount) {
            throw new InsufficientFundsException("Balance not Found");
        }
        return entity;
    }

    public CardResponseDTO getByCardNumber(String id) {
        CardEntity entity = cardRepository.findByNumber(id).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return toDTO(entity);
    }


    public List<CardResponseDTO> getByPhoneId(String phone) {
        List<CardResponseDTO> dtoList = new LinkedList<>();
        cardRepository.findByPhoneAndStatus(phone, StatusEnum.ACTIVE).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public List<CardResponseDTO> getByClientId(String cid) {
        List<CardResponseDTO> dtoList = new LinkedList<>();
        cardRepository.findByClientIdAndStatus(cid, StatusEnum.ACTIVE).stream().forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    public Long getBalance(String number) {
        return cardRepository.getBalance(number).orElseThrow(() -> {
            log.warn("Card number not found");
            throw new ItemNotFoundException("Card number not found");
        });
    }

    public Boolean paymentMinus(Long amount, String cid) {
        int n = cardRepository.paymentMinus(amount, cid);
        return n > 0;
    }

    public Boolean paymentPlus(Long amount, String cid) {
        int n = cardRepository.paymentPlus(amount, cid);
        return n > 0;
    }

    public Boolean assignPhone(String phone, String cid) {
        int n = cardRepository.assignPhone(phone, cid);
        return n > 0;
    }

    public List<CardResponseDTO> filter(CardFilterRequestDTO filterDTO) {
        List<CardResponseDTO> responseDTO = new LinkedList<>();
        cardCustomRepository.filter(filterDTO).forEach(entity -> {
            responseDTO.add(toDTO(entity));
        });
        return responseDTO;
    }

    public Boolean chengStatus(StatusEnum status, String id) {
        int n = cardRepository.chengStatus(status, id);
        return n > 0;
    }

    private String getCardNumber() {
        int a = (int) (Math.random() * (max - min + 1) + min);
        int b = (int) (Math.random() * (max - min + 1) + min);
        int c = (int) (Math.random() * (max - min + 1) + min);
        String cardNumber = "8600-" + a + "-" + b + "-" + c;

        Optional<CardEntity> optional = cardRepository.findByNumber(cardNumber);
        if (optional.isPresent()) {
            getCardNumber();
        }
        return cardNumber;
    }

    private CardResponseDTO toDTO(CardEntity entity) {
        CardResponseDTO responseDTO = new CardResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setNumber(entity.getNumber());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setExpiryDate(entity.getExpiryDate());
        responseDTO.setBalance(entity.getBalance());
        return responseDTO;
    }
}
