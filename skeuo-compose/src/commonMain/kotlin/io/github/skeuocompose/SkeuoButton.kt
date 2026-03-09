package io.github.skeuocompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.skeuomorph.SkeuoPalette
import io.github.skeuomorph.SkeuoPalettes
import io.github.skeuomorph.SkeuoSurface
import io.github.skeuomorph.SkeuoSurfaceStyle


@Composable
fun SkeuoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    palette: SkeuoPalette,
    style: SkeuoSurfaceStyle = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(18.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        raisedElevation = 12.dp,
        pressedElevation = 2.dp,
    ),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = palette.content,
    ),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    SkeuoSurface(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.55f)
            .defaultMinSize(minWidth = 88.dp, minHeight = 44.dp)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        palette = palette,
        style = style,
        pressed = enabled && isPressed,
    ) {
        Text(text = text, style = textStyle)
    }
}

@Preview
@Composable
fun SkeuoButtonPreview() {
    Box(contentAlignment = Alignment.Center) {
        SkeuoButton(
            modifier = Modifier.padding(64.dp),
            text = "Fuck You",
            palette = SkeuoPalettes.RetroBlack,
            onClick = {}
        )
    }
}