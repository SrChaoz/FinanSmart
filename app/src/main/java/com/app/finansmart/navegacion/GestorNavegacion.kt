package com.app.finansmart.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel
import com.app.finansmart.vistas.extras.VistaCreditos
import com.app.finansmart.vistas.extras.VistaInfoUser
import com.app.finansmart.vistas.login.VistaRegistro
import com.app.finansmart.vistas.login.VistaVacia
import com.app.finansmart.vistas.login.Vistalogin
import com.app.finansmart.vistas.mains.AgregarMovimiento
import com.app.finansmart.vistas.mains.VistaEditar
import com.app.finansmart.vistas.mains.VistaFoto
import com.app.finansmart.vistas.mains.VistaResumen
import com.app.finansmart.vistas.mains.VistaSettings
import com.app.finansmart.vistas.mains.Vistainicio

@Composable
fun GestorNavegacion(loginViewModel: LoginViewModel, movimientosViewModel: MovimientosViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "vistaVacia") {
        composable("login") {
            Vistalogin(loginViewModel = loginViewModel, navController = navController)
        }
        composable("registro") {
            VistaRegistro(loginViewModel, navController)
        }
        composable("inicio") {
            Vistainicio(loginViewModel, navController, movimientosViewModel)
        }
        composable("resumen") {
            VistaResumen(movimientosViewModel = movimientosViewModel, navController = navController, loginViewModel = loginViewModel)
        }
        composable("configuration") {
            VistaSettings(movimientosViewModel = movimientosViewModel, navController = navController, loginViewModel = loginViewModel)
        }
        composable("creditos") {
            VistaCreditos(navController = navController)
        }
        composable("infoUser") {
            VistaInfoUser(navController = navController, loginViewModel = loginViewModel)
        }
        //para manejar el inicio de sesion y cambiar el star destinantion a la ruta de la vista vacia
        composable("vistaVacia") {
            VistaVacia(navController = navController)
        }
        composable("agregarNota") {
            AgregarMovimiento(
                navController = navController,
                movimientosViewModel = movimientosViewModel,
                loginViewModel = loginViewModel,
            )
        }
        composable(
            route = "editarSaldo/{idDocumento}",
            arguments = listOf(navArgument("idDocumento") { type = NavType.StringType })
        ) {
            val idDocumento = it.arguments?.getString("idDocumento") ?: ""
            VistaEditar(
                navController = navController,
                movimientosViewModel = movimientosViewModel,
                loginViewModel = loginViewModel,
                idDocumento = idDocumento

            )
        }

        composable(
            route = "vistafoto/{idDocumento}",
            arguments = listOf(navArgument("idDocumento") { type = NavType.StringType })
        ) {
            val idDocumento = it.arguments?.getString("idDocumento") ?: ""
            VistaFoto(
                movimientosViewModel = movimientosViewModel,
                idDocumento = idDocumento

            )
        }

    }
}
