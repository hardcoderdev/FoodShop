@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    id("android-app-convention")
}

android {
    defaultConfig {
        applicationId = "hardcoder.dev.foodshop"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
}

dependencies {
    implementation(projects.presentation)
    implementation(projects.data)
    implementation(libs.bundles.common.android)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.di)
    implementation(libs.bundles.ui)
    implementation(libs.coil)
    implementation(libs.epic.adapter)
}