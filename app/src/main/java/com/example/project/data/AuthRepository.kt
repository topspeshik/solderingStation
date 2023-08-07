package com.example.project.data

import android.util.Log
import com.example.project.domain.AuthState
import com.example.project.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository  @Inject constructor(){

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database  = Firebase.database
    val myRef = database.getReference("users")

    fun register(email: String, password: String, user: User) : Flow<AuthState> = flow {
        emit(AuthState.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            user.id = firebaseAuth.currentUser!!.uid
            myRef.child(user.id).setValue(user)
            emit(AuthState.Success())
        }
        catch (e: Exception){
            emit(AuthState.Error())

        }

    }

    fun login(email: String, password: String) : Flow<AuthState> = flow {
        emit(AuthState.Loading())

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()

            val user = myRef.child(firebaseAuth.currentUser!!.uid).get().await()

            emit(AuthState.Success(user.getValue(User::class.java)))
        }
        catch (e: Exception){
            emit(AuthState.Error())
        }
    }

    fun resetPassword(email: String) : Flow<AuthState> = flow {
        emit(AuthState.Loading())

        try {
            val result = firebaseAuth.sendPasswordResetEmail(email).await()
            emit(AuthState.Success())
        }
        catch (e: Exception){
            emit(AuthState.Error())
        }
    }


}