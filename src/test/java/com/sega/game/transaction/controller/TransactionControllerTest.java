package com.sega.game.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.dto.RequestDto;
import com.sega.game.transaction.dto.ResponseDto;
import com.sega.game.transaction.dto.TransactionDto;
import com.sega.game.transaction.service.ProductService;
import com.sega.game.transaction.service.TransactionService;
import com.sega.game.transaction.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.sega.game.transaction.utils.ResponseUtils.createResponseDto;
import static com.sega.game.transaction.utils.ResponseUtils.createTransactionDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    private static User user;
    private static Product product;
    private static Transaction transaction;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void transact() throws Exception {
        given(productService.findById(anyLong())).willReturn(Optional.of(product));
        given(userService.findById(anyLong())).willReturn(Optional.of(user));
        given(transactionService.transact(any())).willReturn(transaction);

        RequestDto requestDto = RequestDto.builder()
                .productId(20)
                .userId(10)
                .build();

        mockMvc.perform(post("/transact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isCreated());
     }

    @Test
    void transact_with_bad_request() throws Exception {
        given(productService.findById(anyLong())).willReturn(Optional.empty());
        given(userService.findById(anyLong())).willReturn(Optional.empty());

        RequestDto requestDto = RequestDto.builder()
                .productId(200)
                .userId(100)
                .build();

        mockMvc.perform(post("/transact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTransactions() throws Exception {
        TransactionDto transactionDto = createTransactionDto(transaction);
        ResponseDto responseDto = createResponseDto(new ArrayList<>(Arrays.asList(transactionDto)));
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity(responseDto, HttpStatus.OK);

        given(transactionService.allTransactions()).willReturn(responseEntity);
        mockMvc.perform(get("/transactions/all"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransaction() throws Exception {
        TransactionDto transactionDto = createTransactionDto(transaction);
        ResponseDto responseDto = createResponseDto(new ArrayList<>(Arrays.asList(transactionDto)));
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity(responseDto, HttpStatus.OK);

        given(transactionService.getTransaction(30)).willReturn(responseEntity);

        mockMvc.perform(get("/transactions/30"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionsByUser_when_user_exists() throws Exception {
        TransactionDto transactionDto = createTransactionDto(transaction);
        ResponseDto responseDto = createResponseDto(new ArrayList<>(Arrays.asList(transactionDto)));
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity(responseDto, HttpStatus.OK);

        given(transactionService.getTransactionsByUser(anyLong())).willReturn(responseEntity);

        mockMvc.perform(get("/transactions/user/10"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactionsByUser_when_user_NOT_exists() throws Exception {
        ResponseDto responseDto = createResponseDto(new ArrayList<>());
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity(responseDto, HttpStatus.NO_CONTENT);

        given(transactionService.getTransactionsByUser(anyLong())).willReturn(responseEntity);

        mockMvc.perform(get("/transactions/user/100"))
                .andExpect(status().isNoContent());
    }


    @Test
    void getTransactionsByProduct() throws Exception {
        TransactionDto transactionDto = createTransactionDto(transaction);
        ResponseDto responseDto = createResponseDto(new ArrayList<>(Arrays.asList(transactionDto)));
        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity(responseDto, HttpStatus.OK);

        given(transactionService.getTransactionsByProduct(anyLong())).willReturn(responseEntity);

        mockMvc.perform(get("/transactions/product/30"))
                .andExpect(status().isOk());
    }


}