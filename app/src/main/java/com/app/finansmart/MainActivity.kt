package com.app.finansmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.finansmart.navegacion.GestorNavegacion
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel: LoginViewModel by viewModels()
        val movimientosViewModel: MovimientosViewModel by viewModels()

        setContent {
            GestorNavegacion(loginViewModel = loginViewModel, movimientosViewModel= movimientosViewModel)
            //Vistainicio(viewModel = notasViewModel, navController = )
        }
    }
}