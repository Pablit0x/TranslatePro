plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version Dependencies.kotlinVersion
}

android {
    namespace = "com.ps.translatepro.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.ps.translatepro.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner ="com.ps.translatepro.TestHiltRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeFoundation)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeIconsExtended)
    implementation(Dependencies.composeNavigation)
    implementation(Dependencies.coilCompose)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)

    implementation(Dependencies.ktorAndroid)

    androidTestImplementation(Dependencies.testRunner)
    androidTestImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.composeTesting)
    androidTestImplementation(Dependencies.rules)
    debugImplementation(Dependencies.composeTestManifest)

    kaptAndroidTest(Dependencies.hiltAndroidCompiler)
    androidTestImplementation(Dependencies.hiltTesting)
}