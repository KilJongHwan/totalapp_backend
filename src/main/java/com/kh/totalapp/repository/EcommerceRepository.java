package com.kh.totalapp.repository;

import com.kh.totalapp.entity.EcommerceData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EcommerceRepository extends ElasticsearchRepository<EcommerceData, String> {

}