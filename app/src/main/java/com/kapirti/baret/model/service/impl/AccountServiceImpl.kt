package com.kapirti.baret.model.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.kapirti.baret.model.service.AccountService
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import com.kapirti.baret.model.UserUid
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {
    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val currentUserEmail: String
        get() = auth.currentUser?.email.orEmpty()

    override val currentUserDisplayName: String
        get() = auth.currentUser?.displayName.orEmpty()


    override val currentUser: Flow<UserUid>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { UserUid(it.uid) } ?: UserUid())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) { auth.signInWithEmailAndPassword(email, password).await() }
    override suspend fun sendRecoveryEmail(email: String) { auth.sendPasswordResetEmail(email).await() }
    override suspend fun linkAccount(email: String, password: String) { auth.createUserWithEmailAndPassword(email, password).await() }
    override suspend fun displayName(newValue: String){
        val profileUpdates = userProfileChangeRequest {
            displayName = newValue
        }
        auth.currentUser?.let { it.updateProfile(profileUpdates).await() }
    }
    override suspend fun signOut() { auth.signOut() }
    override suspend fun deleteAccount() { auth.currentUser!!.delete().await() }
}