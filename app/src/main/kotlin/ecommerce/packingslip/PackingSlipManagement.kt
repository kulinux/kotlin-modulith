package ecommerce.packingslip

import ecommerce.packingslip.generator.PackingSlipGenerator
import ecommerce.payment.PaymentCreated
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PackingSlipManagement(val events: ApplicationEventPublisher) {

    @Autowired
    private lateinit var generator: PackingSlipGenerator

    @EventListener
    fun on(event: PaymentCreated) {
        val binary = generator.generate(event)
        events.publishEvent(PackingSlipCreated(binary))
    }
}