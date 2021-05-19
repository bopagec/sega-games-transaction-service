package com.sega.game.transaction.controller;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.dto.RequestDto;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.service.ProductService;
import com.sega.game.transaction.service.TransactionService;
import com.sega.game.transaction.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transact")
    public ResponseEntity<Transaction> transact(@RequestBody RequestDto requestDto) {
        logger.info("transact method called with transaction {}", requestDto);
        Optional<Product> optProduct = productService.findById(requestDto.getProductId());
        Optional<User> optUser = userService.findById(requestDto.getUserId());

        if (optProduct.isPresent() && optUser.isPresent()) {
            Transaction transaction = new Transaction().builder()
                    .user(optUser.get())
                    .product(optProduct.get()).build();
            Transaction transact = transactionService.transact(transaction);
            logger.info("transaction saved: {}", transact);
            return new ResponseEntity(transact, HttpStatus.CREATED);
        } else {
            logger.info("transaction not saved");
            return new ResponseEntity("", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<ResponseDto> getAllTransactions() {
        logger.info("getAllTransactions method called.");
        return transactionService.allTransactions();
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<ResponseDto> getTransaction(@PathVariable long transactionId) {
        logger.info("getTransaction method called with transactionId {}", transactionId);
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping("/transactions/user/{userId}")
    public ResponseEntity<ResponseDto> getTransactionsByUser(@PathVariable long userId) {
        logger.info("getTransactionsByUser method called with userId {}", userId);
        return transactionService.getTransactionsByUser(userId);
    }

    @GetMapping("/transactions/product/{productId}")
    public ResponseEntity<ResponseDto> getTransactionsByProduct(@PathVariable long productId) {
        logger.info("getTransactionsByProduct method called with productId {}", productId);
        return transactionService.getTransactionsByProduct(productId);
    }
}
