package com.example.whirly.src.client;

import java.util.List;

public interface OperatorsInterface {

    Boolean isOperator(String element);

    Boolean isConstraint(String element);

    void prepareLists(List<String> list, List<Integer> constraints, List<Integer> operators);

    List<String> checkOperator(List<String> sublist, List<String> list, List<Integer> operators, int index);

}
