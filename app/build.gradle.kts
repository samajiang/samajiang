plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.android"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.mysql.connector.java)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.github.SheHuan:NiceImageView:1.0.5")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation ("io.github.youth5201314:banner:2.2.3")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    implementation ("com.baidu.lbsyun:BaiduMapSDK_Location:9.6.4")
    implementation ("com.baidu.lbsyun:BaiduMapSDK_Search:7.6.4")
    implementation(libs.datastore.core.android)
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}