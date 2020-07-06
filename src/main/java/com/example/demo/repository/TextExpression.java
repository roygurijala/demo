package com.example.demo.repository;

import com.example.demo.repository.visitor.QueryExpressionVisitor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class TextExpression extends QueryExpression {
    @JsonProperty
    private final String text;
    @JsonProperty
    private final TextSearch search;

    public TextExpression(TextSearch search, String text) {
        this.text = text;
        this.search = search;
    }

    @Override
    public void visit(QueryExpressionVisitor visitor) {
        visitor.visitTextExpression(this);
    }

    public String getText() {
        return text;
    }

    public TextSearch getSearch() {
        return search;
    }

    @Override
    public String toString() {
        return "TextExpression{" +
                       "text='" + text + '\'' +
                       ", search=" + search +
                       '}';
    }

    public enum TextSearch {
       STARTS_WITH,ENDS_WITH,EQUALS,CONTAINS;

        @JsonValue
        public String getJsonValue() {
            return this.toString().toLowerCase().replace('_','-');
        }

    }
}
