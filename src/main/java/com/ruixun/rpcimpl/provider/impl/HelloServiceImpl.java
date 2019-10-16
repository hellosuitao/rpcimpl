package com.ruixun.rpcimpl.provider.impl;

import com.ruixun.rpcimpl.provider.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String userName) {
        return Integer.valueOf(System.getProperty("port"))+"---------------->"+"hello------------>"+userName;
    }
}
