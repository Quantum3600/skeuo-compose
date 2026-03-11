# Surfaces & Cards

The foundation of the SkeuoCompose design system.

## SkeuoSurface

`SkeuoSurface` is the low-level component that handles shadows, bevels, and textures. Almost all other components are built on top of it.

```kotlin
SkeuoSurface(
    palette = SkeuoPalettes.Ivory,
    style = SkeuoSurfaceStyle(
        shape = RoundedCornerShape(12.dp),
        bevelKind = SkeuoBevelKind.Classic,
        textureKind = SkeuoTextureKind.Grainy
    )
) {
    // Content
}
```

### Bevel Kinds
- `Classic`: Standard raised look.
- `Keyboard`: Deeper side shadows for a keycap look.
- `Convex`: Curved outward.
- `Concave`: Recessed/hollowed inward.
- `Subtle`: Minimal depth for flat-style layouts.

---

## SkeuoCard

A high-level container with preset elevations and shapes.

### Standard Card
Used for grouping content with a physical "raised" presence.

```kotlin
SkeuoCard { 
    Text("Raised Card")
}
```

### Convex Card
Best for "pill" style buttons or decorative elements that should feel bulbous.

```kotlin
SkeuoCardConvex { 
    Text("Bulging Surface")
}
```

### Concave Card
Used to create "recessed" areas, often used as tracks for sliders or backgrounds for other buttons.

```kotlin
SkeuoCardConcave { 
    Text("Inside a Hole")
}
```
