package dev.sahildesai.theweatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun RequestLocationPermissionIfNeeded(
    permissionState: PermissionState,
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        onPermissionResult(granted)
    }

    LaunchedEffect(permissionState) {
        if (permissionState == PermissionState.UNKNOWN) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (granted) onPermissionResult(true)
            else launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}