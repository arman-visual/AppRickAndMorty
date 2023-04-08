package com.aquispe.apprickandmorty.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

val backgroundRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(Color(0xFF477FFF), Color(0xFF134A6F)),
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 0.95f)
        )
    }
}