@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.example.mentormatch

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.example.mentormatch.CommonImage
//import com.example.mentormatch.CommonProgressSpinner
//import com.example.mentormatch.TCViewModel
//import com.example.mentormatch.data.UserData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SwipeCards(navController: NavController, vm: TCViewModel) {
    val userData = vm.userData.value
    val context = LocalContext.current
    val postNotificationPermission=
        rememberPermissionState(permission =  Manifest.permission.POST_NOTIFICATIONS)
    val matchNotificationService=MatchNotificationService(context)
    LaunchedEffect(key1 = true ){
        if(!postNotificationPermission.status.isGranted){
            postNotificationPermission.launchPermissionRequest()
        }
    }
    TransparentSystemBars()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFCA4B4F),
                        Color(0xFF5787FA),
                    )
                )
            )
//                        .systemBarsPadding()
    ) {
        Box(modifier = Modifier.weight(1f)) {
            val states = profiles.reversed()
                .map { it to rememberSwipeableCardState() }
            var hint by remember {
                mutableStateOf("Arraste para o lado ou pressione o botão")
            }
            if(states.all { it.second.swipedDirection != null }) {
                hint = "Não há mais perfis para mostrar"
            }

            Hint(hint)

            val scope = rememberCoroutineScope()
            Box(
                Modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .aspectRatio(1f)) {
                states.forEach { (matchProfile, state) ->
                    if (state.swipedDirection == null) {
                        ProfileCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .swipableCard(
                                    state = state,
                                    blockedDirections = listOf(Direction.Down),
                                    onSwiped = {
                                        // swipes are handled by the LaunchedEffect
                                        // so that we track button clicks & swipes
                                        // from the same place
                                    },
                                    onSwipeCancel = {
                                        Log.d("Swipeable-Card", "Cancelled swipe")
                                        hint = "You canceled the swipe"
                                    }
                                ),
                            matchProfile = matchProfile
                        )
                    }
                    LaunchedEffect(matchProfile, state.swipedDirection) {
                     //   if (state.swipedDirection != null) {
                        //      hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
                        // }
                        if (state.swipedDirection == Direction.Right) {

                            val conditionsMet = listOf(
                                userData?.university == matchProfile.university.toString(),
                                userData?.field == matchProfile.field.toString(),
                                userData?.hobbie == matchProfile.hobbie.toString(),
                                userData?.city == matchProfile.city.toString(),
                            ).count { it }

                            if (conditionsMet >= 2 && matchProfile.available != Available.NAO) {
                                hint = "Você deu match com ${matchProfile.name}"
                                matchNotificationService.showBasicNotification(matchProfile.name)
                                Log.d("MATCH", "matchProfile.name")
                                Log.d("MATCH", "OCORREU UM MATCH")
                            }
                            else {
                                hint = ""
                                Log.d("MATCH", "NÃO OCORREU UM MATCH")
                            }
                           // hint = ""
                        }
                    }
                }
            }

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CircleButton(
                    onClick = {
                        scope.launch {
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second
                            last?.swipe(Direction.Left)
                        }
                    },
                    icon = Icons.Rounded.Close
                )
                CircleButton(
                    onClick = {
                        scope.launch {
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second

                            last?.swipe(Direction.Right)
                         //   Log.d("MATCH", "OCORREU UM MATCH")
                        }
                    },
                    icon = Icons.Rounded.Favorite
                )
            }
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.SWIPE,
            navController = navController)
    }
}

@Composable
private fun CircleButton(
    onClick: () -> Unit,
    icon: ImageVector,
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .size(56.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
        onClick = onClick
    ) {
        Icon(icon, null,
            tint = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier,
    matchProfile: MatchProfile,
) {
    Card(modifier) {
        Box {
            Image(contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(matchProfile.drawableResId),
                contentDescription = null)
            Scrim(Modifier.align(Alignment.BottomCenter))
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(text = matchProfile.name + " | " + matchProfile.assignment,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(10.dp))
                Text(text = matchProfile.bio,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }
}

@Composable
private fun Hint(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false
        )
        onDispose {}
    }
}

private fun stringFrom(direction: Direction): String {
    return when (direction) {
        Direction.Left -> "Left 👈"
        Direction.Right -> "Right 👉"
        Direction.Up -> "Up 👆"
        Direction.Down -> "Down 👇"
    }
}


@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth())
}
