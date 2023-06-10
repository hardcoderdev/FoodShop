plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("android-app-convention")
}

android {
    namespace = "hardcoder.dev.foodshop"
    compileSdk = 33

    defaultConfig {
        applicationId = "hardcoder.dev.foodshop"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            @Suppress("UnstableApiUsage")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
    implementation(libs.bundles.common.android)
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.di)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.adapter.delegates)
    implementation(libs.bundles.retrofit)
}