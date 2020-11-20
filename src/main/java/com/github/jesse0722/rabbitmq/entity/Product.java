package com.github.jesse0722.rabbitmq.entity;

import lombok.Data;

/**
 * @author Lijiajun
 * @date 2020/11/12 14:45
 */
@Data
public class Product {
    private String name;
    private int count;

    public Product(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Product() {
    }
}
