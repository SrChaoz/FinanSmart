package com.app.finansmart.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BarraNavegacion(navController: NavController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            //padding para alinear la barra y el boton central
            .padding(bottom = 20.dp),

        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            modifier = Modifier
                .size(350.dp, 70.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomEnd = 30.dp,
                        bottomStart = 30.dp
                    )
                )
                .background(Color(0xFFFFFFFF))
                .clickable(enabled = false) {}


        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                IconButton(
                    onClick = { navController.navigate("inicio") },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Home, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                IconButton(
                    onClick = { navController.navigate("resumen") },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(50.dp)
                    )

                }
                Box(
                    modifier = Modifier.run {
                        clip(CircleShape)
                            .size(100.dp, 100.dp)
                            .background(Color(0x002C2B2B))
                    }

                ) {

                }

                IconButton(
                    onClick = { navController.navigate("infoUser") },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(50.dp)
                    )

                }

                IconButton(
                    onClick = { navController.navigate("configuration") },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings, contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(50.dp)
                    )

                }
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            //padding para alinear la barra y el boton central
            .padding(bottom = 15.dp),

        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.run {
                clip(CircleShape)
                    .background(Color(0xFF0F0F0F))
                    .size(80.dp, 80.dp)

            }

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center

            ) {


                IconButton(
                    onClick = { navController.navigate("agregarNota") },
                    modifier = Modifier
                        .size(100.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle, contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(80.dp)
                    )

                }
            }
        }


    }


}