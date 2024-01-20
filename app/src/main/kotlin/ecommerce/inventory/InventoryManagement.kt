package ecommerce.inventory

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class InventoryManagement (val events: ApplicationEventPublisher) {


    @EventListener
    fun on(event: OutOfStockCommand) {
    }

    @EventListener
    fun on(event: StockAvailableCommand) {
    }
}