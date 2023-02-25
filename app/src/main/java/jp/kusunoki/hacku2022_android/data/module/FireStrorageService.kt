package jp.kusunoki.hacku2022_android.data.module

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.*
import javax.inject.Inject

class FireStorageService @Inject constructor() :FireStorageServiceInterface {
    private val instance = Firebase.storage
    private val ref = instance.reference
    override fun uploadImage(
        bytes: ByteArray,
        onCompleteListener: (it: Task<Uri>) -> Unit,
        onFailureListener: (e: Exception) -> Unit
    ) {
        val mountedRef = ref.child(
            "images/" + UUID.randomUUID().toString() + ".jpg"
        )
        val task = mountedRef.putBytes(bytes)
        task.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            mountedRef.downloadUrl
        }.addOnCompleteListener(
            onCompleteListener
        ).addOnFailureListener(onFailureListener)
    }
}

interface FireStorageServiceInterface{
    fun uploadImage(
        bytes: ByteArray,
        onCompleteListener: (it: Task<Uri>) -> Unit = {},
        onFailureListener: (e: Exception) -> Unit = {}
    )
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class FireStorageModule {
    @Binds
    abstract fun provideFireStorage(
        fireStorageService: FireStorageService
    ): FireStorageServiceInterface
}