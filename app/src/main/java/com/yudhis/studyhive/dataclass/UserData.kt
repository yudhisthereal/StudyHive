package com.yudhis.studyhive.dataclass

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.yudhis.studyhive.DummyCourses
import com.yudhis.studyhive.R
import com.yudhis.studyhive.data.participantsGenerated
import com.yudhis.studyhive.data.userData

data class UserData(
    var participants: MutableMap<String, Participant>,
)
var selectedParticipantID = ""
val MAX_PARTICIPANTS = 5
@Composable
fun dummyParticipants() : MutableMap<String, Participant> {
    participantsGenerated = true
    return mutableMapOf(
        "1" to Participant(
            pName = "Muhammad Al Azhar",
            pNickName = "Azhar",
            pId = "1",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            pAddress = "Permukaan Bumi",
            pBirthdate = "17 April 2003",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            courseHistory = DummyCourses(9)
        ),
        "2" to Participant(
            pName = "Julian",
            pNickName = "Jul",
            pId = "2",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            pAddress = "Permukaan Bumi",
            pBirthdate = "17 November 2001",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            courseHistory = DummyCourses(7)
        ),
        "3" to Participant(
            pName = "Doni Setiawan",
            pNickName = "Doni",
            pId = "3",
            pPic = ImageVector.vectorResource(id = R.drawable.ic_person),
            pAddress = "Permukaan Bumi",
            pBirthdate = "23 Juni 2002",
            sktm = ImageVector.vectorResource(id = R.drawable.ic_file),
            skd = ImageVector.vectorResource(id = R.drawable.ic_file),
            courseHistory = DummyCourses(5)
        )
    )
}

fun getNewId(): String {
    var i = 0
    while (userData.participants.keys.contains(i.toString())) i++
    return i.toString()
}