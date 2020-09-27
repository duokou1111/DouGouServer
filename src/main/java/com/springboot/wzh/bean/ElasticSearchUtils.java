package com.springboot.wzh.bean;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticSearchUtils implements InitializingBean {
    private static final String INDEX_NAME = "dougou";
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
        boolean isSet = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
        if(!isSet){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
            System.out.println("createIndexResponse:"+createIndexResponse);
        }
    }
    public IndexResponse addDocument(String id,String jsonStr) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.id(id);
        indexRequest.source(jsonStr, XContentType.JSON);
        return restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
    }
    public boolean doucumentIsExist(String id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX_NAME,id);
        return restHighLevelClient.exists(getRequest,RequestOptions.DEFAULT);
    }
    public DeleteResponse deleteDocument(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME,id);
        return restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    }

}
