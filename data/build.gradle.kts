plugins {
    id("android-library-convention")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(projects.domain)
    api(projects.foundation.coroutines)
    api(libs.bundles.retrofit)
    api(libs.kotlin.serialization)
    api(libs.bundles.room.database)
    kapt(libs.room.compiler)
}