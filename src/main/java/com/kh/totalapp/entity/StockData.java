package com.kh.totalapp.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Data
@Document(indexName = "stock_data")
public class StockData {

    @Id
    private String id;

    @Field(name = "stock_code", type = FieldType.Keyword)
    private String stockCode;

    @Field(name = "company_name", type = FieldType.Text)
    private String companyName;

    @Field(name = "price", type = FieldType.Double)
    private Double price;

    @Field(name = "volume", type = FieldType.Long)
    private Long volume;

    @Field(name = "date", type = FieldType.Date)
    private String date;

    // 다른 필드들을 추가할 수 있습니다.
}
