# Inputs & Controls

Tactile input components for high-precision or aesthetically rich user interfaces.

## SkeuoSwitch
A physical-style toggle switch. It features a recessed track and a raised sliding knob with tactile ridges.

```kotlin
var isChecked by remember { mutableStateOf(false) }

SkeuoSwitch(
    checked = isChecked,
    onCheckedChange = { isChecked = it },
    knobPalette = SkeuoPalettes.RoyalBlue,
    trackPalette = SkeuoPalettes.RetroBlack
)
```

## SkeuoSlider
A linear slider with a tactile thumb. It supports both horizontal and vertical orientations.

```kotlin
var sliderValue by remember { mutableFloatStateOf(0.5f) }

SkeuoSlider(
    value = sliderValue,
    onValueChange = { sliderValue = it },
    orientation = SkeuoOrientation.Horizontal,
    activeTrackPalette = SkeuoPalettes.Crimson
)
```

## SkeuoKnob
A rotary dial inspired by analog synthesizers and audio equipment. It includes a progress arc and a central indicator dot.

```kotlin
var volume by remember { mutableFloatStateOf(0.7f) }

SkeuoKnob(
    value = volume,
    onValueChange = { volume = it },
    palette = SkeuoPalettes.Metallic,
    accentColor = Color.Yellow
)
```
