package com.zivame.geektrustassignment.service.remote.base


data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code: Int, val state: Boolean) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T, message: String, code: Int, state: Boolean): Resource<T> {
            return Resource(Status.SUCCESS, data, message, code, state)
        }

        fun <T> error(data: T? = null, message: String, code: Int, state: Boolean): Resource<T> {
            return Resource(Status.ERROR, data, message, code, state)
        }

        fun <T> loading(state: Boolean): Resource<T> {
            return Resource(Status.LOADING, null, null, -1, state)
        }
    }
}