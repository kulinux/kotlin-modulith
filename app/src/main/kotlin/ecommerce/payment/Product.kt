package ecommerce.payment


enum class ProductType {
    PHYSICAL,
    MEMBERSHIP
}

enum class ProductSubtype {
    BOOK
}
class Product(val type: ProductType, val subtype: ProductSubtype? = null)
