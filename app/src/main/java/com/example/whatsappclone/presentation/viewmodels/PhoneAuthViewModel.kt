package com.example.whatsappclone.presentation.viewmodels

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.model.PhoneAuthUser
import com.example.whatsappclone.util.AppPrefs
import com.example.whatsappclone.util.ImageUtils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/** Every state the phone-authentication flow can be in. */
sealed class AuthState {
    data object Idle : AuthState()                                   // nothing happening
    data object Loading : AuthState()                                // request in flight
    data class CodeSent(val verificationId: String) : AuthState()    // OTP SMS sent
    data class Success(val user: PhoneAuthUser) : AuthState()        // signed in
    data class Error(val message: String) : AuthState()              // something failed
}

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : ViewModel() {

    // Private
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    // Public
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val userRef = database.reference.child("users")



    // 1. To send verification code
    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                Log.d("PhoneAuth", "onCodeSent triggered, verificationId: $verificationId")
                _authState.value = AuthState.CodeSent(verificationId)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("PhoneAuth", "onVerificationCompleted triggered automatically")
                signInWithCredential(credential, activity)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.e("PhoneAuth", "verificationFailed: ${exception.message}")
                _authState.value = AuthState.Error(exception.message ?: "Verification failed")
            }
        }

        val phoneAuthOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)


    }





    // 2. Sign In with credential
    private fun signInWithCredential(credential: PhoneAuthCredential, context: Context) {
        _authState.value = AuthState.Loading

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    val phoneAuthUser = PhoneAuthUser(
                        userId = firebaseUser?.uid ?: "",
                        phoneNumber = firebaseUser?.phoneNumber ?: ""
                    )

                    markUserAsSignedIn(context)
                    _authState.value = AuthState.Success(phoneAuthUser)
                    fetchUserProfile(phoneAuthUser.userId)
                } else {
                    val errorMsg = task.exception?.message ?: "Sign in failed"
                    _authState.value = AuthState.Error(errorMsg)
                }
            }
    }






    /** Remember (in SharedPreferences) that this device already registered. */
    private fun markUserAsSignedIn(context: Context) {
        AppPrefs.setSignedIn(context, true)
    }


    // 4. Fetch User profile from firebase
    private fun fetchUserProfile(userId: String) {
        userRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val userProfile = snapshot.getValue(PhoneAuthUser::class.java)
                    if (userProfile != null) {
                        _authState.value = AuthState.Success(userProfile)
                    }
                }
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error("Failed to fetch user profile")
            }
    }




    /** Step 2: user typed the OTP -> combine it with the verification id and sign in. */


    // 5. Verify OTP entered by user
    fun verifyCode(otp: String, context: Context) {
        val currentState = _authState.value
        if (currentState !is AuthState.CodeSent || currentState.verificationId.isEmpty()) {
            Log.e("PhoneAuth", "Attempting to verify OTP without valid verification ID")
            _authState.value = AuthState.Error("Verification not started or invalid ID")
            return
        }

        val credential = PhoneAuthProvider.getCredential(currentState.verificationId, otp)
        signInWithCredential(credential, context)
    }





    // 6. Save user profile in database
    fun saveUserProfile(
        userId: String,
        name: String,
        status: String,
        profileImage: Bitmap?
    ) {
        val encodedImage = profileImage?.let { ImageUtils.bitmapToBase64(it) }
        val phoneNumber = auth.currentUser?.phoneNumber ?: ""

        val userProfile = PhoneAuthUser(
            userId = userId,
            name = name,
            status = status,
            phoneNumber = phoneNumber,
            profileImage = encodedImage
        )

        val databaseInstance = FirebaseDatabase.getInstance().reference
        databaseInstance.child("users")
            .child(userId)
            .setValue(userProfile)
    }



    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

    fun signOut(activity: Activity) {
        auth.signOut()
        AppPrefs.setSignedIn(activity, false)
        resetAuthState()
    }
}
