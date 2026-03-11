package io.github.skeuocompose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SkeuoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trackPalette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    knobPalette: SkeuoPalette = SkeuoPalettes.RoyalBlue,
    trackWidth: Dp = 88.dp,
    trackHeight: Dp = 44.dp,
    knobWidth: Dp = 48.dp,
    knobHeight: Dp = 40.dp,
    indicatorColor: Color = Color.White,
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val knobOffset by animateDpAsState(
        targetValue = if (checked) (trackWidth - knobWidth - 2.dp) else 2.dp,
        animationSpec = tween(durationMillis = 200),
        label = "skeuoSwitchKnob",
    )

    Row(
        modifier = modifier.alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Left indicator (Off symbol)
        StatusIndicator(active = !checked, hasDot = false, color = indicatorColor)

        Box(
            modifier = Modifier
                .size(width = trackWidth, height = trackHeight)
                .clickable(
                    enabled = enabled,
                    indication = null,
                    interactionSource = interactionSource,
                ) {
                    onCheckedChange(!checked)
                },
            contentAlignment = Alignment.CenterStart
        ) {
            // Recessed track
            SkeuoSurface(
                modifier = Modifier.fillMaxSize(),
                palette = trackPalette,
                style = SkeuoSurfaceStyle(
                    shape = RoundedCornerShape(trackHeight / 2),
                    bevelKind = SkeuoBevelKind.Concave,
                    textureKind = SkeuoTextureKind.Grainy,
                    contentPadding = PaddingValues(0.dp),
                    raisedElevation = 0.dp,
                    pressedElevation = 0.dp,
                    gutterWidth = 1.dp
                )
            ) {}

            // Sliding Knob
            SkeuoSurface(
                modifier = Modifier
                    .offset(x = knobOffset)
                    .size(width = knobWidth, height = knobHeight),
                palette = knobPalette,
                style = SkeuoSurfaceStyle(
                    shape = RoundedCornerShape(knobHeight / 2),
                    bevelKind = SkeuoBevelKind.Classic,
                    textureKind = SkeuoTextureKind.Matte,
                    contentPadding = PaddingValues(0.dp),
                    raisedElevation = 10.dp,
                    pressedElevation = 4.dp,
                )
            ) {
                // Ridges on the knob
                KnobRidges()
            }
        }

        // Right indicator (On symbol)
        StatusIndicator(active = checked, hasDot = true, color = indicatorColor)
    }
}

@Composable
private fun KnobRidges() {
    Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp, vertical = 10.dp)) {
        val ridgeWidth = 2.dp.toPx()
        val spacing = 4.dp.toPx()
        val totalRidges = 3
        val totalWidth = (totalRidges * ridgeWidth) + ((totalRidges - 1) * spacing)
        val startX = (size.width - totalWidth) / 2f

        for (i in 0 until totalRidges) {
            val x = startX + i * (ridgeWidth + spacing)
            
            // Ridge Shadow (right side)
            drawRoundRect(
                color = Color.Black.copy(alpha = 0.35f),
                topLeft = Offset(x + 1f, 0f),
                size = Size(ridgeWidth, size.height),
                cornerRadius = CornerRadius(1.dp.toPx())
            )
            // Ridge Highlight (left side)
            drawRoundRect(
                color = Color.White.copy(alpha = 0.45f),
                topLeft = Offset(x - 1f, 0f),
                size = Size(ridgeWidth, size.height),
                cornerRadius = CornerRadius(1.dp.toPx())
            )
        }
    }
}

@Composable
private fun StatusIndicator(active: Boolean, hasDot: Boolean, color: Color) {
    val opacity = if (active) 0.9f else 0.4f
    Box(
        modifier = Modifier.size(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = color.copy(alpha = opacity),
                radius = size.width / 2f - 1.dp.toPx(),
                style = Stroke(width = 2.dp.toPx())
            )
            if (hasDot) {
                drawCircle(
                    color = color.copy(alpha = opacity),
                    radius = 2.dp.toPx()
                )
            }
        }
    }
}

@Preview
@Composable
fun SkeuoSwitchPreview() {
    var checked by remember { mutableStateOf(false) }

    SkeuoSwitch(
        checked = checked,
        onCheckedChange = { checked = it },
        knobPalette = SkeuoPalettes.Metallic,
        trackPalette = SkeuoPalettes.RetroBlack,
    )
}
