# Skeuomorphic Compose

A Compose Multiplatform component library for lifelike UI controls using layered gradients, tactile shadows, and subtle grain textures.

## Included Components

- `SkeuoSurface`
- `SkeuoCard`
- `SkeuoButton`
- `SkeuoSwitch`
- Reusable effects: `skeuoLayer` and `skeuoTexture`

## Install (after publication)

```kotlin
dependencies {
    implementation("io.github.skeuomorph:skeuo-ui:0.1.0")
}
```

## Usage

```kotlin
SkeuoCard(
    palette = SkeuoPalettes.Ivory,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Hardware Controls")

        SkeuoButton(
            text = "Power",
            onClick = { /* ... */ },
            palette = SkeuoPalettes.Steel,
        )

        var checked by remember { mutableStateOf(true) }
        SkeuoSwitch(
            checked = checked,
            onCheckedChange = { checked = it },
            palette = SkeuoPalettes.Mint,
        )
    }
}
```

## Maven Central Publish Setup

1. Update placeholder metadata in `gradle.properties`:
   - `GROUP`
   - `POM_URL`, `POM_SCM_*`
   - `POM_DEVELOPER_*`
2. Create a Sonatype account and project namespace for your `GROUP`.
3. Create a GPG key for artifact signing.
4. Set credentials and signing data via env vars or Gradle properties:

```bash
SONATYPE_USERNAME=...
SONATYPE_PASSWORD=...
SIGNING_KEY_ID=...
SIGNING_KEY=...                    # ASCII-armored private key
SIGNING_PASSWORD=...
```

5. Publish release:

```bash
gradle publishToSonatype closeAndReleaseSonatypeStagingRepository
```

For snapshots, set `VERSION_NAME` to a `-SNAPSHOT` version and run:

```bash
gradle publishAllPublicationsToSonatypeRepository
```

## Notes

- This repository is scaffolded for Kotlin Multiplatform + Compose (`android` + `desktop` JVM targets).
- If you prefer the Gradle wrapper, generate it once on your machine with `gradle wrapper`.