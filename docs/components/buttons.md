# Buttons

SkeuoCompose provides several button types to match different physical interfaces.

## SkeuoButton
The standard tactile button. It has a rounded shape, a subtle gradient, and realistic shadows that change when pressed.

```kotlin
SkeuoButton(
    text = "Confirm",
    palette = SkeuoPalettes.Steel,
    onClick = { /* ... */ }
)
```

## SkeuoKeyButton
Designed to look like a mechanical keyboard keycap. It features a deeper physical side (depth) and distinct top-down lighting.

```kotlin
SkeuoKeyButton(
    text = "ENTER",
    palette = SkeuoPalettes.Carbon,
    depth = 8.dp,
    onClick = { /* ... */ }
)
```

## SkeuoGutterButton & SkeuoControlCircle
These represent "recessed" buttons found on high-end electronics. The button sits inside a "gutter" or "well".

```kotlin
// Circular control (like an iPod center button)
SkeuoControlCircle(
    palette = SkeuoPalettes.RetroBlack,
    onClick = { /* ... */ }
) {
    Icon(Icons.Default.PlayArrow, contentDescription = null)
}

// Rounded square gutter button
SkeuoGutterButton(
    shape = RoundedCornerShape(12.dp),
    onClick = { /* ... */ }
) {
    Text("MENU")
}
```
