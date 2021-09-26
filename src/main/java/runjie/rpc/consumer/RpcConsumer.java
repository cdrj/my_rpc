package runjie.rpc.consumer;

import runjie.rpc.api.IRpcCalc;
import runjie.rpc.api.IRpcHello;
import runjie.rpc.consumer.proxy.RpcProxy;

public class RpcConsumer {
    public static void main(String[] args) {
        // 本机之间的正常调用
        // IRpcHello iRpcHello = new RpcHelloProvider();
        // iRpcHello.hello("Tom");

        // 肯定是用动态代理来实现的
        // 传给它接口，返回一个接口的实例，伪代理
        IRpcHello rpcHello = RpcProxy.create(IRpcHello.class);
        System.out.println(rpcHello.hello("ZouChangLin"));

        int a = 10;
        int b = 4;
        IRpcCalc iRpcCalc = RpcProxy.create(IRpcCalc.class);

        System.out.println(String.format("%d + %d = %d", a, b, iRpcCalc.add(a, b)));
        System.out.println(String.format("%d - %d = %d ", a, b, iRpcCalc.sub(a, b)));
        System.out.println(String.format("%d * %d = %d", a, b, iRpcCalc.mul(a, b)));
        System.out.println(String.format("%d / %d = %d", a, b, iRpcCalc.div(a, b)));
    }
}