package io.github.skeuocompose

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class SkeuoPalette(
    val highlight: Color,
    val midTone: Color,
    val lowTone: Color,
    val shadow: Color,
    val border: Color,
    val textureTint: Color,
    val content: Color,
)

object SkeuoPalettes {
    val Ivory = SkeuoPalette(
        highlight = Color(0xFFF5F1E8),
        midTone = Color(0xFFE4DACA),
        lowTone = Color(0xFFD2C3AE),
        shadow = Color(0xFFB7A58C),
        border = Color(0xFF907E66),
        textureTint = Color(0xFF8A7963),
        content = Color(0xFF2B241B),
    )

    val Steel = SkeuoPalette(
        highlight = Color(0xFFF0F4F8),
        midTone = Color(0xFFD5DEE8),
        lowTone = Color(0xFFBAC5D3),
        shadow = Color(0xFF98A6B8),
        border = Color(0xFF6F7C8D),
        textureTint = Color(0xFF5D6B7D),
        content = Color(0xFF1D2430),
    )

    val Mint = SkeuoPalette(
        highlight = Color(0xFFDFF5E8),
        midTone = Color(0xFFB7E6CF),
        lowTone = Color(0xFF8ECFAE),
        shadow = Color(0xFF5CA880),
        border = Color(0xFF3F7A5C),
        textureTint = Color(0xFF2F664C),
        content = Color(0xFF152A1F),
    )

    val RoyalBlue = SkeuoPalette(
        highlight = Color(0xFF4A90E2),
        midTone = Color(0xFF357ABD),
        lowTone = Color(0xFF2A5F91),
        shadow = Color(0xFF1E4468),
        border = Color(0xFF15304A),
        textureTint = Color(0xFF25527A),
        content = Color(0xFFFFFFFF),
    )

    val Crimson = SkeuoPalette(
        highlight = Color(0xFFFF5252),
        midTone = Color(0xFFD32F2F),
        lowTone = Color(0xFFB71C1C),
        shadow = Color(0xFF7F0000),
        border = Color(0xFF5A0000),
        textureTint = Color(0xFF9E1A1A),
        content = Color(0xFFFFFFFF),
    )

    val Gold = SkeuoPalette(
        highlight = Color(0xFFFFE082),
        midTone = Color(0xFFFFC107),
        lowTone = Color(0xFFFFA000),
        shadow = Color(0xFFE65100),
        border = Color(0xFFBF360C),
        textureTint = Color(0xFFFFB300),
        content = Color(0xFF3E2723),
    )

    val Metallic = SkeuoPalette(
        highlight = Color(0xFFFFFFFF),
        midTone = Color(0xFFE0E0E0),
        lowTone = Color(0xFFBDBDBD),
        shadow = Color(0xFF9E9E9E),
        border = Color(0xFF757575),
        textureTint = Color(0xFF616161),
        content = Color(0xFF212121),
    )

    val IPodClassic = SkeuoPalette(
        highlight = Color(0xFFFFFFFF),
        midTone = Color(0xFFF2F2F2),
        lowTone = Color(0xFFE5E5E5),
        shadow = Color(0xFFCCCCCC),
        border = Color(0xFFAAAAAA),
        textureTint = Color(0xFF888888),
        content = Color(0xFF333333),
    )

    val RetroBlack = SkeuoPalette(
        highlight = Color(0xFF424242),
        midTone = Color(0xFF212121),
        lowTone = Color(0xFF1A1A1A),
        shadow = Color(0xFF0D0D0D),
        border = Color(0xFF000000),
        textureTint = Color(0xFF333333),
        content = Color(0xFFE0E0E0),
    )

    val Carbon = SkeuoPalette(
        highlight = Color(0xFF3A3A3A),
        midTone = Color(0xFF2C2C2C),
        lowTone = Color(0xFF1E1E1E),
        shadow = Color(0xFF121212),
        border = Color(0xFF080808),
        textureTint = Color(0xFF252525),
        content = Color(0xFFF5F5F5),
    )

    /**
     * Retro keyboard blue, inspired by classic custom keycaps.
     */
    val RetroBlue = SkeuoPalette(
        highlight = Color(0xFF8EAFCA),
        midTone = Color(0xFF5B8DAE),
        lowTone = Color(0xFF416B87),
        shadow = Color(0xFF2C495D),
        border = Color(0xFF1A2E3B),
        textureTint = Color(0xFF4A7996),
        content = Color(0xFFFFFFFF),
    )

    /**
     * Retro keyboard orange, warm amber tone.
     */
    val RetroOrange = SkeuoPalette(
        highlight = Color(0xFFEBB461),
        midTone = Color(0xFFD98C1D),
        lowTone = Color(0xFFB06F14),
        shadow = Color(0xFF80500A),
        border = Color(0xFF543405),
        textureTint = Color(0xFFC47D18),
        content = Color(0xFFFFFFFF),
    )
}
