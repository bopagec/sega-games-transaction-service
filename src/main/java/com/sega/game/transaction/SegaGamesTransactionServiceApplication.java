package com.sega.game.transaction;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.Transaction;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.service.ProductService;
import com.sega.game.transaction.service.TransactionService;
import com.sega.game.transaction.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SegaGamesTransactionServiceApplication {
    Logger logger = LoggerFactory.getLogger(SegaGamesTransactionServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SegaGamesTransactionServiceApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner loadData(UserService userService, ProductService productService, TransactionService transactionService) {
        return args -> {
            List<Product> products = new ArrayList<>(
                    Arrays.asList(
                            Product.builder().name("Sega Soccer Slam").price(10).build(),
                            Product.builder().name("Sega Sports Mobile Golf").price(9).build(),
                            Product.builder().name("Sega Super Monkey Ball ").price(11.5).build(),
                            Product.builder().name("Sega Home Run King 2").price(22.5).build(),
                            Product.builder().name("Sega Mobile BaseBall").price(17.5).build(),
                            Product.builder().name("Sega Rally Championship").price(10.5).build(),
                            Product.builder().name("Sega Snowboarding").price(13.5).build(),
                            Product.builder().name("Tricky Third").price(15.5).build(),
                            Product.builder().name("Super Real Tennis").price(9).build(),
                            Product.builder().name("Shining Wind X").price(25).build()
                    )
            );

            List<User> users = new ArrayList<>(Arrays.asList(
                    User.builder().dob(LocalDate.of(1980, 11, 27)).email("bob.steven@gmail.com").name("Bob Steven").build(),
                    User.builder().dob(LocalDate.of(1977, 3, 31)).email("david.goggins@gmail.com").name("David Goggings").build(),
                    User.builder().dob(LocalDate.of(1987, 9, 21)).email("allan.roof@gmail.com").name("Allan Roof").build(),
                    User.builder().dob(LocalDate.of(1985, 1, 5)).email("steve.anderson@gmail.com").name("Steve Anderson").build(),
                    User.builder().dob(LocalDate.of(1990, 2, 15)).email("richard.jackson@gmail.com").name("Richard Jackson").build()
            ));

            products.stream().forEach(product -> {
                productService.saveProduct(product);
                logger.info("product loaded {}", product);
            });

            users.stream().forEach(user -> {
                userService.saveUser(user);
                logger.info("user loaded {}", user);
            });

            for (int i = 0, j = 0; i < 5; i++, j++) {
                Transaction p1 = new Transaction().builder().product(products.get(j)).user(users.get(i)).build();
                Transaction p2 = new Transaction().builder().product(products.get(++j)).user(users.get(i)).build();
                transactionService.transact(p1);
                transactionService.transact(p2);
            }

        };
    }

}
