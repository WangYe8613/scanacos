package org.wy.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloServiceImpl {

    // @Value可以从Nacos配置中心的配置文件中读取对应的配置变量值
    // ${}中的key要与配置文件中的key一致，否则无法读取
    @Value("${testValue}")
    private String testValue;

    @GetMapping(value = "/hi")
    public String sayHello(@RequestParam(name = "name") String name) {
        return "Hello World: " + name;
    }

    // 新增一个接口，用于验证"@Value的功能"
    @GetMapping(value = "/testValue")
    public String getValueFromNacosConfigServer() {
        return "testValue is: " + testValue;
    }
}
