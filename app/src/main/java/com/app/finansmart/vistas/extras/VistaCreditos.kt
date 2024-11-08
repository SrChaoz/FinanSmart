package com.app.finansmart.vistas.extras

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.finansmart.componentes.SettingsItem
import com.app.finansmart.componentes.SettingsSection

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VistaCreditos(navController: NavController){

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Creditos de la app") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF18181A), // Color de fondo
                    titleContentColor = Color.White // Color del texto del título
                ),
                actions = {

                }
            )
        }
    ){
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF18181A))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 86.dp, start = 26.dp, end = 26.dp)
        ) {
            SettingsSection(title = "Desarrolladores") {
                SettingsItem("Mateo Coveña ") {

                }
                SettingsItem("Samuel Bun") {

                }
                SettingsItem("Daniel Zambrano") {

                }
                SettingsItem("Yakov Seni") {

                }
            }
            SettingsSection(title = "Colaborador") {
                SettingsItem("Ing. Victor Hugo Palcios") {

                }
            }


        }
    }
}