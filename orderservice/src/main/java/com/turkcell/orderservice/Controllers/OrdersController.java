package com.turkcell.orderservice.Controllers;

import com.turkcell.orderservice.clients.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

   private final ProductServiceClient productServiceClient;

    @PostMapping
    public String addOrder(@RequestParam int productId)
    {
/*
       var result = webClientBuilder
                .build()
                .get()
                .uri("http://localhost:8082/api/v1/products?productId="+productId)
                .retrieve()
                .bodyToMono(Integer.class)
               .block();


        System.out.println("Ürün Servisinden Gelen Cevap"+result);
       if(result<=0)
           throw new RuntimeException("Ürün Stokta Yok");
*/

        int stockResult = productServiceClient.getStockByProductId(productId);

        System.out.println("Ürün Servisinden Gelen Cevap"+stockResult);
        if(stockResult<=0)
            throw new RuntimeException("Ürün Stokta Yok");
        return "Sipariş Eklendi";
    }
    @GetMapping
    public String getMapping()
    {
        return "Merhaba ben gateway üzerinden geliyorum";
    }
}
