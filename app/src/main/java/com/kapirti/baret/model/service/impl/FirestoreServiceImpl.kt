package com.kapirti.baret.model.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.User
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await


class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
): FirestoreService {
    override val usersAll: Flow<List<User>>
        get() =
            auth.currentUser.flatMapLatest { users ->
                userCollection().snapshots().map { snapshot -> snapshot.toObjects() }
            }


    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)

    companion object {
        private const val USER_COLLECTION = "User"
    }
}