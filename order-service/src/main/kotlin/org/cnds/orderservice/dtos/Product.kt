package org.cnds.orderservice.dtos

import java.math.BigDecimal

data class Product(
    val id : Int,
    val name: String,
    val price: BigDecimal,

)
