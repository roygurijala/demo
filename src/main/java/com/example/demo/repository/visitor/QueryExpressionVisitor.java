package com.example.demo.repository.visitor;

import com.example.demo.repository.ListCombiningExpression;
import com.example.demo.repository.RangeExpression;
import com.example.demo.repository.TextExpression;

public interface QueryExpressionVisitor {
    void visitListCombiningExpression(ListCombiningExpression expression);
    <T> void visitRangeExpression(RangeExpression<T> expression);
    void visitTextExpression(TextExpression expression);
}
