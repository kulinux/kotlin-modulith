package ecommerce.membership

import com.ninjasquad.springmockk.MockkBean
import ecommerce.payment.PaymentCreated
import ecommerce.payment.Product
import ecommerce.payment.ProductSubtype
import ecommerce.payment.ProductType
import ecommerce.membership.internal.MembershipUpgrade
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.modulith.test.ApplicationModuleTest


@ApplicationModuleTest
class MembershipManagementShould {

    @Autowired
    private lateinit var event: ApplicationEventPublisher

    @MockkBean(relaxed = true)
    private lateinit var membershipUpgrade: MembershipUpgrade

    @Test
    fun `If the payment is an upgrade to a membership, apply the upgrade`() {
        val product = Product(ProductType.MEMBERSHIP, ProductSubtype.UPGRADE)
        val paymentCreated = PaymentCreated(product)
        event.publishEvent(paymentCreated)

        verify { membershipUpgrade.upgrade(product) }

    }
}