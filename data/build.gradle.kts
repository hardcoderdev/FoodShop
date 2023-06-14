plugins {
    id("android-library-convention")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    implementation(projects.domain)
    implementation(projects.foundation.coroutines)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.serialization)
    implementation(libs.bundles.room.database)
    kapt(libs.room.compiler)
}