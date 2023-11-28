package com.zivame.geektrustassignment.constant

sealed class AppConstants {
    class URLConstants: AppConstants() {
        companion object {
            const val API_BASE_URL = "https://findfalcone.geektrust.com/"
            const val PLANETS = "planets"
            const val VEHCILES = "vehicles"
            const val TOKEN = "token"
            const val FIND = "find"
        }
    }

    class NetworkConstants: AppConstants() {
        companion object {
            const val DEFAULT_ERROR_CODE = -1
        }
    }
}
