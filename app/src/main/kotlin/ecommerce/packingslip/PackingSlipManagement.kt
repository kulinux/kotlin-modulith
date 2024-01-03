package ecommerce.packingslip

import ecommerce.packingslip.generator.PackingSlipGenerator
import ecommerce.payment.PaymentCreated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PackingSlipManagement {

    @Autowired
    private lateinit var generator: PackingSlipGenerator

    @EventListener
    fun on(event: PaymentCreated) {
        generator.generate(event)
    }
}