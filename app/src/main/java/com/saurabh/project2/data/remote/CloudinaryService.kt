package com.saurabh.project2.data.remote

import android.content.Context
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.nio.charset.Charset
import kotlin.coroutines.resume

class CloudinaryService(private val context: Context) {

    private fun isMediaManagerInitialized(): Boolean {
        return try {
            MediaManager.get() // Throws if not initialized
            true
        } catch (e: Exception) {
            false
        }
    }
    init {
        if (!isMediaManagerInitialized()) {
            val config = loadCloudinaryConfig()
            MediaManager.init(context, config)
        }
    }

    private fun loadCloudinaryConfig(): Map<String, String> {
        val inputStream = context.resources.openRawResource(
            context.resources.getIdentifier("cloudinary_config", "raw", context.packageName)
        )
        val json = inputStream.readBytes().toString(Charset.defaultCharset())
        val jsonObject = JSONObject(json)

        return mapOf(
            "cloud_name" to jsonObject.getString("cloud_name"),
            "api_key" to jsonObject.getString("api_key"),
            "api_secret" to jsonObject.getString("api_secret")
        )
    }

    suspend fun uploadImage(imagePath: String, folder: String): Result<String> =
        suspendCancellableCoroutine { cont ->
            MediaManager.get().upload(imagePath)
                .option("folder", folder)
                .option("resource_type", "auto")
                .option("quality", "auto")
                .option("fetch_format", "auto")
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {}

                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                    override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                        val url = resultData?.get("secure_url") as? String
                        cont.resume(Result.success(url ?: ""))
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        cont.resume(Result.failure(Exception(error?.description ?: "Upload failed")))
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                        cont.resume(Result.failure(Exception(error?.description ?: "Upload rescheduled")))
                    }
                })
                .dispatch()
        }
}
