package com.vico.titungan.model

import android.os.Parcelable
import com.vico.titungan.data.PointType
import kotlinx.parcelize.Parcelize


@Parcelize
data class Player(
    val name: String,
    val shape: String? = null,
//    var diceIndex: Int = 0,
//    val type: PlayerType = PlayerType.Human
) : Parcelable