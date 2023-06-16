plugins {
    id("android-library-convention")
}

dependencies {
    api(projects.domain)
    implementation(libs.bundles.common.android)
    implementation(libs.bundles.adapter.delegates)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.ui)
}