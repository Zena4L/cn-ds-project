package org.cn.product.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.cn.product.utils.ValidationMessages.NOT_BLANK
import org.cn.product.utils.ValidationMessages.NOT_EMPTY
import org.cn.product.utils.ValidationMessages.NOT_NULL
import java.math.BigDecimal

data class ProductRequest(
    @field:NotNull(message = NOT_NULL) @field:NotBlank(message = NOT_BLANK) @field:NotEmpty(message = NOT_EMPTY) val name: String,
    @field:NotNull(message = NOT_NULL) val price: BigDecimal
)

data class ProductUpdated(
    val name: String? = null,
    val price: BigDecimal? = null
)