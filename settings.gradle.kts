enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()

        maven {
            setUrl("https://jitpack.io")
            setUrl("https://kotlin.bintray.com/kotlinx")
            setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            setUrl("https://chaquo.com/maven-test")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "PyComposeUIApp"

include(":app")
///include(":pycomposeui")
//include(":python-multiplatform")
