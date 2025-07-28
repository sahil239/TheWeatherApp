package dev.sahildesai.theweatherapp.data.utils

import retrofit2.Response

// A sealed class representing either a successful result or an error.
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failure(val code: Int? = null, val errorMessage: String) : ApiResult<Nothing>()
}

// A helper function to safely call a suspend function that returns a Retrofit Response.
suspend fun <T : Any> parseAPICall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Failure(response.code(), "Empty response body")
            }
        } else {
            ApiResult.Failure(response.code(), response.message())
        }
    } catch (e: Exception) {
        ApiResult.Failure(null, e.localizedMessage ?: "Unknown error occurred")
    }
}