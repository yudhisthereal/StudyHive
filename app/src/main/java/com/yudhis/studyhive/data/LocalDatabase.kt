package com.yudhis.studyhive.data

import com.google.firebase.firestore.auth.User
import com.yudhis.studyhive.dataclass.*
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