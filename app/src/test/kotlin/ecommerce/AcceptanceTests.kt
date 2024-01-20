package ecommerce

import ecommerce.inventory.OutOfStockCommand
import ecommerce.inventory.RoyaltyManagement
import ecommerce.inventory.StockAvailableCommand
import ecommerce.membership.MembershipManagement
import ecommerce.order.OrderSubmitted
import ecommerce.order.Payment
import ecommerce.order.Source
import ecommerce.payment.*
import ecommerce.product.Product
import ecommerce.product.ProductSubtype
import ecommerce.product.ProductType
import ecommerce.shipping.ShippingManagement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.test.EnableScenarios
import org.springframework.modulith.test.Scenario
import kotlin.test.assertFails


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

    @Test
    fun `If we accept an order over the web, then we have to wait for payment to arrive`(scenario: Scenario) {
        //when order is submitted over the web
        scenario.publish(OrderSubmitted(Product(ProductType.MEMBERSHIP), Source.WEB, Payment.TRANSFER))
            .andWaitForEventOfType(PaymentConfirmed::class.java)
            .toArrive()
    }

    @Test
    fun `If we accept an order over the web that its a credit-card order In the case of credit card orders, we can process the order immediately`(
        scenario: Scenario
    ) {
        val ts = System.currentTimeMillis()
        scenario
            .publish(OrderSubmitted(Product(ProductType.MEMBERSHIP), Source.WEB, Payment.CREDIT_CARD))
            .forEventOfType(PaymentConfirmed::class.java)
            .toArriveAndVerify { it -> assertThat(it.timestamp - ts).isLessThan(1000) }
    }

    @Test
    fun `If they are not currently in stock, we have to delay processing credit card orders until the become available again`(
        scenario: Scenario
    ) {
        val product = Product(ProductType.PHYSICAL)
        scenario
            .publish(OutOfStockCommand(product))

        scenario
            .publish(OrderSubmitted(product, Source.WEB, Payment.CREDIT_CARD))
            .forEventOfType(PaymentConfirmed::class.java)
            .toArriveAndVerify { _ -> assertFails{ "We have to delay payment if we have no stock" } }

        val ts = System.currentTimeMillis()
        scenario
            .publish(StockAvailableCommand(product))
            .forEventOfType(PaymentConfirmed::class.java)
            .toArriveAndVerify { it -> assertThat(it.timestamp - ts).isLessThan(1000) }
    }


}