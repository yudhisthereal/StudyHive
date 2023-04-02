package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yudhis.studyhive.composeables.*
import com.yudhis.studyhive.data.coursesDataset
import com.yudhis.studyhive.data.filteredDataByQuery
import com.yudhis.studyhive.dataclass.MenuItem
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import com.yudhis.studyhive.ui.theme.Transparent
import com.yudhis.studyhive.dataclass.*
import com.yudhis.studyhive.ui.theme.Gray500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var _auth: FirebaseAuth
    private lateinit var _courses: MutableList<Course>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _auth = FirebaseAuth.getInstance()
        val user = _auth.currentUser
        val gsc = GoogleSignIn.getClient(this@MainActivity, GoogleSignInOptions.DEFAULT_SIGN_IN)
        setContent {
            generateDummyCourses()
            _courses = coursesDataset
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
                                confirmLogout = {
                                    logoutConfOpen = true
                                }
                            )
                        },
                    ) { padding ->
                        val modifier = Modifier.padding(padding)
                        val searchQuery = remember {
                            mutableStateOf(TextFieldValue(""))
                        }
                        BackHandler(enabled = scaffoldState.drawerState.isOpen) {
                            coroutineScope.launch { scaffoldState.drawerState.close() }
                        }
                        MainUI(user, modifier, searchQuery)
                    }
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
    fun generateDummyCourses() {
        var courses = mutableListOf<Course>()
        val titles = listOf<String>(
            "Cyber Security Basics",
            "Linux Full Guide",
            "Wordpress Masterclass",
            "Game Development with Godot Engine",
            "Unreal Engine 101",
            "Ethical Hacking"
        )

        for (i in 1 until 127) {
            courses.add(
                Course(
                    title = titles[Random().nextInt(titles.size)] + " ID $i",
                    briefDescription = "Hi! What's up? Oh you wanna strike a job offer but not too sure about your skills? Well, you might want to consider enrolling this course, NOW!",
                    fullDescription = "",
                    image = painterResource(id = R.drawable.img_course_demo_256)
                )
            )
        }

        coursesDataset = courses
    }
    @Composable
    fun MainUI(user : FirebaseUser?, modifier : Modifier, searchState: MutableState<TextFieldValue>) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxSize()
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
                            },
                            onSearch = { query ->
                                _courses = filteredDataByQuery(query).toMutableList()
                            }
                        )
                        Spacer(
                            modifier = Modifier
                                .height(32.dp)
                        )

                        if (searchState.value.text.isBlank()) {
                            SectionHeader(text = "Populer")
                            DiscoverCoursesAndEvents(_courses.toList())
                            Spacer(modifier = Modifier.height(16.dp))
                            SectionHeader(text = "Untukmu")
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        else {
                            SectionHeader(text = "Hasil Pencarian")
                            Spacer(modifier = Modifier.height(16.dp))
                            _courses = filteredDataByQuery(searchState.value.text).toMutableList()
                        }
                        CourseSuggestions(_courses.toList())
                    }
                }
            }
        }
    }

    @Composable
    private fun CourseSuggestions(items: List<Course>) {
        LazyColumn {
            items(
                items = items,
                itemContent = { courseData ->
                    CourseEntry(
                        courseData = courseData,
                        onEnrollClicked = {
                            TODO("Go To Course Overview")
                        }
                    )
                }
            )
        }
    }

    @Composable
    private fun DiscoverCoursesAndEvents(items: List<Course>) {
        LazyRow {
            items(items) {item ->
                MyCard(
                    size = Medium,
                    cornerRadius = 8.dp,
                    text = item.title,
                    imagePainter = painterResource(id = R.drawable.img_demo_course),
                    description = "Discover ${item.title}"
                )
            }
        }
    }

    @Composable
    private fun SearchBar(
        searchState: MutableState<TextFieldValue>,
        onQueryChanged: (newQuery: TextFieldValue) -> Unit,
        onSearch: (query: String) -> Unit
    ) {
//        val context = LocalContext.current.applicationContext
        val focusManager = LocalFocusManager.current
        var hasFocus by remember {mutableStateOf(false)}
        val keyboardState by keyboardAsState()

        OutlinedTextField(
            value = searchState.value,
            onValueChange = {
                onQueryChanged(it)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onBackground,
                unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(ContentAlpha.high)
            ),
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(com.google.android.material.R.drawable.ic_search_black_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            },
            placeholder = {Text(text = "Cari Kursus atau Event", color = Gray500)},
            singleLine = true,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(32.dp), clip = true)
                .background(color = MaterialTheme.colors.background)
                .onFocusChanged {
                    Handler(Looper.getMainLooper()).postDelayed({
                        hasFocus = it.hasFocus
                    }, 1000)
                },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchState.value.text)
                    focusManager.clearFocus()
                }
            )
        )
        if (hasFocus && keyboardState == Keyboard.Closed) {
            focusManager.clearFocus()
        }
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
            modifier = Modifier.padding(start = 32.dp),
            softWrap = true
        )
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
    fun MainDrawerMenu(confirmLogout : () -> Unit) {

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
                        TODO("not yet implemented")
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
    fun SectionHeader(text:String, textAlign: TextAlign = TextAlign.Start) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlign
        )
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



