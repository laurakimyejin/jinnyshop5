package com.jinnyshop5.Product.entity;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }

}