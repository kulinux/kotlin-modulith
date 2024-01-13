package ecommerce.order

import ecommerce.payment.Product

enum class Source {
    WEB
}

data class OrderSubmitted(val product: Product, val source: Source)