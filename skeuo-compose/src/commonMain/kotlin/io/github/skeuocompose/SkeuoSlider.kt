package io.github.skeuocompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Orientation for the [SkeuoSlider].
 */
enum class SkeuoOrientation {
    Horizontal,
    Vertical
}

/**
 * A skeuomorphic Slider component with a thumb similar to the one in SkeuoSwitch.
 *
 * @param value current value of the slider
 * @param onValueChange callback in which value should be updated
 * @param modifier the [Modifier] to be applied to this slider
 * @param enabled controls the enabled state of this slider
 * @param valueRange range of values that this slider can take
 * @param orientation orientation of the slider (Horizontal or Vertical)
 * @param trackPalette palette for the inactive part of the track
 * @param activeTrackPalette optional palette for the active part of the track
 * @param knobPalette palette for the slider knob
 * @param trackThickness thickness of the slider track
 * @param knobWidth width of the slider knob along its primary axis of travel
 * @param knobHeight height of the slider knob across its primary axis of travel
 */
@Composable
fun SkeuoSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    orientation: SkeuoOrientation = SkeuoOrientation.Horizontal,
    trackPalette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    activeTrackPalette: SkeuoPalette? = null,
    knobPalette: SkeuoPalette = SkeuoPalettes.Metallic,
    trackThickness: Dp = 12.dp,
    knobWidth: Dp = 48.dp,
    knobHeight: Dp = 34.dp,
) {
    val density = LocalDensity.current
    val isHorizontal = orientation == SkeuoOrientation.Horizontal

    // If vertical, we swap knob dimensions for the layout:
    // knobWidth (travel axis) becomes layout height, knobHeight (cross axis) becomes layout width.
    val actualKnobWidth = if (isHorizontal) knobWidth else knobHeight
    val actualKnobHeight = if (isHorizontal) knobHeight else knobWidth

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val onValueChangeState = rememberUpdatedState(onValueChange)

    BoxWithConstraints(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.5f)
            .then(
                if (isHorizontal) Modifier.height(maxOf(trackThickness, actualKnobHeight)).fillMaxWidth()
                else Modifier.width(maxOf(trackThickness, actualKnobWidth)).fillMaxHeight()
            ),
        contentAlignment = Alignment.Center
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        
        // The knob size along the direction of travel
        val knobTravelSizePx = with(density) { knobWidth.toPx() }

        val trackTotalLength = if (isHorizontal) widthPx else heightPx
        val usableLength = (trackTotalLength - knobTravelSizePx).coerceAtLeast(0f)

        val range = valueRange.endInclusive - valueRange.start
        val fraction = if (range != 0f) ((value - valueRange.start) / range).coerceIn(0f, 1f) else 0f
        
        val thumbOffsetPx = fraction * usableLength

        val draggableState = rememberDraggableState { delta ->
            val oldOffset = fraction * usableLength
            // For vertical, positive delta is downwards. Sliders usually increase upwards.
            val deltaToApply = if (isHorizontal) delta else -delta
            val newOffset = (oldOffset + deltaToApply).coerceIn(0f, usableLength)
            val newFraction = if (usableLength > 0) newOffset / usableLength else 0f
            val newValue = newFraction * range + valueRange.start
            onValueChangeState.value(newValue)
        }

        // Track Container
        Box(
            modifier = Modifier
                .then(
                    if (isHorizontal) Modifier.fillMaxWidth().height(trackThickness)
                    else Modifier.fillMaxHeight().width(trackThickness)
                )
                .pointerInput(enabled, isHorizontal, usableLength, range, valueRange.start, knobTravelSizePx) {
                    if (!enabled) return@pointerInput
                    detectTapGestures { offset ->
                        val position = if (isHorizontal) offset.x else (size.height - offset.y)
                        val newFraction = if (usableLength > 0) {
                            ((position - knobTravelSizePx / 2) / usableLength).coerceIn(0f, 1f)
                        } else 0f
                        onValueChangeState.value(newFraction * range + valueRange.start)
                    }
                },
            contentAlignment = if (isHorizontal) Alignment.CenterStart else Alignment.BottomCenter
        ) {
            // Inactive Track (background)
            SkeuoSurface(
                modifier = Modifier.fillMaxSize(),
                palette = trackPalette,
                style = SkeuoSurfaceStyle(
                    shape = RoundedCornerShape(trackThickness / 2),
                    bevelKind = SkeuoBevelKind.Concave,
                    textureKind = SkeuoTextureKind.Grainy,
                    contentPadding = PaddingValues(0.dp),
                    raisedElevation = 0.dp,
                    pressedElevation = 0.dp,
                    gutterWidth = 1.dp
                )
            ) {}

            // Active Track
            if (activeTrackPalette != null) {
                val activeLengthFraction = if (trackTotalLength > 0) (thumbOffsetPx + knobTravelSizePx / 2) / trackTotalLength else 0f
                SkeuoSurface(
                    modifier = if (isHorizontal) Modifier.fillMaxWidth(activeLengthFraction).fillMaxHeight()
                               else Modifier.fillMaxHeight(activeLengthFraction).fillMaxWidth(),
                    palette = activeTrackPalette,
                    style = SkeuoSurfaceStyle(
                        shape = RoundedCornerShape(trackThickness / 2),
                        bevelKind = SkeuoBevelKind.Concave,
                        textureKind = SkeuoTextureKind.Grainy,
                        contentPadding = PaddingValues(0.dp),
                        raisedElevation = 0.dp,
                        pressedElevation = 0.dp,
                        gutterWidth = 0.dp
                    )
                ) {}
            }
        }

        // Thumb
        SkeuoSurface(
            modifier = Modifier
                .align(if (isHorizontal) Alignment.CenterStart else Alignment.BottomCenter)
                .offset {
                    if (isHorizontal) {
                        IntOffset(thumbOffsetPx.roundToInt(), 0)
                    } else {
                        IntOffset(0, -thumbOffsetPx.roundToInt())
                    }
                }
                .size(width = actualKnobWidth, height = actualKnobHeight)
                .draggable(
                    state = draggableState,
                    orientation = if (isHorizontal) Orientation.Horizontal else Orientation.Vertical,
                    enabled = enabled,
                    interactionSource = interactionSource
                ),
            palette = knobPalette,
            pressed = isPressed,
            style = SkeuoSurfaceStyle(
                shape = RoundedCornerShape(if (isHorizontal) actualKnobHeight / 2 else actualKnobWidth / 2),
                bevelKind = SkeuoBevelKind.Classic,
                textureKind = SkeuoTextureKind.Matte,
                contentPadding = PaddingValues(0.dp),
                raisedElevation = 10.dp,
                pressedElevation = 4.dp,
            )
        ) {
            SliderKnobRidges(orientation)
        }
    }
}

