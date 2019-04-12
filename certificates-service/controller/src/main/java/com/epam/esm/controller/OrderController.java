package com.epam.esm.controller;

import com.epam.esm.service.create.CreateOrderService;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.find.FindOrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @NonNull
    private final FindOrderService searchService;
    @NonNull
    private final CreateOrderService createService;

    @GetMapping
    public List<OrderDto> ordersByUser(@Positive @RequestParam(required = false, defaultValue = "1") Integer page,
                                       @Positive @RequestParam(required = false, defaultValue = "5") Integer limit,
                                        Authentication authentication){
        return searchService.userOrders(page, limit, authentication.getName());
    }

    @GetMapping("/{id}")
    public OrderDto findById(@Positive @PathVariable("id") Long id){
        return searchService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> buyCertificates(@RequestBody List<Long> id, Authentication authentication){
        OrderDto created = createService.orderCertificatesByUser(authentication.getName(), id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

}
