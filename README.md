# HeroDatePicker
![plot](./images/banner.jpg)

[![Download](https://img.shields.io/jitpack/version/com.github.hamid97m/herodatepicker)](https://jitpack.io/#hamid97m/herodatepicker)

## Setup

### Configure the repositories

`HeroDatePicker` is available through the *JitPack* repository. You can declare this repository in your
build script as follows:

<details open>
<summary>Kotlin DSL</summary>

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

</details>

<details>
<summary>Groovy DSL</summary>

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

</details>

### Add the dependency

After repository configuration, add a dependency on BazaarPay to your module's `build.gradle`
file:

<details open>
<summary>Kotlin DSL</summary>

```kotlin
dependencies {
    implementation("com.github.hamid97m:herodatepicker:1.0")
}
```

</details>

<details>
<summary>Groovy DSL</summary>

```groovy
dependencies {
    implementation 'com.github.hamid97m:herodatepicker:1.0'
}
```

</details>


## Usage

```kotlin
HeroDatePicker(modifier = Modifier.fillMaxWidth()) { selectedDate -> }
```

<img src="https://github.com/hamid97m/HeroDatePicker/blob/master/images/library.png" width="250 align="center"">

