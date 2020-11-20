package com.github.jesse0722.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lijiajun
 * @date 2020/11/09 16:39
 */
@Data
public class Order implements Serializable {

    private String id;

    // 其他业务字段
    private String name;

    private List<Product> products;

    public Order() {
    }

    public Order(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
