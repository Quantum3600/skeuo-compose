# Getting Started

SkeuoCompose is a library for Compose Multiplatform that brings skeuomorphic design to your apps.

## Prerequisites

- **Kotlin**: 2.1.0 or higher
- **Compose Multiplatform**: 1.7.3 or higher
- **Android SDK**: Min SDK 24 (if targeting Android)
- **JDK**: 17 or higher (JDK 21 recommended)

## Installation

Add the dependency to your `commonMain` source set in `build.gradle.kts`:

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

## Basic Usage

The simplest way to start is by using a `SkeuoCard` to wrap your existing UI:

```kotlin
import io.github.skeuocompose.*

@Composable
fun MySkeuoUI() {
    SkeuoCard(palette = SkeuoPalettes.Ivory) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Hello Skeuomorphism!")
            
            SkeuoButton(
                text = "Click Me",
                palette = SkeuoPalettes.Steel,
                onClick = { /* ... */ }
            )
        }
    }
}
```

## Platform Support

- **Android**: Fully supported.
- **Desktop (JVM)**: Fully supported.
- **iOS/Web**: Experimental (should work as it's common code, but not explicitly tested).
