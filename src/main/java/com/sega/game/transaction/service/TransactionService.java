package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.dto.TransactionDto;
import com.sega.game.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void transact(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public ResponseEntity<ResponseDto> allTransactions(){
        Iterable<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDto> dtoList = new ArrayList<>();

        transactions.forEach(transaction -> {
            dtoList.add(createTransactionDto(transaction));
        });

        return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
    }

    private ResponseDto createResponseDto(List<TransactionDto> dtoList){
        return new ResponseDto().builder().transactions(dtoList).build();
    }

    private TransactionDto createTransactionDto(Transaction transaction) {
        return new TransactionDto().builder()
                .price(transaction.getProduct().getPrice())
                .productName(transaction.getProduct().getName())
                .userId(transaction.getUser().getId())
                .transactionId(transaction.getId())
                .build();
    }

    public ResponseEntity<ResponseDto> getTransaction(long id){
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        List<TransactionDto> dtoList = new ArrayList<>();

        if(optTransaction.isPresent()){
            dtoList.add(createTransactionDto(optTransaction.get()));
            return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(createResponseDto(dtoList), HttpStatus.NO_CONTENT);
        }
    }

}
