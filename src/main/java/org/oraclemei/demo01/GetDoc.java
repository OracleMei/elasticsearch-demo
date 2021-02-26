package org.oraclemei.demo01;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @description:
 * @author: meiyc
 * @createDate: 2021/2/25 21:16
 * @version: 1.0
 */
public class GetDoc {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9202, "http")
        ));
        GetRequest request = new GetRequest("book", "B-FC2XcBbtlao2Eh9f-e");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("response.getId() = " + response.getId());
        System.out.println("response.getIndex() = " + response.getIndex());
        if (response.isExists()) {
            //如果文档存在
            long version = response.getVersion();
            System.out.println("version = " + version);
            String sourceAsString = response.getSourceAsString();
            System.out.println("sourceAsString = " + sourceAsString);
        }else{
            System.out.println("文档不存在");
        }
        client.close();
    }
}
