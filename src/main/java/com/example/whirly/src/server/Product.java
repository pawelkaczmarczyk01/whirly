package com.example.whirly.src.server;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Product {

    List<String> constraints;
    BigDecimal net;
    float tax;
}
