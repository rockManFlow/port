package com.rock.port.util;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 在使用HTTPS的时候，让其可以同时支持http，需要配置这个类
 * Created by caoqingyuan on 2017/11/17.
 */
@Configuration
public class HttpConfiguration {
    @Value("${my.http.port}")
    private int port;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        //允许同时兼并http和HTTPS的两种请求
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        //把http的请求自动转换成https的请求
//        TomcatEmbeddedServletContainerFactory tomcat=new TomcatEmbeddedServletContainerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                //SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                //拦截所有的请求地址
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
        tomcat.addAdditionalTomcatConnectors(createHttpConnector());
        return tomcat;
    }

    @Bean
    public Connector createHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(port);
        connector.setSecure(false);
        //也可以设置使http 重定向到HTTPS，需要其他配置---按照网上的教程来配，
        // 但还是不可以实现http访问自动跳转HTTPS。应该是springboot版本的问题
//        connector.setRedirectPort(8003);
        return connector;
    }
}
