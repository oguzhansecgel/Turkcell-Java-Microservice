package com.turkcell.productservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {
    @GetMapping
    public int getStock(@RequestParam int productId)
    {
        if(productId>3)
            return 0;

        return 5;
    }
    @GetMapping("{id}")
    public int test(@PathVariable int id)
    {
        return id;
    }
}
