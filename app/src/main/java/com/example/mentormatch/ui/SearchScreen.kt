package com.example.mentormatch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mentormatch.Assignment
import com.example.mentormatch.Available
import com.example.mentormatch.City
import com.example.mentormatch.CommonDivider
import com.example.mentormatch.CommonProgressSpinner
import com.example.mentormatch.DestinationScreen
import com.example.mentormatch.Field
import com.example.mentormatch.TCViewModel
import com.example.mentormatch.navigateTo

@Composable
fun SearchScreen(navController: NavController, vm: TCViewModel) {
    val inProgress = vm.inProgress.value
    if (inProgress)
        CommonProgressSpinner()
    else {
        val preferencesData = vm.preferencesData.value
        val f = if (preferencesData?.fieldPreference.isNullOrEmpty()) "TODOS"
        else preferencesData!!.fieldPreference!!.uppercase()

        val uni = if (preferencesData?.assignmentPreference.isNullOrEmpty()) "TODOS"
        else preferencesData!!.assignmentPreference!!.uppercase()

        val hob = if (preferencesData?.localPreference.isNullOrEmpty()) "TODOS"
        else preferencesData!!.localPreference!!.uppercase()

        val ct = if (preferencesData?.availablePreference.isNullOrEmpty()) "SIM"
        else preferencesData!!.availablePreference!!.uppercase()


        var field by rememberSaveable { mutableStateOf(Field.valueOf(f)) }
        var assignment by rememberSaveable { mutableStateOf(Assignment.valueOf(uni)) }
        var city by rememberSaveable { mutableStateOf(City.valueOf(hob)) }
        var available by rememberSaveable { mutableStateOf(Available.valueOf(ct)) }

        val scrollState = rememberScrollState()

        Column {
            ScreenContent(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(8.dp),
                vm = vm,
                field = field,
                assignment = assignment,
                city = city,
                available = available,
                onFieldChange = { field = it },
                onAssignmentChange = { assignment = it },
                onCityChange = { city = it },
                onAvailableChange = { available = it },
                onSave = {
                    vm.updatePreferencesData(city, field, assignment, available)
                },
                onBack = { navigateTo(navController, DestinationScreen.Swipe.route) },
                onLogout = {
                    vm.onLogout()
                    navigateTo(navController, DestinationScreen.Login.route)
                }
            )

            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.SEARCH,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier,
    vm: TCViewModel,
    field: Field,
    assignment: Assignment,
    city: City,
    available: Available,
    onFieldChange: (Field) -> Unit,
    onAssignmentChange: (Assignment) -> Unit,
    onCityChange: (City) -> Unit,
    onAvailableChange: (Available) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
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

        CardTela(vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Área: ", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = field == Field.TODOS,
                        onClick = { onFieldChange(Field.TODOS) })
                    Text(
                        text = "Todas",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onFieldChange(Field.TODOS)})
                }
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
                text = "Ocupação:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = assignment == Assignment.TODOS,
                        onClick = { onAssignmentChange(Assignment.TODOS) })
                    Text(
                        text = "Todos",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onAssignmentChange(Assignment.TODOS) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = assignment == Assignment.APRENDIZ,
                        onClick = { onAssignmentChange(Assignment.APRENDIZ) })
                    Text(
                        text = "Aprendiz",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onAssignmentChange(Assignment.APRENDIZ) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = assignment == Assignment.MENTOR,
                        onClick = { onAssignmentChange(Assignment.MENTOR) })
                    Text(
                        text = "Mentor",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onAssignmentChange(Assignment.MENTOR)})
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
                text = "Cidade:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = city == City.TODOS,
                        onClick = { onCityChange(City.TODOS) })
                    Text(
                        text = "Todos",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {onCityChange(City.TODOS) })
                }
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
        CommonDivider()

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
fun CardTela(vm: TCViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Altere suas preferências",
                textAlign = TextAlign.Center,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 24.sp

            )
        }
    }
}

