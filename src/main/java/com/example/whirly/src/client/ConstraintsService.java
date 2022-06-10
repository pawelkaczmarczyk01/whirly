package com.example.whirly.src.client;

import com.example.whirly.src.Constraints;
import com.example.whirly.src.server.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ConstraintsService implements ConstraintsInterface {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private final OperatorsService operatorsService = new OperatorsService();

    public void checkConstraint(List<String> sublist, Product product, Client client) {
        for (int i = 0; i < sublist.size(); i++) {
            if (operatorsService.isConstraint(sublist.get(i))) {
                if (Constraints.VIP_ONLY .name().equals(sublist.get(i))) {
                    sublist.set(i, isVip(client.vip));
                } else if (Constraints.FOR_RICH_PEOPLE .name().equals(sublist.get(i))) {
                    sublist.set(i, isRichPeople(client.accountBalance));
                } else if (Constraints.WITH_RELATIONS .name().equals(sublist.get(i))) {
                    sublist.set(i, withRelations(client.relations));
                } else if (Constraints.ADULTS_ONLY.name().equals(sublist.get(i))) {
                    sublist.set(i, isAdult(client.birthDate));
                } else if (Constraints.SUFFICIENT_BALANCE.name().equals(sublist.get(i))) {
                    sublist.set(i, sufficientBalance(product, client.accountBalance));
                }
            }
        }
    }

    public String isRichPeople(BigDecimal accountBalance) {
        return parseToDouble(accountBalance) >= 4000D ? TRUE : FALSE;
    }

    public String withRelations(List<Client> relations) {
        return relations.size() > 0 ? TRUE : FALSE;
    }

    public String isVip(boolean vip) {
        return String.valueOf(vip);
    }

    public String isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18 ? TRUE : FALSE;
    }

    public String sufficientBalance(Product product, BigDecimal accountBalance) {
        return parseToDouble(accountBalance) >= parseToDouble(product.getNet()) * (1f - (product.getTax()/100f)) ? TRUE : FALSE;
    }

    private Double parseToDouble(BigDecimal value) {
        return (Double.parseDouble(String.valueOf(value)));
    }
}
