plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "io.github.thisisthepy"
version = "3.11.6"

fun generateCinterrpDefinition(): File {
    val pythonPath = "$rootDir/app/dist/root/python3/include/python$version"
    val arm64IncludePath = "$rootDir/app/dist/include/iphoneos"
    val x64IncludePath = "$rootDir/app/dist/include/iphonesimulator"
    val arm64LibraryPath = "$rootDir/app/dist/lib/iphoneos"
    val x64LibraryPath = "$rootDir/app/dist/lib/iphonesimulator"
    val projectPath = "$projectDir"
    val buildPath = "build/PythonFFI"
    val defFilePath = "$buildPath/PythonFFI.def"

    val script = """
    language = Objective-C
    headers = $projectPath/PythonFFI/PythonFFI.h $pythonPath/Python.h

    compilerOpts.arm64 = -I$projectPath/PythonFFI -I$pythonPath -I$arm64IncludePath
    compilerOpts.x64 = -I$projectPath/PythonFFI -I$pythonPath -I$x64IncludePath
    
    staticLibraries = libpython$version.a libcrypto.a libffi.a libpyobjus.a libssl.a
    libraryPaths.arm64 = $arm64LibraryPath
    libraryPaths.x64 = $x64LibraryPath

    linkerOpts.arm64 = -L/usr/lib/swift -platform_version ios 13.0 13.0 -L/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/swift/iphoneos
    linkerOpts.x64 = -L/usr/lib/swift -platform_version ios-simulator 13.0 13.0 -L/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/swift/iphonesimulator
    """

    val buildDir = project.file(buildPath)
    buildDir.mkdirs()
    val defFile = project.file(defFilePath)
    if (!defFile.exists()) {
        defFile.createNewFile()
    }
    defFile.writeText(script)

    return defFile
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    val defFile = generateCinterrpDefinition()

    ios()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.compilations {
            val main by getting {
                cinterops {
                    val PythonFFI by creating {
                        defFile(defFile)
                        packageName("$group.python.ffi")
                    }
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}
