package io.github.skeuomorph

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SkeuoSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    palette: SkeuoPalette = SkeuoPalettes.Mint,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val knobOffset by animateDpAsState(
        targetValue = if (checked) 34.dp else 2.dp,
        animationSpec = tween(durationMillis = 160),
        label = "skeuoSwitchKnob",
    )

    val trackStyle = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(2.dp),
        raisedElevation = 8.dp,
        pressedElevation = 2.dp,
    )

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.55f)
            .size(width = 70.dp, height = 36.dp)
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = interactionSource,
            ) {
                onCheckedChange(!checked)
            },
    ) {
        SkeuoSurface(
            modifier = Modifier.fillMaxSize(),
            palette = palette,
            style = trackStyle,
            pressed = checked,
        ) {}

        SkeuoSurface(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = knobOffset)
                .size(32.dp),
            palette = SkeuoPalettes.Steel,
            style = SkeuoSurfaceStyle(
                shape = RoundedCornerShape(999.dp),
                contentPadding = PaddingValues(0.dp),
                raisedElevation = 6.dp,
                pressedElevation = 2.dp,
            ),
            pressed = checked,
        ) {}
    }
}

@Preview
@Composable
fun SkeuoSwitchPreview() {
    var checked by remember { mutableStateOf(false) }
    SkeuoSwitch(
        checked = checked,
        onCheckedChange = { checked = it },
    )
}