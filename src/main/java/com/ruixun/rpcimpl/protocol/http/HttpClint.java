package com.ruixun.rpcimpl.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.ruixun.rpcimpl.framework.Invocation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClint {

    public String send(String hostName, Integer port, Invocation invocation){

        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("http", null, hostName, port, "/", null, null))
                    .POST(HttpRequest.BodyPublishers.ofString(JSONObject.toJSONString(invocation)))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //      http://www.pgt.com/rest/content/list?categoryId=2&page=1&rows=20
       /* String url = "http://www.pgt.com/rest/rpc/content/list";
        Map<String, String> map = new HashMap<>();
        map.put("categoryId","2");
        map.put("page","1");
        map.put("rows","20");
        String result = HttpClientUtil.doGet(url, map);*/
        return null;
    }
}
