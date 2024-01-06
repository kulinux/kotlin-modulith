package ecommerce

import ecommerce.inventory.RoyaltyManagement
import ecommerce.payment.PaymentCreated
import ecommerce.payment.Product
import ecommerce.payment.ProductSubtype
import ecommerce.payment.ProductType
import ecommerce.shipping.MembershipManagement
import ecommerce.shipping.ShippingManagement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.test.EnableScenarios
import org.springframework.modulith.test.Scenario


@SpringBootTest
@EnableScenarios
class AcceptanceTests {


    @Autowired
    private lateinit var shipping: ShippingManagement

    @Autowired
    private lateinit var royalty: RoyaltyManagement

    @Autowired
    private lateinit var membership: MembershipManagement

    @BeforeEach
    fun setup() {
        shipping.reset()
        royalty.reset()
        membership.reset()
    }

    @Test
    fun `If the payment is for a physical product, generate a packing slip for shipping`(scenario: Scenario) {
        scenario.publish(PaymentCreated(Product(ProductType.PHYSICAL)))
            .andWaitForStateChange { shipping.count() }

        assertThat(shipping.count()).isEqualTo(1)
    }

    @Test
    fun `If the payment is for a book, create a duplicate packing slip for the royalty department`(scenario: Scenario) {
        scenario.publish(PaymentCreated(Product(ProductType.PHYSICAL, ProductSubtype.BOOK)))
            .andWaitForStateChange(royalty::count)

        assertThat(royalty.count()).isEqualTo(1)
    }

    @Test
    fun `If the payment is for a membership, activate that membership`(scenario: Scenario) {
        scenario.publish(PaymentCreated(Product(ProductType.MEMBERSHIP)))
            .andWaitForStateChange(membership::count)

        assertThat(membership.count()).isEqualTo(1)
    }


    @Test
    fun `If the payment is for a membership, do not generate a packing slip for shipping`(scenario: Scenario) {
        scenario.publish(PaymentCreated(Product(ProductType.MEMBERSHIP)))
            .andWaitForStateChange { membership.count() }

        assertThat(shipping.count()).isNull()
    }

}