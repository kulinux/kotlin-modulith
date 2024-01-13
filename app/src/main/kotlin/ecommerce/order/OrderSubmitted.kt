package ecommerce.order

import ecommerce.product.Product

data class OrderSubmitted(val product: Product, val source: Source, val payment: Payment)
enum class Payment {
    CREDIT_CARD,
    TRANSFER
}

enum class Source {
    WEB
}