package com.yudhis.studyhive.dataclass

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.yudhis.studyhive.R
import com.yudhis.studyhive.data.participantsGenerated
import com.yudhis.studyhive.data.userData

data class UserData(
    var participants: MutableMap<String, Participant>,
)
var editedParticipantId = ""
val MAX_PARTICIPANTS = 5
@Composable
fun dummyParticipants() : MutableMap<String, Participant> {
    participantsGenerated = true
    return mutableMapOf(
        "1" to Participant(
            pName = "Muhammad Al Azhar",
            pNickName = "Azhar",
            pId = "17",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            address = "Permukaan Bumi",
            birthdate = "17 April 2003",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            course_history = listOf(
                // HELPPPP
            )
        ),
        "2" to Participant(
            pName = "Julian",
            pNickName = "Jul",
            pId = "23",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            address = "Permukaan Bumi",
            birthdate = "17 November 2001",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            course_history = listOf(
                // HELPPPP
            )
        ),
        "3" to Participant(
            pName = "Doni Setiawan",
            pNickName = "Doni",
            pId = "33",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            address = "Permukaan Bumi",
            birthdate = "23 Juni 2002",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            course_history = listOf(
                // HELPPPP
            )
        )
    )
}

fun getNewId(): String {
    var i = 0
    while (userData.participants.keys.contains(i.toString())) i++
    return i.toString()
}