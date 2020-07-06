package com.example.demo.todo.repository.solr;

import com.example.demo.todo.document.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface OrderRepository extends CustomRepository, SolrCrudRepository<Order, Long> {

    Order findByOrderId(Long orderid);

    @Query("odesc:*?0*")
    Page<Order> findByOrderDescription(String searchTerm, Pageable pageable);

    @Query("orderDescription:*?0* OR orderName:*?0* OR productName:*?0*")
    @Highlight(prefix = "<b>", postfix = "</b>")
    HighlightPage<Order> findByCustomerQuery(String searchTerm, Pageable pageable);

    List<Order> findByOrderNameEndingWith(String name);
    List<Order> findByOrderNameEndingWithAndProductNameStartingWith(String orderName, String productName);
}