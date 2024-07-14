package com.vico.titungan.model

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.Parcelize

@Parcelize
data class TitunganCell(
    val x: Int,
    val y: Int,
    var number: Int,
    var owner: Player? = null,
    var isActive: Boolean = false,
    var isClosed: Boolean = false
) : Parcelable
