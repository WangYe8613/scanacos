package org.wy.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServiceImpl {

    @GetMapping(value = "/hi")
    public String sayHello(@RequestParam(name = "name") String name) {
        return "Hello World: " + name;
    }
}
