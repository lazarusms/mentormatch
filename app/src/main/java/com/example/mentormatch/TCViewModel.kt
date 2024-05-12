package com.example.mentormatch


import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mentormatch.data.COLLECTION_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.util.Calendar
import java.util.UUID
import com.example.mentormatch.data.Event
import javax.inject.Inject
import com.example.mentormatch.data.UserData


@HiltViewModel
class TCViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf<Event<String>?>(null)
    val signedIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)

    init {
    //    auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
        }
    }


    fun onSignup(username: String, email: String, pass: String) {
        if (username.isEmpty() or email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Preencha todos os campos")
            return
        }

        inProgress.value = true
        db.collection(COLLECTION_USER).whereEqualTo("username", username).get()
            .addOnSuccessListener {
                if (it.isEmpty)
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful)
                                createOrUpdateProfile(username = username)
                            else
                                handleException(task.exception, "erro")
                        }
                else
                    handleException(customMessage = "erro2")
                inProgress.value = false
            }
            .addOnFailureListener {
                handleException(it)
            }
    }
    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(COLLECTION_USER).document(uid)
            .addSnapshotListener { value, error ->
                if (error != null)
                    handleException(error, "Cannot retrieve user data")
                if (value != null) {
                    val user = value.toObject<UserData>()
                    userData.value = user
                    inProgress.value = false
                  //  populateCards()
                  //  populateChats()
                }
            }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        Log.e("Ex", "exception", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)
        inProgress.value = false
    }

    private fun createOrUpdateProfile(
        name: String?           = null,
        username: String?       = null,
        imageUrl: String?       = null,
        field: Field?           = null,
        university: University? = null,
        hobbie: Hobbie?         = null,
        city: City?             = null,
        available: Available?   = null
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username = username ?: userData.value?.username,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            field = field?.toString() ?: userData.value?.field,
            university = university?.toString() ?: userData.value?.university,
            hobbie = hobbie?.toString() ?: userData.value?.hobbie,
            city = city?.toString() ?: userData.value?.city,
            available = available?.toString() ?: userData.value?.available

        )
        uid?.let { uid ->
            inProgress.value = true
            db.collection(COLLECTION_USER).document(uid)
                .get()
                .addOnSuccessListener {
                    if (it.exists())
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                             //   this.userData.value = userData
                                inProgress.value = false
                               // populateCards()
                            }
                            .addOnFailureListener {
                                handleException(it, "Cannot update user")
                            }
                    else {
                        db.collection(COLLECTION_USER).document(uid).set(userData)
                        inProgress.value = false
                        getUserData(uid)
                    }
                }
                .addOnFailureListener {
                    handleException(it, "Cannot create user")
                }
        }
    }

    fun onLogin(email: String, pass: String) {
        if (email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signedIn.value = true
                    inProgress.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }
                } else
                    handleException(task.exception, "Login failed")
            }
            .addOnFailureListener {
                handleException(it, "Login failed")
            }
    }

    fun onLogout() {
        auth.signOut()
        signedIn.value = false
        userData.value = null
        popupNotification.value = Event("Logged out")
    }

    fun updateProfileData(
        name: String,
        username: String,
        field: Field,
        university: University?,
        hobbie: Hobbie?,
        city: City?,
        available: Available?
    ) {
        createOrUpdateProfile(
            name = name,
            username = username,
            field = field,
            university = university,
            hobbie = hobbie,
            city = city,
            available = available
        )
    }
    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) {
            createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProgress.value = true

        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)

        uploadTask
            .addOnSuccessListener {
                val result = it.metadata?.reference?.downloadUrl
                result?.addOnSuccessListener(onSuccess)
            }
            .addOnFailureListener {
                handleException(it)
                inProgress.value = false
            }
    }
}