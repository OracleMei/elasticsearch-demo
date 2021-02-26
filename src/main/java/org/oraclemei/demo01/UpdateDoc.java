package org.oraclemei.demo01;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @description:
 * @author: meiyc
 * @createDate: 2021/2/25 21:24
 * @version: 1.0
 */
public class UpdateDoc {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9202, "http")
        ));
        UpdateRequest request = new UpdateRequest("book", "B-FC2XcBbtlao2Eh9f-e");
        request.doc("{\"name\": \"三国演义update\"}", XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("response.getId() = " + response.getId());
        System.out.println("response.getIndex() = " + response.getIndex());
        System.out.println("response.getVersion() = " + response.getVersion());
        if (response.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("更新成功!");
        }
        client.close();
    }
}
