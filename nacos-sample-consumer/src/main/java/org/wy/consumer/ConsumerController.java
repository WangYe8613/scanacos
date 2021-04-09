package org.wy.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ProviderFeign providerFeign;

    @GetMapping("/openfeign/hi")
    public String sayHello(@RequestParam(name = "name") String name) {
        return providerFeign.sayHello(name);
    }

    @GetMapping("/openfeign/testValue")
    public String getValueFromNacosConfigServer() {
        return providerFeign.getValueFromNacosConfigServer();
    }
}
