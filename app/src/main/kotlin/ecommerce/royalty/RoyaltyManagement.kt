package ecommerce.inventory

import ecommerce.packingslip.PackingSlipCreated
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class RoyaltyManagement {
    private var count: Int? = null

    @EventListener
    fun on(event: PackingSlipCreated) {
        count = 1
    }

    fun count(): Int? = count
    fun reset() {
        count = null
    }
}