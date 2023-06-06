package com.aquispe.apprickandmorty.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aquispe.apprickandmorty.R


val CaveatFont = FontFamily(
    Font(R.font.caveat_bold, FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.caveat_medium, FontWeight.W500, style = FontStyle.Normal),
    Font(R.font.caveat_regular, FontWeight.W400, style = FontStyle.Normal),
    Font(R.font.caveat_semibold, FontWeight.SemiBold, style = FontStyle.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    defaultFontFamily = CaveatFont
)
