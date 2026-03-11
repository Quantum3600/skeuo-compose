package io.github.skeuocompose

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.skeuocompose.SkeuoBevelKind
import io.github.skeuocompose.SkeuoPalette
import io.github.skeuocompose.SkeuoTextureKind
import kotlin.math.max
import kotlin.random.Random

fun Modifier.skeuoTexture(
    kind: SkeuoTextureKind,
    palette: SkeuoPalette,
    alpha: Float = 0.08f,
    seed: Int = 17,
): Modifier {
    return drawWithCache {
        val random = Random(seed + size.width.toInt() * 31 + size.height.toInt() * 17)
        
        val grains = if (kind == SkeuoTextureKind.Grainy) {
            val grainCount = ((size.width * size.height) / 420f).toInt().coerceIn(50, 460)
            List(grainCount) {
                Grain(
                    x = random.nextFloat() * size.width,
                    y = random.nextFloat() * size.height,
                    radius = 0.6f + random.nextFloat() * 1.6f,
                    opacity = 0.25f + random.nextFloat() * 0.75f,
                )
            }
        } else emptyList()

        onDrawWithContent {
            drawContent()
            
            when (kind) {
                SkeuoTextureKind.None -> {}
                SkeuoTextureKind.Grainy -> {
                    grains.forEach { grain ->
                        drawCircle(
                            color = palette.textureTint.copy(alpha = alpha * grain.opacity),
                            radius = grain.radius,
                            center = Offset(grain.x, grain.y),
                            blendMode = BlendMode.Multiply,
                        )
                    }
                }
                SkeuoTextureKind.Matte -> {
                    drawRect(
                        brush = Brush.verticalGradient(
                            0.0f to Color.White.copy(alpha = alpha * 0.2f),
                            0.5f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = alpha * 0.2f),
                        ),
                        blendMode = BlendMode.Overlay
                    )
                }
                SkeuoTextureKind.Glossy -> {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height * 0.5f)
                        quadraticBezierTo(size.width * 0.5f, size.height * 0.45f, 0f, size.height * 0.5f)
                        close()
                    }
                    drawPath(
                        path = path,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = alpha * 4f), Color.White.copy(alpha = alpha * 0.5f))
                        )
                    )
                }
                SkeuoTextureKind.Concentric -> {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val maxRadius = max(size.width, size.height)
                    
                    var radius = 1f
                    while (radius < maxRadius) {
                        drawCircle(
                            color = palette.shadow.copy(alpha = alpha * 1.2f),
                            radius = radius,
                            center = center,
                            style = Stroke(width = 0.5f)
                        )
                        radius += 1.5f
                    }
                    
                    drawRect(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = alpha * 5f),
                                Color.Transparent,
                                Color.Black.copy(alpha = alpha * 3f),
                                Color.Transparent,
                                Color.White.copy(alpha = alpha * 5f),
                                Color.Transparent
                            ),
                            center = center
                        ),
                        blendMode = BlendMode.Overlay
                    )
                }
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
    bevelKind: SkeuoBevelKind = SkeuoBevelKind.Classic,
    depth: Dp = 0.dp,
): Modifier {
    val actualElevation = when {
        pressed -> pressedElevation
        bevelKind == SkeuoBevelKind.Concave -> 0.dp
        else -> raisedElevation + depth
    }
    return this
        .shadow(elevation = actualElevation, shape = shape, clip = false)
        .clip(shape)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                when (bevelKind) {
                    SkeuoBevelKind.Classic -> drawClassicBevel(pressed = pressed, highlight = palette.highlight, shadow = palette.shadow)
                    SkeuoBevelKind.Keyboard -> drawKeyboardBevel(pressed = pressed, highlight = palette.highlight, shadow = palette.shadow, midTone = palette.midTone, depth = depth.toPx())
                    SkeuoBevelKind.Convex -> drawFourSidedBevel(inner = false, highlight = palette.highlight, shadow = palette.shadow)
                    SkeuoBevelKind.Concave -> drawFourSidedBevel(inner = true, highlight = palette.highlight, shadow = palette.shadow)
                    SkeuoBevelKind.Subtle -> drawSubtleBevel(pressed = pressed, highlight = palette.highlight, shadow = palette.shadow)
                }
            }
        }
}

