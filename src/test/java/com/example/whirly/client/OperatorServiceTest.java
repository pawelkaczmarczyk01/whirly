package com.example.whirly.client;

import static org.assertj.core.api.Assertions.*;

import com.example.whirly.src.client.OperatorsService;
import com.example.whirly.src.server.Product;
import com.example.whirly.src.server.ProductFactory;
import com.example.whirly.src.server.ProductSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OperatorServiceTest {

    OperatorsService operatorsService = new OperatorsService();

    boolean isConstraint;
    boolean isOperator;

    @DisplayName("Constraint exist")
    @Test
    void isConstraintExistTest() {
        //when
        isConstraint = operatorsService.isConstraint("VIP_ONLY");
        //then
        assertThat(isConstraint).isTrue();
    }

    @DisplayName("Constraint not exist")
    @Test
    void isConstraintNotExistTest() {
        //when
        isConstraint = operatorsService.isConstraint("VIP_ONLY_NOT)");
        //then
        assertThat(isConstraint).isFalse();
    }

    @DisplayName("Operator !")
    @Test
    void isOperatorNotTest() {
        //when
        isOperator = operatorsService.isOperator("!");
        //then
        assertThat(isOperator).isTrue();
    }

    @DisplayName("Operator &&")
    @Test
    void isOperatorAndTest() {
        //when
        isOperator = operatorsService.isOperator("&&");
        //then
        assertThat(isOperator).isTrue();
    }

    @DisplayName("Operator ||")
    @Test
    void isOperatorOrTest() {
        //when
        isOperator = operatorsService.isOperator("||");
        //then
        assertThat(isOperator).isTrue();
    }

    @DisplayName("Undefined operator")
    @Test
    void isOperatorUndefined() {
        //when
        isOperator = operatorsService.isOperator("undefined");
        //then
        assertThat(isOperator).isFalse();
    }

    @DisplayName("Prepare list test 1")
    @Test
    void prepareListsTest_1() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("AND('VIP_ONLY','FOR_RICH_PEOPLE')", BigDecimal.valueOf(50.1f), 23f); //true
        Product product = ProductFactory.create(spec);
        List<Integer> constraints = new ArrayList<>();
        List<Integer> operators = new ArrayList<>();
        //when
        operatorsService.prepareLists(product.getConstraints(), constraints, operators);
        //then
        assertThat(operators.size()).isEqualTo(2);
        assertThat(operators.get(0)).isEqualTo(-1);
        assertThat(operators.get(1)).isEqualTo(0);

        assertThat(constraints.size()).isEqualTo(2);
        assertThat(constraints.get(0)).isEqualTo(2);
        assertThat(constraints.get(1)).isEqualTo(3);
    }

    @DisplayName("Prepare list test 2")
    @Test
    void prepareListsTest_2() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        List<Integer> constraints = new ArrayList<>();
        List<Integer> operators = new ArrayList<>();
        //when
        operatorsService.prepareLists(product.getConstraints(), constraints, operators);
        //then
        assertThat(operators.size()).isEqualTo(3);
        assertThat(operators.get(0)).isEqualTo(5);
        assertThat(operators.get(1)).isEqualTo(3);
        assertThat(operators.get(2)).isEqualTo(0);

        assertThat(constraints.size()).isEqualTo(3);
        assertThat(constraints.get(0)).isEqualTo(2);
        assertThat(constraints.get(1)).isEqualTo(7);
        assertThat(constraints.get(2)).isEqualTo(9);
    }

    @DisplayName("Prepare list test 3")
    @Test
    void prepareListsTest_3() throws Exception {
        //given
        ProductSpecification spec = new ProductSpecification("OR('VIP_ONLY',NOT('ADULTS_ONLY'),OR('WITH_RELATIONS','SUFFICIENT_BALANCE'))", BigDecimal.valueOf(50.1f), 23f);
        Product product = ProductFactory.create(spec);
        List<Integer> constraints = new ArrayList<>();
        List<Integer> operators = new ArrayList<>();
        //when
        operatorsService.prepareLists(product.getConstraints(), constraints, operators);
        //then
        assertThat(operators.size()).isEqualTo(4);
        assertThat(operators.get(0)).isEqualTo(-1);
        assertThat(operators.get(1)).isEqualTo(7);
        assertThat(operators.get(2)).isEqualTo(3);
        assertThat(operators.get(3)).isEqualTo(0);

        assertThat(constraints.size()).isEqualTo(4);
        assertThat(constraints.get(0)).isEqualTo(2);
        assertThat(constraints.get(1)).isEqualTo(5);
        assertThat(constraints.get(2)).isEqualTo(9);
        assertThat(constraints.get(3)).isEqualTo(10);
    }
}
