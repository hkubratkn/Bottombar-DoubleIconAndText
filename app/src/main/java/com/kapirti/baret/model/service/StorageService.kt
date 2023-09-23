package com.kapirti.baret.model.service

interface StorageService {
    suspend fun getUser(): String
    suspend fun getAsset(uid: String): String

    suspend fun saveUser(photo: ByteArray)
    suspend fun saveAsset(photo: ByteArray, uid: String)

//    suspend fun deleteWork()
  //  suspend fun deleteUser()
}