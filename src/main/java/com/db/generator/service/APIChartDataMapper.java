package com.db.generator.service;


import db.com.model.Message;

public interface APIChartDataMapper<T> {
    Message mapToDefaultMessage(T o);
}
