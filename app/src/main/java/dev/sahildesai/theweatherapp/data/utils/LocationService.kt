package dev.sahildesai.theweatherapp.data.utils

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class LocationService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->

        if (!hasPermission()) {
            cont.resume(null) {}
            return@suspendCancellableCoroutine
        }

        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            cont.resume(location) { cancellationTokenSource.cancel() }
        }.addOnFailureListener {
            cont.resume(null) { cancellationTokenSource.cancel() }
        }
    }

    private fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}