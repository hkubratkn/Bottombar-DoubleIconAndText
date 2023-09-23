package com.kapirti.baret.model.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.kapirti.baret.core.constants.Constants.RENT
import com.kapirti.baret.core.constants.Constants.SELL
import com.kapirti.baret.core.data_store.CityRepository
import com.kapirti.baret.core.data_store.RentSellRepository
import com.kapirti.baret.core.data_store.TypeRepository
import com.kapirti.baret.model.Asset
import com.kapirti.baret.model.AssetProfile
import com.kapirti.baret.model.Feedback
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.User
import com.kapirti.baret.model.UserUid
import com.kapirti.baret.model.service.trace
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await


class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
    private val typeRepository: TypeRepository,
    private val cityRepository: CityRepository,
    private val rentSellRepository: RentSellRepository
): FirestoreService {
    override val usersAll: Flow<List<User>>
        get() =
            auth.currentUser.flatMapLatest { users ->
                userCollection().snapshots().map { snapshot -> snapshot.toObjects() }
            }
    override val assets: Flow<List<Asset>>
        get() =
            typeRepository.getType().flatMapLatest { type ->
                cityRepository.getCityScore().flatMapLatest { city ->
                    rentSellRepository.getRentSell().flatMapLatest { rentSell ->
                        assetCollection(city = city, type = type, rentSell = if(rentSell == RENT){ RENT } else{ SELL })
                            .snapshots()
                            .map { snapshot -> snapshot.toObjects() }
                    }
                }
            }


    override suspend fun getUser(uid: String): User? = userDocument(uid).get().await().toObject()

    override suspend fun saveUser(user: User): Unit = trace(SAVE_USER_TRACE) { userDocument(auth.currentUserId).set(user).await() }
    override suspend fun saveUserAsset(assetProfile: AssetProfile, uid: String): Unit = trace(SAVE_USER_ASSET_TRACE){ userAssetDocument(uid = uid).set(assetProfile).await() }
    override suspend fun saveAsset(asset: Asset, type: String, city: Int, rentSell: String): String = trace(SAVE_ASSET_TRACE) { assetCollection(type = type, city = city, rentSell = rentSell).add(asset).await().id }
    override suspend fun saveFeedback(feedback: Feedback): Unit = trace(SAVE_FEEDBACK_TRACE) { feedbackCollection().add(feedback).await() }

    override suspend fun updateUserOnline(value: Boolean): Unit = trace(UPDATE_USER_ONLINE_TRACE){ userDocument(auth.currentUserId).update(ONLINE_FIELD, value).await() }
    override suspend fun updateUserLastSeen(): Unit = trace(UPDATE_USER_LAST_SEEN_TRACE) { userDocument(auth.currentUserId).update(LAST_SEEN_FIELD, FieldValue.serverTimestamp()).await()}
    override suspend fun updateUserToken(token: String): Unit = trace(UPDATE_USER_TOKEN_TRACE) { userDocument(auth.currentUserId).update(TOKEN_FIELD, token).await() }
    override suspend fun updateUserDisplayName(newValue: String): Unit = trace(UPDATE_USER_DISPLAY_NAME_TRACE){ userDocument(auth.currentUserId).update(DISPLAY_NAME_FIELD, newValue).await()}
    override suspend fun updateUserName(newValue: String): Unit = trace(UPDATE_USER_NAME_TRACE){ userDocument(auth.currentUserId).update(NAME_FIELD, newValue).await() }
    override suspend fun updateUserSurname(newValue: String): Unit = trace(UPDATE_USER_SURNAME_TRACE){ userDocument(auth.currentUserId).update(SURNAME_FIELD, newValue).await() }
    override suspend fun updateUserContact(newValue: String): Unit = trace(UPDATE_USER_CONTACT_TRACE){ userDocument(auth.currentUserId).update(CONTACT_FIELD, newValue).await() }
    override suspend fun updateUserDescription(newValue: String): Unit = trace(UPDATE_USER_DESCRIPTION_TRACE){ userDocument(auth.currentUserId).update(DESCRIPTION_FIELD, newValue).await() }
    override suspend fun updateUserPhoto(photo: String): Unit = trace(UPDATE_USER_PHOTO_TRACE) { userDocument(auth.currentUserId).update(PHOTO_FIELD, photo).await() }




    override suspend fun deleteAccount(): Unit = trace(DELETE_ACCOUNT_TRACE) { deleteCollection().add(UserUid(id = auth.currentUserId)).await() }

    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
    private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
    private fun userAssetDocument(uid: String): DocumentReference = userDocument(auth.currentUserId).collection(ASSET_COLLECTION).document(uid)
    private fun assetCollection(type: String, city: Int, rentSell: String): CollectionReference = firestore.collection(type).document(city.toString()).collection(rentSell)
    private fun deleteCollection(): CollectionReference = firestore.collection(DELETE_COLLECTION)
    private fun feedbackCollection(): CollectionReference = firestore.collection(FEEDBACK_COLLECTION)


    companion object {
        private const val DATE_FIELD = "date"
        private const val ONLINE_FIELD = "online"
        private const val LAST_SEEN_FIELD = "lastSeen"
        private const val TOKEN_FIELD = "token"
        private const val DISPLAY_NAME_FIELD = "displayName"
        private const val NAME_FIELD = "name"
        private const val SURNAME_FIELD = "surname"
        private const val CONTACT_FIELD = "contact"
        private const val DESCRIPTION_FIELD = "description"
        private const val PHOTO_FIELD = "photo"

        private const val USER_COLLECTION = "User"
        private const val ASSET_COLLECTION = "Asset"
        private const val FEEDBACK_COLLECTION = "Feedback"
        private const val DELETE_COLLECTION = "Delete"

        private const val SAVE_USER_TRACE = "saveUser"
        private const val SAVE_USER_ASSET_TRACE = "saveUserAsset"
        private const val SAVE_ASSET_TRACE = "saveAsset"
        private const val SAVE_FEEDBACK_TRACE = "saveFeedback"

        private const val UPDATE_USER_ONLINE_TRACE = "updateUserOnline"
        private const val UPDATE_USER_LAST_SEEN_TRACE = "updateUserLastSeen"
        private const val UPDATE_USER_TOKEN_TRACE = "updateUserToken"
        private const val UPDATE_USER_DISPLAY_NAME_TRACE = "updateUSerDisplayName"
        private const val UPDATE_USER_NAME_TRACE = "updateUserName"
        private const val UPDATE_USER_SURNAME_TRACE = "updateUserSurname"
        private const val UPDATE_USER_CONTACT_TRACE = "updateUserContact"
        private const val UPDATE_USER_DESCRIPTION_TRACE = "updateUserDescription"
        private const val UPDATE_USER_PHOTO_TRACE = "updateUserPhoto"

        private const val DELETE_ACCOUNT_TRACE = "deleteAccount"
    }
}


