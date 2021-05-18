package com.sega.game.transaction.controller;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.service.TransactionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

}
