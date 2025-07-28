package dev.sahildesai.theweatherapp.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import dev.sahildesai.theweatherapp.R

@Composable
fun LoadImageFromUrl(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Crop
){
    AsyncImage(
        model = imageUrl,
        error = painterResource(R.drawable.ic_error),
        contentDescription = title,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.ic_downloading)
    )
}

@Composable
fun LoadingData(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

