package com.quenhwyfar.databasesqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quenhwyfar.databasesqlite.ui.theme.DatabaseSqliteTheme

class MainActivity : ComponentActivity() {
    private lateinit var kategoriRepository: KategoriRepository
    private lateinit var parcaRepository: ParcaRepository
    private lateinit var kategorikParcalar:MutableList<Parca>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        kategoriRepository = KategoriRepository(this)
        parcaRepository = ParcaRepository(this)

        kategoriRepository.KategorileriOlustur()

        setContent {
            DatabaseSqliteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    kategorikParcalar = parcaRepository.ParcalarByKategoriID(1)
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen()
    {
        val kategoriler = kategoriRepository.GetKategoriler()

        var expanded = remember { mutableStateOf(false) }
        var secilenKategori = remember { mutableStateOf(kategoriler[0] )  }

        Column(
            Modifier
                .fillMaxSize(),
            Arrangement.Top, Alignment.Start
        )
        {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(Color.LightGray))
            {
                Row(
                    Modifier
                        .clickable {
                            expanded.value = !expanded.value
                        }
                        .align(Alignment.TopStart))
                {
                    Text(text = secilenKategori.value.Aciklama)
                    Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = expanded.value,
                    onDismissRequest = {  expanded.value = false })
                {
                    kategoriler.forEach {

                        DropdownMenuItem(
                            text =
                            {
                                Text(text = it.Aciklama )
                            },
                            onClick = {
                                secilenKategori.value = it

                                expanded.value = false

                                kategorikParcalar = parcaRepository.ParcalarByKategoriID(secilenKategori.value.K_ID)
                            })
                    }
                }
            }

            ParcaListesiGoster(lst = kategorikParcalar)
        }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ){
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(this@MainActivity,AddActivity::class.java)
                        intent.putExtra("kategoriID",secilenKategori.value.K_ID)
                        startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }

    }

    @Composable
    fun ParcaGoster(p:Parca)
    {
        var ctx = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(color = Color.White)
                .border(1.dp, Color.DarkGray)
        ) {
            Text(
                text = p.Adi,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(color = Color.DarkGray)
                    .height(1.dp)
            )

            Text(
                text = "Stok Adedi: ${p.StokAdedi}",
                modifier = Modifier.padding(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(color = Color.DarkGray)
                    .height(1.dp)
            )

            Text(
                text = "Fiyatı: ${p.Fiyati} TL",
                modifier = Modifier.padding(8.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(2.dp)
            )
        }
    }

    @Composable
    fun ParcaListesiGoster(lst:List<Parca>)
    {
        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.Bottom,
            userScrollEnabled = true
        )
        {
            this.items(lst)
            {
                ParcaGoster(p = it)
            }
        }
    }
}