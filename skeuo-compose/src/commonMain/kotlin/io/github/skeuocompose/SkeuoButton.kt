package io.github.skeuocompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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

/**
 * A button with a thick gutter and flat surface, giving it a recessed look.
 * This style is common in premium remote controls and high-end electronics.
 * It can be used with any [shape].
 */
@Composable
fun SkeuoGutterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    palette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    borderWidth: Dp = 1.dp,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(12.dp),
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    SkeuoSurface(
        modifier = modifier
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        palette = palette,
        pressed = enabled && isPressed,
        style = SkeuoSurfaceStyle(
            shape = shape,
            borderWidth = borderWidth,
            gutterWidth = 2.dp,
            bevelKind = SkeuoBevelKind.Subtle,
            raisedElevation = 0.dp,
            pressedElevation = 0.dp,
            contentPadding = contentPadding
        ),
        content = content
    )
}

/**
 * A circular variant of [SkeuoGutterButton], matching the style of small 
 * functional buttons on high-end electronic remotes.
 */
@Composable
fun SkeuoControlCircle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    palette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    SkeuoGutterButton(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        enabled = enabled,
        palette = palette,
        borderWidth = borderWidth,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        content = content
    )
}

/**
 * A variant of [SkeuoButton] that looks like a keyboard key with adjustable depth.
 */
@Composable
fun SkeuoKeyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    palette: SkeuoPalette = SkeuoPalettes.Carbon,
    depth: Dp = 6.dp,
    shape: Shape = RoundedCornerShape(6.dp)
) {
    SkeuoButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        palette = palette,
        style = SkeuoSurfaceStyle(
            shape = shape,
            bevelKind = SkeuoBevelKind.Keyboard,
            depth = depth,
            raisedElevation = 8.dp,
            pressedElevation = 2.dp,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
        ),
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = palette.content,
        )
    )
}

@Preview
@Composable
fun SkeuoButtonVariantsPreview() {
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Standard
        SkeuoButton(
            text = "Standard",
            palette = SkeuoPalettes.Ivory,
            onClick = {}
        )

        // Remote Control Buttons (Any Shape)
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            // Circle
            SkeuoControlCircle(onClick = {}) {
                Text("•••", color = Color.White, fontWeight = FontWeight.Bold)
            }
            // Rounded Square Gutter Button
            SkeuoGutterButton(
                onClick = {},
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(16.dp),
                borderWidth = 1.dp,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("≡", color = Color.White, fontSize = 24.sp)
            }
        }

        // Keyboard Variant
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SkeuoKeyButton(
                text = "Q",
                depth = 4.dp,
                onClick = {}
            )
            SkeuoKeyButton(
                text = "W",
                depth = 4.dp,
                onClick = {}
            )
            SkeuoKeyButton(
                text = "E",
                depth = 4.dp,
                onClick = {}
            )
        }
        
        SkeuoKeyButton(
            text = "SPACE",
            modifier = Modifier.defaultMinSize(minWidth = 200.dp),
            depth = 8.dp,
            onClick = {}
        )
    }
}
