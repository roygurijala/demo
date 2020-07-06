package com.example.demo.todo.repository.solr;

import com.example.demo.repository.QueryExpression;
import com.example.demo.repository.visitor.SolrQueryExpressionVisitor;
import com.example.demo.todo.document.Order;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import javax.annotation.Resource;
import java.util.List;

public class ASTRepositoryImpl implements ASTRepository {
    @Resource
    private SolrTemplate template;

    @Override
    public List<Order> getOrders(QueryExpression query) {
        SimpleQuery search = new SimpleQuery();
        SolrQueryExpressionVisitor visitor = new SolrQueryExpressionVisitor();

        if (query != null) {
            query.visit(visitor);
            search.addCriteria(visitor.getCriteria());
        } else {
            search.addCriteria(new Criteria());
        }

        ScoredPage<Order> page = template.queryForPage("Sample", search, Order.class);
        return page.getContent();
    }
}
