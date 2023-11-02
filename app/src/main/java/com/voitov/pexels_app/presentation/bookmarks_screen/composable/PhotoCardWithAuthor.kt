package com.voitov.pexels_app.presentation.bookmarks_screen.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.voitov.pexels_app.R
import com.voitov.pexels_app.presentation.component.PhotoCard
import com.voitov.pexels_app.presentation.ui.theme.DarkGrayDarkShade
import com.voitov.pexels_app.presentation.ui.theme.DarkGrayLightShade
import com.voitov.pexels_app.presentation.ui.theme.Pexels_appTheme

@Composable
fun PhotoCardWithAuthor(
    imageUrl: String,
    author: String,
    onRenderFailed: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    @DrawableRes placeholder: Int = R.drawable.placeholder,
    placeholderTint: Color = if (isSystemInDarkTheme()) DarkGrayLightShade else DarkGrayDarkShade,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    onBeingLoadedColor: Color = MaterialTheme.colorScheme.secondary,
    contentScale: ContentScale = ContentScale.Crop,
) {
    PhotoCard(
        imageUrl = imageUrl,
        onRenderFailed = onRenderFailed,
        modifier = modifier,
        onClick = onClick,
        placeholder = placeholder,
        placeholderTint = placeholderTint,
        shape = shape,
        onBeingLoadedColor = onBeingLoadedColor,
        contentScale = contentScale,
        uiHoveredOverPhotoCard = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(top = 6.dp, bottom = 8.dp)
                    .align(Alignment.BottomCenter), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = Color.White
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewPhotoCardWithAuthor() {
    Pexels_appTheme {
        PhotoCardWithAuthor(imageUrl = "", author = "Someone", onRenderFailed = {})
    }
}