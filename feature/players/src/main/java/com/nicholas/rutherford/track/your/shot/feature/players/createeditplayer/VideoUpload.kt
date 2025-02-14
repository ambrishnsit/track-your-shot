package com.nicholas.rutherford.track.your.shot.feature.players.createeditplayer

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("RememberReturnType")
@Composable
fun VideoPickerScreen(
    onVideoSelected: (Uri) -> Unit
) {
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Create temporary file for camera video
    val videoFile = remember {
        File(
            context.getExternalFilesDir(null),
            "Video_${
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(Date())
            }.mp4"
        )
    }

    // Uri for camera video
    val videoFileUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            videoFile
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success) {
            videoUri = videoFileUri
            onVideoSelected(videoFileUri)
        }
    }

    // Permission launcher for camera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(videoFileUri)
        }
    }


    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            videoUri = it
            onVideoSelected(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Record video button
        Button(
            onClick = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Record Video")
        }

        // Select from gallery button
        Button(
            onClick = {
                galleryLauncher.launch("video/*")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Select from Gallery")
        }

        // Preview selected video
        videoUri?.let { uri ->
            Text(
                "Selected Video: ${uri.lastPathSegment}",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

// Usage example:
@Composable
fun VideoUploadScreen() {
    VideoPickerScreen { uri ->
        // Handle the selected video URI
        println("Selected video URI: $uri")
        // You can now upload the video or process it further
    }
}