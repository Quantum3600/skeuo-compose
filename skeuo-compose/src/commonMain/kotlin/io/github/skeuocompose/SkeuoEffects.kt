package io.github.skeuomorph

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Modifier.skeuoTexture(
    tint: Color,
    alpha: Float = 0.08f,
    seed: Int = 17,
): Modifier {
    return drawWithCache {
        val random = Random(seed + size.width.toInt() * 31 + size.height.toInt() * 17)
        val grainCount = ((size.width * size.height) / 420f).toInt().coerceIn(50, 460)
        val grains = List(grainCount) {
            Grain(
                x = random.nextFloat() * size.width,
                y = random.nextFloat() * size.height,
                radius = 0.6f + random.nextFloat() * 1.6f,
                opacity = 0.25f + random.nextFloat() * 0.75f,
            )
        }

        onDrawWithContent {
            drawContent()
            grains.forEach { grain ->
                drawCircle(
                    color = tint.copy(alpha = alpha * grain.opacity),
                    radius = grain.radius,
                    center = androidx.compose.ui.geometry.Offset(grain.x, grain.y),
                    blendMode = BlendMode.Multiply,
                )
            }
        }
    }
}

fun Modifier.skeuoLayer(
    shape: Shape,
    palette: SkeuoPalette,
    pressed: Boolean,
    raisedElevation: Dp = 14.dp,
    pressedElevation: Dp = 3.dp,
): Modifier {
    return this
        .shadow(elevation = if (pressed) pressedElevation else raisedElevation, shape = shape, clip = false)
        .clip(shape)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawBevel(pressed = pressed, highlight = palette.highlight, shadow = palette.shadow)
            }
        }
}

private fun DrawScope.drawBevel(
    pressed: Boolean,
    highlight: Color,
    shadow: Color,
) {
    val diagonal = androidx.compose.ui.geometry.Offset(size.width, size.height)
    if (pressed) {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(shadow.copy(alpha = 0.22f), Color.Transparent),
                start = androidx.compose.ui.geometry.Offset.Zero,
                end = diagonal,
            ),
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, highlight.copy(alpha = 0.22f)),
                start = androidx.compose.ui.geometry.Offset.Zero,
                end = diagonal,
            ),
        )
    } else {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(highlight.copy(alpha = 0.28f), Color.Transparent),
                start = androidx.compose.ui.geometry.Offset.Zero,
                end = diagonal,
            ),
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, shadow.copy(alpha = 0.22f)),
                start = androidx.compose.ui.geometry.Offset.Zero,
                end = diagonal,
            ),
        )
    }
}

private data class Grain(
    val x: Float,
    val y: Float,
    val radius: Float,
    val opacity: Float,
)