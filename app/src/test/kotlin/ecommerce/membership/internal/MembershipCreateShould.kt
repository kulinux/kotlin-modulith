package ecommerce.membership.internal

import ecommerce.product.Product
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MembershipCreateShould {

    private lateinit var membershipCreate: MembershipCreate

    private lateinit var emailSender: EmailSender

    @BeforeEach
    fun setUp() {
        emailSender = mockk(relaxed = true)
        membershipCreate = MembershipCreate(emailSender)
    }

    @Test
    fun `If the payment is for a membership, e-mail the owner and inform them of the activation`() {
        val product = mockk<Product>()
        membershipCreate.create(product)
        verify { emailSender.send() }

    }
}