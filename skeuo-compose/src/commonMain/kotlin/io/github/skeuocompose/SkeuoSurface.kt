package io.github.skeuocompose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
enum class SkeuoBevelKind {
    Classic,
    Keyboard,
    Convex,
    Concave,
    Subtle,
}

@Immutable
enum class SkeuoTextureKind {
    None,
    Grainy,
    Matte,
    Glossy,
    Concentric,
}

@Immutable
data class SkeuoSurfaceStyle(
    val shape: Shape = RoundedCornerShape(20.dp),
    val borderWidth: Dp = 1.dp,
    val gutterWidth: Dp = 0.dp,
    val contentPadding: PaddingValues = PaddingValues(16.dp),
    val raisedElevation: Dp = 14.dp,
    val pressedElevation: Dp = 3.dp,
    val bevelKind: SkeuoBevelKind = SkeuoBevelKind.Classic,
    val textureKind: SkeuoTextureKind = SkeuoTextureKind.Grainy,
    val depth: Dp = 0.dp,
)

@Composable
fun SkeuoSurface(
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.Ivory,
    style: SkeuoSurfaceStyle = SkeuoSurfaceStyle(),
    pressed: Boolean = false,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit,
) {
    val backgroundBrush = remember(palette, pressed, style.bevelKind) { 
        skeuoGradient(palette = palette, pressed = pressed, bevelKind = style.bevelKind) 
    }
    val borderColor = if (pressed || style.bevelKind == SkeuoBevelKind.Concave) {
        palette.border.copy(alpha = 0.55f)
    } else {
        palette.border.copy(alpha = 0.82f)
    }

    Box(
        modifier = modifier
            .skeuoLayer(
                shape = style.shape,
                palette = palette,
                pressed = pressed,
                raisedElevation = style.raisedElevation,
                pressedElevation = style.pressedElevation,
                bevelKind = style.bevelKind,
                depth = style.depth,
            )
            .let {
                if (style.gutterWidth > 0.dp) {
                    it.border(width = style.gutterWidth, color = palette.shadow.copy(alpha = 0.3f), shape = style.shape)
                        .padding(style.gutterWidth)
                } else it
            }
            .background(brush = backgroundBrush, shape = style.shape)
            .border(width = style.borderWidth, color = borderColor, shape = style.shape)
            .skeuoTexture(
                kind = style.textureKind,
                palette = palette,
                alpha = if (pressed) 0.05f else 0.09f,
            )
            .padding(style.contentPadding),
        contentAlignment = contentAlignment,
    ) {
        CompositionLocalProvider(LocalContentColor provides palette.content) {
            content()
        }
    }
}

private fun skeuoGradient(palette: SkeuoPalette, pressed: Boolean, bevelKind: SkeuoBevelKind): Brush {
    if (bevelKind == SkeuoBevelKind.Subtle) {
        return if (pressed) {
            Brush.verticalGradient(
                colors = listOf(palette.midTone, palette.lowTone),
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(palette.midTone, palette.midTone, palette.lowTone),
            )
        }
    }

    if (bevelKind == SkeuoBevelKind.Convex) {
        return Brush.verticalGradient(
            colors = listOf(
                brighten(palette.highlight, 0.12f),
                palette.midTone,
                darken(palette.lowTone, 0.08f),
            ),
        )
    }

    if (bevelKind == SkeuoBevelKind.Concave) {
        return Brush.verticalGradient(
            colors = listOf(
                darken(palette.lowTone, 0.12f),
                palette.midTone,
                brighten(palette.highlight, 0.08f),
            ),
        )
    }

    if (bevelKind == SkeuoBevelKind.Keyboard) {
        return if (pressed) {
            Brush.verticalGradient(
                colors = listOf(palette.lowTone, palette.midTone),
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(palette.midTone, palette.lowTone),
            )
        }
    }

    return if (pressed) {
        Brush.verticalGradient(
            colors = listOf(
                palette.midTone,
                palette.lowTone,
                palette.shadow.copy(alpha = 0.95f),
            ),
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                brighten(palette.highlight, 0.08f),
                palette.midTone,
                darken(palette.lowTone, 0.04f),
                palette.shadow,
            ),
        )
    }
}

private fun brighten(color: Color, amount: Float): Color {
    val a = amount.coerceIn(0f, 1f)
    return Color(
        red = (color.red + (1f - color.red) * a).coerceIn(0f, 1f),
        green = (color.green + (1f - color.green) * a).coerceIn(0f, 1f),
        blue = (color.blue + (1f - color.blue) * a).coerceIn(0f, 1f),
        alpha = color.alpha,
    )
}

private fun darken(color: Color, amount: Float): Color {
    val a = amount.coerceIn(0f, 1f)
    return Color(
        red = (color.red * (1f - a)).coerceIn(0f, 1f),
        green = (color.green * (1f - a)).coerceIn(0f, 1f),
        blue = (color.blue * (1f - a)).coerceIn(0f, 1f),
        alpha = color.alpha,
    )
}
