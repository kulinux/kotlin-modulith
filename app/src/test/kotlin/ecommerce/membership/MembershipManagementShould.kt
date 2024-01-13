package ecommerce.membership

import com.ninjasquad.springmockk.MockkBean
import ecommerce.membership.internal.MembershipCreate
import ecommerce.payment.PaymentCreated
import ecommerce.product.Product
import ecommerce.product.ProductSubtype
import ecommerce.product.ProductType
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

    @MockkBean(relaxed = true)
    private lateinit var membershipCreate: MembershipCreate


    @Test
    fun `If the payment is an upgrade to a membership, apply the upgrade`() {
        val product = Product(ProductType.MEMBERSHIP, ProductSubtype.UPGRADE)
        val paymentCreated = PaymentCreated(product)
        event.publishEvent(paymentCreated)

        verify { membershipUpgrade.upgrade(product) }
    }

    @Test
    fun `If the payment is a create to a membership, apply the create`() {
        val product = Product(ProductType.MEMBERSHIP)
        val paymentCreated = PaymentCreated(product)
        event.publishEvent(paymentCreated)

        verify { membershipCreate.create(product) }
    }
}