package ecommerce.payment

import ecommerce.order.OrderSubmitted
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PaymentManagement(val events: ApplicationEventPublisher) {

    @EventListener
    fun on(event: OrderSubmitted) {
        Thread.sleep(5000)
        events.publishEvent(PaymentConfirmed())
    }

    fun create(product: Product) {
        events.publishEvent(PaymentCreated(product))
    }
}