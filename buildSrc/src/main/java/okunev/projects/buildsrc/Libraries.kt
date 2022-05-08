package okunev.projects.buildsrc

object Libraries {
    const val androidCore = "androidx.core:core-ktx:${Versions.androidCore}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val viewBinding =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding}"

    const val textRecognizer =
        "com.google.android.gms:play-services-mlkit-text-recognition:${Versions.textRecognizer}"

    object ViewModel {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.viewModel}"
        const val common = "androidx.lifecycle:lifecycle-common-java8:${Versions.viewModel}"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.viewModel}"
    }

    const val jetpackNavigation =
        "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNavigation}"

    object Dagger {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object CameraX {
        const val core = "androidx.camera:camera-core:${Versions.camerax}"
        const val camera = "androidx.camera:camera-camera2:${Versions.camerax}"
        const val lifecycle = "androidx.camera:camera-lifecycle:${Versions.camerax}"
        const val video = "androidx.camera:camera-video:${Versions.camerax}"
        const val view = "androidx.camera:camera-view:${Versions.camerax}"
        const val ext = "androidx.camera:camera-extensions:${Versions.camerax}"
    }
}