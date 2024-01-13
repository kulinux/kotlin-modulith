package ecommerce.payment

import ecommerce.product.Product
import ecommerce.product.ProductType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.AssertablePublishedEvents


@ApplicationModuleTest
class PaymentManagementShould {

    @Autowired
    private val paymentManagement: PaymentManagement? = null



    @Test
    fun generateProductCreatedEventWhenCreated(events: AssertablePublishedEvents) {

        val aProduct = Product(ProductType.PHYSICAL)
        paymentManagement?.create(aProduct)

        assertThat(events)
            .contains(PaymentCreated::class.java)
            .matching { it.product == aProduct }
    }
}