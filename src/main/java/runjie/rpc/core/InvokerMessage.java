package runjie.rpc.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author runjie
 * @ClassName InvokerMessage
 * @Description TODO
 * @date 2021/9/20 21:53
 * @Version 1.0
 */
@Data
public class InvokerMessage implements Serializable {
    private String className; // 服务名称
    private String methodName; // 调用哪个方法
    private Class<?>[] params; // 参数列表
    private Object[] values; // 参数值
}
