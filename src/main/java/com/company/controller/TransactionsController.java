package com.company.controller;

import com.company.dto.request.TransactionsFilterRequestDTO;
import com.company.dto.request.TransactionsRequestDTO;
import com.company.service.TransactionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Transactions")
@Slf4j
@RestController
@RequestMapping("/v1/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @ApiOperation(value = "Create", notes = "Method create Transactions")
    @PreAuthorize("hasRole('BANK')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid TransactionsRequestDTO requestDTO) {
        log.info("Create: {},{}",TransactionsController.class,requestDTO);
        return ResponseEntity.ok(transactionsService.create(requestDTO));
    }

    @ApiOperation(value = "get", notes = "Method create Transactions")
    @GetMapping("/cardId/{cardId}")
    @PreAuthorize("hasRole('BANK')")
    public ResponseEntity<?> getByCardId(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "5") int size,
                                          @PathVariable("cardId") String cardId) {
        log.info("Get By Card Id: {},{}",TransactionsController.class,cardId);
        return ResponseEntity.ok(transactionsService.getByCardIdAndPagination(page, size, cardId));
    }
    @ApiOperation(value = "get", notes = "Method create Transactions")
    @GetMapping("/clientId/{clientId}")
    @PreAuthorize("hasRole('BANK')")
    public ResponseEntity<?> getByClientId(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "5") int size,
                                         @PathVariable("clientId") String cardId) {
        log.info("Get By Client Id: {},{}",TransactionsController.class,cardId);
        return ResponseEntity.ok(transactionsService.getByClientId(page, size, cardId));
    }
    @ApiOperation(value = "get", notes = "Method create Transactions")
    @GetMapping("/profileName/{profileName}")
    @PreAuthorize("hasRole('BANK')")
    public ResponseEntity<?> getByProfileName(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "5") int size,
                                         @PathVariable("profileName") String cardId) {
        log.info("Get By Profile Name Id: {},{}",TransactionsController.class,cardId);
        return ResponseEntity.ok(transactionsService.getByProfileName(page, size, cardId));
    }
    @ApiOperation(value = "get", notes = "Method create Transactions")
    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasRole('BANK')")
    public ResponseEntity<?> getByPhone(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "5") int size,
                                         @PathVariable("phone") String cardId) {
        log.info("Get By Phone Id: {},{}",TransactionsController.class,cardId);
        return ResponseEntity.ok(transactionsService.getByPhone(page, size, cardId));
    }
    @ApiOperation(value = "get Filter ", notes = "Method Get Filter")
    @PreAuthorize("hasRole('BANK')")
    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody TransactionsFilterRequestDTO requestDTO) {
        log.info("Filter: {},{}",TransactionsController.class,requestDTO);
        return ResponseEntity.ok(transactionsService.filter(requestDTO));
    }
}
