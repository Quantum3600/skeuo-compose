# SkeuoCompose 🎨

[![Maven Central](https://img.shields.io/maven-central/v/io.github.quantum3600/skeuo-compose)](https://central.sonatype.com/artifact/io.github.quantum3600/skeuo-compose)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.7.3-blue)](https://www.jetbrains.com/lp/compose-multiplatform/)

SkeuoCompose is a **Compose Multiplatform** component library designed to bring lifelike, tactile UI elements back to modern applications. It uses layered gradients, procedural textures (grain, matte, glossy, concentric), and realistic shadows to create a sense of depth and physical presence.

---

## 🚀 Installation

Add the dependency to your `commonMain` source set in your `build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.quantum3600:skeuo-compose:0.1.0-alpha01")
            }
        }
    }
}
```

---

## 🎨 Core Concepts

### SkeuoPalette
Every component in SkeuoCompose is driven by a `SkeuoPalette`. A palette defines the highlights, mid-tones, shadows, and textures of a material.

**Available Palettes:**
`Ivory`, `Steel`, `Mint`, `RoyalBlue`, `Crimson`, `Gold`, `Metallic`, `IPodClassic`, `RetroBlack`, `Carbon`, `RetroBlue`, `RetroOrange`.

```kotlin
val myButtonPalette = SkeuoPalettes.Steel
```

### SkeuoSurface
The foundation of the library. All other components use `SkeuoSurface` internally. It handles the beveling, elevation, and textures.

---

## 🧱 Components

### 1. Buttons
We offer several button variants for different hardware aesthetics.

*   **SkeuoButton**: Standard tactile button.
*   **SkeuoKeyButton**: Looks like a mechanical keyboard key with adjustable depth.
*   **SkeuoGutterButton**: A recessed button style typical of high-end remotes.
*   **SkeuoControlCircle**: A circular variant of the gutter button.

```kotlin
SkeuoButton(
    text = "Action",
    palette = SkeuoPalettes.RoyalBlue,
    onClick = { /* Handle click */ }
)

SkeuoKeyButton(
    text = "ESC",
    palette = SkeuoPalettes.Carbon,
    depth = 6.dp,
    onClick = { /* ... */ }
)
```

### 2. Cards
Containers for your UI content with physical depth.

*   **SkeuoCard**: Standard raised card.
*   **SkeuoCardConvex**: Card with a rounded-outward surface.
*   **SkeuoCardConcave**: Card with a recessed, hollowed-out surface.

```kotlin
SkeuoCard(palette = SkeuoPalettes.Ivory) {
    Text("Physical Card Content")
}
```

### 3. Controls
Tactile input components for a rich user experience.

*   **SkeuoSwitch**: A sliding toggle switch with a metallic or matte knob.
*   **SkeuoSlider**: A smooth slider with a tactile thumb.
*   **SkeuoKnob**: A rotary knob with a progress arc and accent indicators.
*   **SkeuoDpad**: A directional pad for navigation or media controls.

```kotlin
// Switch
SkeuoSwitch(
    checked = isEnabled,
    onCheckedChange = { isEnabled = it },
    knobPalette = SkeuoPalettes.Metallic
)

// Knob
SkeuoKnob(
    value = volume,
    onValueChange = { volume = it },
    palette = SkeuoPalettes.Steel,
    accentColor = Color.Cyan
)
```

---

## 📖 Publishing to GitBook

GitBook makes it easy to host beautiful documentation directly from your GitHub repository.

1.  **Create a GitBook Account**: Sign up at [GitBook.com](https://www.gitbook.com).
2.  **Import your Repository**:
    *   Click on **"New Space"**.
    *   Select **"Sync with GitHub"**.
    *   Authorize GitBook and select the `SkeuoCompose` repository.
3.  **Organize with SUMMARY.md**:
    Create a `SUMMARY.md` in your project root to define the navigation sidebar:
    ```markdown
    # Table of contents
    * [Introduction](README.md)
    * [Getting Started](docs/getting-started.md)
    * [Components](docs/components.md)
      * [Buttons](docs/buttons.md)
      * [Controls](docs/controls.md)
    ```
4.  **Automatic Updates**: Every time you push to your main branch, GitBook will automatically update the documentation site.
5.  **Custom Domain**: (Optional) In GitBook settings, you can map the docs to a custom domain like `docs.skeuocompose.dev`.

---

## 📜 License

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for more details.