/**
 *
 * package com.kapirti.baret.model.service.impl
 *
 * import com.google.firebase.firestore.CollectionReference
 * import com.google.firebase.firestore.DocumentReference
 * import com.google.firebase.firestore.FieldValue
 * import com.google.firebase.firestore.FirebaseFirestore
 * import com.google.firebase.firestore.ktx.snapshots
 * import com.google.firebase.firestore.ktx.toObject
 * import com.google.firebase.firestore.ktx.toObjects
 * import com.kapirti.baret.model.Feedback
 * import com.kapirti.baret.model.User
 * import com.kapirti.baret.model.UserUid
 * import com.kapirti.baret.model.service.AccountService
 * import com.kapirti.baret.model.service.FirestoreService
 * import com.kapirti.baret.model.service.trace
 * import kotlinx.coroutines.flow.Flow
 * import kotlinx.coroutines.flow.map
 * import javax.inject.Inject
 * import kotlinx.coroutines.tasks.await
 *
 * class FirestoreServiceImpl @Inject constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) : FirestoreService {
 *   //  override suspend fun cars(type: String): Flow<List<Car>> = carCollection(type).snapshots().map { snapshot -> snapshot.toObjects() }
 *
 *   //  override suspend fun saveUserCar(userCar: UserCar): Unit = trace(SAVE_USER_CAR_TRACE) { userCarDocument(uid = auth.currentUserId, carUid = userCar.uid).set(userCar).await() }
 *  //   override suspend fun saveCar(car: Car): String = trace(SAVE_CAR_TRACE) { carCollection(car.type).add(car).await().id }


 *
 *     private fun userCarDocument(uid: String, carUid: String): DocumentReference = userDocument(uid).collection(CAR_COLLECTION).document(carUid)
 *     private fun carCollection(carType: String): CollectionReference = firestore.collection(CAR_COLLECTION).document(carType).collection(carType)
 *
 *
 *
 *
 *
 *     }
 * }
 *
 *    /**
 *    override val usersAll: Flow<List<User>>
 *    get() =
 *    auth.currentUser.flatMapLatest { users ->
 *    userCollection().snapshots().map { snapshot -> snapshot.toObjects() }
 *    }
 *    override val userBlock: Flow<List<Block>>
 *    get() =
 *    auth.currentUser.flatMapLatest { user ->
 *    userBlockCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
 *    }
 *    override val chats: Flow<List<Chat>>
 *    get() =
 *    auth.currentUser.flatMapLatest { user ->
 *    userChatCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
 *    }
 *
 *    override val archiveChats: Flow<List<Chat>>
 *    get() =
 *    auth.currentUser.flatMapLatest { user ->
 *    userArchiveCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
 *    }
 *
 *
 *    override suspend fun getUserChat(uid: String, chatId: String): Chat? = chatCollection(uid).document(chatId).get().await().toObject()
 *    override suspend fun users(lang: String): Flow<List<User>> = userCollection().whereEqualTo(LANGUAGE_FIELD, lang).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
 *    override suspend fun chatRow(chatId: String): Flow<List<ChatRow>> = chatCollection(chatId).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
 *  override suspend fun saveUserChat(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_CHAT_TRACE){ userChatCollection(uid).document(chatId).set(chat).await() }
 *    override suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_ARCHIVE_TRACE){ userArchiveCollection(uid).document(chatId).set(chat).await()}
 *    override suspend fun saveChatRow(chatId: String, chatRow: ChatRow): Unit = trace(SAVE_CHAT_ROW) { chatCollection(chatId = chatId).add(chatRow).await() }
 *    override suspend fun saveFeedback(feedback: Feedback): Unit = trace(SAVE_FEEDBACK_TRACE){ feedbackCollection().add(feedback).await() }
 *
 *      override suspend fun updateUserChatUnreadZero(chatId: String): Unit = trace(UPDATE_USER_CHAT_UNREAD_ZERO_TRACE){ userChatCollection(auth.currentUserId).document(chatId).update(UNREAD_FIELD, 0).await() }
 *    override suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int): Unit = trace(UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE){ userChatCollection(uid).document(chatId).update(UNREAD_FIELD, unread).await() }
 *    override suspend fun block(uid: String, partnerUid: String, block: Block): Unit = trace(SAVE_BLOCK_USER) { userBlockDocument(uid, partnerUid).set(block).await() }
 *    override suspend fun report(uid: String, partnerUid: String, report: Report): Unit = trace(SAVE_REPORT) { userReportDocument(uid = uid, partnerUid = partnerUid).set(report).await() }
 *
 *    override suspend fun deleteChat(chatId: String) {
 *    val matchingChats = chatCollection(chatId).get().await()
 *    matchingChats.map { it.reference.delete().asDeferred() }.awaitAll()
 *    }
 *
 *    override suspend fun deleteUserChat(uid: String, chatId: String) { userChatCollection(uid = uid).document(chatId).delete().await() }
 *    override suspend fun deleteUserArchive(uid: String, chatId: String){ userArchiveCollection(uid).document(chatId).delete().await()}
 *
 *
 *
 *
 *    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
 *    private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
 *    private fun userChatCollection(uid: String): CollectionReference = userDocument(uid).collection(CHAT_COLLECTION)
 *    private fun userArchiveCollection(uid: String): CollectionReference = userDocument(uid).collection(ARCHIVE_COLLECTION)
 *    private fun userBlockCollection(uid: String): CollectionReference = userDocument(uid).collection(BLOCK_COLLECTION)
 *    private fun userBlockDocument(uid: String, partnerUid: String): DocumentReference = userBlockCollection(uid).document(partnerUid)
 *    private fun userReportDocument(uid: String, partnerUid: String): DocumentReference = userDocument(partnerUid).collection(REPORT_COLLECTION).document(uid)
 *    private fun chatCollection(chatId: String): CollectionReference = firestore.collection(CHAT_COLLECTION).document(chatId).collection(chatId)
 *
 *    companion object {
 *    private const val DATE_FIELD = "date"
 *
 *    private const val UNREAD_FIELD = "unread"
 *
 *    private const val BLOCK_COLLECTION = "Block"
 *    private const val REPORT_COLLECTION = "Report"
 *    private const val CHAT_COLLECTION = "Chat"
 *    private const val ARCHIVE_COLLECTION = "Archive"
 *
 *    private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
 *    private const val SAVE_USER_ARCHIVE_TRACE = "saveUserArchive"
 *    private const val SAVE_BLOCK_USER = "saveBlockUser"
 *    private const val SAVE_REPORT = "saveReport"
 *    private const val SAVE_CHAT_ROW = "saveChatRow"
 *    private const val SAVE_FEEDBACK_TRACE = "saveFeedback"
 *
 *    private const val UPDATE_USER_CHAT_UNREAD_ZERO_TRACE = "updateUserChatUnreadZero"
 *    private const val UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE = "updateUserChatUnreadIncrease"
 *
 *    }
 *    /**
 *    override suspend fun delete(taskId: String) {
 *    currentCollection(auth.currentUserId).document(taskId).delete().await()
 *    }
 *
 *    // https://firebase.google.com/docs/firestore/manage-data/delete-data#kotlin+ktx_2
 *    override suspend fun deleteAllForUser(userId: String) {
 *    val matchingTasks = currentCollection(userId).get().await()
 *    matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
 *    }
 *    */
 *    }
 *     * override val tasks: Flow<List<Task>>
 *         get() =
 *             auth.currentUser.flatMapLatest { user ->
 *                 currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
 *             }
 *
 *     override suspend fun getTask(taskId: String): Task? =
 *         currentCollection(auth.currentUserId).document(taskId).get().await().toObject()
 *
 *    override suspend fun save(task: Task): String =
 *    trace(SAVE_TASK_TRACE) { currentCollection(auth.currentUserId).add(task).await().id }
 *
 *     override suspend fun update(task: Task): Unit =
 *         trace(UPDATE_TASK_TRACE) {
 *             currentCollection(auth.currentUserId).document(task.id).set(task).await()
 *         }
 *
 *     override suspend fun delete(taskId: String) {
 *         currentCollection(auth.currentUserId).document(taskId).delete().await()
 *     }
 *
 *     override suspend fun deleteAllForUser(userId: String) {
 *         val matchingTasks = currentCollection(userId).get().await()
 *         matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
 *     }
 *
 *     private fun currentCollection(uid: String): CollectionReference =
 *         firestore.collection(USER_COLLECTION).document(uid).collection(TASK_COLLECTION)
 *     }*/
 *
 * */