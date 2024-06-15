package net.pro.comtam.domain.model

data class Address(
    val ward: String = "",
    val street: String = "",
    val houseNumber: String = ""
)

data class User(
    val id: String = "",
    val userName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val avatar: String = "",
    val address: Address = Address()
)