package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yudhis.studyhive.composeables.AppBar
import com.yudhis.studyhive.composeables.DrawerBody
import com.yudhis.studyhive.dataclass.MenuItem
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val gsc = GoogleSignIn.getClient(this@MainActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        setContent {
            StudyHiveTheme {
                Box{
                    val scaffoldState = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()

                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {MainAppBar(coroutineScope, scaffoldState)},
                        drawerContent = { MainDrawerMenu(gsc) },
                        content = { padding ->
                            val modifier = Modifier.padding(padding)
                            MainUI(user, modifier)
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun MainUI(user : FirebaseUser?, modifier : Modifier) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column()
            {
                Spacer(modifier = Modifier
                    .height(16.dp))
                Greeting(user)
            }
        }
    }

    @Composable
    fun Greeting(user : FirebaseUser?) {
        Text(text = "Welcome,", fontSize = 24.sp)
        Text(text = user?.displayName.toString() + "!", fontSize = 32.sp)
    }

    @Composable
    private fun MainAppBar(coroutineScope: CoroutineScope, scaffoldState : ScaffoldState) {
        AppBar(
            onMenuClicked = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }

    @Composable
    private fun MainDrawerMenu(gsc : GoogleSignInClient) {
        DrawerBody(
            items = listOf(
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
                        val intent = Intent(this@MainActivity, AccountInfoActivity::class.java)
                        startActivity(intent)
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
    }
    @Preview(showBackground = true)
    @Composable
    fun AppPreview()
    {
        Box(
            modifier = Modifier
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
                .background(Color(0,0,0,0))
        )
        {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(32.dp)
            ) {
                Column()
                {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                    Text(text = "Welcome,", fontSize = 24.sp)
                    Text(text = "YUDHIS!", fontSize = 32.sp)
                }
            }
        }
    }
}



