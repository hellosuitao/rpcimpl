package com.ruixun.rpcimpl.framework;

import java.util.List;
import java.util.Random;

public class LoadBalance {//此类为负载均衡类
    public static URL random(List<URL> urls){//模拟随机负载均衡策略
        Random random = new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);//随机返回某个服务器上的接口（ip+port）
    }
}
