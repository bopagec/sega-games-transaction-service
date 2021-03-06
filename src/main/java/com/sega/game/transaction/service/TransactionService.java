package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.dto.TransactionDto;
import com.sega.game.transaction.repository.TransactionRepository;
import com.sega.game.transaction.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.sega.game.transaction.utils.ResponseUtils.*;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transact(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public ResponseEntity<ResponseDto> allTransactions() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> dtoList = new ArrayList<>();

        transactions.forEach(transaction -> {
            dtoList.add(createTransactionDto(transaction));
        });

        return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getTransaction(long id) {
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        List<TransactionDto> dtoList = new ArrayList<>();

        if (optTransaction.isPresent()) {
            dtoList.add(createTransactionDto(optTransaction.get()));
            return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<ResponseDto> getTransactionsByUser(long userId) {
        Collection<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<TransactionDto> dtoList = new ArrayList<>();

        transactions.forEach(transaction -> {
            dtoList.add(createTransactionDto(transaction));
        });

        return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getTransactionsByProduct(long productId) {
        Collection<Transaction> transactions = transactionRepository.findByProductId(productId);
        List<TransactionDto> dtoList = new ArrayList<>();

        transactions.forEach(transaction -> {
            dtoList.add(createTransactionDto(transaction));
        });

        return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
    }


}
