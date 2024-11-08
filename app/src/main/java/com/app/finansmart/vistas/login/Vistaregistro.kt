package com.app.finansmart.vistas.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.R
import com.app.finansmart.componentes.Alerta
import com.app.finansmart.viewmodels.LoginViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaRegistro(loginViewModel: LoginViewModel, navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var mostrarAlerta by remember { mutableStateOf(false) }
    var mostrarAlertaContrasenaInsegura by remember { mutableStateOf(false) }
    var mostrarAlertaContrasenasNoCoinciden by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondonomoney),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logofinansmart),
                contentDescription = "Logo",
                modifier = Modifier.size(300.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .size(width = 280.dp, height = 56.dp),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFEBFFDD),
                        unfocusedBorderColor = Color(0xFFEBFFDD),
                        cursorColor = Color.Red,
                        containerColor = Color.White
                    ),
                    value = usuario,
                    onValueChange = { usuario = it },
                    placeholder = { Text(text = "Usuario") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .size(width = 280.dp, height = 56.dp),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFEBFFDD),
                        unfocusedBorderColor = Color(0xFFEBFFDD),
                        cursorColor = Color.Red,
                        containerColor = Color.White
                    ),
                    value = correo,
                    onValueChange = { correo = it },
                    placeholder = { Text(text = "Correo") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .size(width = 280.dp, height = 56.dp),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFEBFFDD),
                        unfocusedBorderColor = Color(0xFFEBFFDD),
                        cursorColor = Color.Red,
                        containerColor = Color.White
                    ),
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    placeholder = { Text(text = "Contraseña") },
                    visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                            Icon(
                                imageVector = if (contrasenaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Mostrar/Ocultar Contraseña"
                            )
                        }
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .size(width = 280.dp, height = 56.dp),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFEBFFDD),
                        unfocusedBorderColor = Color(0xFFEBFFDD),
                        cursorColor = Color.Red,
                        containerColor = Color.White
                    ),
                    value = confirmarContrasena,
                    onValueChange = { confirmarContrasena = it },
                    placeholder = { Text(text = "Confirmar Contraseña") },
                    visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                            Icon(
                                imageVector = if (contrasenaVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Mostrar/Ocultar Contraseña"
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = {
                    if (contrasena.length <= 6) {
                        mostrarAlertaContrasenaInsegura = true
                    } else if (contrasena != confirmarContrasena) {
                        mostrarAlerta = true
                    } else {
                        loginViewModel.CrearUsuario(correo, usuario, contrasena) {
                            navController.navigate("inicio")
                        }
                    }
                },
                border = BorderStroke(2.dp, Color(0xFFEBFFDD)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF494B4D),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .size(200.dp, 60.dp)
            ) {
                Text(text = "Registrarse")
            }
            if (mostrarAlertaContrasenaInsegura) {
                Alerta(
                    titulo = "Contraseña Insegura",
                    mensaje = "Elija una contraseña más segura, mayor a 6 dígitos.",
                    textoConfirmacion = "Aceptar",
                    alConfirmar = { mostrarAlertaContrasenaInsegura = false },
                    alRechazar = {}
                )
            }

            if (mostrarAlerta) {
                Alerta(
                    titulo = "Error",
                    mensaje = "Las contraseñas no coinciden",
                    textoConfirmacion = "Aceptar",
                    alConfirmar = { mostrarAlerta = false },
                    alRechazar = {

                    }
                )
            }
            if (loginViewModel.mostrarAlerta) {
                Alerta(
                    titulo = "Mensaje Alerta",
                    mensaje = "Error al Crear Usuario,\n Email y Usuario en Uso",
                    textoConfirmacion = "Aceptar",
                    alConfirmar = { loginViewModel.cerrarAlerta() },
                    alRechazar = {}
                )
            }
        }
    }
}