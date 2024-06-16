// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false  //  fỉrebase tự động lấy version tương ứng khi thêm thư viện
}