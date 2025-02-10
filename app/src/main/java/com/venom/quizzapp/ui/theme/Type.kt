package com.venom.quizzapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.venom.quizzapp.R

val customFont = FontFamily(
    Font(R.font.roboto_serif, FontWeight.Normal)  // Reference should match the filename exactly
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)