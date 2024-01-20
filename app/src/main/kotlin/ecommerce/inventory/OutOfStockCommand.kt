package ecommerce.inventory

import ecommerce.product.Product

data class OutOfStockCommand(val product: Product)