package ecommerce.membership.internal

import ecommerce.payment.Product
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MembershipUpdateShould {

    private lateinit var membershipUpgrade: MembershipUpgrade

    private lateinit var emailSender: EmailSender

    @BeforeEach
    fun setUp() {
        emailSender = mockk(relaxed = true)
        membershipUpgrade = MembershipUpgrade(emailSender)
    }

    @Test
    fun `If the payment is for a membership, e-mail the owner and inform them of the activation`() {
        val product = mockk<Product>()
        membershipUpgrade.upgrade(product)
        verify { emailSender.send() }

    }
}