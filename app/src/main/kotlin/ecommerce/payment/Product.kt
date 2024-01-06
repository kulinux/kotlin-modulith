package ecommerce.payment


enum class ProductType {
    PHYSICAL
}

enum class ProductSubtype {
    BOOK
}
class Product(type: ProductType, subtype: ProductSubtype? = null)
