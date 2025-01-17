plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "zamoranogarcia.juanjose.pokemospmdm"
    compileSdk = 34

    defaultConfig {
        applicationId = "zamoranogarcia.juanjose.pokemospmdm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Añado Firebase Authentication
    implementation("com.google.firebase:firebase-auth:21.3.0")

    // Añadimos Firebase Firestore
    implementation("com.google.firebase:firebase-firestore:24.8.1")

    // Añadimos Retrofit (API REST)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Añadimos Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.squareup.picasso:picasso:2.71828")

    // autentificacion con google
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    implementation ("com.google.android.material:material:1.9.0")

}