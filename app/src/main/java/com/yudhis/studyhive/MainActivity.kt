package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.yudhis.studyhive.composeables.AppBar
import com.yudhis.studyhive.composeables.DrawerBody
import com.yudhis.studyhive.composeables.DrawerHeader
import com.yudhis.studyhive.composeables.MenuItem
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gsc = GoogleSignIn.getClient(this@MainActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        setContent {
            StudyHiveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)
                    {
                        Scaffold(
                            scaffoldState = scaffoldState,
                            topBar = {
                                AppBar(
                                    onNavigationIconClicked = {
                                        coroutineScope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    }
                                )
                            },
                            drawerContent = {
                                DrawerHeader()
                                DrawerBody(
                                    items = listOf<MenuItem>(
//                                    MenuItem(
//                                        id = "dashboard",
//                                        text = "Home",
//                                        description = "go to dashboard",
//                                        icon = Icons.Rounded.Home
//                                    ),
                                        MenuItem(
                                            id = "account_info",
                                            text = "Info Akun",
                                            description = "go to account info",
                                            icon = Icons.Rounded.Person
                                        ),
                                        MenuItem(
                                            id = "participants",
                                            text = "Partisipan",
                                            description = "go to participants detail",
                                            icon = ImageVector.vectorResource(id = R.drawable.ic_people)
                                        ),
                                        MenuItem(
                                            id = "logout",
                                            text = "Keluar",
                                            description = "Sign out from StudyHive",
                                            icon = ImageVector.vectorResource(id = R.drawable.ic_logout)
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(8.dp),
                                    itemTextStyle = TextStyle.Default,
                                    onItemClick = { item ->
                                        when (item.id) {
                                            "account_info" -> {
                                                // TODO : Go to account info
                                            }
                                            "participants" -> {
                                                // TODO : Go to participants detail
                                            }
                                            "logout" -> {
                                                gsc.signOut().addOnCompleteListener { task ->
                                                    if (task.isSuccessful) {
                                                        FirebaseAuth.getInstance().signOut()
                                                        finish()
                                                        startActivity(
                                                            Intent(
                                                                this@MainActivity,
                                                                LoginActivity::class.java
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                )
                            },
                            content = { padding ->
                                Text(
                                    text = "",
                                    modifier = Modifier.padding(padding)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )
                    }
                }
            }
        }
    }
}


