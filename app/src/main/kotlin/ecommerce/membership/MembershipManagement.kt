package ecommerce.membership

import ecommerce.membership.internal.MembershipUpgrade
import ecommerce.payment.PaymentCreated
import ecommerce.payment.ProductSubtype
import ecommerce.payment.ProductType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class MembershipManagement(val membershipUpgrade: MembershipUpgrade) {

    private var count: Int? = null

    @EventListener
    fun on(event: PaymentCreated) {
        count = 1
        if (event.product.type == ProductType.MEMBERSHIP && event.product.subtype == ProductSubtype.UPGRADE) {
            membershipUpgrade.upgrade(event.product)
        }

    }

    fun count(): Int? = count

    fun reset() {
        count = null
    }
}