package com.voitov.pexels_app.data.datasource

import android.app.Application
import android.os.Environment
import android.widget.Toast
import com.voitov.pexels_app.data.network.ApiService
import com.voitov.pexels_app.data.network.dto.detailed_photo.PhotoDetailsDto
import com.voitov.pexels_app.data.network.dto.featured_collection.FeaturedCollectionsHolder
import com.voitov.pexels_app.data.network.dto.photo.PhotosHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val context: Application
) : RemoteDataSource {
    private val scope = CoroutineScope(Dispatchers.IO)
    override suspend fun getFeaturedCollections(): FeaturedCollectionsHolder {
        return apiService.getFeaturedCollections(1, 7)
    }

    override suspend fun getCuratedPhotos(): PhotosHolder {
        return apiService.getCuratedPhotos(1, 3)
    }

    override suspend fun searchForPhotos(query: String): PhotosHolder {
        return apiService.searchForPhotos(1, 3, query)
    }

    override suspend fun getPhotoDetails(id: Int): PhotoDetailsDto {
        return apiService.getPhotoDetails(id)
    }

    override fun downloadPhoto(url: String, onCompletedSuccessfully: (Boolean) -> Unit) {
        scope.launch {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val cacheFile = File(url)
                val newFile = File(directory, "${url}.jpg") // ${catEntity.id}
                outputStream = newFile.outputStream()
                inputStream = cacheFile.inputStream()
                val fileReader = ByteArray(1024 * 4)
                var isRead = true
                while (isRead) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        isRead = false
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Downloaded -> ${newFile.absolutePath}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        outputStream.write(fileReader, 0, read)
                    }
                }
                onCompletedSuccessfully(true)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
                onCompletedSuccessfully(false)
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        }
    }
}