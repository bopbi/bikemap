plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.arjunalabs.bikemap"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":feature:map"))
    implementation(project(":feature:home"))
    implementation(project(":utility:navigation"))

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")

    implementation("com.github.Zhuinden:simple-stack:2.6.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:core-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:navigator-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:services:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:services-ktx:2.2.4")

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}