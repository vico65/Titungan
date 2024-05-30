package com.vico.titungan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Operator (
    val tambah: String = "+",
    val kurang: String = "-",
    val kali: String = "x",
    val bagi: String = "/"
) : Parcelable

