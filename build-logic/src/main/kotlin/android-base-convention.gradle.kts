@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

configure<BaseExtension> {
    namespace = buildAndroidNamespace()
    compileSdkVersion(BuildConstants.TARGET_ANDROID_SDK)

    defaultConfig {
        minSdk = BuildConstants.MIN_ANDROID_SDK
        targetSdk = BuildConstants.TARGET_ANDROID_SDK
        versionCode = BuildConstants.APP_VERSION_CODE
        versionName = BuildConstants.APP_VERSION_NAME
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(BuildConstants.JVM_TARGET)
        targetCompatibility = JavaVersion.toVersion(BuildConstants.JVM_TARGET)
    }

    if (this@configure is BaseAppModuleExtension) {
        buildFeatures.viewBinding = true
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = BuildConstants.JVM_TARGET
    }
}

fun Project.buildAndroidNamespace(): String {
    val pathToModule = project.path.replace(
        regex = "[:\\-]".toRegex(),
        replacement = "."
    )

    return (BuildConstants.PROJECT_DEVELOPER_NAME + "." + rootProject.name + pathToModule).lowercase()
}