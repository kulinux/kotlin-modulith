package ecommerce.shipping

import ecommerce.payment.PaymentCreated
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class MembershipManagement {

    private var count: Int? = null

    @EventListener
    fun on(event: PaymentCreated) {
        count = 1
    }

    fun count(): Int? = count

    fun reset() {
        count = null
    }
}