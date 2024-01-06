package ecommerce.shipping

import ecommerce.packingslip.PackingSlipCreated
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class ShippingManagement {

    private var count: Int? = null

    @EventListener
    fun on(event: PackingSlipCreated) {
        count = 1
    }

    fun count(): Int? = count
}