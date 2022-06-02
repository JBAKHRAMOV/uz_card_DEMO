package com.company.controller;

import com.company.dto.request.ClientChangePhoneRequestDTO;
import com.company.dto.request.ClientRequestDTO;
import com.company.enums.StatusEnum;
import com.company.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "Client")
@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
public class ClientController {

    public final ClientService clientService;

      @PreAuthorize("hasRole('BANK')")
     @ApiOperation(value = "Create", notes = "Method create Client")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid ClientRequestDTO requestDTO) {
          log.info("Create: {}",requestDTO);
        return ResponseEntity.ok(clientService.create(requestDTO));
    }

     @ApiOperation(value = "Get by id", notes = "Method get By id")
    @PreAuthorize("hasRole('BANK')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
         log.info("Get by id: {}",id);
        return ResponseEntity.ok(clientService.getById(id));
    }

     @ApiOperation(value = "Get All", notes = "Method get All")
    @GetMapping("")
    public ResponseEntity<?> getAll() {
         log.info("Get All: {}",CardController.class);
        return ResponseEntity.ok(clientService.getAll());
    }

    @ApiOperation(value = "List pagination", notes = "Method pagination list")
     @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("pagination: {}",CardController.class);
        return ResponseEntity.ok(clientService.pagination(page, size));
    }

    @ApiOperation(value = "Cheng changePhone", notes = "Method Cheng changePhone")
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/changePhone/{id}")
    public ResponseEntity<?> changePhone(@RequestBody @Valid ClientChangePhoneRequestDTO requestDTO,
                                         @PathVariable("id") String id) {
        log.info("changePhone: {},{}",CardController.class,requestDTO);
        return ResponseEntity.ok(clientService.changePhone(requestDTO, id));
    }

    @ApiOperation(value = "Cheng update", notes = "Method Cheng update")
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ClientRequestDTO requestDTO, @PathVariable("id") String id) {
        log.info("Update: {},{}",CardController.class,requestDTO);
        return ResponseEntity.ok(clientService.update(requestDTO, id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id Active")
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/changeStatus/{id}/Active")
    public ResponseEntity<?> chengStatusActive(@PathVariable("id") String id) {
        log.info("Cheng Status Active: {},{}",CardController.class,id);
        return ResponseEntity.ok(clientService.changeStatus(StatusEnum.ACTIVE, id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id block")
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/changeStatus/{id}/block")
    public ResponseEntity<?> chengStatusBlock(@PathVariable("id") String id) {
        log.info("Cheng Status Block: {},{}",CardController.class,id);
        return ResponseEntity.ok(clientService.changeStatus(StatusEnum.BLOCK, id));
    }

    @ApiOperation(value = "Cheng Status", notes = "Method Cheng Status by id Not active")
    @PreAuthorize("hasRole('BANK')")
    @PutMapping("/changeStatus/{id}/notactive")
    public ResponseEntity<?> chengStatusNotActive(@PathVariable("id") String id) {
        log.info("Cheng Status Not Active: {},{}",CardController.class,id);
        return ResponseEntity.ok(clientService.changeStatus(StatusEnum.NOT_ACTIVE, id));
    }
}
