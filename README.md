# Accordion 🪗

🚧WIP🚧

A lightweight Jetpack Compose library that helps you:

1. Squeeze composables using a unique effect
2. Automatically resize composables to fit available space

# Screenshots 👀

Here are some screenshots of the library in action:
the first one is original composable and others are the squeezed version of it.

![Logo](images/logo.png) ![Folded](images/logo-folded.png) ![Folded More](images/logo-folded-more.png)

## Usage 🛠️

Basic implementation:

```kotlin
BasicText(
    modifier = Modifier.accordion(height = { original -> original * 0.5f }),
    text = "1\n2\n3\n4",
    style = TextStyle.Default.copy(fontSize = 60.sp)
)
```

More samples and usage examples is available
in [this file](sample/src/main/kotlin/io/amirhparhizgar/accordion/sample/Previews.kt)

## Installation 📦

Currently not published to Maven Central or JitPack. To use:

1. Clone this repository
2. Add the `accordion` module to your project or just copy source files
3. Implement as shown in usage examples

## License

```
Copyright 2025 Amirhossein Parhizgar
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```