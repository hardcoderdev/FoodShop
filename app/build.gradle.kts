plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "hardcoder.dev.foodshop"
    compileSdk = 33

    defaultConfig {
        applicationId = "hardcoder.dev.foodshop"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
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