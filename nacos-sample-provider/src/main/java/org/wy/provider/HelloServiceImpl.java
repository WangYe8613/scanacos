package org.wy.provider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wy.provider.blockhandler.BlockHandler;

@RestController
@RefreshScope
public class HelloServiceImpl {

    // @Value可以从Nacos配置中心的配置文件中读取对应的配置变量值
    // ${}中的key要与配置文件中的key一致，否则无法读取
    @Value("${testValue}")
    private String testValue;

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
}
