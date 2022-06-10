package com.example.whirly.src.server;

import com.example.whirly.src.client.OperatorsService;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {

    private static OperatorsService operatorsService = new OperatorsService();

    //TODO mby
    //zapewne na split było by lepiej :D mało czasu na sprawdzanie niestety :D

    public static Product create(ProductSpecification productSpecification) throws Exception {
        return Product.builder()
                .net(productSpecification.getNet())
                .tax(productSpecification.getTax())
                .constraints(buildConstraint(productSpecification))
                .build();
    }

    private static List<String> buildConstraint(ProductSpecification productSpecification) throws Exception {
        List<String> constraints = new ArrayList<>();
        String constraint = productSpecification.getConstraint();

        for (int i = 0; i <= constraint.length() - 1; i++) {

            if (checkOperator(i, 3, constraint, "OR(")) {
                constraints.add("||");
                i += 1;
            } else if (checkOperator(i, 4, constraint, "AND(")) {
                constraints.add("&&");
                i += 2;
            } else if (checkOperator(i, 4, constraint, "NOT(")) {
                constraints.add("!");
                i += 2;
            } else if (constraint.substring(i, i + 1).equals("(")) {
                constraints.add("(");
            } else if (constraint.substring(i, i + 1).equals(")")) {
                constraints.add(")");
            } else if (constraint.substring(i, i + 1).equals("'")) {
                String constValue = "";

                for (int j = i + 1; j <= constraint.length() - 1; j++) {
                    if ('\'' != constraint.charAt(j)) {
                        constValue += constraint.charAt(j);
                    } else {
                        i = j;
                        j += constraint.substring(i + 1).length();
                    }
                }
                if (operatorsService.isConstraint(constValue)) {
                    constraints.add(constValue);
                } else {
                    throw new Exception("Unknown constraint value in product specification: " + constraint);
                }
            }
        }
        return constraints;
    }

    private static boolean checkOperator(int index, int indexShift, String constraint, String operator) {
        return index + indexShift <= constraint.length() && constraint.substring(index, index + indexShift).equalsIgnoreCase(operator);
    }
}
