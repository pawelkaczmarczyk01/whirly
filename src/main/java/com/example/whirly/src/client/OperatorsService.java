package com.example.whirly.src.client;

import com.example.whirly.src.Constraints;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OperatorsService implements OperatorsInterface {

    public List<String> checkOperator(List<String> sublist, List<String> list, List<Integer> operators, int index) {
        for (int i = 0; i < sublist.size(); i++) {
            if (operators.get(index) != - 1) {
                if ("!".equals(list.get(operators.get(index)))) {
                    if (operators.size() - 1 - index == 0 && operators.size() > 1) {
                        sublist.set(operators.size() - 1 - index, String.valueOf(!Boolean.parseBoolean(sublist.get(operators.size() - index))));
                    } else {
                        sublist.set(operators.size() - 1 - index, String.valueOf(!Boolean.parseBoolean(sublist.get(operators.size() - 1 - index))));
                    }
                } else if ("&&".equals(list.get(operators.get(index)))) {
                    sublist.set(operators.size() - 1 - index, String.valueOf(sublist.stream().allMatch(Boolean::valueOf)));
                } else if ("||".equals(list.get(operators.get(index)))) {
                    sublist.set(operators.size() - 1 - index, String.valueOf(sublist.stream().anyMatch(Boolean::valueOf)));
                }
            }
            index++;
        }
        return sublist;
    }

    public void prepareLists(List<String> list, List<Integer> constraints, List<Integer> operators) {
        for (int i = 0; i < list.size(); i++) {
            if (isConstraint(list.get(i))) {
                constraints.add(i);
                if (constraints.size() > operators.size()) {
                    operators.add(-1);
                }
            } else if (isOperator(list.get(i))) {
                operators.add(i);
            }
        }
        Collections.reverse(operators);
    }

    public Boolean isConstraint(String element) {
        return Arrays.stream(Constraints.values()).filter(f -> element.equals(f.name())).count() == 1;
    }

    public Boolean isOperator(String element) {
        return Arrays.asList("!", "&&", "||").contains(element);
    }
}
