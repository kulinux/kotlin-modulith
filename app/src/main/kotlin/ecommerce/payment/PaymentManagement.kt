package ecommerce.payment

import ecommerce.order.OrderPlaced
import ecommerce.order.OrderSubmitted
import ecommerce.order.Payment
import ecommerce.product.Product
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PaymentManagement(val events: ApplicationEventPublisher) {

    @EventListener
    fun on(event: OrderPlaced) {
        if(event.orderSubmitted.payment != Payment.CREDIT_CARD) {
            Thread.sleep(5000)
        }
        events.publishEvent(PaymentConfirmed())
    }

    fun create(product: Product) {
        events.publishEvent(PaymentCreated(product))
    }
}