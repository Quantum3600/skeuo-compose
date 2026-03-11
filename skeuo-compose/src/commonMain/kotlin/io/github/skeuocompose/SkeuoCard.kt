package io.github.skeuocompose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

@Composable
fun SkeuoCardConvex(
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.Ivory,
    style: SkeuoSurfaceStyle = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(20.dp),
        raisedElevation = 18.dp,
        pressedElevation = 5.dp,
        bevelKind = SkeuoBevelKind.Convex,
    ),
    content: @Composable BoxScope.() -> Unit,
) {
    SkeuoCard(
        modifier = modifier,
        palette = palette,
        style = style,
        content = content,
    )
}

@Composable
fun SkeuoCardConcave(
    modifier: Modifier = Modifier,
    palette: SkeuoPalette = SkeuoPalettes.Ivory,
    style: SkeuoSurfaceStyle = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(20.dp),
        raisedElevation = 0.dp,
        pressedElevation = 0.dp,
        bevelKind = SkeuoBevelKind.Concave,
    ),
    content: @Composable BoxScope.() -> Unit,
) {
    SkeuoCard(
        modifier = modifier,
        palette = palette,
        style = style,
        content = content,
    )
}
