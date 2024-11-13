package org.cnds.orderservice.utils

object ValidationMessages {
    const val NOT_NULL = "This field can not be null"
    const val NOT_BLANK = "This field can not be blank"
    const val MIN_VALUE = 1L
    const val MAX_VALUE = 5L
    const val QUANTITY_MSG = "This field should be a min of $MIN_VALUE and max of $MAX_VALUE"

}