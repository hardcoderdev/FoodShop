enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        versionCatalogs {
            create("libs") {
                from(files("libs.versions.toml"))
            }
        }
    }
}

rootProject.name = "FoodShop"

include(
    ":app",
    ":data",
    ":domain",
    ":presentation",
    ":foundation"
)
include(":foundation:coroutines")
