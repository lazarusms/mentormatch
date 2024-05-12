package com.example.mentormatch

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mentormatch.CommonDivider
import com.example.mentormatch.CommonImage
import com.example.mentormatch.CommonProgressSpinner
import com.example.mentormatch.DestinationScreen
import com.example.mentormatch.TCViewModel
import com.example.mentormatch.navigateTo

enum class Field {
    TECNOLOGIA, MATEMATICA, HISTORIA, ECONOMIA, DESIGN
}

enum class University {
    FIAP, USP, UNICID, PUC, UNINOVE
}

enum class Hobbie {
    CORRER, LER, PROGRAMAR, COMER, VIAJAR
}

enum class City {
    SAOPAULO, COTIA, OSASCO, CARAPICUIBA, BARUERI
}
enum class Available {
    SIM, NAO
}


@Composable
fun ProfileScreen(navController: NavController, vm: TCViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
    else {
        val userData = vm.userData.value
        val f = if (userData?.field.isNullOrEmpty()) "TECNOLOGIA"
        else userData!!.field!!.uppercase()

        val uni = if (userData?.university.isNullOrEmpty()) "FIAP"
        else userData!!.university!!.uppercase()

        val hob = if (userData?.hobbie.isNullOrEmpty()) "PROGRAMAR"
        else userData!!.hobbie!!.uppercase()

        val ct = if (userData?.city.isNullOrEmpty()) "COTIA"
        else userData!!.city!!.uppercase()

        val av = if (userData?.available.isNullOrEmpty()) "SIM"
        else userData!!.available!!.uppercase()

        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var field by rememberSaveable { mutableStateOf(Field.valueOf(f)) }
        var university by rememberSaveable { mutableStateOf(University.valueOf(uni)) }
        var hobbie by rememberSaveable { mutableStateOf(Hobbie.valueOf(hob)) }
        var city by rememberSaveable { mutableStateOf(City.valueOf(ct)) }
        var available by rememberSaveable { mutableStateOf(Available.valueOf(av)) }

        val scrollState = rememberScrollState()

        Column {
            ProfileContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(8.dp),
                vm = vm,
                name = name,
                username = username,
                field = field,
                university = university,
                hobbie = hobbie,
                city = city,
                available = available,
                onNameChange = { name = it },
                onUsernameChange = { username = it },
                onFieldChange = { field = it },
                onUniversityChange = { university = it },
                onHobbieChange = { hobbie = it },
                onCityChange = { city = it },
                onAvailableChange = { available = it },
                onSave = {
                    vm.updateProfileData(name, username, field, university, hobbie, city, available )
                },
                onBack = { navigateTo(navController, DestinationScreen.Swipe.route) },
                onLogout = {
                    vm.onLogout()
                    navigateTo(navController, DestinationScreen.Login.route)
                }
            )

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.PROFILE,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: TCViewModel,
    name: String,
    username: String,
    field: Field,
    university: University,
    hobbie: Hobbie,
    city: City,
    available: Available,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onFieldChange: (Field) -> Unit,
    onUniversityChange: (University) -> Unit,
    onHobbieChange: (Hobbie) -> Unit,
    onCityChange: (City) -> Unit,
    onAvailableChange: (Available) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val imageUrl = vm.userData.value?.imageUrl

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Voltar", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Salvar", modifier = Modifier.clickable { onSave.invoke() })
        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Nome", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = onNameChange,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Área de expertise: ", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.DESIGN,
                        onClick = { onFieldChange(Field.DESIGN) })
                    Text(
                        text = "Design",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.DESIGN)})
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.TECNOLOGIA,
                        onClick = { onFieldChange(Field.TECNOLOGIA)})
                    Text(
                        text = "Tecnologia",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.DESIGN) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.TECNOLOGIA,
                        onClick = { onFieldChange(Field.TECNOLOGIA)})
                    Text(
                        text = "Tecnologia",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.TECNOLOGIA) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.MATEMATICA,
                        onClick = { onFieldChange(Field.MATEMATICA)})
                    Text(
                        text = "Matemática",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.MATEMATICA) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.ECONOMIA,
                        onClick = { onFieldChange(Field.ECONOMIA)})
                    Text(
                        text = "Economia",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.ECONOMIA) })
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Faculdade:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = university == University.FIAP,
                        onClick = { onUniversityChange(University.FIAP) })
                    Text(
                        text = "FIAP",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onUniversityChange(University.FIAP) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = university == University.UNICID,
                        onClick = { onUniversityChange(University.UNICID) })
                    Text(
                        text = "UNICID",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onUniversityChange(University.FIAP) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = university == University.USP,
                        onClick = { onUniversityChange(University.USP) })
                    Text(
                        text = "USP",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onUniversityChange(University.USP) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = university == University.PUC,
                        onClick = { onUniversityChange(University.PUC) })
                    Text(
                        text = "PUC",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onUniversityChange(University.PUC) })
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Hobbie:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = hobbie == Hobbie.COMER,
                        onClick = { onHobbieChange(Hobbie.COMER) })
                    Text(
                        text = "Comer",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onHobbieChange(Hobbie.COMER)})
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = hobbie == Hobbie.LER,
                        onClick = { onHobbieChange(Hobbie.LER) })
                    Text(
                        text = "Ler",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onHobbieChange(Hobbie.LER)})
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = hobbie == Hobbie.PROGRAMAR,
                        onClick = { onHobbieChange(Hobbie.PROGRAMAR) })
                    Text(
                        text = "Programar",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onHobbieChange(Hobbie.PROGRAMAR)})
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = hobbie == Hobbie.VIAJAR,
                        onClick = { onHobbieChange(Hobbie.VIAJAR) })
                    Text(
                        text = "Viajar",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onHobbieChange(Hobbie.VIAJAR)})
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Cidade:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = city == City.COTIA,
                        onClick = { onCityChange(City.COTIA) })
                    Text(
                        text = "Cotia",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onCityChange(City.COTIA) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = city == City.SAOPAULO,
                        onClick = { onCityChange(City.SAOPAULO) })
                    Text(
                        text = "São Paulo",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onCityChange(City.SAOPAULO) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = city == City.BARUERI,
                        onClick = { onCityChange(City.BARUERI) })
                    Text(
                        text = "Barueri",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onCityChange(City.BARUERI) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = city == City.OSASCO,
                        onClick = { onCityChange(City.OSASCO) })
                    Text(
                        text = "Osasco",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onCityChange(City.OSASCO) })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Sair", modifier = Modifier.clickable { onLogout.invoke() })
        }

    }
}

@Composable
fun ProfileImage(imageUrl: String?, vm: TCViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let { vm.uploadProfileImage(uri) }
    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                launcher.launch("image/*")
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(shape = CircleShape, modifier = Modifier
                .padding(8.dp)
                .size(100.dp)) {
                CommonImage(data = imageUrl)
            }
            Text(text = "Mudar foto de perfil")
        }

        val isLoading = vm.inProgress.value
        if (isLoading)
            CommonProgressSpinner()
    }
}

