package ecommerce.order

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class OrderManagement(val events: ApplicationEventPublisher) {

    @EventListener
    fun on(event: OrderSubmitted) {
        events.publishEvent(OrderPlaced(event))
    }


}