package com.example.demo.repository;

import com.example.demo.repository.visitor.QueryExpressionVisitor;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RangeExpression<T> extends QueryExpression {
    @JsonProperty
    private T min;
    @JsonProperty
    private T max;
    @JsonProperty
    private boolean inclusiveLower = true;
    @JsonProperty
    private boolean inclusiveUpper = true;

    @Override
    public void visit(QueryExpressionVisitor visitor) {
        visitor.visitRangeExpression(this);
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public boolean isInclusiveLower() {
        return inclusiveLower;
    }

    public void setInclusiveLower(boolean inclusiveLower) {
        this.inclusiveLower = inclusiveLower;
    }

    public boolean isInclusiveUpper() {
        return inclusiveUpper;
    }

    public void setInclusiveUpper(boolean inclusiveUpper) {
        this.inclusiveUpper = inclusiveUpper;
    }
}
