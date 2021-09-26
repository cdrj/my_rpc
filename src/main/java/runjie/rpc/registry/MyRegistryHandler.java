package runjie.rpc.registry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import runjie.rpc.core.InvokerMessage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyRegistryHandler extends ChannelInboundHandlerAdapter {
    // 在注册中心注册服务需要有容器存放
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();

    // 类名的缓存位置
    private static final List<String> classCache = new ArrayList<>();

    // 约定，只要是写在provider下所有的类都认为是一个可以对完提供服务的实现类
    // edu.xpu.rpc.provider

    public MyRegistryHandler(){
        scanClass("runjie.rpc.provider");
        doRegister();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        // 客户端传过来的调用信息
        InvokerMessage request = (InvokerMessage)msg;
        // 先判断有没有这个服务
        String serverClassName = request.getClassName();
        if(registryMap.containsKey(serverClassName)){
            // 获取服务对象
            Object clazz = registryMap.get(serverClassName);
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParams());
            result = method.invoke(clazz, request.getValues());
            System.out.println("request=" + request);
            System.out.println("result=" + result);
        }
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    // 实现简易IOC容器
    // 扫描出包里面所有的Class
    private void scanClass(String packageName){
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        File[] files = dir.listFiles();
        for (File file: files){
            if(file.isDirectory()){
                scanClass(packageName + "." + file.getName());
            }else{
                // 拿出类名
                String className = packageName + "." + file.getName().replace(".class", "").trim();
                classCache.add(className);
            }
        }
    }

    // 把扫描到的Class实例化，放到Map中
    // 注册的服务名称就叫做接口的名字 [约定优于配置]
    private void doRegister(){
        if(classCache.size() == 0) return;
        for (String className: classCache){
            try {
                Class<?> clazz = Class.forName(className);
                // 服务名称
                Class<?> anInterface = clazz.getInterfaces()[0];
                registryMap.put(anInterface.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}