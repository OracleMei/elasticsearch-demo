package org.oraclemei.demo01;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @description:
 * @author: meiyc
 * @createDate: 2021/2/25 20:52
 * @version: 1.0
 */
public class DocTest01 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9202, "http")
        ));
        //构建一个 IndexRequest 请求，参数就是索引名称
        IndexRequest request = new IndexRequest("book12");
        //给请求配置一个 id，这个就是文档 id。如果指定了 id，相当于 put book/_doc/id ，也可以不指定 id，相当于 post book/_doc
//        request.id("1");
        //构建索引文本，有三种方式：JSON 字符串、Map 对象、XContentBuilder
        request.source("{\"name\": \"三国演义2\",\"author\": \"罗贯中2\"}", XContentType.JSON);
        //执行请求，有同步和异步两种方式
        //同步
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //获取文档id
        String id = indexResponse.getId();
        System.out.println("id = " + id);
        //获取索引名称
        String index = indexResponse.getIndex();
        System.out.println("index = " + index);
        //判断文档是否添加成功
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("文档添加成功");
        }
        //判断文档是否更新成功（如果 id 已经存在）
        if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("文档更新成功");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        //判断分片操作是否都成功
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("有存在问题的分片");
        }
        //有存在失败的分片
        if (shardInfo.getFailed() > 0) {
            //打印错误信息
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                System.out.println("failure.reason() = " + failure.reason());
            }
        }

        //异步
//        client.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
//            @Override
//            public void onResponse(IndexResponse indexResponse) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        });
        client.close();
    }
}
