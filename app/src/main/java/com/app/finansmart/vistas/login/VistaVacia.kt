package com.app.finansmart.vistas.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

//para que guarde el logueo
@Composable
fun VistaVacia(navController: NavController) {
    LaunchedEffect(Unit) {
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate("login")
        } else {
            navController.navigate("inicio")
        }

    }
}