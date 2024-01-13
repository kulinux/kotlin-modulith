package ecommerce.payment

import ecommerce.order.Source
import org.springframework.context.ApplicationEvent

data class CreatePayment(val product: Product, val source: Source)
