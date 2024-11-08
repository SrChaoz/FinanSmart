package com.app.finansmart.vistas.mains

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.app.finansmart.R
import com.app.finansmart.componentes.CajaSaldo
import com.app.finansmart.componentes.FinanOutlinedButton
import com.app.finansmart.viewmodels.LoginViewModel
import com.app.finansmart.viewmodels.MovimientosViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarMovimiento(
    navController: NavController,
    movimientosViewModel: MovimientosViewModel,
    loginViewModel: LoginViewModel,
) {
    var titulo by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var tipoMovimiento by remember { mutableStateOf("movimiento") }
    val contexto = LocalContext.current
    val datos by loginViewModel.datos.collectAsState()
    val idUsuario = if (datos.isNotEmpty()) datos[0].idUsuario else ""
    val idDocumento = if (datos.isNotEmpty()) datos[0].idDocumento else ""

    val file = contexto.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(contexto),
        contexto.packageName + ".provider", file
    )

    var image by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val imageDefault = R.drawable.photo
    val permissionCheckResult =
        ContextCompat.checkSelfPermission(contexto, android.Manifest.permission.CAMERA)


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        image = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it != null) {
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(contexto, "Permiso denegado", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        loginViewModel.TraerUsuario()
    }
    val saldo = datos[0].saldo
    //val idUsuario = loginViewModel.estado.idUsuario ?: ""
    if (idUsuario.isEmpty()) {
        println("Error: idUsuario está vacío o es nulo")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Nuevo Movimiento") },

                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                },
                actions = {
                    IconButton(onClick = {
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Icon(
                            painter = rememberAsyncImagePainter(if (image.path?.isNotEmpty() == true) image else imageDefault),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF18181A), // Color de fondo
                    titleContentColor = Color.White // Color del texto del título
                )
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF18181A))

        ) {

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .offset(y = 200.dp)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
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
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = { Text(text = "Titulo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
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

                value = valor,
                onValueChange = { valor = it },
                placeholder = { Text(text = "Valor") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            )


            // Selector de tipo de movimiento
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoMovimiento == "movimiento",
                    onClick = { tipoMovimiento = "movimiento" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Green
                    )
                )
                Text(
                    text = "Ingreso",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = tipoMovimiento == "gasto",
                    onClick = { tipoMovimiento = "gasto" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Red,
                        unselectedColor = Color.Red
                    )
                )
                Text(
                    text = "Gasto",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            FinanOutlinedButton(
                onClick = {
                    val valorFloat = valor.replace(",", ".").toFloatOrNull() ?: 0f
                    if (idUsuario.isNotEmpty()) {
                        if (tipoMovimiento == "movimiento" || (tipoMovimiento == "gasto" && saldo >= valorFloat)) {
                            movimientosViewModel.GuardarMovimiento(
                                titulo,
                                valorFloat.toString(),
                                tipoMovimiento,
                                image

                            ) {
                                loginViewModel.ActualizarSaldo(
                                    idDocumento,
                                    valorFloat,
                                    tipoMovimiento,
                                    saldo
                                ) {

                                    Toast.makeText(
                                        contexto,
                                        "Movimiento Guardado",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    navController.popBackStack()

                                }
                            }
                        } else {
                            Toast.makeText(contexto, "Saldo insuficiente", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                text = "Aceptar"
            )
        }
    }

    Column(
        modifier = Modifier.offset(y = 40.dp)
    ) {
        CajaSaldo(
            loginViewModel = loginViewModel,
            movimientosViewModel = movimientosViewModel,
            navController = navController
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}
