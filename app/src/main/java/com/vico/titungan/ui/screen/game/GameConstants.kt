package com.vico.titungan.ui.screen.game

import com.vico.titungan.model.GameCategoryItem

object GameConstants {
    val gameSizeRange = 3..7
    const val gameDefaultSize = 5
    val listOperators = listOf("+", "-", "x", "/")
    var listNyawa = listOf(3, 4, 5)
    var listWaktu = listOf(15, 30, 45, 60)
    var tiles = listOf("3 x 3", "4 x 4", "5 x 5", "6 x 6", "7 x 7")
    var listGameFirstPlayOption = listOf("Player 1", "Player 2", "Acak")
    val gameCategory: List<GameCategoryItem> = listOf(
        GameCategoryItem(
            title = "Mudah",
            subTitle = listOf("4 x 4", "5"),
            detail = listOf(
                "Kotak 4 x 4",
                "5 Nyawa",
                "4 Operator ( +, -, x, / )",
                "Bilangan kotak 0-100",
                "Waktu 30 detik",
                "Urutan main acak",
                "Kotak tidak tertutup"
            )
        ),
        GameCategoryItem(
            title = "Sedang",
            subTitle = listOf("5 x 5", "4"),
            detail = listOf(
                "Kotak 5 x 5",
                "4 Nyawa",
                "Operator kali(x) dan bagi(/)",
                "Bilangan kotak 0-100",
                "Waktu 20 detik",
                "Urutan main acak",
                "Kotak tidak tertutup"
            )
        ),
        GameCategoryItem(
            title = "Sulit",
            subTitle = listOf("6 x 6", "3"),
            detail = listOf(
                "Kotak 6 x 6",
                "3 Nyawa",
                "Operator kali(x) dan bagi(/)",
                "Bilangan kotak 0-500",
                "Waktu 20 detik",
                "Urutan main acak",
                "Kotak tertutup"
            )
        ),
        GameCategoryItem(
            title = "Kustom",
            subTitle = listOf("Buat sendiri", "game")
        )
    )
}