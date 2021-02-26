package org.oraclemei.demo01;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @description:同步请求
 * @author: meiyc
 * @createDate: 2021/2/25 20:30
 * @version: 1.0
 */
public class HighLevelTest2 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9202, "http")
        ));
        //删除已经存在的索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("blog");
        client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        //创建一个索引
        CreateIndexRequest blog1 = new CreateIndexRequest("blog");
        //直接同构 JSON 配置索引
        blog1.source("{\"settings\": {\"number_of_shards\": 4,\"number_of_replicas\": 2},\"mappings\": {\"properties\": {\"title\": {\"type\": \"keyword\"}}},\"aliases\": {\"blog_alias_javaboy\": {}}}", XContentType.JSON);
        //请求超时时间，连接所有节点的超时时间
        blog1.setTimeout(TimeValue.timeValueMinutes(2));
        //连接 master 节点的超时时间
        blog1.setMasterTimeout(TimeValue.timeValueMinutes(1));
        //执行请求，创建索引
        client.indices().create(blog1, RequestOptions.DEFAULT);
        //关闭 client
        client.close();
    }
}
