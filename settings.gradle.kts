pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in gradle.properties as the password
                password = extra["MAPBOX_DOWNLOADS_TOKEN"].toString()
            }
        }
    }
}
rootProject.name = "BikeMap"
include(":app")
include(":feature:map")
include(":feature:home")
include(":utility:navigation")
include(":feature:record")
include(":data:location")
include(":utility:service")
include(":utility:permission")
include(":utility:notification")
