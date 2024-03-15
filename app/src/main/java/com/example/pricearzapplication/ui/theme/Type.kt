package com.example.pricearzapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.example.pricearzapplication.R

val font_medium = FontFamily(
    Font(R.font.iranyekanmedium)
)
val font_bold = FontFamily(
    Font(R.font.iranyekanbold)
)
val font_standard = FontFamily(
    Font(R.font.iranyekan)
)
val h1 = TextStyle(
    fontFamily = font_bold,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 25.sp,
    textDirection = TextDirection.Ltr
)

val h2 =TextStyle(
    fontFamily = font_standard,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 25.sp,
    textDirection = TextDirection.Ltr

)
val h3 =TextStyle(
    fontFamily = font_medium,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    textDirection = TextDirection.Ltr
)

// Set of Material typography styles to start with
//
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = font_medium,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = font_bold,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = font_standard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 25.sp
    )
)