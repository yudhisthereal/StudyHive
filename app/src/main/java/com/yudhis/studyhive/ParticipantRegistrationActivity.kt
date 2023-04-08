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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.data.userData
import com.yudhis.studyhive.dataclass.Participant
import com.yudhis.studyhive.dataclass.getNewId
import com.yudhis.studyhive.ui.theme.StudyHiveTheme

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
                        confirmExitEditing(
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
                                            pName = pFullName,
                                            pNickName = pNickName,
                                            address = pAddress,
                                            pPic = pPic,
                                            birthdate = pBirthDate,
                                            pId = newId,
                                            skd = skd,
                                            sktm = sktm,
                                            course_history = listOf()
                                        )
                                        userData.participants[newId] = newParticipant
                                        startActivity(
                                            Intent(
                                                context,
                                                ParticipantListActivity::class.java
                                            )
                                        )
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
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
