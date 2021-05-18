package com.sega.game.transaction.controller;

import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transact")
    public void transact(@RequestBody Transaction transaction){
        transactionService.transact(transaction);
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<ResponseDto> getAllTransactions(){
        logger.info("getAllTransactions method called.");
        return transactionService.allTransactions();
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<ResponseDto> getTransaction(@PathVariable long transactionId){
        logger.info("getTransaction method called with transactionId {}", transactionId);
        return transactionService.getTransaction(transactionId);
    }

}
