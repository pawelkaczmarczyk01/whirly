package com.example.whirly.src.client;

import com.example.whirly.src.server.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ConstraintsInterface {

    String isRichPeople(BigDecimal accountBalance);

    String withRelations(List<Client> relations);

    String isVip(boolean vip);

    String isAdult(LocalDate birthDate);

    String sufficientBalance(Product product, BigDecimal accountBalance);

    void checkConstraint(List<String> sublist, Product product, Client client);
}
