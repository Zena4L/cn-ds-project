package org.cnds.orderservice.domain

import org.springframework.data.repository.kotlin.CoroutineCrudRepository


interface OrderRepository : CoroutineCrudRepository<Order, Int> {
}