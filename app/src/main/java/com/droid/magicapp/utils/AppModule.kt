package com.droid.magicapp.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val firebaseDBModule = module {
    single { FirebaseDatabase.getInstance() }
    single { FirebaseDatabase.getInstance().setPersistenceEnabled(true) }
    single { FirebaseDatabase.getInstance().reference }
}