package com.ruixun.rpcimpl.register;

import com.alibaba.fastjson.JSONObject;
import com.ruixun.rpcimpl.framework.URL;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZookeeperRegister {
    static CuratorFramework client;//相当于zkclient
    static {
         client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3,1000));
         client.start();//这样就表示建立
    }

    private static Map<String, List<URL>> REGISTER = new HashMap<>();

    public static  void register(String interfaceName,URL url){//接口和路径保存到zookeeper上面
        /*方法 withMode(CreateMode.EPHEMERAL)
        临时节点随着client连接的消失而消失 */

        /*/dubbo/service/%s/%s   %s/%s父节点（服务名）有很多子节点，子节点是该服务(interfaceName)（接口）对应的地址（ip+port）
         * /dubbo/service/%s      理解为dubbo里面的某一个服务
         * */
        System.out.println("进入zookeeperRegister-----------------");
        try {
            String result = client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(String.format("/rpcimpldubbo/service/%s/%s",interfaceName, JSONObject.toJSONString(url)),null);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<URL> get(String interfaceName){
        List<URL> urls = new ArrayList<>();
        try {
            List<String> result = client.getChildren().forPath("/rpcimpldubbo/service/%s");
            for (String url:result
                 ) {
                urls.add(JSONObject.parseObject(url,URL.class));
            }
//            REGISTER.put(interfaceName,urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }
}
