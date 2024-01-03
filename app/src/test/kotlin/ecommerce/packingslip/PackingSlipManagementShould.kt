package ecommerce.packingslip

import com.ninjasquad.springmockk.MockkBean
import ecommerce.packingslip.generator.PackingSlipGenerator
import ecommerce.payment.PaymentCreated
import ecommerce.payment.Product
import ecommerce.payment.ProductType
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest
class PackingSlipManagementShould {

    @Autowired
    private lateinit var event: ApplicationEventPublisher

    @MockkBean(relaxed = true)
    private lateinit var generator: PackingSlipGenerator

    @Test
    fun `generate a packing slip for shipping when payment is for a physical product`() {
        val paymentCreated = PaymentCreated(Product(ProductType.PHYSICAL))

        event.publishEvent(paymentCreated)
        verify { generator.generate(paymentCreated) }
    }

    @Test
    fun `publish generate a packing slip for shipping when payment is for a physical product`() {
        TODO("Not implemented")
    }

}