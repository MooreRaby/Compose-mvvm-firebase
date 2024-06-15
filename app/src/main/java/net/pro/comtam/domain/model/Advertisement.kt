package net.pro.comtam.domain.model

import androidx.compose.ui.graphics.Color

data class Advertisement(
    val title: String,
    val subTitle: String,
    val color: Color,
    val image: Int
)