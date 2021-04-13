package org.wy.provider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wy.provider.blockhandler.BlockHandler;
import org.wy.provider.fallbackhandler.FallbackHandler;

@RestController
@RefreshScope
public class HelloServiceImpl {

    // @Value可以从Nacos配置中心的配置文件中读取对应的配置变量值
    // ${}中的key要与配置文件中的key一致，否则无法读取
    @Value("${testValue}")
    private String testValue;

    // 记录访问次数
    private static int count = 0;

    @GetMapping(value = "/hi")
    // value 是资源名称，要确保与Sentinel服务中设置的一致且唯一，与Spring的AOP中的切点概念很相似
    // blockHandler 是处理BlockException的接口名称，接口默认需要和原方法在同一个类中
    // 如果希望使用其他类的函数，需要指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
    @SentinelResource(value = "sayHello", blockHandlerClass = BlockHandler.class, blockHandler = "blockHandlerForSayHello")
    public String sayHello(@RequestParam(name = "name") String name) {
        return "Hello World: " + name;
    }

    // 新增一个接口，用于验证"@Value的功能"
    @GetMapping(value = "/testValue")
    // value 是资源名称，要确保与Sentinel服务中设置的一致且唯一，与Spring的AOP中的切点概念很相似
    // blockHandler 是处理BlockException的接口名称，接口默认需要和原方法在同一个类中
    // 如果希望使用其他类的函数，需要指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
    @SentinelResource(value = "getValueFromNacosConfigServer", blockHandlerClass = BlockHandler.class, blockHandler = "blockHandlerForGetValueFromNacosConfigServer")
    public String getValueFromNacosConfigServer() {
        return "testValue is: " + testValue;
    }

    // 新增一个接口，用于验证降级规则
    @GetMapping(value = "/degrade")
    // value 是资源名称，要确保与Sentinel服务中设置的一致且唯一，与Spring的AOP中的切点概念很相似
    // fallback 是当前资源访问发生降级时的处理接口，接口默认需要和原方法在同一个类中
    // 如果希望使用其他类的函数，需要指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
    @SentinelResource(value = "testDegradeRule", fallbackClass = FallbackHandler.class, fallback = "fallbakcHandlerForTestDegradeRule")
    public String testDegradeRule() {

        // 保证count为奇数时异常，偶数时不异常
        if ((count++ & 1) == 1) {
            throw new RuntimeException("人为制造异常");
        } else {
            return "降级测试：不发生异常";
        }
    }
}
