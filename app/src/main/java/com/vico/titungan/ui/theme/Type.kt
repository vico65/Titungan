package com.vico.titungan.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vico.titungan.R

private val FredokaFontFamily = FontFamily(
    Font(R.font.fredoka_light, FontWeight.Thin),
    Font(R.font.fredoka_light, FontWeight.ExtraLight),
    Font(R.font.fredoka_light, FontWeight.Light),
    Font(R.font.fredoka_regular, FontWeight.Normal),
    Font(R.font.fredoka_medium, FontWeight.Medium),
    Font(R.font.fredoka_semi_bold, FontWeight.SemiBold),
    Font(R.font.fredoka_bold, FontWeight.Bold),
    Font(R.font.fredoka_bold, FontWeight.ExtraBold),
    Font(R.font.fredoka_bold, FontWeight.Black),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FredokaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)