private fun DrawScope.drawFourSidedBevel(
    inner: Boolean,
    highlight: Color,
    shadow: Color,
) {
    val thickness = 1.5f
    val lightColor = highlight.copy(alpha = 0.35f)
    val darkColor = shadow.copy(alpha = 0.35f)

    if (inner) {
        drawRect(
            brush = Brush.verticalGradient(listOf(darkColor, Color.Transparent), endY = thickness * 4),
            size = Size(size.width, thickness * 4)
        )
        drawRect(
            brush = Brush.horizontalGradient(listOf(darkColor, Color.Transparent), endX = thickness * 4),
            size = Size(thickness * 4, size.height)
        )
        drawRect(
            brush = Brush.verticalGradient(listOf(Color.Transparent, lightColor), startY = size.height - thickness * 4),
            topLeft = Offset(0f, size.height - thickness * 4),
            size = Size(size.width, thickness * 4)
        )
        drawRect(
            brush = Brush.horizontalGradient(listOf(Color.Transparent, lightColor), startX = size.width - thickness * 4),
            topLeft = Offset(size.width - thickness * 4, 0f),
            size = Size(thickness * 4, size.height)
        )
    } else {
        drawRect(
            brush = Brush.verticalGradient(listOf(lightColor, Color.Transparent), endY = thickness * 4),
            size = Size(size.width, thickness * 4)
        )
        drawRect(
            brush = Brush.horizontalGradient(listOf(lightColor, Color.Transparent), endX = thickness * 4),
            size = Size(thickness * 4, size.height)
        )
        drawRect(
            brush = Brush.verticalGradient(listOf(Color.Transparent, darkColor), startY = size.height - thickness * 4),
            topLeft = Offset(0f, size.height - thickness * 4),
            size = Size(size.width, thickness * 4)
        )
        drawRect(
            brush = Brush.horizontalGradient(listOf(Color.Transparent, darkColor), startX = size.width - thickness * 4),
            topLeft = Offset(size.width - thickness * 4, 0f),
            size = Size(thickness * 4, size.height)
        )
    }
}

private fun DrawScope.drawClassicBevel(
    pressed: Boolean,
    highlight: Color,
    shadow: Color,
) {
    val diagonal = Offset(size.width, size.height)
    if (pressed) {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(shadow.copy(alpha = 0.22f), Color.Transparent),
                start = Offset.Zero,
                end = diagonal,
            ),
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, highlight.copy(alpha = 0.22f)),
                start = Offset.Zero,
                end = diagonal,
            ),
        )
    } else {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(highlight.copy(alpha = 0.28f), Color.Transparent),
                start = Offset.Zero,
                end = diagonal,
            ),
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(Color.Transparent, shadow.copy(alpha = 0.22f)),
                start = Offset.Zero,
                end = diagonal,
            ),
        )
    }
}

private fun DrawScope.drawSubtleBevel(
    pressed: Boolean,
    highlight: Color,
    shadow: Color,
) {
    if (!pressed) {
        drawLine(
            color = highlight.copy(alpha = 0.15f),
            start = Offset(0f, 0.5f),
            end = Offset(size.width, 0.5f),
            strokeWidth = 1f
        )
        drawLine(
            color = shadow.copy(alpha = 0.15f),
            start = Offset(0f, size.height - 0.5f),
            end = Offset(size.width, size.height - 0.5f),
            strokeWidth = 1f
        )
    }
}

private fun DrawScope.drawKeyboardBevel(
    pressed: Boolean,
    highlight: Color,
    shadow: Color,
    midTone: Color,
    depth: Float,
) {
    if (pressed) {
        drawClassicBevel(true, highlight, shadow)
        return
    }

    val slant = depth * 1.5f
    
    // Slanted sides (Truncated Pyramid Look)
    // Left side
    val leftPath = Path().apply {
        moveTo(0f, 0f)
        lineTo(slant, slant)
        lineTo(slant, size.height - slant)
        lineTo(0f, size.height)
        close()
    }
    drawPath(leftPath, shadow.copy(alpha = 0.15f))
    
    // Right side
    val rightPath = Path().apply {
        moveTo(size.width, 0f)
        lineTo(size.width - slant, slant)
        lineTo(size.width - slant, size.height - slant)
        lineTo(size.width, size.height)
        close()
    }
    drawPath(rightPath, shadow.copy(alpha = 0.25f))

    // Top side
    val topPath = Path().apply {
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width - slant, slant)
        lineTo(slant, slant)
        close()
    }
    drawPath(topPath, highlight.copy(alpha = 0.1f))

    // Bottom side (the "depth" face)
    val bottomPath = Path().apply {
        moveTo(0f, size.height)
        lineTo(slant, size.height - slant)
        lineTo(size.width - slant, size.height - slant)
        lineTo(size.width, size.height)
        close()
    }
    drawPath(bottomPath, shadow.copy(alpha = 0.4f))

    // Inner Dish Effect (Concave top) - Center must be transparent to show content
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, shadow.copy(alpha = 0.12f)),
            center = Offset(size.width / 2f, size.height / 2f),
            radius = size.width / 1.2f
        ),
        topLeft = Offset(slant, slant),
        size = Size(size.width - slant * 2, size.height - slant * 2)
    )
    
    // Subtle top surface highlight
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(highlight.copy(alpha = 0.2f), Color.Transparent),
            startY = slant,
            endY = slant + 4.dp.toPx()
        ),
        topLeft = Offset(slant, slant),
        size = Size(size.width - slant * 2, 4.dp.toPx())
    )
}

private data class Grain(
    val x: Float,
    val y: Float,
    val radius: Float,
    val opacity: Float,
)
