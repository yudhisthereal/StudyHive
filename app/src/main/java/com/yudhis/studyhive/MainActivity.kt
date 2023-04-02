package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yudhis.studyhive.composeables.AppBar
import com.yudhis.studyhive.dataclass.MenuItem
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import com.yudhis.studyhive.ui.theme.Transparent
import com.yudhis.studyhive.composeables.MyCard
import com.yudhis.studyhive.dataclass.*
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
                Box {
                    val scaffoldState = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()
                    var logoutConfOpen by remember {
                        mutableStateOf(false)
                    }

                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = { MainAppBar(coroutineScope, scaffoldState) },
                        drawerContent = {
                            MainDrawerMenu(
                                gsc = gsc,
                                confirmLogout = {
                                    logoutConfOpen = true
                                }
                            )
                        },
                        content = { padding ->
                            val modifier = Modifier.padding(padding)
                            val searchQuery = remember {
                                mutableStateOf(TextFieldValue(""))
                            }
                            MainUI(user, modifier, searchQuery)
                        }
                    )
                    if (logoutConfOpen) {
                        LogoutConfirmation(
                            onDismiss = { logoutConfOpen = false },
                            gsc = gsc
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MainUI(user : FirebaseUser?, modifier : Modifier, searchState: MutableState<TextFieldValue>) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column()
            {
                Greeting(user)
                Spacer(modifier = Modifier.height(10.dp))
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(y = 48.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                                clip = true
                            )
                            .background(MaterialTheme.colors.background)
                    ) {

                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        SearchBar(
                            searchState = searchState,
                            onQueryChanged = {newQuery ->
                                searchState.value = newQuery
                            }
                        )
                        Spacer(
                            modifier = Modifier
                                .height(32.dp)
                                .padding(16.dp)
                        )
                        DiscoverCoursesAndEvents()
                        //CourseSuggestions()
                    }
                }
            }
        }
    }

    @Composable
    private fun CourseSuggestions() {
        TODO("Not yet implemented")
    }

    @Composable
    private fun DiscoverCoursesAndEvents() {
        Column{
            Text(
                text = "Discover Things!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
            LazyRow {
                items(count = 73) {id ->
                    MyCard(
                        size = Large,
                        cornerRadius = 8.dp,
                        text = "Cool Course $id",
                        imagePainter = painterResource(id = R.drawable.img_demo_course),
                        description = "Course Card $id"
                    )
                }
            }
        }
    }

    @Composable
    private fun SearchBar(searchState:MutableState<TextFieldValue>, onQueryChanged: (newQuery:TextFieldValue) -> Unit) {
        TextField(
            value = searchState.value,
            onValueChange = {
                onQueryChanged(it)
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(com.google.android.material.R.drawable.ic_search_black_24),
                    contentDescription = null
                )
            },
            placeholder = {Text(text = "Cari Kursus atau Event")},
            singleLine = true,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(32.dp), clip = true)
                .background(color = MaterialTheme.colors.background)
        )
    }

    @Composable
    fun Greeting(user : FirebaseUser?) {

        Text(
            text = "Welcome,",
            fontSize = 24.sp,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = 32.dp)
            )
        Text(
            text = user?.displayName.toString() + "!",
            fontSize = 32.sp,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(start = 32.dp))
    }

    @Composable
    fun MainAppBar(coroutineScope: CoroutineScope, scaffoldState : ScaffoldState) {
        AppBar(
            onMenuClicked = {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }

    @Composable
    fun LogoutConfirmation(onDismiss : () -> Unit, gsc : GoogleSignInClient)
    {
        Dialog(
            onDismissRequest = { onDismiss() },
            content = {
                Surface(
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Keluar dari StudyHive?",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                                onClick = {
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
                            )
                            {
                                Text(text = "Ya", color = MaterialTheme.colors.onPrimary)
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(Transparent),
                                elevation = ButtonDefaults.elevation(0.dp),
                                onClick = {
                                    onDismiss()
                                }
                            )
                            {
                                Text(text = "Tidak", color = MaterialTheme.colors.onBackground)
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun MainDrawerMenu(gsc : GoogleSignInClient, confirmLogout : () -> Unit) {

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
            modifier = Modifier,
            itemTextStyle = TextStyle.Default,
            onItemClick = { item ->
                when (item.id) {
                    "account_info" -> {
                        val intent = Intent(this, AccountInfoActivity::class.java)
                        startActivity(intent)
                    }
                    "participants" -> {
                        TODO("not yet implemented")
                    }
                    "logout" -> {
                         confirmLogout()
                    }
                }
            }
        )
    }

    @Composable
    fun DrawerBody(
        items : List<MenuItem>,
        modifier : Modifier,
        itemTextStyle : TextStyle,
        onItemClick : (MenuItem) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(24.dp)
        ) {
            Text(
                text = "Menu",
                fontWeight = FontWeight.Black,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
            ) {
                items(items = items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onItemClick(item)
                            }
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.description,
                            tint = contentColorFor(backgroundColor = MaterialTheme.colors.background)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.text,
                            style = itemTextStyle,
                            modifier = Modifier
                                .weight(1f),
                            fontSize = 18.sp,
                            color = contentColorFor(backgroundColor = MaterialTheme.colors.background)
                        )
                    }
                }
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun AppPreview()
    {
        Surface(
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(32.dp),
            color = MaterialTheme.colors.onPrimary
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Keluar dari StudyHive?"
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                        onClick = {},
                    )
                    {
                        Text(text = "Ya", color = MaterialTheme.colors.onPrimary)
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onPrimary),
                        onClick = {

                        }
                    )
                    {
                        Text(text = "Tidak", color = MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}



