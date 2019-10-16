package com.ruixun.rpcimpl.customer;

import com.ruixun.rpcimpl.framework.ProxyFactory;
import com.ruixun.rpcimpl.provider.api.HelloService;

public class Customer {
    public static void main(String[] args) {

        HelloService helloService = ProxyFactory.getProxy(HelloService.class);//启用代理，代理HelloService接口
        /*代理对象helloService的sayHello方法执行，实际执行的是代理对象的invoke（调用）方法*/
        for (; ;){
            try {
                String result = helloService.sayHello("代理HelloService------------->");
                System.out.println(result+"-------------->");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
