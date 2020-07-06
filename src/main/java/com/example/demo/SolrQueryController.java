package com.example.demo;

import com.example.demo.repository.ListCombiningExpression;
import com.example.demo.repository.QueryExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolrQueryController {

    @GetMapping("/query")
    public QueryExpression getExpression() {
        return new ListCombiningExpression(ListCombiningExpression.Combination.AND, null);
    }

    @GetMapping("/post")
    public String getExpression(@RequestBody QueryExpression expression) {
        return expression.toString();
    }
}
