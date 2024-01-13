package ecommerce.payment

import ecommerce.order.Source
import ecommerce.product.Product

data class CreatePayment(val product: Product, val source: Source)
