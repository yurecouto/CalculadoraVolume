package com.example.calculadoravolume


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoravolume.ui.theme.CalculadoraVolumeTheme
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraVolumeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Calculadora()
                }
            }
        }
    }
}

@Composable
fun Calculadora() {

    // Ajuda com a modelagem dos temas
    Scaffold(
        // A barra superior do app
        topBar = {TopAppBar(title = { Text(text = "Calculadora Volume") });},

        // A aba de conteudo
        content = {
            Column( modifier = Modifier
                .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                // Variaveis a serem usadas
                var valorA by remember { mutableStateOf("") }
                var valorB by remember { mutableStateOf("") }
                var resultado by remember { mutableStateOf("") }
                val pi = 3.141592
                val focusManager = LocalFocusManager.current

                // Agora, temos uma estrutura baseada em linhas e colunas
                Spacer( modifier = Modifier.height(20.dp) )
                Row {

                    Column {

                        // Dentro dessa coluna, temos os dois inputs
                        Spacer( modifier = Modifier.height(30.dp) )
                        OutlinedTextField(
                            value = valorA,
                            label = {Text("Altura em Cm")},
                            onValueChange = {valorA = it},
                            modifier = Modifier
                                .width(180.dp))

                        Spacer( modifier = Modifier.height(40.dp) )
                        OutlinedTextField(
                            value = valorB,
                            label = {Text("Raio da base em Cm")},
                            onValueChange = {valorB = it},
                            modifier = Modifier
                                .width(180.dp))
                    }

                    Image(painter = painterResource(R.drawable.screenshot_from_2022_03_03_20_05_39),
                        contentDescription = "Exemplo")

                }

                Spacer( modifier = Modifier.height(30.dp) )

                // Aqui campo de resultado
                OutlinedTextField(
                    value = resultado,
                    label = {Text("Volume")},
                    onValueChange = {resultado = it},
                    modifier = Modifier.width(180.dp),
                )
                Spacer( modifier = Modifier.height(20.dp) )

                // O botão que retorna o resultado
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        val h = valorA.toDouble()
                        val r = valorB.toDouble()
                        val volume = h * pi * (r * r)
                        resultado = cmToM(volume)
                    },
                    modifier = Modifier
                        .width(180.dp),
                ){ Text(text = "Calcular o Volume") }

                Spacer( modifier = Modifier.height(20.dp) )
                Text(text = "Adicionar Margem?", fontSize = 20.sp)
                Spacer( modifier = Modifier.height(20.dp) )

                // Nessa row temos os botões de margem
                Row {
                    Button(
                        onClick = { porcentagem(valorA, valorB, 1.05) },
                        modifier = Modifier
                            .width(80.dp),
                    ){ Text(text = "+ 5%") }

                    Spacer( modifier = Modifier.width(10.dp) )
                    Button(
                        onClick = { porcentagem(valorA, valorB, 1.10) },
                        modifier = Modifier
                            .width(80.dp),
                    ){ Text(text = "+ 10%") }

                    Spacer( modifier = Modifier.width(10.dp) )
                    Button(
                        onClick = { porcentagem(valorA, valorB, 1.15) },
                        modifier = Modifier
                            .width(80.dp),
                    ){ Text(text = "+ 15%") }

                    Spacer( modifier = Modifier.width(10.dp) )
                    Button(
                        onClick = { porcentagem(valorA, valorB, 1.20) },
                        modifier = Modifier
                            .width(82.dp),
                    ){ Text(text = "+ 20%") }
                }

            }
        }
    )
}

fun porcentagem(valorA: String, valorB: String, porcent: Double): String {
    val h = valorA.toDouble()
    val r = valorB.toDouble()
    val pi = 3.141592
    val volume = (h * pi * (r * r)) * porcent
    return cmToM(volume)
}

fun cmToM (cm3: Double): String {
    val m = (cm3 / 1000000)

    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    val roundoff = df.format(m)
    return "$roundoff metros cubicos"
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculadora() {
    Calculadora()
}