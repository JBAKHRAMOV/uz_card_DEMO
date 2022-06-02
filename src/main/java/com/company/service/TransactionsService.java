package com.company.service;

import com.company.dto.request.TransactionsFilterRequestDTO;
import com.company.dto.request.TransactionsRequestDTO;
import com.company.dto.response.CardResponseDTO;
import com.company.dto.response.ClientResponseDTO;
import com.company.dto.response.TransactionsResponseDTO;
import com.company.entity.TransactionsEntity;
import com.company.enums.StatusEnum;
import com.company.mapper.TransactionsMapper;
import com.company.repository.TransactionsRepository;
import com.company.repository.custom.TransactionsCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final CardService cardService;
    private final TransactionsCustomRepository transactionsCustomRepository;

    @Transactional
    public TransactionsResponseDTO create(TransactionsRequestDTO requestDTO) {

        cardService.paymentMinus(requestDTO.getAmount(), requestDTO.getFromCardId());
        cardService.paymentPlus(requestDTO.getAmount(), requestDTO.getToCardId());

        TransactionsEntity entity = new TransactionsEntity();
        entity.setAmount(requestDTO.getAmount());
        entity.setFromCardId(requestDTO.getFromCardId());
        entity.setToCardId(requestDTO.getToCardId());
        entity.setStatus(StatusEnum.ACTIVE);
        transactionsRepository.save(entity);
        return toDTO(entity);
    }

    public PageImpl<TransactionsResponseDTO> getByCardIdAndPagination(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);


        var pagination = transactionsRepository.getByCardId(pageable, cid, StatusEnum.ACTIVE);

        var list=pagination
                .stream()
                .map(this::toDTOMapper)
                .toList();

        return new PageImpl<>(list, pageable, pagination.getTotalElements());
    }

    public PageImpl<TransactionsResponseDTO> getByPhone(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);


        var pagination = transactionsRepository.getByPhone(pageable, cid, StatusEnum.ACTIVE);

        var list=pagination
                .stream()
                .map(this::toDTOMapper)
                .toList();

        return new PageImpl<>(list, pageable, pagination.getTotalElements());
    }

    public PageImpl<TransactionsResponseDTO> getByProfileName(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);

        var pagination = transactionsRepository.getByProfileName(pageable, cid, StatusEnum.ACTIVE);

        var list= pagination
                .stream()
                .map(this::toDTOMapper)
                .toList();

        return new PageImpl<>(list, pageable, pagination.getTotalElements());
    }

    public PageImpl<TransactionsResponseDTO> getByClientId(int page, int size, String cid) {
        Pageable pageable = PageRequest.of(page, size);

        var pagination = transactionsRepository.getByClientId(pageable, cid, StatusEnum.ACTIVE);

        var list=pagination
                .stream()
                .map(this::toDTOMapper)
                .toList();

        return new PageImpl<>(list, pageable, pagination.getTotalElements());
    }

    public List<TransactionsResponseDTO> filter(TransactionsFilterRequestDTO filterDTO) {
        return transactionsCustomRepository.filter(filterDTO)
                .stream()
                .map(this::toDTO)
                .toList();
    }



    /**
     * OTHER METHODS*/

    private TransactionsResponseDTO toDTOMapper(TransactionsMapper entity) {

        TransactionsResponseDTO responseDTO = new TransactionsResponseDTO();
        responseDTO.setId(entity.getT_id());

        responseDTO.setFromCard(
                new CardResponseDTO(
                        entity.getFcr_id(),
                        getCardNumberSkr(entity.getFcr_number()),
                        new ClientResponseDTO(
                                entity.getFcl_id(),
                                entity.getFcl_name(),
                                entity.getFcl_surname())));

        responseDTO.setToCard(
                new CardResponseDTO(
                        entity.getTcr_id(),
                        getCardNumberSkr(entity.getTcr_number()),
                        new ClientResponseDTO(
                                entity.getTcl_id(),
                                entity.getTcl_name(),
                                entity.getTcl_surname())));

        responseDTO.setAmount(entity.getT_amount());
        responseDTO.setStatus(entity.getT_status());
        responseDTO.setCreatedDate(entity.getT_createdDate());
        return responseDTO;
    }

    private String getCardNumberSkr(String entity) {
        return entity.substring(0, 4) + "-****-****-" + entity.substring(entity.length() - 4);
    }

    private TransactionsResponseDTO toDTO(TransactionsEntity entity) {
        TransactionsResponseDTO responseDTO = new TransactionsResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setFromCardId(entity.getFromCardId());
        responseDTO.setToCardId(entity.getToCardId());
        responseDTO.setAmount(entity.getAmount());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        return responseDTO;
    }
}
