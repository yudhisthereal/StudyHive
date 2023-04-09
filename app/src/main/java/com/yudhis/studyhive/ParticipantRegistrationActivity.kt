package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.data.saveUserData
import com.yudhis.studyhive.data.userData
import com.yudhis.studyhive.dataclass.Course
import com.yudhis.studyhive.dataclass.Participant
import com.yudhis.studyhive.dataclass.getNewId
import com.yudhis.studyhive.tools.randomColor
import com.yudhis.studyhive.tools.randomCourseCategory
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import java.util.*

class ParticipantRegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyHiveTheme{
                var validate by remember {mutableStateOf(false)}
                val context = LocalContext.current.applicationContext
                val pNickName by remember {mutableStateOf("")}
                val pFullName by remember {mutableStateOf("")}
                val pAddress by remember {mutableStateOf("")}
                val pBirthDate by remember {mutableStateOf("")}
                val sktm = ImageVector.vectorResource(id = R.drawable.ic_file)
                val skd = ImageVector.vectorResource(id = R.drawable.ic_file)

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxSize()
                ) {
                    val nameState = remember { mutableStateOf(TextFieldValue(pFullName)) }
                    val nickNameState = remember { mutableStateOf(TextFieldValue(pNickName)) }
                    val addressState = remember { mutableStateOf(TextFieldValue(pAddress)) }
                    val birthDateState = remember { mutableStateOf(TextFieldValue(pBirthDate)) }
                    var showExitEditingDialog by remember { mutableStateOf(false) }
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(156.dp)
                            .clip
                                (RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        // Title, Profile Pic, Participant Name
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            // Activity Title
                            Text(
                                text = "Edit Partisipan",
                                fontSize = 16.sp,
                                color = MaterialTheme.colors.onPrimary,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                            // Profile Pic
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                                contentDescription = "Participant Profile Pic",
                                modifier = Modifier
                                    .width(64.dp)
                                    .height(64.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colors.secondary,
                                        shape = CircleShape
                                    ),
                            )
                        }
                    }

                    // Content
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {
                        // Participant Full Name
                        item {
                            EditableField(
                                fieldState = nameState,
                                title = "Nama Lengkap",
                                placeholder = "Saddam Husein",
                                validate = validate
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        // Participant Nick Name
                        item {
                            EditableField(
                                fieldState = nickNameState,
                                title = "Nama Panggilan",
                                placeholder = "Husein",
                                validate = validate
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        // Address
                        item {
                            EditableField(
                                fieldState = addressState,
                                title = "Alamat",
                                placeholder = "Jalan Belimbing No.47",
                                validate = validate
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        // Birth Date
                        item {
                            EditableField(
                                fieldState = birthDateState,
                                title = "Tanggal Lahir",
                                placeholder = "07 Agustus 2003",
                                validate = validate
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                        // SKTM
                        item {
                            FileField(
                                title = "Surat Keterangan Tidak Mampu",
                                fileName = "Belum Diisi",
                            )
                        }
                        item { Spacer(Modifier.height(22.dp)) }
                        //SKD
                        item {
                            FileField(
                                title = "Surat Keterangan Disabilitas",
                                fileName = "Belum Diisi"
                            )
                        }
                        item { Spacer(Modifier.height(22.dp)) }

                        // Save Button
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Button(
                                    onClick = {
                                        showExitEditingDialog = true
                                    },
                                    shape = RoundedCornerShape(32.dp)
                                ) {
                                    Text(text = "Simpan")
                                }
                            }
                        }
                    }
                    BackHandler(true) {
                        // confirm finish registration
                        showExitEditingDialog = true
                    }

                    // Confirm exit editing dialog
                    if (showExitEditingDialog) {
                        val pPic = ImageVector.vectorResource(id = R.drawable.ic_person)
                        val courses = DummyCourses()
                        ConfirmExitEditing(
                            onDissmissRequest = {
                                showExitEditingDialog = false
                            },
                            saveChanges = { save ->
                                // Save the data
                                if (save) {
                                    validate = true
                                    if (nameState.value.text.isNotBlank()
                                        && nickNameState.value.text.isNotBlank()
                                        && addressState.value.text.isNotBlank()
                                        && birthDateState.value.text.isNotBlank()
                                    ) {
                                        val newId = getNewId()
                                        val newParticipant = Participant(
                                            pName = nameState.value.text,
                                            pNickName = nickNameState.value.text,
                                            pAddress = addressState.value.text,
                                            pPic = pPic,
                                            pBirthdate = birthDateState.value.text,
                                            pId = newId,
                                            skd = skd,
                                            sktm = sktm,
                                            courseHistory = courses
                                        )
                                        userData.participants[newId] = newParticipant
                                        startActivity(
                                            Intent(
                                                context,
                                                ParticipantListActivity::class.java
                                            )
                                        )
                                        finish()
                                        saveUserData()
                                    }
                                    else {
                                        showExitEditingDialog = false
                                        Toast.makeText(context, "Pastikan semua field terisi", Toast.LENGTH_LONG).show()
                                    }
                                }
                                else {
                                    startActivity(
                                        Intent(
                                            context,
                                            ParticipantListActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DummyCourses(count:Int = 11): MutableList<Course> {
    val courses = mutableListOf<Course>()
    val titles = setOf(
        "Cyber Security Basics",
        "Linux Full Guide",
        "Wordpress Masterclass",
        "Game Development with Godot Engine",
        "Unreal Engine 101",
        "Ethical Hacking"
    )
    val locations = setOf(
        "Bandung",
        "Malang",
        "Bekasi",
        "Jakarta",
        "Bogor",
        "Padang",
        "Denpasar",
        "Pontianak"
    )

    val startDate = setOf(
        "1 Januari 2023",
        "2 februari 2023",
        "3 Maret 2023",
        "4 April 2023",
        "5 Mei 2023",
        "6 Juni 2023"
    )

    val endDate = setOf(
        "1 Juli 2023",
        "2 Agustus 2023",
        "3 September 2023",
        "4 Oktober 2023",
        "5 November 2023",
        "6 Desember 2023"
    )

    val rating = Random().nextFloat() * (5.0f - 1.0f) + 1.0f

    val courseDescriptions = mapOf(
        "Cyber Security Basics" to "This course will cover the basics of cyber security and help you become familiar with different types of cyber attacks and how to protect yourself against them.",
        "Linux Full Guide" to "This course is a comprehensive guide to using the Linux operating system, from the basics of installation and configuration to advanced topics like network administration and server management.",
        "Wordpress Masterclass" to "In this masterclass, you will learn everything you need to know to create beautiful and functional websites using WordPress, the world's most popular content management system.",
        "Game Development with Godot Engine" to "If you've ever wanted to make your own video games, this course is for you. You'll learn how to use the Godot game engine to create 2D and 3D games from scratch, even if you have no prior experience with programming or game development.",
        "Unreal Engine 101" to "Unreal Engine is one of the most popular game engines in the world, used by professional game developers to create blockbuster titles. In this course, you'll learn the basics of game development with Unreal Engine and create your own games.",
        "Ethical Hacking" to "This course will teach you how to use ethical hacking techniques to identify and exploit vulnerabilities in computer systems and networks. You'll learn how to use tools like Kali Linux and Metasploit to perform penetration testing and secure your own systems."
    )

    val pembicara1 = listOf(
        "Bill Gates",
        "Mark zuckeberg",
        "Elon Musk",
        "Ada Lovelace",
        "Grace Hopper",
        "Guido van Rossum"
    )
    val pembicara2 = listOf(
        "Tim Berners-Lee",
        "James Gosling",
        "Ken Thompson",
        "Larry Page",
        "Barack Obama",
        "Donald Trump"
    )

    val contentsMap = mapOf(
        "Cyber Security Basics" to listOf(
            "-Konsep dasar keamanan siber",
            "-Metode serangan umum seperti phishing, malware, dan serangan DoS",
            "-Teknik enkripsi dan dekripsi",
            "-Keamanan jaringan dan sistem operasi",
            "-Penanganan insiden keamanan"
        ),
        "Linux Full Guide" to listOf(
            "-Pemahaman dasar tentang sistem operasi Linux",
            "-Cara menginstall dan mengkonfigurasi sistem Linux",
            "-Penggunaan perintah terminal dan file manager",
            "-Manajemen paket dan dependensi",
            "-Konfigurasi jaringan dan firewall"
        ),
        "Wordpress Masterclass" to listOf(
            "-Konsep dasar tentang Wordpress sebagai platform CMS",
            "-Cara menginstall Wordpress pada server",
            "-Penggunaan tema dan plugin pada Wordpress",
            "-Konfigurasi dasar pada Wordpress seperti halaman, posting, dan widget",
            "-Optimalisasi SEO untuk website menggunakan Wordpress"
        ),
        "Game Development with Godot Engine" to listOf(
            "-Pemahaman tentang pengembangan game",
            "-Penggunaan Godot Engine sebagai framework pengembangan game",
            "-Pembuatan game mechanic, scene, dan asset",
            "-Pemrograman game dengan GDScript",
            "-Optimalisasi performa dan debugging"
        ),
        "Unreal Engine 101" to listOf(
            "-Pengenalan Unreal Engine dan konsep game development",
            "-Pembuatan environment dan asset game",
            "-Pemrograman gameplay dengan Blueprints visual scripting",
            "-Pemrograman gameplay dengan C++",
            "-Optimasi performa dan debugging"
        ),
        "Ethical Hacking" to listOf(
            "-Pengenalan tentang hacking dan serangan siber",
            "-Penggunaan tools hacking seperti Kali Linux",
            "-Metode pengujian keamanan untuk aplikasi dan jaringan",
            "-Pemahaman tentang exploit dan teknik social engineering",
            "-Prinsip-prinsip etika dalam melakukan hacking"
        )
    )

    for (i in 1 until count) {
        val title = titles.elementAt(Random().nextInt(titles.size)) + " ID $i"
        val fullDescription = courseDescriptions[title.substringBefore(" ID")] ?: ""
        val contents = contentsMap[title.substringBefore(" ID")] ?: emptyList()
        val location = locations.elementAt(Random().nextInt(locations.size))

        courses.add(
            Course(
                title = title,
                briefDescription = "Hi! What's up? Oh you wanna strike a job offer but not too sure about your skills? Well, you might want to consider enrolling this course, NOW!",
                fullDescription = fullDescription,
                image = painterResource(id = R.drawable.img_course_demo_256),
                tint = randomColor(),
                category = randomCourseCategory(),
                rating = rating,
                fee = Random().nextInt(1_000_000),
                location = location,
                startDate = startDate.elementAt(Random().nextInt(startDate.size)),
                endDate = endDate.elementAt(Random().nextInt(endDate.size)),
                pembicara1 = pembicara1[Random().nextInt(pembicara1.size)],
                pembicara2 = pembicara2[Random().nextInt(pembicara2.size)],
                courseContents = contents.joinToString("\n")
            )
        )
    }
    return courses
}