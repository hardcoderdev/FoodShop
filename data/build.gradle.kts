plugins {
    id("android-library-convention")
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":foundation"))
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.serialization)
    implementation(libs.bundles.room.database)
    kapt(libs.room.compiler)
}