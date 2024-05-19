package com.example.mentormatch.data

// Representa os dados do usuário recuperados do Firebase Firestore
data class UserData(
    var userId:     String? = "",
    var name:       String? = "",
    var username:   String? = "",
    var imageUrl:   String? = "",
    var field:      String? = "",
    var university: String? = "",
    var hobbie:     String? = "",
    var city:       String? = "",
    var available:  String? = "",
    var bio:        String? = "",

) {
    fun toMap() = mapOf(
        "userId"     to userId,
        "name"       to name,
        "username"   to username,
        "imageUrl"   to imageUrl,
        "field"      to field,
        "university" to university,
        "hobbie"     to hobbie,
        "city"       to city,
        "available"  to available,
        "bio"        to bio
    )
}

data class PreferencesData(
    var userId:               String? = "",
    var localPreference:      String? = "",
    var fieldPreference:      String? = "",
    var assignmentPreference: String? = "",
    var availablePreference:  String? = "",

    ) {
    fun toMap() = mapOf(
        "userId"               to userId,
        "localPreference"      to localPreference,
        "fieldPreference"      to fieldPreference,
        "assignmentPreference" to assignmentPreference,
        "availablePreference"  to availablePreference
    )
}