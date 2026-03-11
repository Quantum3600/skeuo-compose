# Palettes

SkeuoCompose uses a centralized `SkeuoPalette` system to ensure visual consistency across all components.

## Predefined Palettes

The library comes with a set of carefully crafted palettes in the `SkeuoPalettes` object:

| Name | Description |
| :--- | :--- |
| `Ivory` | Warm, creamy plastic look. |
| `Steel` | Cool, brushed metal / industrial gray. |
| `Mint` | Soft, tech-green. |
| `RoyalBlue` | Vibrant, deep blue. |
| `Crimson` | Intense, hardware-red. |
| `Gold` | Luxurious metallic gold. |
| `Metallic` | Neutral, high-contrast aluminum. |
| `IPodClassic` | Iconic 2000s white/gray aesthetic. |
| `RetroBlack` | Classic "Hi-Fi" stereo black. |
| `Carbon` | Modern dark-mode charcoal. |
| `RetroBlue` | Mechanical keyboard inspired blue. |
| `RetroOrange` | Mechanical keyboard inspired amber. |

## Creating a Custom Palette

You can define your own materials by creating a `SkeuoPalette` instance:

```kotlin
val MyCustomMaterial = SkeuoPalette(
    highlight = Color(0xFFFFFFFF),    // Top-most bevel highlight
    midTone = Color(0xFFD0D0D0),      // Main surface color
    lowTone = Color(0xFFB0B0B0),      // Bottom-most bevel shadow
    shadow = Color(0xFF808080),       // Cast shadow color
    border = Color(0xFF606060),       // Fine border stroke
    textureTint = Color(0xFF404040),  // Color used for noise/grain
    content = Color(0xFF212121),      // Text and Icon color
)
```

## Using Palettes

Simply pass the palette to any Skeuo component:

```kotlin
SkeuoButton(
    text = "Custom",
    palette = MyCustomMaterial,
    onClick = { /* ... */ }
)
```
