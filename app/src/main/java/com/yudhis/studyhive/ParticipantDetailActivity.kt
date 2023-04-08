@file:JvmName("ParticipantDetailActivityKt")

package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.firebase.auth.FirebaseAuth
import com.yudhis.studyhive.data.userData
import com.yudhis.studyhive.dataclass.editedParticipantId
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import com.yudhis.studyhive.ui.theme.Transparent

class ParticipantDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyHiveTheme{
                var isEditing: Boolean by remember { mutableStateOf(false) }
                var pNickName by remember {mutableStateOf(userData.participants[editedParticipantId]?.pNickName as String)}
                var pFullName by remember {mutableStateOf(userData.participants[editedParticipantId]?.pName as String)}
                var pAddress by remember {mutableStateOf(userData.participants[editedParticipantId]?.address as String)}
                var pBirthDate by remember {mutableStateOf(userData.participants[editedParticipantId]?.birthdate as String)}
                // UI if not isEditing
                if (!isEditing) {
                    BackHandler(true) {
                        val intent = Intent(this, ParticipantListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                    ) {
                        // Header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(224.dp)
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
                                    text = "Partisipan",
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
                                        .width(72.dp)
                                        .height(72.dp)
                                        .clip(CircleShape)
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colors.secondary,
                                            shape = CircleShape
                                        ),
                                )
                                // Participant Name
                                Text(
                                    text = pNickName,
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(16.dp)
                                )
                            }
                        }

                        // Content
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp)
                        ) {
                            //Tab : {Profil | Riwayat}
                            item {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .clip(RoundedCornerShape(32.dp))
                                            .border(
                                                width = 1.dp,
                                                color = MaterialTheme.colors.onSurface,
                                                shape = RoundedCornerShape(32.dp)
                                            )
                                            .background(MaterialTheme.colors.background)
                                            .padding(horizontal = 8.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceAround,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ) {
                                            // Button Profile Tab
                                            Button(
                                                onClick = {},
                                                shape = RoundedCornerShape(32.dp),
                                            ) {
                                                Text(text = "Profil")
                                            }

                                            Spacer(Modifier.width(8.dp))

                                            Button(
                                                onClick = {},
                                                shape = RoundedCornerShape(32.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Transparent,
                                                    contentColor = MaterialTheme.colors.onBackground
                                                ),
                                                elevation = ButtonDefaults.elevation(0.dp),
                                            ) {
                                                Text(text = "Riwayat")
                                            }
                                        }
                                    }
                                }
                            }
                            item {
                                Spacer(Modifier.height(16.dp))
                            }

                            // Participant Full Name
                            item {
                                ReadOnlyField(
                                    text = pFullName,
                                    title = "Nama Lengkap"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // Address
                            item {
                                ReadOnlyField(
                                    text = pAddress,
                                    title = "Alamat"
                                )
                            }
                            // Birth Date
                            item { Spacer(Modifier.height(24.dp)) }
                            item {
                                ReadOnlyField(
                                    text = pBirthDate,
                                    title = "Tanggal Lahir"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // SKTM
                            item {
                                FileField(
                                    title = "Surat Keterangan Tidak Mampu",
                                    fileName = "Belum Diisi"
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

                            // Edit Button
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Button(
                                        onClick = {
                                            isEditing = true
                                        },
                                        shape = RoundedCornerShape(32.dp)
                                    ) {
                                        Text(text = "Edit")
                                    }
                                }
                            }
                        }
                    }
                }
                // UI if isEditing
                else {
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
                                    placeholder = "Saddam Husein"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // Participant Nick Name
                            item {
                                EditableField(
                                    fieldState = nickNameState,
                                    title = "Nama Panggilan",
                                    placeholder = "Husein"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // Address
                            item {
                                EditableField(
                                    fieldState = addressState,
                                    title = "Alamat",
                                    placeholder = "Jalan Belimbing No.47"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // Birth Date
                            item {
                                EditableField(
                                    fieldState = birthDateState,
                                    title = "Tanggal Lahir",
                                    placeholder = "07 Agustus 2003"
                                )
                            }
                            item { Spacer(Modifier.height(24.dp)) }
                            // SKTM
                            item {
                                FileField(
                                    title = "Surat Keterangan Tidak Mampu",
                                    fileName = "Belum Diisi"
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
                            // confirm exit editing
                            showExitEditingDialog = true
                        }

                        // Confirm exit editing dialog
                        if (showExitEditingDialog) {
                            confirmExitEditing(
                                onDissmissRequest = {
                                    showExitEditingDialog = false
                                },
                                saveChanges = {save ->
                                    // Save the data
                                    if (save) {
                                        pFullName = nameState.value.text
                                        pNickName = nickNameState.value.text
                                        pAddress = addressState.value.text
                                        pBirthDate = birthDateState.value.text
                                        userData.participants[editedParticipantId]?.pName = pFullName
                                        userData.participants[editedParticipantId]?.pNickName = pNickName
                                        userData.participants[editedParticipantId]?.address = pAddress
                                        userData.participants[editedParticipantId]?.birthdate = pBirthDate
                                    }
                                    isEditing = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun confirmExitEditing(onDissmissRequest: () -> Unit, saveChanges: (yes:Boolean) -> Unit) {
    Dialog(onDismissRequest = {onDissmissRequest()}) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Simpan Perubahan?",
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        saveChanges(true)
                    }
                )
                {
                    Text(text = "Simpan", color = MaterialTheme.colors.onPrimary)
                }
                Button(
                    colors = ButtonDefaults.buttonColors(Transparent),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        saveChanges(false)
                    }
                )
                {
                    Text(text = "Tidak", color = MaterialTheme.colors.onBackground)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ParticipantFormPreview() {
    val pNickName = "Partisipan 101"
    val pFullName = "Partisipan 101 Filkom UB"
    val pAddress = "Address is bikini bottom"
    val pBirthDate = "13 April 2003"
    StudyHiveTheme{
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
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
                        text = "Partisipan",
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
                            .width(72.dp)
                            .height(72.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colors.secondary,
                                shape = CircleShape
                            ),
                    )
                    // Participant Name
                    Text(
                        text = pNickName,
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }

            // Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                //Tab : {Profil | Riwayat}
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                                .clip(RoundedCornerShape(32.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colors.onSurface,
                                    shape = RoundedCornerShape(32.dp)
                                )
                                .background(MaterialTheme.colors.background)
                                .padding(horizontal = 8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(2.dp)
                            ) {
                                // Button Profile Tab
                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(32.dp),
                                ) {
                                    Text(text = "Profil")
                                }

                                Spacer(Modifier.width(8.dp))

                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(32.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Transparent,
                                        contentColor = MaterialTheme.colors.onBackground
                                    ),
                                    elevation = ButtonDefaults.elevation(0.dp),
                                ) {
                                    Text(text = "Riwayat")
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(16.dp))
                }

                // Participant Full Name
                item {
                    ReadOnlyField(
                        text = pFullName,
                        title = "Nama Lengkap"
                    )
                }
                item { Spacer(Modifier.height(24.dp)) }
                // Address
                item {
                    ReadOnlyField(
                        text = pAddress,
                        title = "Alamat"
                    )
                }
                // Birth Date
                item { Spacer(Modifier.height(24.dp)) }
                item {
                    ReadOnlyField(
                        text = pBirthDate,
                        title = "Tanggal Lahir"
                    )
                }
                item { Spacer(Modifier.height(24.dp)) }
                // SKTM
                item {
                    FileField(
                        title = "Surat Keterangan Tidak Mampu",
                        fileName = "Belum Diisi"
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

                // Edit Button
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Text(text = "Edit")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReadOnlyField(text: String = "Not Specified", title: String = "Not Specified") {
    // Address
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun EditableField(
    title: String,
    placeholder: String,
    fieldState: MutableState<TextFieldValue>
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colors.onBackground
    )
    Spacer(Modifier.height(7.dp))
    OutlinedTextField(
        value = fieldState.value,
        onValueChange = {
            fieldState.value = it
        },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 12.sp)
    )
}

@Composable
fun FileField(title: String, fileName: String) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(Modifier.height(2.dp))
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
            )
        ) {
            Text(text = fileName, fontSize = 12.sp)
        }
    }
}