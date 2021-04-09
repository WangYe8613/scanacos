package org.wy.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nacos-sample-provider")
public interface ProviderFeign {
    @GetMapping("/hi")
    String sayHello(@RequestParam(name = "name") String name);

    @GetMapping("/testValue")
    String getValueFromNacosConfigServer();
}
