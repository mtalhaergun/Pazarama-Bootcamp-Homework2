package com.quenhwyfar.databasesqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quenhwyfar.databasesqlite.ui.theme.DatabaseSqliteTheme

class AddActivity : ComponentActivity() {
    private lateinit var parcaRepository: ParcaRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parcaRepository = ParcaRepository(this)
        val kategori = intent.getIntExtra("kategoriID",1)
        setContent {
            DatabaseSqliteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddScreen(kategori = kategori)
                }
            }
        }


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddScreen(kategori : Int){
        var kategoriID by remember { mutableStateOf(kategori) }
        var ad by remember { mutableStateOf("") }
        var stok by remember { mutableStateOf("") }
        var fiyat by remember { mutableStateOf("") }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = ad,
                onValueChange = {ad = it},
                label = { Text(text = "Parca Adi")},
                modifier = Modifier.padding(4.dp)
            )

            OutlinedTextField(
                value = stok.toString(),
                onValueChange = {stok = it},
                label = { Text(text = "Stok")},
                modifier = Modifier.padding(4.dp)
            )

            OutlinedTextField(
                value = fiyat.toString(),
                onValueChange = {fiyat = it},
                label = { Text(text = "Fiyat")},
                modifier = Modifier.padding(4.dp)
            )

            Button(
                onClick = {
                    val parca = Parca(
                        Kategori_ID = kategoriID,
                        Adi = ad,
                        StokAdedi = stok.toInt(),
                        Fiyati = fiyat.toLong()
                    )
                    parcaRepository.ParcaEkle(parca)
                    val intent = Intent(this@AddActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(  text = "EKLE")
            }

        }

    }
}
