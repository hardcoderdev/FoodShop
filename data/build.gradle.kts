plugins {
    id("android-library-convention")
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(projects.domain)
    implementation(projects.foundation.coroutines)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.serialization)
    implementation(libs.bundles.room.database)
    kapt(libs.room.compiler)
}