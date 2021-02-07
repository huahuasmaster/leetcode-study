package com.zyz.study.es;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/2/6 22:48
 */
@Component
@Slf4j
public class OrderApp {

    public static final String INDEX = "jindex";
    public static final String TYPE = "jtype";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建/全量更新
     */
    public void newOrder() {

        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
            contentBuilder.field("buyerName","huahua");
            contentBuilder.field("shopName","hupu");
            contentBuilder.field("itemName","a nice yeezy");
            contentBuilder.endObject();

            IndexRequest indexRequest = new IndexRequest(INDEX)
                    .source(contentBuilder)
                    .id("321")
                    .type(TYPE)
                    .version(2)
                    .versionType(VersionType.EXTERNAL_GTE)
                    .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            IndexResponse response = restHighLevelClient.index(indexRequest);
            log.info(JSONUtil.toJsonStr(response));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 局部更新
     */
    public void updateOrder() {
        UpdateRequest updateRequest = new UpdateRequest()
                .index(INDEX)
                .type(TYPE);
        Map<String, Object> order = new HashMap<>();
        order.put("shopId", 1121L);
        updateRequest.doc(order);
        updateRequest.id("321");
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            log.info(JSONUtil.toJsonStr(updateResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     */
    public void deleteOrder() {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index(INDEX);
        deleteRequest.type(TYPE);
        deleteRequest.id("321");

        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
            log.info(JSONUtil.toJsonStr(deleteResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量创建
     */
    public void bulkNewOrder() {


        BulkRequest bulkRequest = new BulkRequest();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", 123213L);
        map.put("amount", 432432L);
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source(map)
                .id("321")
                .type(TYPE)
                .version(2)
                .versionType(VersionType.EXTERNAL_GTE);
        bulkRequest.add(indexRequest);

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.type(TYPE);
        updateRequest.index(INDEX);
        updateRequest.id("321");
        updateRequest.doc(new HashMap<String, Object>() {{
            put("buyerName", 3214L);
        }});
        updateRequest.version(1L);
        bulkRequest.add(updateRequest);

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index(INDEX);
        deleteRequest.type(TYPE);
        deleteRequest.id("321");


        bulkRequest.add(deleteRequest);
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest);
            log.info(JSONUtil.toJsonStr(bulkResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchOrder() {
        SearchRequest searchRequest = new SearchRequest();// 搜索的元数据，包括index,type,搜索条件等
        searchRequest.indices(INDEX);
        searchRequest.types(TYPE);


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();// 最外层的搜索，包含query, from, size, sort等
        sourceBuilder.from(0);
        sourceBuilder.size(2);
        sourceBuilder.sort("id");
        // 这个是原来的_source，表示要获取什么字段，或者排除什么字段
        sourceBuilder.fetchSource("id", null);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();// "bool": {}
        boolQueryBuilder.should(QueryBuilders.termQuery("buyerName", "huahua"));// 在bool里的should数组里添加
        boolQueryBuilder.should(QueryBuilders.termQuery("shopName", "taobao"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("amount").gte(100));// bool.must
        boolQueryBuilder.filter(QueryBuilders.matchQuery("itemName", "dog"));// bool.filter
        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);
        doSearch(searchRequest);


    }

    public void doSearch(SearchRequest searchRequest) {
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            if (searchResponse.getHits().getTotalHits() > 0 && searchResponse.getHits().getHits().length > 0) {
                for (SearchHit searchHit : searchResponse.getHits()) {
                    log.info(JSONUtil.toJsonStr(searchHit.getSourceAsMap()));
                }
            }
            if (searchResponse.getAggregations() != null) {
                log.info(JSONUtil.toJsonStr(searchResponse.getAggregations().getAsMap()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aggrOrder() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.types(TYPE);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 计算平均价格
        sourceBuilder.aggregation(AggregationBuilders.avg("amount_avg").field("amount"));
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        doSearch(searchRequest);

    }
}
