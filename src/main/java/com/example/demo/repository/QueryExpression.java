package com.example.demo.repository;

import com.example.demo.repository.visitor.QueryExpressionVisitor;
import com.fasterxml.jackson.annotation.*;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({
                      @JsonSubTypes.Type(value = ListCombiningExpression.class, name = "combination"),
                      @JsonSubTypes.Type(value = TextExpression.class, name = "text"),
                      @JsonSubTypes.Type(value = DateRangeExpression.class, name = "date"),
                      @JsonSubTypes.Type(value = IntegerRangeExpression.class, name = "integer")
              })
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class QueryExpression {
    @JsonProperty
    private String field;

    @JsonIgnore
    public String getField() {
        return this.field;
    }

    public abstract void visit(QueryExpressionVisitor visitor);
}
