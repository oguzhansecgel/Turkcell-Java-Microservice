package com.turkcell.productservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/v1")
public class ExampleController {
    @GetMapping
    public String hello()
    {
        return "Merhaba";
    }
}
