package com.springboot.wzh.bean;

import com.alibaba.fastjson.JSONObject;
import com.springboot.wzh.domain.RedisStreamSettings;
import com.springboot.wzh.model.StreamInfoDTO;
import com.springboot.wzh.service.ClassificaionTagService;
import lombok.val;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ElasticSearchUtils implements InitializingBean {
    private static final String INDEX_NAME = "dougou";
    @Autowired
    ClassificaionTagService tagService;
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
    public List<StreamInfoDTO> searchDocument(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title",keyword));
        boolQueryBuilder.should(QueryBuilders.wildcardQuery("username","*"+keyword+"*"));
        searchSourceBuilder.timeout(new TimeValue(3, TimeUnit.SECONDS));
        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("username");
        highlightBuilder.preTags("<span style=\"color:#FF6208\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //**************
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        LinkedList<StreamInfoDTO> linkedList = new LinkedList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            RedisStreamSettings redisStreamSettings = JSONObject.parseObject(hit.getSourceAsString(), RedisStreamSettings.class);
            HighlightField fieldTitle = hit.getHighlightFields().get("title");
            HighlightField fieldUser = hit.getHighlightFields().get("username");
            if(fieldTitle != null) {
               redisStreamSettings.setTitle(fieldTitle.getFragments()[0].string());
            }
            if(fieldUser != null){
                redisStreamSettings.setUsername(fieldUser.getFragments()[0].string());
            }
            StreamInfoDTO streamInfoDTO = new StreamInfoDTO();
            streamInfoDTO.setRedisStreamSettings(redisStreamSettings);
            streamInfoDTO.setHeat(39029);
            streamInfoDTO.setTagName(tagService.getTagNameById(streamInfoDTO.getRedisStreamSettings().getTagId()));
            linkedList.add(streamInfoDTO);
        }
        return linkedList;
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
