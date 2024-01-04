package ecommerce.packingslip

import com.ninjasquad.springmockk.MockkBean
import ecommerce.packingslip.generator.PackingSlipGenerator
import ecommerce.payment.PaymentCreated
import ecommerce.payment.Product
import ecommerce.payment.ProductType
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.AssertablePublishedEvents

@ApplicationModuleTest
class PackingSlipManagementShould {

    @Autowired
    private lateinit var event: ApplicationEventPublisher

    @MockkBean(relaxed = true)
    private lateinit var generator: PackingSlipGenerator

    val paymentCreated = PaymentCreated(Product(ProductType.PHYSICAL))

    @Test
    fun `generate a packing slip for shipping when payment is for a physical product`() {
        event.publishEvent(paymentCreated)
        verify { generator.generate(paymentCreated) }
    }

    @Test
    fun `publish generate a packing slip for shipping when payment is for a physical product`(events: AssertablePublishedEvents) {
        val binary = byteArrayOf(1, 2, 3)

        every { generator.generate(paymentCreated) } returns binary

        event.publishEvent(paymentCreated)

        Assertions.assertThat(events)
            .contains(PackingSlipCreated::class.java)
            .matching { it.binary == binary }

    }
}