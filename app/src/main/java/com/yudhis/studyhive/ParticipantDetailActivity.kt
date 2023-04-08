@file:JvmName("ParticipantDetailActivityKt")

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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yudhis.studyhive.composeables.HistoryEntry
import com.yudhis.studyhive.data.userData
import com.yudhis.studyhive.dataclass.Course
import com.yudhis.studyhive.dataclass.selectedParticipantID
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import com.yudhis.studyhive.ui.theme.Transparent

private enum class UIState{
    DETAIL, EDIT, HISTORY
}

class ParticipantDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyHiveTheme{
                val context = LocalContext.current.applicationContext
                var pNickName by remember {mutableStateOf(userData.participants[selectedParticipantID]?.pNickName as String)}
                var pFullName by remember {mutableStateOf(userData.participants[selectedParticipantID]?.pName as String)}
                var pAddress by remember {mutableStateOf(userData.participants[selectedParticipantID]?.address as String)}
                var pBirthDate by remember {mutableStateOf(userData.participants[selectedParticipantID]?.birthdate as String)}
                var uiState by remember {mutableStateOf<UIState>(UIState.DETAIL)}

                when (uiState) {
                    UIState.DETAIL -> {
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
                                        (
                                        RoundedCornerShape(
                                            bottomStart = 32.dp,
                                            bottomEnd = 32.dp
                                        )
                                    )
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
                                                    onClick = { uiState = UIState.DETAIL},
                                                    shape = RoundedCornerShape(32.dp),
                                                ) {
                                                    Text(text = "Profil")
                                                }

                                                Spacer(Modifier.width(8.dp))

                                                Button(
                                                    onClick = { uiState = UIState.HISTORY },
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
                                                uiState = UIState.EDIT
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
                    UIState.EDIT -> {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxSize()
                        ) {
                            var validate by remember { mutableStateOf(false) }
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
                                ConfirmExitEditing(
                                    onDissmissRequest = {
                                        showExitEditingDialog = false
                                    },
                                    saveChanges = {save ->
                                        // Save the data
                                        if(save) {
                                            validate = true
                                            if (nameState.value.text.isNotBlank()
                                                && nickNameState.value.text.isNotBlank()
                                                && addressState.value.text.isNotBlank()
                                                && birthDateState.value.text.isNotBlank()
                                            ) {
                                                pFullName = nameState.value.text
                                                pNickName = nickNameState.value.text
                                                pAddress = addressState.value.text
                                                pBirthDate = birthDateState.value.text
                                                userData.participants[selectedParticipantID]?.pName =
                                                    pFullName
                                                userData.participants[selectedParticipantID]?.pNickName =
                                                    pNickName
                                                userData.participants[selectedParticipantID]?.address =
                                                    pAddress
                                                userData.participants[selectedParticipantID]?.birthdate =
                                                    pBirthDate
                                            }
                                            else {
                                                Toast.makeText(context, "Pastikan semua field terisi", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                        uiState = UIState.DETAIL
                                    }
                                )
                            }
                        }
                    }
                    UIState.HISTORY -> {
                        BackHandler(true) {
                            uiState = UIState.DETAIL
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
                                        (
                                        RoundedCornerShape(
                                            bottomStart = 32.dp,
                                            bottomEnd = 32.dp
                                        )
                                    )
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(32.dp)
                            ) {
                                //Tab : {Profil | Riwayat}
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
                                                onClick = { uiState = UIState.DETAIL },
                                                shape = RoundedCornerShape(32.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Transparent,
                                                    contentColor = MaterialTheme.colors.onBackground
                                                ),
                                                elevation = ButtonDefaults.elevation(0.dp)
                                            ) {
                                                Text(text = "Profil")
                                            }

                                            Spacer(Modifier.width(8.dp))

                                            Button(
                                                onClick = { uiState = UIState.HISTORY },
                                                shape = RoundedCornerShape(32.dp)
                                            ) {
                                                Text(text = "Riwayat")
                                            }
                                        }
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                                CourseHistory(history = userData.participants[selectedParticipantID]?.course_history as MutableList<Course>)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseHistory(history: MutableList<Course>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        for (entry in history) {
            item {
                HistoryEntry(courseData = entry)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ConfirmExitEditing(onDissmissRequest: () -> Unit, saveChanges: (yes:Boolean) -> Unit) {
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
                                    onClick = { },
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
    fieldState: MutableState<TextFieldValue>,
    validate:Boolean = false
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = MaterialTheme.colors.onBackground
    )
    Spacer(Modifier.height(7.dp))

    // check errors (validation)
    var isError = false
    if (validate) {
        isError = fieldState.value.text.isBlank()
    }

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
        textStyle = TextStyle(fontSize = 12.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = MaterialTheme.colors.error
        ),
        isError = isError
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