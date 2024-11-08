package com.app.finansmart.vistas.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.R

import com.app.finansmart.componentes.Alerta
import com.app.finansmart.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vistalogin(loginViewModel: LoginViewModel, navController: NavController) {
    var correo by remember {
        mutableStateOf("")
    }
    var contrasena by remember {
        mutableStateOf("")
    }
    var contrasenaVisible by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondonomoney),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logofinansmart),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFEBFFDD),
                    unfocusedBorderColor = Color(0xFFEBFFDD),
                    cursorColor = Color.Red,
                    containerColor = Color.White

                ),
                modifier = Modifier
                    .size(width = 280.dp, height = 56.dp),
                value = correo,
                onValueChange = {
                    correo = it
                },
                placeholder = { Text(text = "Correo") },
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFEBFFDD),
                    unfocusedBorderColor = Color(0xFFEBFFDD),
                    cursorColor = Color.Red,
                    containerColor = Color.White

                ),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(width = 280.dp, height = 56.dp),
                //.offset(x = 0.dp, y = 45.dp),
                value = contrasena,
                onValueChange = {
                    contrasena = it
                },
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


        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {
                    loginViewModel.login(correo, contrasena) {
                        navController.navigate("inicio") {
                            // popUpTo("login") { inclusive = true } // Elimina la pantalla de login del stack
                        }
                    }
                },
                border = BorderStroke(2.dp, Color(0xFFEBFFDD)),
                colors = ButtonDefaults.run {
                    outlinedButtonColors(
                        containerColor = Color(0xFF494B4D),
                        contentColor = Color.White
                    )
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(200.dp, 60.dp),

                ) {
                Text(text = "Ingresar")
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 150.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre los textos
                verticalAlignment = Alignment.CenterVertically // Alinea verticalmente en el centro
            ) {
                Text(
                    text = "Don’t have an account?", color = Color.White,
                    modifier = Modifier

                )
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .clickable { navController.navigate("registro") }

                )


            }
            if (loginViewModel.mostrarAlerta) {
                Alerta(titulo = "Mensaje Alerta",
                    mensaje = "Usuario o Contrasenia incorrecta",
                    textoConfirmacion = "aceptar",
                    alConfirmar = { loginViewModel.cerrarAlerta() }) {
                }
            }

        }
    }
}