# HeroDatePicker

<img alt="Banner" src="./images/banner.jpg" width="500"/>

[![Download](https://img.shields.io/jitpack/version/com.github.hamid97m/herodatepicker)](https://jitpack.io/#hamid97m/herodatepicker)

## Setup

### Configure the repositories

`HeroDatePicker` is available through the *JitPack* repository. You can declare this repository in
your build script as follows:

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

After repository configuration, add a dependency on HeroDatePicker to your module's `build.gradle`
file:

<details open>
<summary>Kotlin DSL</summary>

```kotlin
dependencies {
    implementation("com.github.hamid97m:herodatepicker:1.0.0")
}
```

</details>

<details>
<summary>Groovy DSL</summary>

```groovy
dependencies {
    implementation 'com.github.hamid97m:herodatepicker:1.0.0'
}
```

</details>

## Usage

```kotlin
HeroDatePicker(Modifier.fillMaxWidth()) { selectedDate ->
    val (year, month, day) = selectedDate
    println("Selected Date: $year/$month/$day")
}
```

In addition, there is a sample app available in the
project's [sample directory](https://github.com/hamid97m/HeroDatePicker/tree/master/sample) that
demonstrates how to use the library.

<p align="center">
<img alt="Screenshot" src="./images/screenshot.gif" width="250"/>
</p>

License
--------

    Copyright 2023 Hamid Mahmoodi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
