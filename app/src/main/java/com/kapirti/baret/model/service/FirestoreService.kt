package com.kapirti.baret.model.service

import com.kapirti.baret.model.User
import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    val usersAll: Flow<List<User>>
}
/**
package com.kapirti.baret.model.service

import com.kapirti.baret.model.Feedback
import com.kapirti.baret.model.User
import kotlinx.coroutines.flow.Flow

suspend fun getUser(uid: String): User?
//    suspend fun cars(type: String): Flow<List<Car>>

suspend fun saveUser(user: User)
//    suspend fun saveUserCar(userCar: UserCar)
//    suspend fun saveCar(car: Car): String
suspend fun saveFeedback(feedback: Feedback)

suspend fun updateUserOnline(value: Boolean)
suspend fun updateUserLastSeen()
suspend fun updateUserToken(token: String)
suspend fun updateUserDisplayName(newValue: String)
suspend fun updateUserName(newValue: String)
suspend fun updateUserSurname(newValue: String)
suspend fun updateUserBirthday(newValue: String)
suspend fun updateUserContact(newValue: String)
suspend fun updateUserDescription(newValue: String)
suspend fun updateUserPhoto(photo: String)

suspend fun deleteAccount()
}
/**
val usersAll: Flow<List<User>>
val userBlock: Flow<List<Block>>
val chats: Flow<List<Chat>>
val archiveChats: Flow<List<Chat>>

suspend fun users(lang: String): Flow<List<User>>
suspend fun chatRow(chatId: String): Flow<List<ChatRow>>

suspend fun getUserChat(uid: String, chatId: String): Chat?

suspend fun saveUserChat(uid: String, chatId: String, chat: Chat)
suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat)
suspend fun saveChatRow(chatId: String, chatRow: ChatRow)


suspend fun updateUserChatUnreadZero(chatId: String)
suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int)

suspend fun block(uid: String, partnerUid: String, block: Block)
suspend fun report(uid: String, partnerUid: String, report: Report)

suspend fun deleteChat(chatId: String)
suspend fun deleteUserChat(uid: String, chatId: String)
suspend fun deleteUserArchive(uid: String, chatId: String)
/**
suspend fun delete(taskId: String)
suspend fun deleteAllForUser(userId: String)*/
}
 *
 *
 * val tasks: Flow<List<Task>>
suspend fun getTask(taskId: String): Task?
suspend fun save(task: Task): String
suspend fun update(task: Task)
suspend fun delete(taskId: String)
suspend fun deleteAllForUser(userId: String)*/

 */