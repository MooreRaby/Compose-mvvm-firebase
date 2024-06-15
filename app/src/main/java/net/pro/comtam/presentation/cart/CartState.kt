package net.pro.comtam.presentation.cart

import net.pro.comtam.domain.model.CartItem


data class CartState(
    val list: MutableList<CartItem> = mutableListOf()
)

