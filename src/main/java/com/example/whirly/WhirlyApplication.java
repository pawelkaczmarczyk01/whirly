package com.example.whirly;

import com.example.whirly.src.client.Answer;
import com.example.whirly.src.client.Client;
import com.example.whirly.src.server.ProductFactory;
import com.example.whirly.src.server.Product;
import com.example.whirly.src.server.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@SpringBootApplication
@Slf4j
public class WhirlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhirlyApplication.class, args);

		ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);

		Client client = new Client(true, LocalDate.of(2004,06,10), BigDecimal.valueOf(50000f), Collections.emptyList());

		Product product = null;

		try {
			product = ProductFactory.create(spec);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		Answer result = client.canPurchase(product);

		System.out.println("Klient posiada dostęp?");
		System.out.println(result.isAccess());
	}
}
