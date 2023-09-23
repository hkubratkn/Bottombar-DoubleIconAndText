package com.kapirti.baret.ui.presentation.edit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.kapirti.baret.core.constants.Constants.PROFILE_SCREEN
import com.kapirti.baret.core.constants.Constants.RENT
import com.kapirti.baret.core.constants.Constants.SELL
import com.kapirti.baret.core.constants.Constants.SPLASH_SCREEN
import com.kapirti.baret.core.constants.EditType.CAR_SELL
import com.kapirti.baret.core.constants.EditType.CONTACT_DESCRIPTION
import com.kapirti.baret.core.constants.EditType.DISPLAY_NAME
import com.kapirti.baret.core.constants.EditType.FEEDBACK
import com.kapirti.baret.core.constants.EditType.LOCAL_SHIPPING_RENT
import com.kapirti.baret.core.constants.EditType.LOCAL_SHIPPING_SELL
import com.kapirti.baret.core.constants.EditType.NAME_SURNAME
import com.kapirti.baret.core.constants.EditType.PHOTO
import com.kapirti.baret.core.constants.EditType.PROFILE
import com.kapirti.baret.core.constants.EditType.WORK_MACHINE_RENT
import com.kapirti.baret.core.constants.EditType.WORK_MACHINE_SELL
import com.kapirti.baret.core.data_store.CityRepository
import com.kapirti.baret.core.data_store.EditRepository
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.core.room.profile.ProfileDao
import com.kapirti.baret.model.Asset
import com.kapirti.baret.model.AssetProfile
import com.kapirti.baret.model.Feedback
import com.kapirti.baret.model.User
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.model.service.StorageService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val storageService: StorageService,
    private val cityRepository: CityRepository,
    private val editRepository: EditRepository,
    private val editTypeRepository: EditTypeRepository,
    private val profileDao: ProfileDao
): BaretViewModel(logService) {
    val uid = accountService.currentUserId
    val profile: Flow<Profile> = profileDao.getProfile()

    private val _editType = mutableStateOf<String?>(null)
    val editType: String?
        get() = _editType.value

    private val _displayName = mutableStateOf<String?>(null)
    val displayName: String?
        get() = _displayName.value

    private val _name = mutableStateOf<String?>(null)
    val name: String?
        get() = _name.value

    private val _surname = mutableStateOf<String?>(null)
    val surname: String?
        get() = _surname.value

    private val _description = mutableStateOf<String?>(null)
    val description: String?
        get() = _description.value

    private val _contact = mutableStateOf<String?>(null)
    val contact: String?
        get() = _contact.value

    private val _avatar = mutableStateOf<String?>(null)
    val avatar: String?
        get() = _avatar.value




    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value

    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap
        get() = _bitmap.value


    private val _city = mutableStateOf<Int?>(null)
    val city: Int?
        get() = _city.value

    init {
        launchCatching {
            editTypeRepository.readEditTypeState().collect {
                _editType.value = it
                cityRepository.getCityScore().collect { itCity ->
                    _city.value = itCity
                }
            }
        }
    }


    fun onDisplayNameChange(newValue: String) {
        _displayName.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onNameChange(newValue: String) {
        _name.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onSurnameChange(newValue: String) {
        _surname.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onDescriptionChange(newValue: String) {
        _description.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onContactChange(newValue: String) {
        _contact.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onAvatarChange(newValue: String) {
        _avatar.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onCityChange(newValue: Int) {
        launchCatching {
            cityRepository.changeCity(newValue)
            _isNextEnabled.value = getIsNextEnabled()
        }
    }
    fun onSelfieResponse(uri: Uri) {
        _selfieUri.value = uri
        _isNextEnabled.value = getIsNextEnabled()
    }


    private val questionOrder: List<SurveyQuestion> = when (_editType.value){
        PROFILE -> listOf(
            SurveyQuestion.DISPLAY_NAME,
            SurveyQuestion.NAME_SURNAME,
            SurveyQuestion.CONTACT_DESCRIPTION,
            SurveyQuestion.AVATAR)
        DISPLAY_NAME -> listOf(SurveyQuestion.DISPLAY_NAME)
        NAME_SURNAME -> listOf(SurveyQuestion.NAME_SURNAME)
        CONTACT_DESCRIPTION -> listOf(SurveyQuestion.CONTACT_DESCRIPTION)
        CAR_SELL -> listOf(
            SurveyQuestion.CAR_TYPE,
            SurveyQuestion.CITY,
            SurveyQuestion.TITLE_DESCRIPTION,
            SurveyQuestion.PRICE,
            SurveyQuestion.TAKE_SELFIE)
        LOCAL_SHIPPING_SELL, LOCAL_SHIPPING_RENT -> listOf(
            SurveyQuestion.LOCAL_SHIPPING_TYPE,
            SurveyQuestion.CITY,
            SurveyQuestion.TITLE_DESCRIPTION,
            SurveyQuestion.PRICE,
            SurveyQuestion.TAKE_SELFIE)
        WORK_MACHINE_SELL, WORK_MACHINE_RENT -> listOf(
            SurveyQuestion.WORK_MACHINE_TYPE,
            SurveyQuestion.CITY,
            SurveyQuestion.TITLE_DESCRIPTION,
            SurveyQuestion.PRICE,
            SurveyQuestion.TAKE_SELFIE)
        PHOTO -> listOf(SurveyQuestion.TAKE_SELFIE)
        FEEDBACK -> listOf(SurveyQuestion.FEEDBACK)
        else -> emptyList()
    }

    private var questionIndex = 0

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    private val _isSurveyComplete = mutableStateOf(false)
    val isSurveyComplete: Boolean
        get() = _isSurveyComplete.value


    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }


    fun onDonePressed(token: String?, profile: Profile?, context: Context, popUpScreen: () -> Unit, restartApp: (String) -> Unit,) {
        when (_editType.value) {
            PROFILE -> { saveProfile(token = token ?: "", restartApp) }
            DISPLAY_NAME -> { saveDisplayName( restartApp = restartApp) }
            NAME_SURNAME -> { saveNameSurname(profile = profile,  restartApp = restartApp) }
            CONTACT_DESCRIPTION -> { saveContactDescription(profile = profile,  restartApp = restartApp) }
            PHOTO -> { saveProfileBitmap(context = context, profile = profile, restartApp = restartApp) }

            CAR_SELL -> { saveCar(context = context, restartApp = restartApp)}
            LOCAL_SHIPPING_SELL, LOCAL_SHIPPING_RENT -> { saveLocalShipping(context = context, restartApp = restartApp)}
            WORK_MACHINE_SELL, WORK_MACHINE_RENT -> { saveWorkMachine(context = context, restartApp = restartApp)}

            FEEDBACK -> { feedbackSave(popUpScreen) }
        }
    }
    private fun saveProfile(token: String, restartApp: (String) -> Unit,){
        launchCatching {
            accountService.displayName(_displayName.value ?: "")
            firestoreService.saveUser(
                User(
                    displayName = _displayName.value ?: "",
                    name = _name.value ?: "",
                    surname = _surname.value ?: "",
                    photo = _avatar.value ?: "",
                    description = _description.value ?: "",
                    contact = _contact.value ?: "",
                    completed = true,
                    online = true,
                    uid = uid,
                    token = token,
                    date = Timestamp.now()
                )
            )
            editRepository.completeT()
            profileDao.insert(
                Profile(
                    namedb = _name.value ?: "",
                    surnamedb = _surname.value ?: "",
                    contactdb = _contact.value ?: "",
                    descriptiondb = _description.value ?: "",
                    photodb = _avatar.value ?: "",
                )
            )
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveProfileBitmap(context: Context, profile: Profile?, restartApp: (String) -> Unit){
        launchCatching {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveProfileInside(restartApp, profile = profile)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveProfileInside(restartApp, profile = profile)
                }
            }
        }
    }
    private fun saveProfileInside(restartApp: (String) -> Unit, profile: Profile?){
        launchCatching {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()

                storageService.saveUser(byteDizisi)
                val link = storageService.getUser()
                firestoreService.updateUserPhoto(link)
                profile?.let{ profileDao.delete(profile) }
                val user = firestoreService.getUser(accountService.currentUserId)
                if (user != null) {
                    profileDao.insert(
                        Profile(
                            namedb = user.name,
                            surnamedb = user.surname,
                            contactdb = user.contact,
                            descriptiondb = user.description,
                            photodb = user.photo,
                        )
                    )
                }
            }
            restartApp(SPLASH_SCREEN)
        }
    }
    private fun saveDisplayName(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.displayName(_displayName.value!!)
            firestoreService.updateUserDisplayName(newValue = _displayName.value!!)
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveNameSurname(profile: Profile?, restartApp: (String) -> Unit) {
        launchCatching {
            firestoreService.updateUserName(newValue = _name.value!!)
            firestoreService.updateUserSurname(newValue = _surname.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveContactDescription(profile: Profile?, restartApp: (String) -> Unit) {
        launchCatching {
            firestoreService.updateUserContact(newValue = _contact.value!!)
            firestoreService.updateUserDescription(newValue = _description.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }

    private fun saveCar(context: Context, restartApp: (String) -> Unit){
        launchCatching {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveCarInside(restartApp)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveCarInside(restartApp)
                }
            }
        }
    }
    private fun saveCarInside(restartApp: (String) -> Unit){
        launchCatching {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val uid = UUID.randomUUID().toString()

                storageService.saveAsset(uid = uid, photo = byteDizisi)
                val link = storageService.getAsset(uid = uid)

                val assetUid = firestoreService.saveAsset(
                    Asset(
                        title = _name.value ?: "",
                        description = _surname.value ?: "",
                        price = _contact.value ?: "",
                        photo = link,
                        date = Timestamp.now()
                    ),
                    type = _displayName.value ?: "",
                    city = _city.value ?: 34,
                    rentSell = SELL
                )
                firestoreService.saveUserAsset(
                    AssetProfile(
                        type = _displayName.value ?: "",
                        city = _city.value ?: 34,
                        rentSell = SELL
                    ),
                    uid = assetUid
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveLocalShipping(context: Context, restartApp: (String) -> Unit){
        launchCatching {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveLocalShippingInside(restartApp)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveLocalShippingInside(restartApp)
                }
            }
        }
    }
    private fun saveLocalShippingInside(restartApp: (String) -> Unit){
        launchCatching {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val uid = UUID.randomUUID().toString()

                storageService.saveAsset(uid = uid, photo = byteDizisi)
                val link = storageService.getAsset(uid = uid)

                val assetUid = firestoreService.saveAsset(
                    Asset(
                        title = _name.value ?: "",
                        description = _surname.value ?: "",
                        price = _contact.value ?: "",
                        photo = link,
                        date = Timestamp.now()
                    ),
                    type = _displayName.value ?: "",
                    city = _city.value ?: 34,
                    rentSell = if(_editType.value == LOCAL_SHIPPING_RENT){ RENT} else {SELL}
                )
                firestoreService.saveUserAsset(
                    AssetProfile(
                        type = _displayName.value ?: "",
                        city = _city.value ?: 34,
                        rentSell = if(_editType.value == LOCAL_SHIPPING_RENT){ RENT} else {SELL}
                    ),
                    uid = assetUid
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveWorkMachine(context: Context, restartApp: (String) -> Unit){
        launchCatching {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveWorkMachineInside(restartApp)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveWorkMachineInside(restartApp)
                }
            }
        }
    }
    private fun saveWorkMachineInside(restartApp: (String) -> Unit){
        launchCatching {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val uid = UUID.randomUUID().toString()

                storageService.saveAsset(uid = uid, photo = byteDizisi)
                val link = storageService.getAsset(uid = uid)

                val assetUid = firestoreService.saveAsset(
                    Asset(
                        title = _name.value ?: "",
                        description = _surname.value ?: "",
                        price = _contact.value ?: "",
                        photo = link,
                        date = Timestamp.now()
                    ),
                    type = _displayName.value ?: "",
                    city = _city.value ?: 34,
                    rentSell = if(_editType.value == WORK_MACHINE_RENT){ RENT} else {SELL}
                )
                firestoreService.saveUserAsset(
                    AssetProfile(
                        type = _displayName.value ?: "",
                        city = _city.value ?: 34,
                        rentSell = if(_editType.value == WORK_MACHINE_RENT){ RENT} else {SELL}
                    ),
                    uid = assetUid
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }

    private fun feedbackSave(popUpScreen: () -> Unit){
        launchCatching {
            firestoreService.saveFeedback(
                feedback = Feedback(text = _description.value ?: "")
            )
            popUpScreen()
        }
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }
    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.DISPLAY_NAME -> _displayName.value != null
            SurveyQuestion.NAME_SURNAME -> _name.value != null && _surname.value != null
            SurveyQuestion.CONTACT_DESCRIPTION -> _contact.value != null && _description.value != null
            SurveyQuestion.AVATAR -> _avatar.value != null

            SurveyQuestion.CAR_TYPE -> _displayName.value != null
            SurveyQuestion.LOCAL_SHIPPING_TYPE -> _displayName.value != null
            SurveyQuestion.WORK_MACHINE_TYPE -> _displayName.value != null
            SurveyQuestion.CITY -> true
            SurveyQuestion.TITLE_DESCRIPTION, -> _name.value != null && _surname.value != null
            SurveyQuestion.PRICE -> _contact.value != null

            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null
            SurveyQuestion.FEEDBACK -> _description.value != null
        }
    }
    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }
}

/**




private val _price = mutableStateOf<Int?>(null)
val price: Int?
get() = _price.value


fun onPriceChange(newValue: String) {
_price.value = newValue.toInt()
_isNextEnabled.value = getIsNextEnabled()
}






HOME_RENT -> listOf()
HOME_SELL -> listOf()
/**
ASSET -> listOf(
SurveyQuestion.TITLE_DESCRIPTION,
SurveyQuestion.IS_RENT,
SurveyQuestion.CITY,
SurveyQuestion.PRICE,
SurveyQuestion.TAKE_SELFIE
)
CITY -> listOf(SurveyQuestion.CITY)*/
}
//       ASSET -> { saveAsset(context = context, restartApp = restartApp) }
//      CITY -> { popUpScreen() }
else -> {}
}
}
 */

enum class SurveyQuestion {
    DISPLAY_NAME,
    NAME_SURNAME,
    CONTACT_DESCRIPTION,
    AVATAR,

    CAR_TYPE,
    LOCAL_SHIPPING_TYPE,
    WORK_MACHINE_TYPE,
    CITY,
    TITLE_DESCRIPTION,
    PRICE,

    TAKE_SELFIE,
    FEEDBACK,
}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)

private fun kucukBitmapOlustur(kullanicininSectigiBitmap: Bitmap, maximumBoyut: Int) : Bitmap {
    var width = kullanicininSectigiBitmap.width
    var height = kullanicininSectigiBitmap.height

    val bitmapOrani : Double = width.toDouble() / height.toDouble()

    if (bitmapOrani > 1) {
        width = maximumBoyut
        val kisaltilmisHeight = width / bitmapOrani
        height = kisaltilmisHeight.toInt()
    } else {
        height = maximumBoyut
        val kisaltilmisWidth = height * bitmapOrani
        width = kisaltilmisWidth.toInt()
    }

    return Bitmap.createScaledBitmap(kullanicininSectigiBitmap,width,height,true)
}
/**

editTypeRepository.readEditTypeState().collect {
_editType.value = it



    private val _editType = mutableStateOf<String?>(null)
    val editType: String?
        get() = _editType.value










    fun onDonePressed(token: String?, profile: Profile?, context: Context, popUpScreen: () -> Unit, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        when (_editType.value) {
            PROFILE -> { saveProfile(token = token ?: "", restartApp, onShowSnackbar = onShowSnackbar) }
            DISPLAY_NAME -> { saveDisplayName( restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            NAME_SURNAME -> { saveNameSurname(profile = profile,  restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            CONTACT_DESCRIPTION -> { saveContactDescription(profile = profile,  restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            PHOTO -> { saveProfileBitmap(context = context, profile = profile, restartApp = restartApp, onShowSnackbar = onShowSnackbar) }

            CAR_SELL -> { saveCar(context = context, restartApp = restartApp, onShowSnackbar = onShowSnackbar)}

            FEEDBACK -> { feedbackSave(popUpScreen, onShowSnackbar = onShowSnackbar) }
        }
    }
    private fun saveProfile(token: String, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            accountService.displayName(_displayName.value ?: "")
            firestoreService.saveUser(
                User(
                    displayName = _displayName.value ?: "",
                    name = _name.value ?: "",
                    surname = _surname.value ?: "",
                    photo = _avatar.value ?: "",
                    description = _description.value ?: "",
                    contact = _contact.value ?: "",
                    completed = true,
                    online = true,
                    uid = uid,
                    token = token,
                    date = Timestamp.now()
                )
            )
            editRepository.completeT()
            profileDao.insert(
                Profile(
                    namedb = _name.value ?: "",
                    surnamedb = _surname.value ?: "",
                    contactdb = _contact.value ?: "",
                    descriptiondb = _description.value ?: "",
                    photodb = _avatar.value ?: "",
                )
            )
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveProfileBitmap(context: Context, profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveProfileInside(restartApp = restartApp, profile = profile, onShowSnackbar = onShowSnackbar)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveProfileInside(restartApp = restartApp, profile = profile, onShowSnackbar = onShowSnackbar)
                }
            }
        }
    }
    private fun saveProfileInside(restartApp: (String) -> Unit, profile: Profile?, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()

                storageService.saveUser(byteDizisi)
                val link = storageService.getUser()
                firestoreService.updateUserPhoto(link)
                profile?.let{ profileDao.delete(profile) }
                val user = firestoreService.getUser(accountService.currentUserId)
                if (user != null) {
                    profileDao.insert(
                        Profile(
                            namedb = user.name,
                            surnamedb = user.surname,
                            contactdb = user.contact,
                            descriptiondb = user.description,
                            photodb = user.photo,
                        )
                    )
                }
            }
            restartApp(SPLASH_SCREEN)
        }
    }
    private fun saveDisplayName(restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            accountService.displayName(_displayName.value!!)
            firestoreService.updateUserDisplayName(newValue = _displayName.value!!)
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveNameSurname(profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.updateUserName(newValue = _name.value!!)
            firestoreService.updateUserSurname(newValue = _surname.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveContactDescription(profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.updateUserContact(newValue = _contact.value!!)
            firestoreService.updateUserDescription(newValue = _description.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }



    private fun saveCar(context: Context, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveCarInside(restartApp = restartApp, onShowSnackbar = onShowSnackbar)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveCarInside(restartApp = restartApp, onShowSnackbar = onShowSnackbar)
                }
            }
        }
    }
    private fun saveCarInside(restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val uid = UUID.randomUUID().toString()

                storageService.saveAsset(uid = uid, photo = byteDizisi)
                val link = storageService.getAsset(uid = uid)

                val assetUid = firestoreService.saveAsset(
                    Asset(
                        title = _name.value ?: "",
                        description = _surname.value ?: "",
                        price = _contact.value ?: "",
                        photo = link,
                        date = Timestamp.now()
                    ),
                    type = _displayName.value ?: "",
                    city = _city.value ?: 34,
                    rentSell = SELL
                )
                firestoreService.saveUserAsset(
                    AssetProfile(
                        type = _displayName.value ?: "",
                        city = _city.value ?: 34,
                        rentSell = SELL
                    ),
                    uid = assetUid
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }

    private fun feedbackSave(popUpScreen: () -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.saveFeedback(
                feedback = Feedback(text = _description.value ?: "")
            )
            popUpScreen()
        }
    }

}

 */




    /**
    val uid = accountService.currentUserId
    val profile: Flow<Profile> = profileDao.getProfile()


    private val _editType = mutableStateOf<String?>(null)
    val editType: String?
        get() = _editType.value

    private val _displayName = mutableStateOf<String?>(null)
    val displayName: String?
        get() = _displayName.value

    private val _name = mutableStateOf<String?>(null)
    val name: String?
        get() = _name.value

    private val _surname = mutableStateOf<String?>(null)
    val surname: String?
        get() = _surname.value

    private val _contact = mutableStateOf<String?>(null)
    val contact: String?
        get() = _contact.value

    private val _city = mutableStateOf<Int?>(null)
    val city: Int?
        get() = _city.value

    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value

    private val _bitmap = mutableStateOf<Bitmap?>(null)
    val bitmap
        get() = _bitmap.value




    fun onDisplayNameChange(newValue: String) {
        _displayName.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onNameChange(newValue: String) {
        _name.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onSurnameChange(newValue: String) {
        _surname.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onContactChange(newValue: String) {
        _contact.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSelfieResponse(uri: Uri) {
        _selfieUri.value = uri
        _isNextEnabled.value = getIsNextEnabled()
    }

    private val questionOrder: List<SurveyQuestion> = when (_editType.value) {
        /**        PROFILE -> listOf(
        SurveyQuestion.DISPLAY_NAME,
        SurveyQuestion.NAME_SURNAME,
        SurveyQuestion.CONTACT_DESCRIPTION,
        SurveyQuestion.AVATAR,
        )
        DISPLAY_NAME -> listOf(SurveyQuestion.DISPLAY_NAME)
        NAME_SURNAME -> listOf(SurveyQuestion.NAME_SURNAME)
        CONTACT_DESCRIPTION -> listOf(SurveyQuestion.CONTACT_DESCRIPTION)
        PHOTO -> listOf(SurveyQuestion.TAKE_SELFIE)
         */
        CAR_SELL -> listOf(
            SurveyQuestion.CAR_TYPE,
            SurveyQuestion.CITY,
            SurveyQuestion.TITLE_DESCRIPTION,
            SurveyQuestion.PRICE,
            SurveyQuestion.TAKE_SELFIE
        )
        /**        CAR -> listOf(
        SurveyQuestion.CAR_TYPE,
        SurveyQuestion.CAR_DATE,
        SurveyQuestion.CAR_MILES,
        SurveyQuestion.PRICE,
        SurveyQuestion.CITY,
        SurveyQuestion.DESCRIPTION,
        SurveyQuestion.TAKE_SELFIE
        )*/
//        CITY -> listOf(SurveyQuestion.CITY)
        //     FEEDBACK -> listOf(SurveyQuestion.FEEDBACK)

        else -> emptyList()
    }


    private var questionIndex = 0

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    private val _isSurveyComplete = mutableStateOf(false)
    val isSurveyComplete: Boolean
        get() = _isSurveyComplete.value


    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }


    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            /**            SurveyQuestion.DISPLAY_NAME -> _displayName.value != null
            SurveyQuestion.NAME_SURNAME -> _name.value != null && _surname.value != null
            SurveyQuestion.CONTACT_DESCRIPTION -> _contact.value != null && _description.value != null
            SurveyQuestion.AVATAR -> _avatar.value != null
             */
            SurveyQuestion.CAR_TYPE -> _displayName.value != null
            SurveyQuestion.CITY -> true
            SurveyQuestion.TITLE_DESCRIPTION, -> _name.value != null && _surname.value != null
            SurveyQuestion.PRICE -> _contact.value != null

            /** SurveyQuestion.CAR_TYPE -> _avatar.value != null
            SurveyQuestion.CAR_DATE -> _birthday.value != null
            SurveyQuestion.CAR_MILES -> _carMiles.value != null
            SurveyQuestion.PRICE -> _price.value != null

             */
            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null
//            SurveyQuestion.FEEDBACK -> _displayName.value != null
        }
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }

    fun onDonePressed(
        token: String?,
        profile: Profile?,
        context: Context,
        popUpScreen: () -> Unit,
        restartApp: (String) -> Unit,
        onShowSnackbar: suspend (String, String?) -> Boolean, ) {
        when (_editType.value) {
//            PROFILE -> { saveProfile(token = token ?: "", restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            //          DISPLAY_NAME -> { saveDisplayName(restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            //        NAME_SURNAME -> { saveNameSurname(profile = profile, restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            //      CONTACT_DESCRIPTION -> { saveContactDescription(profile = profile, restartApp = restartApp, onShowSnackbar = onShowSnackbar) }
            //    PHOTO -> { savePhoto(context = context, profile = profile, restartApp = restartApp, onShowSnackbar = onShowSnackbar) }

            CAR_SELL -> {
                saveCar(context = context, restartApp = restartApp, onShowSnackbar = onShowSnackbar)
            }
            //           CITY -> { popUpScreen() }
            //  FEEDBACK -> { feedbackSave(popUpScreen = popUpScreen, onShowSnackbar = onShowSnackbar) }
            else -> {}
        }
    }


    private fun saveCar(
        context: Context,
        restartApp: (String) -> Unit,
        onShowSnackbar: suspend (String, String?) -> Boolean, ) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    _bitmap.value =
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    saveCarInside(restartApp = restartApp, onShowSnackbar = onShowSnackbar)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    saveCarInside(restartApp = restartApp, onShowSnackbar = onShowSnackbar)
                }
            }
        }
    }
    private fun saveCarInside(
        restartApp: (String) -> Unit,
        onShowSnackbar: suspend (String, String?) -> Boolean, ) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()
                val uid = UUID.randomUUID().toString()

                storageService.saveAsset(uid = uid, photo = byteDizisi)
                val link = storageService.getAsset(uid = uid)

                val assetUid = firestoreService.saveAsset(
                    Asset(
                        title = _name.value ?: "",
                        description = _surname.value ?: "",
                        price = _contact.value ?: "",
                        photo = link,
                        date = Timestamp.now()
                    ),
                    type = _displayName.value ?: "",
                    city = _city.value ?: 34,
                    rentSell = SELL
                )
                firestoreService.saveUserAsset(
                    AssetProfile(
                        type = _displayName.value ?: "",
                        city = _city.value ?: 34,
                        rentSell = SELL
                    ),
                    uid = assetUid
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
}
/**



    private val _description = mutableStateOf<String?>(null)
    val description: String?
        get() = _description.value



    private val _avatar = mutableStateOf<String?>(null)
    val avatar: String?
        get() = _avatar.value









    fun onDescriptionChange(newValue: String) {
        _description.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }
    fun onAvatarChange(newValue: String) {
        _avatar.value = newValue
        _isNextEnabled.value = getIsNextEnabled()
    }



    private fun saveProfile(token: String, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            accountService.displayName(_displayName.value ?: "")
            firestoreService.saveUser(
                User(
                    displayName = _displayName.value ?: "",
                    name = _name.value ?: "",
                    surname = _surname.value ?: "",
                    contact = _contact.value ?: "",
                    description = _description.value ?: "",
                    photo = _avatar.value ?: "",
                    completed = true,
                    online = true,
                    uid = uid,
                    token = token,
                    date = Timestamp.now()
                )
            )
            editRepository.completeT()
            profileDao.insert(
                Profile(
                    namedb = _name.value ?: "",
                    surnamedb = _surname.value ?: "",
                    contactdb = _contact.value ?: "",
                    descriptiondb = _description.value ?: "",
                    photodb = _avatar.value ?: "",
                )
            )
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun savePhoto(context: Context, profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _selfieUri?.let {
                if (Build.VERSION.SDK_INT < 28){
                    _bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
                    savePhotoInside(restartApp, profile = profile, onShowSnackbar = onShowSnackbar)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
                    _bitmap.value = ImageDecoder.decodeBitmap(source)
                    savePhotoInside(restartApp, profile = profile, onShowSnackbar = onShowSnackbar)
                }
            }
        }
    }
    private fun savePhotoInside(restartApp: (String) -> Unit, profile: Profile?, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            _bitmap.value?.let { bitmapNew ->
                val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
                val outputStream = ByteArrayOutputStream()
                kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                val byteDizisi = outputStream.toByteArray()

                storageService.saveUser(byteDizisi)
                val link = storageService.getUser()
                firestoreService.updateUserPhoto(link)
                profile?.let{ profileDao.delete(profile) }
                val user = firestoreService.getUser(accountService.currentUserId)
                if (user != null) {
                    profileDao.insert(
                        Profile(
                            namedb = user.name,
                            surnamedb = user.surname,
                            contactdb = user.contact,
                            descriptiondb = user.description,
                            photodb = user.photo,
                        )
                    )
                }
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveDisplayName(restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            accountService.displayName(_displayName.value!!)
            firestoreService.updateUserDisplayName(newValue = _displayName.value!!)
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveNameSurname(profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.updateUserName(newValue = _name.value!!)
            firestoreService.updateUserSurname(newValue = _surname.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }
    private fun saveContactDescription(profile: Profile?, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.updateUserContact(newValue = _contact.value!!)
            firestoreService.updateUserDescription(newValue = _description.value!!)
            profile?.let { profileDao.delete(it) }
            val user = firestoreService.getUser(accountService.currentUserId)
            if (user != null) {
                profileDao.insert(
                    Profile(
                        namedb = user.name,
                        surnamedb = user.surname,
                        contactdb = user.contact,
                        descriptiondb = user.description,
                        photodb = user.photo,
                    )
                )
            }
            restartApp(PROFILE_SCREEN)
        }
    }

    }


    private fun feedbackSave(popUpScreen: () -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,){
        launchCatching(onShowSnackbar = onShowSnackbar) {
            firestoreService.saveFeedback(
                feedback = Feedback(text = _displayName.value ?: "")
            )
            popUpScreen()
        }
    }


}

/**
CITY,
private val _price = mutableStateOf<Int?>(null)
val price: Int?
get() = _price.value

private val _carMiles = mutableStateOf<Int?>(null)
val carMiles: Int?
get() = _carMiles.value




private val _city = mutableStateOf<Int?>(null)
val city: Int?
get() = _city.value






fun onPriceChange(newValue: String) {
try{
_price.value = newValue.toInt()
} catch(e: NumberFormatException){
throw e
}
_isNextEnabled.value = getIsNextEnabled()
}
fun onCarMilesChange(newValue: String) {
_carMiles.value = newValue.toInt()
_isNextEnabled.value = getIsNextEnabled()
}


fun onCityChange(newValue: Int) {
launchCatching {
cityRepository.changeCity(newValue)
_isNextEnabled.value = getIsNextEnabled()
}
}







private fun saveCar(context: Context, restartApp: (String) -> Unit){
launchCatching {
_selfieUri?.let {
if (Build.VERSION.SDK_INT < 28){
_bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it.value)
saveCarInside(restartApp,)
} else {
val source = ImageDecoder.createSource(context.contentResolver, it.value!!)
_bitmap.value = ImageDecoder.decodeBitmap(source)
saveCarInside(restartApp)
}
}
}
}
private fun saveCarInside(restartApp: (String) -> Unit,){
launchCatching {
_bitmap.value?.let { bitmapNew ->
val kucukBitmap = kucukBitmapOlustur(bitmapNew!!, 300)
val outputStream = ByteArrayOutputStream()
kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
val byteDizisi = outputStream.toByteArray()
val photoUid = UUID.randomUUID().toString()

storageService.saveCar(photoUid, byteDizisi)
val link = storageService.getCar(photoUid)
val carLink = firestoreService.saveCar(
Car (
type = _avatar.value ?: "",
year = _birthday.value ?: "",
mile = _carMiles.value ?: 0,
where = _city.value ?: 34,
price = _price.value ?: 0,
description = _name.value ?: "",
photo = link,
writer = uid,
date = Timestamp.now()
)
)
firestoreService.saveUserCar(
UserCar(
uid = carLink,
type = _avatar.value ?: "",
date = Timestamp.now()
)
)
}
restartApp(SPLASH_SCREEN)
}
}




}



CAR_TYPE,
CAR_DATE,
CAR_MILES,
PRICE,

CITY,

}*/
*/
enum class SurveyQuestion {
  //  DISPLAY_NAME,
    //NAME_SURNAME,
    //CONTACT_DESCRIPTION,
    //AVATAR,

    CAR_TYPE,
    CITY,
    TITLE_DESCRIPTION,
    PRICE,

    TAKE_SELFIE,
//    FEEDBACK,
}


data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)

private fun kucukBitmapOlustur(kullanicininSectigiBitmap: Bitmap, maximumBoyut: Int) : Bitmap {
    var width = kullanicininSectigiBitmap.width
    var height = kullanicininSectigiBitmap.height

    val bitmapOrani : Double = width.toDouble() / height.toDouble()

    if (bitmapOrani > 1) {
        width = maximumBoyut
        val kisaltilmisHeight = width / bitmapOrani
        height = kisaltilmisHeight.toInt()
    } else {
        height = maximumBoyut
        val kisaltilmisWidth = height * bitmapOrani
        width = kisaltilmisWidth.toInt()
    }

    return Bitmap.createScaledBitmap(kullanicininSectigiBitmap,width,height,true)
}*/