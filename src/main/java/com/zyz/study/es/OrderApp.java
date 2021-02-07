package com.zyz.study.es;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.VersionType;
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
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 123213L);
        map.put("amount", 432432L);
        IndexRequest indexRequest = new IndexRequest(INDEX);
        indexRequest.source(map);
        indexRequest.id("321");
        indexRequest.type(TYPE);
        indexRequest.version(2);
        indexRequest.versionType(VersionType.EXTERNAL_GTE);
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);


        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info(JSONUtil.toJsonStr(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 局部更新
     */
    public void updateOrder() {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX);
        updateRequest.type(TYPE);
        Map<String, Object> order = new HashMap<>();
        order.put("shopId", 1121L);
        updateRequest.doc(order);
        updateRequest.id("321");
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
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
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info(JSONUtil.toJsonStr(deleteResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量创建
     */
    public void bulkNewOrder() {
        restHighLevelClient.bulkAsync()
    }
}
