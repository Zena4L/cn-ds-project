package org.cnds.orderservice.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.cnds.orderservice.utils.ValidationMessages.MAX_VALUE
import org.cnds.orderservice.utils.ValidationMessages.MIN_VALUE
import org.cnds.orderservice.utils.ValidationMessages.NOT_BLANK
import org.cnds.orderservice.utils.ValidationMessages.NOT_NULL
import org.cnds.orderservice.utils.ValidationMessages.QUANTITY_MSG

data class OrderRequest(
    @field:NotNull(message = NOT_NULL)
    val productId: Int,

    @field:NotNull(message = NOT_NULL)
    @field:NotEmpty(message = NOT_BLANK)
    val productName: String,

    @field:NotNull(message = NOT_NULL)
    @field:Max(value = MIN_VALUE, message = QUANTITY_MSG)
    @field:Min(value = MAX_VALUE, message = QUANTITY_MSG)
    val quantity: Int = 0,

    @field:NotNull(message = NOT_NULL)
    val productPrice: Int,
)
