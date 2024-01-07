package ecommerce.membership.internal

import ecommerce.payment.Product
import org.springframework.stereotype.Service

@Service
class MembershipCreate(val emailSender: EmailSender) {
    fun create(product: Product) {
        emailSender.send()
    }

}
