import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.tzel.movieflix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tzel.movieflix"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    val localProperties = Properties()
    localProperties.load(project.rootProject.file("local.properties").inputStream())

    flavorDimensions += listOf("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "MovieFlix DEV")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY_NAME")}\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/\"")
        }
        create("prod") {
            dimension = "env"
            resValue("string", "app_name", "MovieFlix")
            buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY_NAME")}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    buildFeatures {
        buildConfig = true
    }

    applicationVariants.all {
        outputs.all {
            val output = (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
            output.outputFileName = "${flavorName}_${versionName}_MOVIEFLIX_${buildType.name}.apk"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.activity.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.hilt.navigation.compose)

    implementation(libs.timber)

    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.converter.moshi)

    implementation(libs.coil.compose)

    implementation(libs.androidx.datastore)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // Testing and debug
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}