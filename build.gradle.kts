import java.time.Duration

plugins {
    kotlin("multiplatform") version "2.3.10" apply false
    id("com.android.library") version "9.1.0" apply false
    id("org.jetbrains.compose") version "1.10.2" apply false
    kotlin("jvm") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10" apply false
    id("com.vanniktech.maven.publish") version "0.36.0" apply false
}

group = providers.gradleProperty("GROUP").get()
version = providers.gradleProperty("VERSION_NAME").get()

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(21)
}