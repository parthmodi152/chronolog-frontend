import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.cs346"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        javaHome = "/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home"
        mainClass = "com.cs346.chronolog.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb,TargetFormat.Exe)
            packageName = "Chronolog"
            packageVersion = "1.0.0"
            description = "Chronolog"
            copyright = "© 2023. All rights reserved."
            vendor = "cs346.love"

            windows {
                packageVersion = "1.0.0"
                msiPackageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
                iconFile.set(File("img/ic_launcher.ico"))
            }

            linux {
                packageVersion = "1.0.0"
                debPackageVersion = "1.0.0"
                rpmPackageVersion = "1.0.0"
                iconFile.set(File("img/ic_launcher.png"))
            }

            macOS{
                packageVersion = "1.0.0"
                dmgPackageVersion = "1.0.0"
                pkgPackageVersion = "1.0.0"
                dockName = "Chronolog"

                packageBuildVersion = "1.0.0"
                dmgPackageBuildVersion = "1.0.0"
                pkgPackageBuildVersion = "1.0.0"
                iconFile.set(File("img/ic_launcher.icns"))
            }
        }
    }
}
