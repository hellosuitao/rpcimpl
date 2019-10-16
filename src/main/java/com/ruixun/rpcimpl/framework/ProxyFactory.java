package com.ruixun.rpcimpl.framework;

import com.ruixun.rpcimpl.protocol.http.HttpClint;
import com.ruixun.rpcimpl.provider.api.HelloService;
import com.ruixun.rpcimpl.register.RemoteMapRegister;
import com.ruixun.rpcimpl.register.ZookeeperRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {

    public static <T> T getProxy(final Class interfaceClass) {//接口名
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader()
                , new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //method 代表我现在代理的是哪个方法
                HttpClint httpClint = new HttpClint();
                Invocation invocation = new Invocation(
                        interfaceClass.getName()/*代理的接口的名字*/
                        ,method.getName()/*代理的方法的名字*/
                        ,method.getParameterTypes()/*方法的参数类型*/
                        ,args)/*方法的参数值*/;

                        /*服务消费者要使用下面的服务（接口），但服务（接口）的地址应该从哪里来
                        * 应该从远程注册中心里面取*/
//                List<URL> urls = RemoteMapRegister.get(interfaceClass.getName());//根据接口名，会拿到各个服务器上面提供的接口
                List<URL> urls = ZookeeperRegister.get(interfaceClass.getName());//根据接口名，会拿到各个服务器上面提供的接口
                /*负载均衡，随机或者轮询*/
                URL randomUrl = LoadBalance.random(urls);/*该负载均衡可以去dubbo里面配置*/
                String result = httpClint.send(randomUrl.getHostName(), randomUrl.getPort(), invocation);
                return result;
            }
        });
    }
}
