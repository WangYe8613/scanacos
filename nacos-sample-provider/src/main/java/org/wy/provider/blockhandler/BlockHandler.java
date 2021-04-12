package org.wy.provider.blockhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.wy.provider.HelloServiceImpl;

import java.util.logging.Logger;

/**
 * 定义全局兜底的类
 * 注意，返回类型和参数要与调用者一致，且都需要添加异常类，并且方法的修饰符必须是public static
 */
public class BlockHandler {

    private static Logger logger = Logger.getLogger(HelloServiceImpl.class.toString());

    /**
     * 当sayHello接口访问限流时，就会调用该接口
     * 该函数的传参和返回值类型必须与资源点的传参一样，
     *
     * @param e
     * @return
     */
    public static String blockHandlerForSayHello(String name, BlockException e) {
        if (e instanceof FlowException) {
            FlowRule rule = ((FlowException) e).getRule();
            double qps = rule.getCount();
            logger.warning("接口[/hi]的访问被限流了，要求：QPS <= [" + qps + "]");
            return "接口[/hi]的访问被限流了，要求：QPS <= [" + qps + "]";
        } else {
            return "";
        }
    }

    /**
     * 当getValueFromNacosConfigServer接口访问限流时，就会调用该接口
     *
     * @param e
     * @return
     */
    public static String blockHandlerForGetValueFromNacosConfigServer(BlockException e) {
        if (e instanceof FlowException) {
            FlowRule rule = ((FlowException) e).getRule();
            double qps = rule.getCount();
            logger.warning("接口[/testValue]的访问被限流了，要求：QPS <= [" + qps + "]");
            return "接口[/testValue]的访问被限流了，要求：QPS <= [" + qps + "]";
        } else {
            return "";
        }
    }
}
