package ecommerce.inventory

import ecommerce.product.Product

data class StockAvailableCommand(val product: Product)