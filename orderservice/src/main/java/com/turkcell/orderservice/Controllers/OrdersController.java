package com.turkcell.orderservice.Controllers;

import com.turkcell.orderservice.clients.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.turkcell.common.events.OrderCreatedEvent;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

   private final ProductServiceClient productServiceClient;
   private final KafkaTemplate<String,Object> kafkaTemplate;
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

        //kafka mesaj gönder
        // default topic dışında bir topic kullanılması istendiğinde kullanılabilir
        kafkaTemplate.send("orderTopic","NewOrder",new OrderCreatedEvent(1, LocalDateTime.now().minusDays(3)));

        return "Sipariş Eklendi";
    }
    @GetMapping
    public String getMapping()
    {
        return "Merhaba ben gateway üzerinden geliyorum";
    }



}
