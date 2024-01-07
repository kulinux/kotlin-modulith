package ecommerce.membership.internal

import ecommerce.payment.Product
import org.springframework.stereotype.Service


@Service
class MembershipUpgrade(val emailSender: EmailSender) {
    fun upgrade(product: Product) {
        emailSender.send()
    }
}
