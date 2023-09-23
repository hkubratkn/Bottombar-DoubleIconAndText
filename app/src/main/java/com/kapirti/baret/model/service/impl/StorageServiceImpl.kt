package com.kapirti.baret.model.service.impl

import com.google.firebase.storage.FirebaseStorage
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.StorageService
import javax.inject.Inject
import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.kapirti.baret.model.service.trace
import kotlinx.coroutines.tasks.await

class StorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val storage: FirebaseStorage,
): StorageService {
    override suspend fun getUser(): String = storageReference().downloadUrl.await().toString()
    override suspend fun getAsset(uid: String): String = storageAsset(uid = uid).downloadUrl.await().toString()

    override suspend fun saveUser(photo: ByteArray): Unit = trace(SAVE_USER_TRACE) { storageUser().putBytes(photo).await() }
    override suspend fun saveAsset(photo: ByteArray, uid: String): Unit = trace(SAVE_ASSET_TRACE) { storageAsset(uid = uid).putBytes(photo).await() }



//    override suspend fun deleteUser(): Unit = trace(DELETE_USER_TRACE){ storageUser().delete().await() }
  //  override suspend fun deleteWork(): Unit = trace(DELETE_WORK_TRACE){ storageCar("").delete().await() }

    private fun storageReference(): StorageReference = storage.reference
    private fun storageUser(): StorageReference = storageReference().child(USER_STORAGE).child("${auth.currentUserId}.jpg")
    private fun storageAsset(uid: String): StorageReference = storageReference().child(auth.currentUserId).child("${uid}.jpg")

    companion object {
        private const val USER_STORAGE = "User"

        private const val SAVE_USER_TRACE = "saveUser"
        private const val SAVE_ASSET_TRACE = "saveAsset"

        private const val DELETE_USER_TRACE = "deleteUser"
        private const val DELETE_WORK_TRACE = "deleteWork"
    }
}