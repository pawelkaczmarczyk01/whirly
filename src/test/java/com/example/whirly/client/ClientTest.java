package com.example.whirly.client;

import com.example.whirly.src.client.Answer;
import com.example.whirly.src.client.Client;
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

public class ClientTest {

    Client client = new Client(false, LocalDate.of(2009,06,10), BigDecimal.valueOf(10f), Collections.emptyList());

    List<Client> clients = Arrays.asList(client);
    Client client2 = new Client(true, LocalDate.of(1970, 1, 20), BigDecimal.valueOf(50000f), clients);
    Client client3 = new Client(false, LocalDate.of(1970, 1, 20), BigDecimal.valueOf(50f), clients);
    Client client4 = new Client(true, LocalDate.of(1970, 1, 20), BigDecimal.valueOf(50f), Collections.emptyList());

    @DisplayName("Client2 + AND('VIP_ONLY','FOR_RICH_PEOPLE')")
    @Test
    void canPurchaseTest1() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client2.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }

    @DisplayName("Client + AND('VIP_ONLY','FOR_RICH_PEOPLE')")
    @Test
    void canPurchaseTest1_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client2 + OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))")
    @Test
    void canPurchaseTest2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client2.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }

    @DisplayName("Client3 + OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))")
    @Test
    void canPurchaseTest2_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client3.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client2 + 'ADULTS_ONLY'")
    @Test
    void canPurchaseTest3() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("'ADULTS_ONLY'", BigDecimal.valueOf(5000f), 20f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client2.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }

    @DisplayName("Client + 'ADULTS_ONLY'")
    @Test
    void canPurchaseTest3_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("'ADULTS_ONLY'", BigDecimal.valueOf(5000f), 20f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client2 + OR('VIP_ONLY','FOR_RICH_PEOPLE')")
    @Test
    void canPurchaseTest4() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client2.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }

    @DisplayName("Client + OR('VIP_ONLY','FOR_RICH_PEOPLE')")
    @Test
    void canPurchaseTest4_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client4 + OR('VIP_ONLY','FOR_RICH_PEOPLE')")
    @Test
    void canPurchaseTest4_3() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client4.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }

    @DisplayName("Client + AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))")
    @Test
    void canPurchaseTest5() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client2 + AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))")
    @Test
    void canPurchaseTest5_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client2.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client3 + AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))")
    @Test
    void canPurchaseTest5_3() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client3.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isFalse();
    }

    @DisplayName("Client4 + AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))")
    @Test
    void canPurchaseTest5_4() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY',NOT('WITH_RELATIONS'), NOT('FOR_RICH_PEOPLE'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        //when
        Answer answer = client4.canPurchase(product);
        //then
        assertThat(answer.isAccess()).isTrue();
    }
}
