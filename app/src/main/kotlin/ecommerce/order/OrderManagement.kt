package ecommerce.order

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OrderManagement(val events: ApplicationEventPublisher) {
    companion object {
        init {
            println("Order Management on the system!!")
        }
    }


}