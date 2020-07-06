package com.example.demo.repository.visitor;

import com.example.demo.repository.ListCombiningExpression;
import com.example.demo.repository.QueryExpression;
import com.example.demo.repository.RangeExpression;
import com.example.demo.repository.TextExpression;
import org.springframework.data.solr.core.query.Criteria;

public class SolrQueryExpressionVisitor implements QueryExpressionVisitor {
    private Criteria criteria;

    public Criteria getCriteria() {
        return this.criteria;
    }

    @Override
    public void visitListCombiningExpression(ListCombiningExpression list) {
        ListCombiningExpression.Combination token = list.getCombiningToken();
        Criteria criteria = null;
        for (QueryExpression expression : list.getExpressions()) {
            if (criteria == null) {
                expression.visit(this);
                criteria = this.criteria;
                continue;

            }

            switch (token) {
                case OR:
                    expression.visit(this);
                    criteria = criteria.connect().or(this.criteria);
                    break;
                case AND:
                default:
                    expression.visit(this);
                    criteria = criteria.connect().and(this.criteria);
                    break;
            }
        }

        if (criteria == null) {
            criteria = new Criteria();
        }

        this.criteria = criteria;
    }

    @Override
    public <T> void visitRangeExpression(RangeExpression<T> expression) {
        Criteria criteria = new Criteria(expression.getField());
        T min = expression.getMin();
        T max = expression.getMax();
        boolean inclusiveLower = expression.isInclusiveLower();
        boolean inclusiveUpper = expression.isInclusiveUpper();
        if (min != null || max != null) {
            criteria = criteria.between(min, max, inclusiveLower, inclusiveUpper);
        }

        this.criteria = criteria;
    }

    @Override
    public void visitTextExpression(TextExpression expression) {
        this.criteria = new Criteria(expression.getField());
        this.criteria = switch (expression.getSearch()) {
            case STARTS_WITH -> this.criteria.startsWith(expression.getText());
            case ENDS_WITH -> this.criteria.endsWith(expression.getText());
            case EQUALS -> this.criteria.is(expression.getText());
            case CONTAINS -> this.criteria.contains(expression.getText());
        };
    }
}
