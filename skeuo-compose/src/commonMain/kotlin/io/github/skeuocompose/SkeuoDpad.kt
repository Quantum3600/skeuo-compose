package io.github.skeuocompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A highly reusable skeuomorphic Dpad component.
 *
 * @param onUpClick Callback for the top action.
 * @param onDownClick Callback for the bottom action.
 * @param onLeftClick Callback for the left action.
 * @param onRightClick Callback for the right action.
 * @param onMiddleClick Callback for the center button action.
 * @param modifier Modifier for the entire Dpad.
 * @param palette The color palette to use.
 * @param upContent Content for the top icon.
 * @param downContent Content for the bottom icon.
 * @param leftContent Content for the left icon.
 * @param rightContent Content for the right icon.
 * @param middleContent Content for the center button.
 */
@Composable
fun SkeuoDpad(
    onUpClick: () -> Unit,
    onDownClick: () -> Unit,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onMiddleClick: () -> Unit,
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    upContent: @Composable BoxScope.() -> Unit = { 
        Icon(Icons.Rounded.Favorite, null, tint = palette.content.copy(alpha = 0.7f)) 
    },
    downContent: @Composable BoxScope.() -> Unit = { 
        Icon(Icons.AutoMirrored.Rounded.VolumeUp, null, tint = palette.content.copy(alpha = 0.7f))
    },
    leftContent: @Composable BoxScope.() -> Unit = { 
        Icon(Icons.Rounded.SkipPrevious, null, tint = palette.content.copy(alpha = 0.7f)) 
    },
    rightContent: @Composable BoxScope.() -> Unit = { 
        Icon(Icons.Rounded.SkipNext, null, tint = palette.content.copy(alpha = 0.7f)) 
    },
    middleContent: @Composable BoxScope.() -> Unit = { 
        Icon(Icons.Rounded.PlayArrow, null, modifier = Modifier.size(40.dp), tint = palette.content) 
    }
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(palette.midTone, palette.lowTone, palette.shadow)
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Directional Clickable Areas (icons on top of the circle)
        DpadActionArea(Alignment.TopCenter, Modifier.padding(top = 4.dp), onUpClick, upContent)
        DpadActionArea(Alignment.BottomCenter, Modifier.padding(bottom = 4.dp), onDownClick, downContent)
        DpadActionArea(Alignment.CenterStart, Modifier.padding(start = 4.dp), onLeftClick, leftContent)
        DpadActionArea(Alignment.CenterEnd, Modifier.padding(end = 4.dp), onRightClick, rightContent)

        // Middle Button - Using existing SkeuoControlCircle component
        SkeuoControlCircle(
            onClick = onMiddleClick,
            palette = palette,
            modifier = Modifier.size(88.dp),
            content = middleContent
        )
    }
}

/**
 * A standalone button component for the 4 corner buttons, 
 * using skeuomorphic shadow effects.
 */
@Composable
fun SkeuoRemoteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.RetroBlack,
    content: @Composable BoxScope.() -> Unit
) {
    SkeuoSurface(
        modifier = modifier.size(64.dp).clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        ),
        palette = palette,
        style = SkeuoSurfaceStyle(
            shape = CircleShape,
            raisedElevation = 10.dp,
            pressedElevation = 2.dp,
            bevelKind = SkeuoBevelKind.Classic,
            contentPadding = PaddingValues(0.dp)
        ),
        content = content
    )
}

@Composable
private fun BoxScope.DpadActionArea(
    alignment: Alignment,
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .align(alignment)
            .then(modifier)
            .size(56.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Preview
@Composable
fun SkeuoRemotePreview() {
    val palette = SkeuoPalettes.RetroBlack
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF151515))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            // Top Corner Buttons
            Row(
                modifier = Modifier.size(240.dp, 64.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkeuoRemoteButton(onClick = {}) { Text("•••", color = Color.White) }
                SkeuoRemoteButton(onClick = {}) { Text("≡", color = Color.White, fontSize = 22.sp) }
            }

            // Central Dpad
            SkeuoDpad(
                onUpClick = {},
                onDownClick = {},
                onLeftClick = {},
                onRightClick = {},
                onMiddleClick = {}
            )

            // Bottom Corner Buttons
            Row(
                modifier = Modifier.size(240.dp, 64.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkeuoRemoteButton(onClick = {}) { Text("🔄", fontSize = 20.sp) }
                SkeuoRemoteButton(onClick = {}) { Text("♫", color = Color.White, fontSize = 22.sp) }
            }
        }
    }
}
