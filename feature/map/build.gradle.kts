plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 24
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":utility:navigation"))

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    testImplementation("junit:junit:4.13.2")

    implementation("com.mapbox.maps:android:10.7.0")

    implementation("com.github.Zhuinden:simple-stack:2.6.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:core-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:fragments-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:navigator-ktx:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:services:2.2.4")
    implementation("com.github.Zhuinden.simple-stack-extensions:services-ktx:2.2.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}