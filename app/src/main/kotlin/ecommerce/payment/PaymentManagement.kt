package ecommerce.payment

import ecommerce.shipping.ShippingManagement
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PaymentManagement(val events: ApplicationEventPublisher) {

    fun create(product: Product) {
        events.publishEvent(PaymentCreated(product))
    }
}