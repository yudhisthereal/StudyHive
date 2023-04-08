package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.data.userData
import com.yudhis.studyhive.dataclass.MAX_PARTICIPANTS
import com.yudhis.studyhive.dataclass.Participant
import com.yudhis.studyhive.dataclass.editedParticipantId
import com.yudhis.studyhive.ui.theme.Gray300
import com.yudhis.studyhive.ui.theme.StudyHiveTheme

class ParticipantListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyHiveTheme {
                val context = LocalContext.current.applicationContext
                // Outer Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    Column() {
                        // Title
                        Spacer(Modifier.height(72.dp))
                        Text(
                            text = "Partisipanmu",
                            fontSize = 32.sp,
                            color = MaterialTheme.colors.onPrimary,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(start = 32.dp),
                        )
                        Spacer(Modifier.height(48.dp))
                        // Content Container
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                                    clip = true
                                )
                                .background(MaterialTheme.colors.background)
                                .padding(24.dp)
                        ) {
                            // Content
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                // The List
                                for (participant in userData.participants.values) {
                                    ParticipantListItem(
                                        participantData = participant,
                                        onDetailClick = { pId ->
                                            editedParticipantId = pId
                                            val intent = Intent(
                                                context,
                                                ParticipantDetailActivity::class.java
                                            )
                                            startActivity(intent)
                                            finish()
                                        },
                                        onDeleteClick = { pId ->
                                            userData.participants.remove(pId)
                                            startActivity(Intent(context, ParticipantListActivity::class.java))
                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                                            finish()
                                            // Toast.makeText(context, "pId: $pId, exists? ${userData.participants.keys.contains(pId)}", Toast.LENGTH_LONG).show()
                                        }
                                    )
                                    Spacer(Modifier.height(10.dp))
                                }
                                Spacer(Modifier.height(16.dp))
                                // Add Button
                                if (userData.participants.size < MAX_PARTICIPANTS) {
                                    Button(
                                        onClick = {
                                            startActivity(Intent(context, ParticipantRegistrationActivity::class.java))
                                            finish()
                                        },
                                        shape = RoundedCornerShape(32.dp)
                                    ) {
                                        Text(text = "Tambah Partisipan")
                                    }
                                }
                                if (userData.participants.isEmpty()) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Anda Belum Memiliki Partisipan.",
                                            fontSize = 32.sp,
                                            color = Gray300,
                                        )
                                    }
                                }
                                // Back and Save Buttons
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    //Back Button
                                    Button(
                                        onClick = {
                                            val intent =
                                                Intent(context, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        },
                                        shape = RoundedCornerShape(32.dp),
                                        colors = ButtonDefaults
                                            .buttonColors(
                                                backgroundColor = MaterialTheme.colors.background,
                                                contentColor = MaterialTheme.colors.onBackground
                                            )
                                    ) {
                                        Text(text = "Kembali")
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    //Save Button
                                    Button(
                                        onClick = {},
                                        shape = RoundedCornerShape(32.dp)
                                    ) {
                                        Text(text = "Simpan")
                                    }
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
fun Preview() {
    StudyHiveTheme {
        val participants = userData.participants
        // Outer Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {
            Column() {
                // Title
                Spacer(Modifier.height(96.dp))
                Text(
                    text = "Partisipanmu",
                    fontSize = 32.sp,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(start = 32.dp),
                )
                Spacer(Modifier.height(48.dp))
                // Content Container
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                            clip = true
                        )
                        .background(MaterialTheme.colors.background)
                        .padding(24.dp)
                ) {
                    // Content
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // The List
                        LazyColumn(
                            horizontalAlignment = Alignment.End
                        ) {
                            for (participant in participants.values) {
                                item {
                                    ParticipantListItem(
                                        participantData = participant,
                                        onDetailClick = {
                                        },
                                        onDeleteClick = {
                                        }
                                    )
                                    Spacer(Modifier.height(10.dp))
                                }
                            }
                            item{
                                Spacer(Modifier.height(16.dp))
                            }
                            // Add Button
                            item {
                                Button(
                                    onClick = {},
                                    shape = RoundedCornerShape(32.dp)
                                ) {
                                    Text(text = "Tambah Partisipan")
                                }
                            }
                        }
                        // Back and Save Buttons
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //Back Button
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(32.dp),
                                colors = ButtonDefaults
                                    .buttonColors(
                                        backgroundColor = MaterialTheme.colors.background,
                                        contentColor = MaterialTheme.colors.onBackground
                                    )
                            ) {
                                Text(text = "Kembali")
                            }
                            Spacer(Modifier.width(8.dp))
                            //Save Button
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(32.dp)
                            ) {
                                Text(text = "Simpan")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ParticipantListItem(
    participantData: Participant,
    onDetailClick: (ID: String) -> Unit,
    onDeleteClick: (ID: String) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val pName = participantData.pName
    val pPic = participantData.pPic
    val displayName =
        if (pName.length <= 19) pName
        else pName.subSequence(0, 24).toString() + "..."
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(56.dp), clip = true)
            .padding(12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            // Profile Pic of Participant
            Spacer(Modifier.width(16.dp))
            Image(
                imageVector = pPic,
                contentDescription = "Profile Pic of Participant",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.width(24.dp))
            // Name, and Detail Button
            Column (
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    modifier = Modifier
                        .clickable(onClick = {
                            onDetailClick(participantData.pId)
                        }),
                    text = "Lihat Detail",
                    textDecoration = TextDecoration.Underline,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.primary
                )
            }

            // Delete Button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        onDeleteClick(participantData.pId)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete participant button",
                        modifier = Modifier.scale(1.25f),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }

}