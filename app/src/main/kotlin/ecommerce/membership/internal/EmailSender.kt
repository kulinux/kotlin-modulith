package ecommerce.membership.internal

import org.springframework.stereotype.Service

@Service
class EmailSender {
    fun send() {
        println("An email was sent")
    }

}
