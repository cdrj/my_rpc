package runjie.rpc.provider;

import runjie.rpc.api.IRpcHello;

/**
 * @author runjie
 * @ClassName RpcCalcProvider
 * @Description TODO
 * @date 2021/9/20 21:54
 * @Version 1.0
 */
public class RpcHelloProvider implements IRpcHello {


    @Override
    public String hello(String name) {
        return "hello, " + name + "!";
    }
}
