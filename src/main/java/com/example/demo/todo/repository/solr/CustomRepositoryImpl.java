package com.example.demo.todo.repository.solr;

import com.example.demo.todo.document.Order;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomRepositoryImpl implements CustomRepository {
    @Resource
    private SolrTemplate solrTemplate;

    @Override
    public List search(ArrayList<HashMap> searchTerms) {
        Criteria conditions = createSearchConditions(searchTerms);
        SimpleQuery search = new SimpleQuery(conditions);

        ScoredPage page = solrTemplate.queryForPage("Sample", search, Order.class);
        return page.getContent();
    }

    private Criteria createSearchConditions(ArrayList<HashMap> searchTerms) {
        Criteria conditions = null;
        for(int i = 0; i < searchTerms.size(); i++) {
            HashMap map = searchTerms.get(i);
            String box1Name = (String)map.get("box1Name");
            String criteriaName = (String)map.get("criteriaName");
            String referenceName = (String)map.get("referenceName");
            String startDate = (String)map.get("referenceStartDate");
            String endDate = (String)map.get("referenceEndDate");
            if(conditions == null) {
                if("contains".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).contains(referenceName);
                } else if("equals".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).is(referenceName);
                }else if("starts with".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).startsWith(referenceName);
                } else if("ends with".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).endsWith(referenceName);
                } else if ("greater than".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).greaterThan(startDate);
                } else if ("less than".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).lessThan(startDate);
                } else if ("in between".equalsIgnoreCase(criteriaName)) {
                    conditions = new Criteria(box1Name).between(startDate, endDate);
                }
            } else {
                if("contains".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).contains(referenceName));
                } else if("equals".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).is(referenceName));
                }else if("starts with".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).startsWith(referenceName));
                } else if("ends with".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).endsWith(referenceName));
                } else if("greater than".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).greaterThan(startDate));
                } else if("less than".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).lessThan(startDate));
                } else if("in between".equalsIgnoreCase(criteriaName)) {
                    conditions = conditions.and(new Criteria(box1Name).between(startDate, endDate));
                }
            }
        }
        return conditions;
    }
}