@Composable
private fun SliderKnobRidges(orientation: SkeuoOrientation) {
    val isHorizontal = orientation == SkeuoOrientation.Horizontal
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = if (isHorizontal) 14.dp else 10.dp,
                vertical = if (isHorizontal) 10.dp else 14.dp
            )
    ) {
        val ridgeThickness = 2.dp.toPx()
        val spacing = 4.dp.toPx()
        val totalRidges = 3
        
        if (isHorizontal) {
            val totalWidth = (totalRidges * ridgeThickness) + ((totalRidges - 1) * spacing)
            val startX = (size.width - totalWidth) / 2f
            for (i in 0 until totalRidges) {
                val x = startX + i * (ridgeThickness + spacing)
                // Ridge Shadow
                drawRoundRect(
                    color = Color.Black.copy(alpha = 0.35f),
                    topLeft = Offset(x + 1f, 0f),
                    size = androidx.compose.ui.geometry.Size(ridgeThickness, size.height),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
                )
                // Ridge Highlight
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.45f),
                    topLeft = Offset(x - 1f, 0f),
                    size = androidx.compose.ui.geometry.Size(ridgeThickness, size.height),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
                )
            }
        } else {
            val totalHeight = (totalRidges * ridgeThickness) + ((totalRidges - 1) * spacing)
            val startY = (size.height - totalHeight) / 2f
            for (i in 0 until totalRidges) {
                val y = startY + i * (ridgeThickness + spacing)
                // Ridge Shadow
                drawRoundRect(
                    color = Color.Black.copy(alpha = 0.35f),
                    topLeft = Offset(0f, y + 1f),
                    size = androidx.compose.ui.geometry.Size(size.width, ridgeThickness),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
                )
                // Ridge Highlight
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.45f),
                    topLeft = Offset(0f, y - 1f),
                    size = androidx.compose.ui.geometry.Size(size.width, ridgeThickness),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
                )
            }
        }
    }
}

@Preview
@Composable
fun SliderPreview() {
    var hValue by remember { mutableFloatStateOf(0.5f) }
    var vValue by remember { mutableFloatStateOf(0.5f) }
    Column(
        modifier = Modifier.padding(32.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkeuoSlider(
            value = hValue,
            onValueChange = { hValue = it },
            modifier = Modifier.fillMaxWidth(),
            activeTrackPalette = SkeuoPalettes.RoyalBlue
        )

        SkeuoSlider(
            value = vValue,
            onValueChange = { vValue = it },
            modifier = Modifier.height(200.dp),
            orientation = SkeuoOrientation.Vertical,
            activeTrackPalette = SkeuoPalettes.Crimson
        )
    }
}
