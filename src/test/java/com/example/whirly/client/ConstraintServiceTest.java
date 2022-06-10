package com.example.whirly.client;

import com.example.whirly.src.client.Client;
import com.example.whirly.src.client.ConstraintsService;
import com.example.whirly.src.server.Product;
import com.example.whirly.src.server.ProductFactory;
import com.example.whirly.src.server.ProductSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintServiceTest {

    Client client = new Client(false, LocalDate.of(2009,06,10), BigDecimal.valueOf(10f), Collections.emptyList());

    List<Client> clients = Arrays.asList(client);
    Client client2 = new Client(true, LocalDate.of(1970, 1, 20), BigDecimal.valueOf(50000f), clients);

    ProductSpecification spec = new ProductSpecification("NOT('VIP_ONLY')", BigDecimal.valueOf(500.0f), 20f);
    Product product = ProductFactory.create(spec);

    ConstraintsService constraintsService = new ConstraintsService();

    public ConstraintServiceTest() throws Exception {
    }

    @DisplayName("Is rich people")
    @Test
    void isRichPeopleTrueTest() {
        String isRichPeople;
        //when
        isRichPeople = constraintsService.isRichPeople(BigDecimal.valueOf(50000f));
        //then
        assertThat(isRichPeople).isEqualTo("true");
    }

    @DisplayName("Is not rich people")
    @Test
    void isRichPeopleFalseTest() {
        String isRichPeople;
        //when
        isRichPeople = constraintsService.isRichPeople(BigDecimal.valueOf(50f));
        //then
        assertThat(isRichPeople).isEqualTo("false");
    }

    @DisplayName("Client with relations")
    @Test
    void withRelationsTrueTest() {
        String withRelations;
        //when
        withRelations = constraintsService.withRelations(client2.getRelations());
        //then
        assertThat(withRelations).isEqualTo("true");
    }

    @DisplayName("A lonely man without friends and prospects :(")
    @Test
    void withRelationsFalseTest() {
        String withRelations;
        //when
        withRelations = constraintsService.withRelations(client.getRelations());
        //then
        assertThat(withRelations).isEqualTo("false");
    }

    @DisplayName("Vip")
    @Test
    void isVipTrueTest() {
        String isVip;
        //when
        isVip = constraintsService.isVip(client.isVip());
        //then
        assertThat(isVip).isEqualTo("false");
    }

    @DisplayName("Not vip")
    @Test
    void isVipFalseTest() {
        String isVip;
        //when
        isVip = constraintsService.isVip(client2.isVip());
        //then
        assertThat(isVip).isEqualTo("true");
    }

    @DisplayName("Adult")
    @Test
    void isAdultTrueTest() {
        String isAdult;
        //when
        isAdult = constraintsService.isAdult(client.getBirthDate());
        //then
        assertThat(isAdult).isEqualTo("false");
    }

    @DisplayName("Not adult")
    @Test
    void isAdultFalseTest() {
        String isAdult;
        //when
        isAdult = constraintsService.isAdult(client2.getBirthDate());
        //then
        assertThat(isAdult).isEqualTo("true");
    }

    @DisplayName("Rich")
    @Test
    void sufficientBalanceTrueTest() {
        String sufficientBalance;
        //when
        sufficientBalance = constraintsService.sufficientBalance(product, client.getAccountBalance());
        //then
        assertThat(sufficientBalance).isEqualTo("false");
    }

    @DisplayName("Poor")
    @Test
    void sufficientBalanceFalseTest() {
        String sufficientBalance;
        //when
        sufficientBalance = constraintsService.sufficientBalance(product, client2.getAccountBalance());
        //then
        assertThat(sufficientBalance).isEqualTo("true");
    }
}
