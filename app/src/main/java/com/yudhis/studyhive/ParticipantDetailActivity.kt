@file:JvmName("ParticipantDetailActivityKt")

package com.yudhis.studyhive

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.ui.theme.StudyHiveTheme
import com.yudhis.studyhive.ui.theme.Transparent

class ParticipantDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pNickName = "Null"
        val pFullName = "Null"
        val pAddress = "Null"
        val pBirthDate = "Null"
        setContent {
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