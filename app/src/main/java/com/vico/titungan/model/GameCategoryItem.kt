package com.vico.titungan.model

import androidx.compose.runtime.Immutable

@Immutable
data class GameCategoryItem(
    val title: String,
    val subTitle: List<String>,
    val detail: List<String>? = null
)