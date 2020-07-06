package com.example.demo;

import com.example.demo.model.QueryParam;
import com.example.demo.todo.document.Order;
import com.example.demo.todo.repository.solr.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.convert.DateTimeConverters;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class OrderController {
    @Resource
    OrderRepository solrOrderRepository;

    @RequestMapping("/order")
    public String createOrder() throws ParseException {
        String description = "Order Created";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-2020 10:20:56";
        String endDateInString = "23-10-2020 10:20:56";
        Date startDate = sdf.parse(dateInString);
        Date endDate = sdf.parse(endDateInString);
        //for(int i = 0; i < 50; i++) {
            Order order = new Order();
            order.setOrderId(Long.valueOf(2121));
            order.setOrderName("Smart Phone Order for 2121");
            order.setOrderDescription("This is order description for 2121");
            order.setProductName("Sony smart phone");
            order.setCustomerName("Sam Isent ");
            order.setCustomerMobile("3019798182");
            order.setStartDate(startDate);
            order.setEndDate(endDate);
            solrOrderRepository.save(order);
        //}

        return description;
    }

    @GetMapping("/order/{orderid}")
    public Order readOrder(@PathVariable Long orderid) {
        return solrOrderRepository.findByOrderId(orderid);
    }
    @PutMapping("/order")
    public String updateOrder(@RequestBody Order order) {
        String description = "Order Updated";
        solrOrderRepository.save(order);
        return description;
    }

    @DeleteMapping("/order/{orderid}")
    public String deleteOrder(@PathVariable Long orderid) {
        String description = "Order Deleted";
        solrOrderRepository.delete(solrOrderRepository.findByOrderId(orderid));
        return description;
    }

    @GetMapping("/order/desc/{orderDesc}/{page}")
    public List< Order > findOrder(@PathVariable String orderDesc, @PathVariable int page) {
        return solrOrderRepository.findByOrderDescription(orderDesc, PageRequest.of(page, 10)).getContent();
    }

    @GetMapping("/order/search/{searchTerm}/{page}")
    public List < Order > findOrderBySearchTerm(@PathVariable String searchTerm, @PathVariable int page) {
        return solrOrderRepository.findByCustomerQuery(searchTerm, PageRequest.of(page, 10)).getContent();
    }

    @RequestMapping("/solrQueryParams")
//    @CrossOrigin(origins = "http://localhost:8000")
    public List<Order> createSolrQueryParams(@RequestBody List<QueryParam> params) {
        ArrayList<HashMap> searchTerms = new ArrayList <HashMap>();

        for(QueryParam param : params) {
            HashMap<String, String> map = new HashMap<String, String>();
            if(param.getBox1().getName() != null) {
                map.put("box1Name", param.getBox1().getName());
            }
            if(param.getCriteriaList().getName() != null) {
                map.put("criteriaName", param.getCriteriaList().getName());
            }
            if(param.getReferenceData().getName() != null) {
                map.put("referenceName", param.getReferenceData().getName());
            }
            if(param.getReferenceData().getStartDate() != null) {
                //String startDate = DateTimeConverters.JavaDateConverter.INSTANCE.convert(param.getReferenceData().getStartDate());
                //searchDateTerms.add(startDate);
                map.put("referenceStartDate", param.getReferenceData().getStartDate());
            }
            if(param.getReferenceData().getEndDate() != null) {
                //String endDate = DateTimeConverters.JavaDateConverter.INSTANCE.convert(param.getReferenceData().getEndDate());
                //searchDateTerms.add(endDate);
                map.put("referenceEndDate", param.getReferenceData().getEndDate());
            }
            searchTerms.add(map);
        }
        List<Order> ordList = solrOrderRepository.search(searchTerms);
//        List<Order> ordList = solrOrderRepository.findByOrderNameEndingWithAndProductNameStartingWith(searchTerms.get(0), searchTerms.get(1));
//        List<Order> ordList = solrOrderRepository.findByOrderNameEndingWith(refData);
        return ordList;
    }
}
