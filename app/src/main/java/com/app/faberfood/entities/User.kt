package com.app.faberfood.entities

data class User(
    val id: Long,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String
){
    fun toMap():MutableMap<String, Any>{
        return mutableMapOf(
//            "user_id" to this.userId,
            "name" to this.name,
            "lastname" to this.lastname
        )

    }
}
