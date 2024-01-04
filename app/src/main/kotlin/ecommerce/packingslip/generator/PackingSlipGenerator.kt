package ecommerce.packingslip.generator

import ecommerce.payment.PaymentCreated
import org.springframework.stereotype.Service

@Service
class PackingSlipGenerator {

    fun generate(paymentCreated: PaymentCreated): ByteArray {
        return byteArrayOf()
    }
}