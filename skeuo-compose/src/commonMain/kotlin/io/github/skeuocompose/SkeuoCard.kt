package io.github.skeuocompose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.skeuomorph.SkeuoPalette
import io.github.skeuomorph.SkeuoPalettes
import io.github.skeuomorph.SkeuoSurface
import io.github.skeuomorph.SkeuoSurfaceStyle


@Composable
fun SkeuoCard(
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.Ivory,
    style: SkeuoSurfaceStyle = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(20.dp),
        raisedElevation = 18.dp,
        pressedElevation = 5.dp,
    ),
    content: @Composable BoxScope.() -> Unit,
) {
    SkeuoSurface(
        modifier = modifier,
        palette = palette,
        style = style,
        pressed = false,
        content = content,
    )
}

@Preview
@Composable
fun SkeuoCardPreview() {
    SkeuoCard {
        // Card content
    }
}