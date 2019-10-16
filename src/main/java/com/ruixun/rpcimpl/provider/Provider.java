package com.ruixun.rpcimpl.provider;

import com.ruixun.rpcimpl.framework.URL;
import com.ruixun.rpcimpl.protocol.http.HttpServer;
import com.ruixun.rpcimpl.provider.api.HelloService;
import com.ruixun.rpcimpl.provider.impl.HelloServiceImpl;
import com.ruixun.rpcimpl.register.ZookeeperRegister;

public class Provider {
    public static void main(String[] args) {
/*1.注册服务分为远程注册和本地注册*/
        //远程注册，要把服务远程注册到注册中心（zookeeper）上去
        URL url = new URL("localhost",Integer.valueOf(System.getProperty("port")));
        /*如果provider和customer启动，这是两个《进程》，当provider启动时，会在自己的进程里面
         * 创建一个RemoteMapRegister对象，然后会往这个对象里面放服务（接口），
         * 但是当customer启动的时候，会调用远程注册中心的get方法，但此时，
         * 远程注册中心（RemoteMapRegister）并没有实例化，此时map里面没有值，所以调用放拿到空服务
         * 会产生空指针异常。
         * 解决这个问题，要用到线程 （redis zookeeper 等中间件解决）*/
//        RemoteMapRegister.register(HelloService.class.getName(),url);

        ZookeeperRegister.register(HelloService.class.getName(),url);

        //本地注册，当远程调用服务，本地要知道调用的服务的（实现类+接口名）
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

/*2.暴露服务（启动tomcat或者nettyserver ）*/
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost",Integer.valueOf(System.getProperty("port")));
    }
}
