package com.example.whirly.src.client;

import com.example.whirly.src.server.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class Client {

    boolean vip;
    LocalDate birthDate;
    BigDecimal accountBalance;
    List<Client> relations;
    ConstraintsService constraintsService;
    OperatorsService operatorsService;

    public Client(boolean vip, LocalDate birthDate, BigDecimal accountBalance, List<Client> relations) {
        this.vip = vip;
        this.birthDate = birthDate;
        this.accountBalance = accountBalance;
        this.relations = relations;
        this.constraintsService = new ConstraintsService();
        this.operatorsService = new OperatorsService();
    }

    public Answer canPurchase(Product product) {

        List<String> list = product.getConstraints();
        List<String> sublist;
        List<Integer> constraints = new ArrayList<>();
        List<Integer> operators = new ArrayList<>();
        Answer answer = new Answer();

        operatorsService.prepareLists(list, constraints, operators);

        constraintsService.checkConstraint(list, product, new Client(vip, birthDate, accountBalance, relations));

        sublist = list.stream().filter(f -> "true".equals(f) || "false".equals(f)).collect(Collectors.toList());
        sublist = operatorsService.checkOperator(sublist, list, operators, 0);

        answer.access = Boolean.parseBoolean(sublist.get(0));

        return answer;
    }
}
