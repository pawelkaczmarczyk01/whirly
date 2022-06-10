package com.example.whirly.src.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProductSpecification {

    String constraint;
    BigDecimal net;
    float tax;
}
