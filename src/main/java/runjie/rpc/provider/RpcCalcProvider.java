package runjie.rpc.provider;

import runjie.rpc.api.IRpcCalc;

/**
 * @author runjie
 * @ClassName RpcCalcProvider
 * @Description TODO
 * @date 2021/9/20 21:54
 * @Version 1.0
 */
public class RpcCalcProvider implements IRpcCalc {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

    @Override
    public int mul(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }
}