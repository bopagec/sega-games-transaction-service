package com.sega.game.transaction;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.repository.ProductRepository;
import com.sega.game.transaction.repository.UserRepository;
import com.sega.game.transaction.service.ProductService;
import com.sega.game.transaction.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class SegaGamesTransactionServiceApplication {
	Logger logger = LoggerFactory.getLogger(SegaGamesTransactionServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SegaGamesTransactionServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner loadData(UserService userService, ProductService productService){
		return args -> {
			productService.saveProduct(new Product().builder().name("Sega Soccer Slam").price(10).build());
			productService.saveProduct(new Product().builder().name("Sega Sports Mobile Golf").price(9).build());
			productService.saveProduct(new Product().builder().name("Sega Super Monkey Ball ").price(11.5).build());
			productService.saveProduct(new Product().builder().name("Sega Home Run King 2").price(22.5).build());
			productService.saveProduct(new Product().builder().name("Sega Mobile BaseBall").price(17.5).build());
			productService.saveProduct(new Product().builder().name("Sega Rally Championship").price(10.5).build());
			productService.saveProduct(new Product().builder().name("Sega Snowboarding").price(13.5).build());
			productService.saveProduct(new Product().builder().name("Tricky Third").price(15.5).build());
			productService.saveProduct(new Product().builder().name("Super Real Tennis").price(9).build());
			productService.saveProduct(new Product().builder().name("Shining Wind X").price(25).build());

			userService.saveUser(new User().builder().dob(LocalDate.of(1980, 11, 27)).email("bob.steven@gmail.com").name("Bob Steven").build());
			userService.saveUser(new User().builder().dob(LocalDate.of(1977, 03, 31)).email("david.goggins@gmail.com").name("David Goggings").build());

			logger.info("data loaded to H2 in memory database");
		};
	}

}
