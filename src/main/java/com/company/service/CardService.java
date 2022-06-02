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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardCustomRepository cardCustomRepository;
    private final ClientService clientService;
    private final int min = 1000;
    private final int max = 9999;

    public CardResponseDTO create(CardRequestDTO requestDTO) {

        clientService.get(requestDTO.getClientId());

        String profileName = AuthorizationConfig.getCurrentProfileUserName();

        var entity = new CardEntity();
        entity.setNumber(getCardNumber());
        entity.setBalance(requestDTO.getBalance());
        entity.setClientId(requestDTO.getClientId());
        entity.setProfileName(profileName);

        cardRepository.save(entity);
        return toDTO(entity);
    }

    public CardResponseDTO getById(String id) {
        return toDTO(checkOrGet(id));
    }

    public List<CardResponseDTO> getAll() {
        return cardRepository.findByStatus(StatusEnum.ACTIVE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CardResponseDTO getByCardNumber(String number) {
        var entity= cardRepository.findByNumber(number).orElseThrow(() -> {
            log.warn("Card id not found");
            throw new ItemNotFoundException("Client id not found");
        });
        return toDTO(entity);
    }


    public List<CardResponseDTO> getByPhoneId(String phone) {
       return cardRepository.findByPhoneAndStatus(phone, StatusEnum.ACTIVE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CardResponseDTO> getByClientId(String cid) {
        return cardRepository.findByClientIdAndStatus(cid, StatusEnum.ACTIVE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Long getBalance(String number) {
        return getByCardNumber(number).getBalance();
    }

    public Boolean assignPhone(String phone, String cid) {
        return 0 < cardRepository.assignPhone(phone, cid);
    }

    public List<CardResponseDTO> filter(CardFilterRequestDTO filterDTO) {
        return cardCustomRepository.filter(filterDTO)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Boolean chengStatus(StatusEnum status, String id) {
        checkOrGet(id);
        return 0 < cardRepository.chengStatus(status, id);
    }


    /**
     * OTHER METHODS*/


    public Boolean paymentMinus(Long amount, String id) {
        if (checkOrGet(id).getBalance()>amount)
            return  0 < cardRepository.paymentMinus(amount, id);
        else
            throw new RuntimeException("amount is not enough");
    }

    public Boolean paymentPlus(Long amount, String id) {
        checkOrGet(id);
        return 0 <  cardRepository.paymentPlus(amount, id);
    }


    private String getCardNumber() {
        var rnd = new Random();
        int number = rnd.nextInt(99999999);

        return "8600"+String.format("%08d", number);
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

    public CardEntity checkOrGet(String id){
        return cardRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");});
    }

    public CardEntity checkCardNum(String num){
        return cardRepository.findByNumber(num).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");});
    }
}
