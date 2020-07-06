package com.example.demo.todo.repository.solr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CustomRepository {
    public List search(ArrayList<HashMap> searchTerms);
}
