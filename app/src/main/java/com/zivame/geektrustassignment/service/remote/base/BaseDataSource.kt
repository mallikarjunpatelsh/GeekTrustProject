package com.zivame.geektrustassignment.service.remote.base

import android.content.Context
import com.zivame.geektrustassignment.R
import com.zivame.geektrustassignment.constant.AppConstants.NetworkConstants.Companion.DEFAULT_ERROR_CODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * This class is for entire project for all the modules
 */
abstract class BaseDataSource(private val context: Context) {

    /**
     * This function will make it separate success and failure cases as well as handle to exceptions
     */
    protected fun <T> getResult(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body: T? = response.body()
                if (body != null) {
                    emit(Resource.success(body, response.message(), response.code(), false))
                } else {
                    emit(
                        error(
                            response.body(),
                            context.getString(R.string.something_went_wrong),
                            response.code()
                        )
                    )
                }
            } else {
                emit(
                    error(
                        response.body(),
                        context.getString(R.string.something_went_wrong),
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                error(
                    message = context.getString(R.string.something_went_wrong),
                    code = DEFAULT_ERROR_CODE
                )
            )
        }
    }


    private fun <T> error(body: T? = null, message: String, code: Int): Resource<T> {
        return Resource.error(body, message, code, false)
    }
}