package com.nicholas.rutherford.track.your.shot.feature.players.createeditplayer.ext

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nicholas.rutherford.track.your.shot.feature.players.createeditplayer.CreateEditPlayerParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadPlayerImageContent(
    hasUploadedImage: Boolean,
    scope: CoroutineScope,
    bottomState: ModalBottomSheetState,
    createEditPlayerParams: CreateEditPlayerParams,
    imageUri: Uri?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (hasUploadedImage && imageUri != null || createEditPlayerParams.state.editedPlayerUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUri ?: createEditPlayerParams.state.editedPlayerUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        createEditPlayerParams.onImageUploadClicked.invoke(imageUri)
                        scope.launch { bottomState.show() }
                    }
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Add a photo icon",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        createEditPlayerParams.onImageUploadClicked.invoke(imageUri)
                        scope.launch { bottomState.show() }
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadPlayerVideoContent (
    hasUploadedVideo: Boolean,
    scope: CoroutineScope,
    bottomState: ModalBottomSheetState,
    createEditPlayerParams: CreateEditPlayerParams,
    videoUri: Uri?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (hasUploadedVideo && videoUri != null || createEditPlayerParams.state.editedPlayerUrl.isNotEmpty()) {
            AsyncImage(
                model = videoUri ?: createEditPlayerParams.state.editedPlayerUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        createEditPlayerParams.onVideoUploadClicked.invoke(videoUri)
                        scope.launch { bottomState.show() }
                    }
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.VideoFile,
                contentDescription = "Add a video icon",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        createEditPlayerParams.onVideoUploadClicked.invoke(videoUri)
                        scope.launch { bottomState.show() }
                    }
            )
        }
    }
}
