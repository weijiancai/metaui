package com.metaui.core.webservice;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ServiceTest {
    public static void main(String[] args) {
        HelloService hello = new HelloServiceService().getHelloServicePort();
        String name = hello.getValue("张三");
        System.out.println(name);
    }
}
