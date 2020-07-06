package com.example.demo.repository;

import com.example.demo.repository.visitor.QueryExpressionVisitor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public class ListCombiningExpression extends QueryExpression {
    @JsonProperty("expressions")
    private final List<QueryExpression> expressions;
    @JsonProperty("token")
    private final Combination combiningToken;

    public ListCombiningExpression(Combination combiningToken, List<QueryExpression> expressions) {
        this.expressions = expressions;
        this.combiningToken = combiningToken;
    }

    @Override
    public void visit(QueryExpressionVisitor visitor) {
        visitor.visitListCombiningExpression(this);
    }

    @Override
    public String toString() {
        return "ListCombiningExpression{" +
                       "expressions=" + expressions +
                       ", combiningToken=" + combiningToken +
                       '}';
    }

    public List<QueryExpression> getExpressions() {
        return this.expressions;
    }

    public Combination getCombiningToken() {
        return this.combiningToken;
    }

    public enum Combination {
        AND, OR;

        @JsonValue
        public String getJsonValue() {
            return this.toString().toLowerCase();
        }
    }
}
