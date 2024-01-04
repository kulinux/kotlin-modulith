package ecommerce

import ecommerce.payment.PaymentCreated
import ecommerce.payment.Product
import ecommerce.payment.ProductType
import ecommerce.shipping.ShippingManagement
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.EnableScenarios
import org.springframework.modulith.test.Scenario

@SpringBootTest
@EnableScenarios
class AcceptanceTests {

    @Autowired
    private lateinit var shipping: ShippingManagement

    @Test
    fun `If the payment is for a physical product, generate a packing slip for shipping`(scenario: Scenario) {
        scenario.publish(PaymentCreated(Product(ProductType.PHYSICAL)))
            .andWaitForStateChange { shipping.count() }
            .andVerify { shippingCount -> shippingCount > 0 }
    }
}