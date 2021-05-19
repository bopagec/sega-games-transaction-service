package com.sega.game.transaction.utils;

import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.dto.TransactionDto;

import java.util.List;

public class ResponseUtils {
    public static ResponseDto createResponseDto(List<TransactionDto> dtoList) {
        return new ResponseDto().builder().transactions(dtoList).build();
    }

    public static TransactionDto createTransactionDto(Transaction transaction) {
        return new TransactionDto().builder()
                .price(transaction.getProduct().getPrice())
                .productName(transaction.getProduct().getName())
                .userId(transaction.getUser().getId())
                .transactionId(transaction.getId())
                .build();
    }
}
