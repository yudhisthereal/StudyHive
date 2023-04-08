package com.yudhis.studyhive.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.R
import com.yudhis.studyhive.dataclass.*
import com.yudhis.studyhive.tools.randomColor

var coursesGenerated = false
var participantsGenerated = false
var coursesDataset: MutableSet<Course> = mutableSetOf()
var userData: UserData = UserData(
    participants = mutableMapOf()
)


fun filteredData(query:String, category:CourseCategory, location:String, feeRange: FeeRange, onlineOrOffline: OnlineOrOffline): Set<Course> {
    return coursesDataset.filter {
        (it.title.contains(query.trim(), ignoreCase = true) || query.isBlank())
                && (it.location.contains(location.trim(), ignoreCase = true) || location.isBlank())
                && (it.category == category || category == CourseCategory.All)
                && ((feeRange.bottomLimit until feeRange.topLimit).contains(it.fee) || feeRange.topLimit == 0)
                && (it.onlineOrOffline == onlineOrOffline || onlineOrOffline == OnlineOrOffline.All)
    }.toSet()
}

fun saveUserData() {
    val id = FirebaseAuth.getInstance().currentUser?.uid as String
    val db = Firebase.firestore
    db.collection("userData").document(id)
        .set(userData)
}

fun saveCourseData() {
    val db = Firebase.firestore
    db.collection("courses").document("courses")
        .set(coursesDataset)
}

@Composable
fun loadUserData(): Boolean {
    val id = FirebaseAuth.getInstance().currentUser?.uid as String
    val db = Firebase.firestore
    val courseImage = painterResource(id = R.drawable.img_course_demo_256)
    val fileImage =  ImageVector.vectorResource(id = R.drawable.ic_file)
    var success: Boolean = false
    db.collection("userData").document(id)
        .get()
        .addOnSuccessListener { document ->
            if (document.data != null) {
                val doc = document.data?.get("participants") as Map<*, *>
                doc.keys.forEach {id ->
                    val participant = doc[id] as Map<*, *>
                    val pId = participant["pid"].toString()
                    val pName = participant["pname"].toString()
                    val pNickName = participant["pnickName"].toString()
                    val pAddress = participant["paddress"].toString()
                    val pBirthdate = participant["pbirthdate"].toString()
                    val pCourseHistory = mutableListOf<Course>()

                    val rawCourseHistory = participant["courseHistory"] as List<*>
                    rawCourseHistory.forEach {
                        val course = it as Map<*, *>
                        var onlineOrOffline = OnlineOrOffline.All
                        if (course["onlineOrOffline"].toString() == "Online")
                            onlineOrOffline = OnlineOrOffline.Online
                        else if (course["onlineOrOffline"].toString() == "Offline")
                            onlineOrOffline = OnlineOrOffline.Offline
                        val thisCourse = Course(
                            title = course["title"].toString(),
                            fullDescription = course["fullDescription"].toString(),
                            briefDescription = course["briefDescription"].toString(),
                            image = courseImage,
                            fee = course["fee"].toString().toInt(),
                            location = course["location"].toString(),
                            onlineOrOffline = onlineOrOffline,
                            rating = course["rating"].toString().toFloat(), // out of 5
                            prerequisites = listOf(),
                            category = CourseCategory.valueOf(course["category"].toString()),
                            startDate = course["startDate"].toString(),
                            endDate = course["endDate"].toString(),
                            tint = randomColor(),
                            pembicara1 = course["pembicara1"].toString(),
                            pembicara2 = course["pembicara2"].toString(),
                            courseContents = course["courseContents"].toString()
                        )
                        pCourseHistory.add(thisCourse)
                    }

                    // make this participant
                    userData.participants[pId] = Participant(
                        pId = pId,
                        pName = pName,
                        pNickName = pNickName,
                        pAddress = pAddress,
                        pBirthdate = pBirthdate,
                        courseHistory = pCourseHistory,
                        pPic = Icons.Default.Person,
                        sktm = fileImage,
                        skd = fileImage
                    )
                }
                success = true
            }
        }
    return success
}