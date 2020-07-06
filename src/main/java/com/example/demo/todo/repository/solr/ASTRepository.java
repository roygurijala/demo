package com.example.demo.todo.repository.solr;

import com.example.demo.repository.QueryExpression;
import com.example.demo.todo.document.Order;

import java.util.List;

public interface ASTRepository {
    List<Order> getOrders(QueryExpression query);
}
