package io.github.skeuocompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A skeuomorphic text field that appears concave (recessed) into the surface.
 * The background is slightly darker than the provided palette to enhance the depth effect.
 */
@Composable
fun SkeuoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    palette: SkeuoPalette = SkeuoPalettes.Ivory,
    placeholder: String? = null,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        color = palette.content
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    // Create a slightly darker version of the palette for the recessed field
    val fieldPalette = remember(palette) {
        palette.copy(
            midTone = darken(palette.midTone, 0.04f),
            lowTone = darken(palette.lowTone, 0.06f),
            highlight = darken(palette.highlight, 0.02f),
            shadow = darken(palette.shadow, 0.05f)
        )
    }

    SkeuoSurface(
        modifier = modifier,
        palette = fieldPalette,
        style = SkeuoSurfaceStyle(
            shape = shape,
            bevelKind = SkeuoBevelKind.Concave,
            raisedElevation = 0.dp,
            pressedElevation = 0.dp,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            textureKind = SkeuoTextureKind.Matte,
            borderWidth = 1.dp
        )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = textStyle.copy(
                                color = palette.content.copy(alpha = 0.4f)
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
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
@Preview
@Composable
fun SkeuoTextFieldPreview() {
    SkeuoTextField(
        value = "Hello, World!",
        onValueChange = {},
    )
}