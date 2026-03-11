package io.github.skeuocompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SkeuoKnob(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.Steel,
    accentColor: Color = Color(0xFF00B4FF),
    knobSize: Dp = 140.dp,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) {
    val startAngle = 145f
    val sweepAngle = 250f
    val ratio = ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start)).coerceIn(0f, 1f)
    val currentAngle = startAngle + ratio * sweepAngle

    Box(
        modifier = modifier.size(knobSize * 1.5f),
        contentAlignment = Alignment.Center
    ) {
        // 1. The Outer Arc and Dots
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 2.dp.toPx()
            val arcRadius = (knobSize.toPx() * 1.3f) / 2f
            val arcPadding = (size.width - arcRadius * 2) / 2f
            val arcSize = Size(arcRadius * 2, arcRadius * 2)
            
            // Track (Thin gray line)
            drawArc(
                color = palette.shadow.copy(alpha = 0.3f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(arcPadding, arcPadding),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress Arc (Accent color)
            drawArc(
                color = accentColor,
                startAngle = startAngle,
                sweepAngle = ratio * sweepAngle,
                useCenter = false,
                topLeft = Offset(arcPadding, arcPadding),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Starting dot
            val startRad = Math.toRadians(startAngle.toDouble())
            drawCircle(
                color = palette.shadow.copy(alpha = 0.6f),
                radius = 3.dp.toPx(),
                center = center + Offset(
                    (arcRadius * cos(startRad)).toFloat(),
                    (arcRadius * sin(startRad)).toFloat()
                )
            )

            // Indicator dot at the end of progress
            val endRad = Math.toRadians(currentAngle.toDouble())
            drawCircle(
                color = accentColor,
                radius = 4.dp.toPx(),
                center = center + Offset(
                    (arcRadius * cos(endRad)).toFloat(),
                    (arcRadius * sin(endRad)).toFloat()
                )
            )
        }

        // 2. The Recessed Gutter (Outer ring)
        SkeuoSurface(
            modifier = Modifier.size(knobSize * 1.15f),
            palette = palette,
            style = SkeuoSurfaceStyle(
                shape = CircleShape,
                bevelKind = SkeuoBevelKind.Concave,
                raisedElevation = 0.dp,
                pressedElevation = 0.dp,
                borderWidth = 1.dp,
                contentPadding = PaddingValues(0.dp)
            )
        ) {}

        // 3. The Main Knob
        SkeuoSurface(
            modifier = Modifier
                .size(knobSize)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val touchPoint = change.position
                        val angleRad = atan2(
                            (touchPoint.y - center.y).toDouble(),
                            (touchPoint.x - center.x).toDouble()
                        )
                        var angleDeg = Math.toDegrees(angleRad).toFloat()
                        
                        var normalizedAngle = (angleDeg - startAngle)
                        while (normalizedAngle < 0) normalizedAngle += 360f
                        
                        if (normalizedAngle <= sweepAngle || normalizedAngle > (360 - (360 - sweepAngle) / 2)) {
                            val newRatio = (normalizedAngle / sweepAngle).coerceIn(0f, 1f)
                            onValueChange(valueRange.start + newRatio * (valueRange.endInclusive - valueRange.start))
                        }
                    }
                },
            palette = palette,
            style = SkeuoSurfaceStyle(
                shape = CircleShape,
                textureKind = SkeuoTextureKind.Concentric,
                raisedElevation = 10.dp,
                pressedElevation = 6.dp,
                borderWidth = 1.5.dp
            )
        ) {
            // Indicator Dot inside the knob
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(currentAngle + 90)
                    .padding(knobSize * 0.15f)
            ) {
                Canvas(modifier = Modifier.size(knobSize * 0.08f).align(Alignment.TopCenter)) {
                    drawCircle(
                        color = accentColor,
                        radius = size.width / 2
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SkeuoKnobPreview() {
    var value by remember { mutableFloatStateOf(0.6f) }
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        SkeuoKnob(
            value = value,
            onValueChange = { value = it },
            palette = SkeuoPalettes.Metallic
        )
    }
}
