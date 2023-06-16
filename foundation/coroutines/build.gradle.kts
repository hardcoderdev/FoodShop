plugins {
    id("android-library-convention")
}

dependencies {
    api(libs.bundles.kotlin.coroutines)
    api(libs.fragment.ktx)
}