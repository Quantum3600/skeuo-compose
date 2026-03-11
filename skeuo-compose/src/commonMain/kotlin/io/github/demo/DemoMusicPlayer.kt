package io.github.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.skeuocompose.*

@Preview
@Composable
fun DemoMusicPlayer() {
    val darkPalette = SkeuoPalettes.RetroBlack
    val surfaceColor = Color(0xFF121212)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceColor)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkeuoControlCircle(
                    onClick = {},
                    modifier = Modifier.size(48.dp),
                    palette = darkPalette
                ) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Text(
                    text = "Now Playing",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                SkeuoControlCircle(
                    onClick = {},
                    modifier = Modifier.size(48.dp),
                    palette = darkPalette
                ) {
                    Icon(Icons.Rounded.Info, contentDescription = "Info", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Album Art Card
            SkeuoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                palette = darkPalette,
                style = SkeuoSurfaceStyle(
                    shape = RoundedCornerShape(32.dp),
                    contentPadding = PaddingValues(16.dp),
                    raisedElevation = 15.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Simulating the Banana Art
                        Text(
                            "🍌",
                            fontSize = 120.sp
                        )
                        Text(
                            "Andy Warhol",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Track Info Card (Recessed)
            SkeuoCardConcave(
                modifier = Modifier.fillMaxWidth(),
                palette = darkPalette,
                style = SkeuoSurfaceStyle(
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(12.dp),
                    bevelKind = SkeuoBevelKind.Concave
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Small Artist Image
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.People, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "The Velvet Underground",
                            color = Color.LightGray.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Sunday Morning",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    SkeuoButton(
                        text = "Follow",
                        onClick = {},
                        palette = SkeuoPalette(
                            highlight = Color(0xFF666666),
                            midTone = Color(0xFF444444),
                            lowTone = Color(0xFF333333),
                            shadow = Color(0xFF222222),
                            border = Color(0xFF111111),
                            textureTint = Color(0xFF555555),
                            content = Color.White
                        ),
                        style = SkeuoSurfaceStyle(
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            raisedElevation = 6.dp,
                            borderWidth = 0.5.dp
                        ),
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Slider
            var progress by remember { mutableFloatStateOf(0.34f) }
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                SkeuoSlider(
                    value = progress,
                    onValueChange = { progress = it },
                    modifier = Modifier.fillMaxWidth(),
                    trackThickness = 6.dp,
                    knobWidth = 28.dp,
                    knobHeight = 16.dp,
                    activeTrackPalette = SkeuoPalettes.Steel
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0:34", color = Color.Gray, fontSize = 12.sp)
                    Text("-2:59", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.fillMaxHeight().padding(vertical = 24.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    SkeuoControlCircle(onClick = {}) {
                        Icon(Icons.Rounded.MoreHoriz, null, tint = Color.White)
                    }
                    SkeuoControlCircle(onClick = {}) {
                        Icon(Icons.Rounded.Repeat, null, tint = Color.White)
                    }
                }

                SkeuoDpad(
                    onUpClick = {},
                    onDownClick = {},
                    onLeftClick = {},
                    onRightClick = {},
                    onMiddleClick = {},
                    modifier = Modifier.size(200.dp),
                    palette = darkPalette,
                    upContent = { Icon(Icons.Rounded.Favorite, null, tint = Color.White.copy(alpha = 0.8f)) },
                    downContent = { Icon(Icons.AutoMirrored.Rounded.VolumeUp, null, tint = Color.White.copy(alpha = 0.8f)) },
                    middleContent = { 
                        Icon(Icons.Rounded.PlayArrow, null, modifier = Modifier.size(44.dp), tint = Color.White) 
                    }
                )

                Column(Modifier.fillMaxHeight().padding(vertical = 24.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    SkeuoControlCircle(onClick = {}) {
                        Icon(Icons.AutoMirrored.Rounded.List, null, tint = Color.White)
                    }
                    SkeuoControlCircle(onClick = {}) {
                        Icon(Icons.Rounded.MusicNote, null, tint = Color.White)
                    }
                }
            }
        }
    }
}
