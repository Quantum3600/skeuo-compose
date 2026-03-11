# Navigation

Components designed for moving through interfaces or controlling external devices.

## SkeuoDpad
A directional pad inspired by classic game controllers and media remotes. It features four directional action areas and a central confirm button.

```kotlin
SkeuoDpad(
    onUpClick = { /* ... */ },
    onDownClick = { /* ... */ },
    onLeftClick = { /* ... */ },
    onRightClick = { /* ... */ },
    onMiddleClick = { /* ... */ },
    palette = SkeuoPalettes.RetroBlack
)
```

### Customizing Content
You can override the icons for each direction:

```kotlin
SkeuoDpad(
    onUpClick = { volumeUp() },
    upContent = { Icon(Icons.Default.Add, null) },
    // ... other directions
)
```

## SkeuoRemoteButton
A standalone circular button with deep shadows, designed to accompany a Dpad in a remote control layout.

```kotlin
SkeuoRemoteButton(
    onClick = { /* ... */ },
    palette = SkeuoPalettes.RetroBlack
) {
    Text("MENU", color = Color.White, fontSize = 12.sp)
}
```
