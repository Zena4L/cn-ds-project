package org.cnds.orderservice.web

import jakarta.validation.Valid
import org.cnds.orderservice.domain.Order
import org.cnds.orderservice.domain.OrderService
import org.cnds.orderservice.dtos.OrderRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderController (private val orderService: OrderService){

    @GetMapping
    fun getAllOrders() = orderService.getAllOrders()

    @PostMapping
    suspend fun submitOrder(@Valid @RequestBody orderRequest: OrderRequest) : Order {
        return orderService.submitOrder(orderRequest)
    }

}