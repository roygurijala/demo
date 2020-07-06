package com.example.demo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactoryBean;

import javax.annotation.Resource;

@Configuration
@EnableSolrRepositories("com.example.demo.todo.repository.solr")
@PropertySource("classpath:application.properties")
@ComponentScan
public class SolrConfig {

    private static final String PROPERTY_NAME_SOLR_SOLR_HOME = "solr.server.url";

    @Resource
    private Environment environment;

    @Bean
    public SolrClient solrClient() {
        System.out.println("Solr path: "+environment.getRequiredProperty(PROPERTY_NAME_SOLR_SOLR_HOME));
        return new HttpSolrClient.Builder(environment.getRequiredProperty(PROPERTY_NAME_SOLR_SOLR_HOME)).build();
    }


    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}