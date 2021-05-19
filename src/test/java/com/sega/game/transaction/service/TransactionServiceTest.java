package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    private static User user;
    private static Product product;
    private static Transaction transaction;

    @InjectMocks
    private static TransactionService transactionService;

    @Mock
    private static TransactionRepository transactionRepository;

    @BeforeAll
    public static void init() {
        user = User.builder()
                .id(10)
                .dob(LocalDate.of(1980, 11, 27))
                .email("bob.steven@gmail.com")
                .name("Bob Steven")
                .build();
        product = Product.builder()
                .id(20)
                .name("Sega Soccer Slam")
                .price(10)
                .build();
        transaction = Transaction.builder()
                .id(30)
                .product(product)
                .user(user)
                .build();
    }

    @Test
    void transact() {
        //given
        given(transactionRepository.save(any())).willReturn(transaction);
        //when
        transactionService.transact(transaction);
        //then
        verify(transactionRepository).save(transaction);
    }

    @Test
    void allTransactions() {
        //given
        given(transactionRepository.findAll()).willReturn(new ArrayList<>(Arrays.asList(transaction)));
        //when
        ResponseEntity<ResponseDto> responseEntity = transactionService.allTransactions();
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1, responseEntity.getBody().getTransactions().size());
    }

    @Test
    void getTransaction_when_transaction_found() {
        //given
        given(transactionRepository.findById(anyLong())).willReturn(Optional.of(transaction));
        //when
        ResponseEntity<ResponseDto> actualResponse = transactionService.getTransaction(30);
        //then
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(1, actualResponse.getBody().getTransactions().size());
        Assertions.assertEquals("Sega Soccer Slam", actualResponse.getBody().getTransactions().get(0).getProductName());
        Assertions.assertEquals(10, actualResponse.getBody().getTransactions().get(0).getUserId());
    }

    @Test
    void getTransaction_when_transaction_NOT_found() {
        //given
        given(transactionRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        ResponseEntity<ResponseDto> actualResponse = transactionService.getTransaction(30);
        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
        Assertions.assertEquals(0, actualResponse.getBody().getTransactions().size());
    }

    @Test
    void getTransactionsByUser() {
        //given
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(transaction));
        given(transactionRepository.findByUserId(anyLong())).willReturn(transactions);
        //when
        ResponseEntity<ResponseDto> actualResponse = transactionService.getTransactionsByUser(10);
        //then
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(1, actualResponse.getBody().getTransactions().size());
        Assertions.assertEquals("Sega Soccer Slam", actualResponse.getBody().getTransactions().get(0).getProductName());
        Assertions.assertEquals(10, actualResponse.getBody().getTransactions().get(0).getUserId());
    }

    @Test
    void getTransactionsByProduct() {
        //given
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(transaction));
        given(transactionRepository.findByProductId(anyLong())).willReturn(transactions);
        //when
        ResponseEntity<ResponseDto> actualResponse = transactionService.getTransactionsByProduct(20);
        //then
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(1, actualResponse.getBody().getTransactions().size());
        Assertions.assertEquals("Sega Soccer Slam", actualResponse.getBody().getTransactions().get(0).getProductName());
        Assertions.assertEquals(10, actualResponse.getBody().getTransactions().get(0).getUserId());
    }
}