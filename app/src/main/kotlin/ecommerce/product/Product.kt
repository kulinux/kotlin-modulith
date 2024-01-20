package ecommerce.product


enum class ProductType {
    PHYSICAL,
    MEMBERSHIP
}

enum class ProductSubtype {
    BOOK,
    UPGRADE
}
class Product(val type: ProductType, val subtype: ProductSubtype? = null)