package com.lupesoft.mvppattern.shared.model.dataAccess.utils.response

import com.lupesoft.mvppattern.shared.model.dataAccess.utils.Status

data class Resource<out T>(val status: Status, val data: T?, val code: Int?, val message: String?) {
    companion object {
        fun <T> success(data: T?, code: Int? = null, message: String? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, code, message)
        }

        fun <T> error(data: T?, code: Int, msg: String?): Resource<T> {
            return Resource(Status.ERROR, data, code, msg)
        }

        fun <T> loading(data: T?, message: String?): Resource<T> {
            return Resource(Status.LOADING, data, null, message)
        }
    }
}