package com.kapirti.baret.ui.presentation.user_profile

import com.google.firebase.Timestamp
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    logService: LogService,
//    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
//    private val profileDao: ProfileDao
): BaretViewModel(logService) {
  //  val uid = accountService.currentUserId
    //val profile: Flow<Profile> = profileDao.getProfile()

    val date = Timestamp.now()

    fun onDoneClick(
    //    chatId: String, text: String, who: String, openAndPopUp: (String, String) -> Unit,
  //      partnerName: String, partnerSurname: String, partnerPhoto: String, partnerUid: String,
//        profileName: String, profileSurname: String, profilePhoto: String
    ) {
        launchCatching {
      //      firestoreService.saveChatRow(chatId = chatId, ChatRow(text = text, who = who, date = date))
    //        firestoreService.saveUserChat(uid = uid, chatId = chatId, Chat(chatId = chatId, partnerName = partnerName, partnerSurname = partnerSurname, partnerPhoto = partnerPhoto, partnerUid = partnerUid, date = date.toDate()))
  //          firestoreService.saveUserChat(uid = partnerUid, chatId = chatId, Chat(chatId = chatId, partnerName = profileName, partnerSurname = profileSurname, partnerPhoto = profilePhoto, partnerUid = uid, date = date.toDate()))
//            openAndPopUp(CHATS_SCREEN, USER_PROFILE_SCREEN)
        }
    }
}