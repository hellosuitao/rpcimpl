package com.ruixun.rpcimpl.protocol.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class HttpServer {

    public void start(String hostName,Integer port){
//        if(jetty)jetty:run
//        if(tomcat)tomcat:run
        Tomcat tomcat = new Tomcat();
        /*tomcat 初始化开始*/
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        Host host = new StandardHost();
        host.setName(hostName);

        String contextPath="";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);
        /*初始化结束*/

        /*发起servlet请求*/
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet());// 容器，别名，类
        context.addServletMappingDecoded("/*","dispatcher");//所有请求，交给dispatcher处理

        //启动tomcat
        /*// 创建Tomcat应用对象
        Tomcat tomcat = new Tomcat();
        // 设置Tomcat的端口号
        tomcat.setPort(8080);
        // 是否设置Tomcat自动部署
        tomcat.getHost().setAutoDeploy(false);
        // 创建上下文
        StandardContext standardContext = new StandardContext();
        // 设置项目名
        standardContext.setPath("/sb");
        // 监听上下文
        standardContext.addLifecycleListener(new FixContextListener());
        // 向tomcat容器对象添加上下文配置
        tomcat.getHost().addChild(standardContext);
        // 创建Servlet
        tomcat.addServlet("/sb", "helloword", new HelloServlet());
        // Servlet映射
        standardContext.addServletMappingDecoded("/hello", "helloword");
        //启动tomcat容器
        tomcat.start();
        //等待
        tomcat.getServer().await();*/
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
    public void close(){

    }
}
