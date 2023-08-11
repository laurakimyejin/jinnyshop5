package com.jinnyshop5.product.entity;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }

}