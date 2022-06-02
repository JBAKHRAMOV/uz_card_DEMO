package com.company.service;

import com.company.config.AuthorizationConfig;
import com.company.dto.request.ClientChangePhoneRequestDTO;
import com.company.dto.request.ClientRequestDTO;
import com.company.dto.response.ClientResponseDTO;
import com.company.entity.ClientEntity;
import com.company.enums.StatusEnum;
import com.company.exeption.ItemNotFoundException;
import com.company.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        clientRepository.findByPhone(requestDTO.getPhone()).orElseThrow(() -> {
            log.warn("there is already such a phone");
            throw new ItemNotFoundException("there is already such a phone");
        });

        String profileName = AuthorizationConfig.getCurrentProfileUserName();

        ClientEntity entity = new ClientEntity();
        entity.setName(requestDTO.getName());
        entity.setSurname(requestDTO.getSurname());
        entity.setMiddleName(requestDTO.getMiddleName());
        entity.setPhone(requestDTO.getPhone());
        entity.setStatus(StatusEnum.ACTIVE);
        entity.setProfileName(profileName);

        clientRepository.save(entity);

        return toDTO(entity);
    }

    public ClientResponseDTO getById(String id) {
        return toDTO(checkOrGet(id));
    }

    public List<ClientResponseDTO> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PageImpl<ClientResponseDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        var pagination = clientRepository.findAll(pageable);

        var list =pagination
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(list, pageable, pagination.getTotalElements());
    }

    public Boolean changeStatus(StatusEnum status, String id) {
        checkOrGet(id);
        return 0 < clientRepository.changeStatus(status, id);
    }

    public Boolean changePhone(ClientChangePhoneRequestDTO requestDTO, String cid) {
        checkOrGet(cid);
        return 0 < clientRepository.changePhone(requestDTO.getPhone(), cid);
    }

    public Boolean update(ClientRequestDTO requestDTO, String cid) {
        checkOrGet(cid);
        return 0 < clientRepository.update(requestDTO.getName(), requestDTO.getSurname(), requestDTO.getMiddleName(), cid);
    }


    /**
     * OHTER METHODS*/


    private ClientResponseDTO toDTO(ClientEntity entity) {
        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setMiddleName(entity.getMiddleName());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setProfileName(entity.getProfileName());
        return responseDTO;
    }

    public ClientEntity checkOrGet(String id){
       return clientRepository.findById(id).orElseThrow(() -> {
            log.warn("Client id not found");
            throw new ItemNotFoundException("Client id not found");
        });
    }


}
