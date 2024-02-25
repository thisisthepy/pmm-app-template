import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetpack.compose)
    //alias(libs.plugins.kotlin.cocoapods)

    alias(libs.plugins.chaquo.python)
}

group = "io.github.thisisthepy"
version = "1.0.0"

chaquopy {
    defaultConfig {
        version = "3.11"
    }
    sourceSets {
        getByName("main") {
            srcDir("src/androidMain/python")
        }
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
        //withJava()
    }
    val osName = System.getProperty("os.name")
    val targetOs = when {
        osName == "Mac OS X" -> "macos"
        osName.startsWith("Win") -> "windows"
        osName.startsWith("Linux") -> "linux"
        else -> error("Unsupported OS: $osName")
    }
    val targetArch = when (val osArch = System.getProperty("os.arch")) {
        "x86_64", "amd64" -> "x64"
        "aarch64" -> "arm64"
        else -> error("Unsupported arch: $osArch")
    }
    val skikoVersion = libs.versions.skiko.get()
    val skikoTarget = "${targetOs}-${targetArch}"

    js(IR) {
        browser {
            useCommonJs()
            binaries.executable()
        }
    }

    ios()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //api(projects.pycomposeui)
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(compose.materialIconsExtended)
                api(compose.material3)
                implementation(libs.ktor.core)
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.core)
                implementation(libs.ktor.jvm)
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.ui.tooling.preview)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.ui.test.junit)
                implementation("androidx.test.ext:junit:1.1.3")
                implementation(libs.androidx.espresso.core)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.skiko:skiko-awt-runtime-$skikoTarget:$skikoVersion")
                api(compose.preview)
                implementation(libs.ktor.jvm)
            }
        }
        val desktopTest by getting
        val jsMain by getting {
            dependencies {
                api(compose.html.core)
                implementation(libs.ktor.js)
                implementation(libs.ktor.jsonjs)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                api("io.github.thisisthepy:python-multiplatform:3.11.6.0")
                implementation(libs.ktor.ios)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "$group.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "$group.android"
        minSdk = 24
        versionCode = 1
        versionName = version.toString()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    sourceSets {
        getByName("main") {
            kotlin.srcDirs("src/androidMain/kotlin")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
            resources.srcDirs("src/commonMain/resources")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "$group.desktop"
            packageVersion = version.toString()
        }
    }
}

compose.experimental {
    web.application {}
}

afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}
