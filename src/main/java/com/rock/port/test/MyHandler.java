package com.rock.port.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式：在原来的类基础之上新增一些功能
 * 静态代理+动态代理
 * Created by caoqingyuan on 2017/10/27.
 */
public class MyHandler implements InvocationHandler {
    private Object object;
    public MyHandler(Object object){
        this.object=object;
    }
    //代理对象，需要执行的代理方法，方法执行需要的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("new proxy start...");
        System.out.println("start show log");
        //执行old目标方法
        Object result=method.invoke(object,args);//带返回值

        System.out.println("end show log");
        //代理之后的返回值
        return "Proxy OK";
    }

    public static void main(String[] args){
        OldUserImpl oldUser=new OldUserImpl();
        MyHandler handler=new MyHandler(oldUser);
        //被代理类的类加载器，被代理类的接口，调用哪个handler的invoke方法
        UserInter user=(UserInter)Proxy.newProxyInstance(OldUserImpl.class.getClassLoader(),OldUserImpl.class.getInterfaces(),handler);
        String re=user.getInfo();
        System.out.println("re="+re);
    }
}
