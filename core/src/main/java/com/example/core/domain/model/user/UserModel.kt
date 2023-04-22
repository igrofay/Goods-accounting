package com.example.core.domain.model.user

interface UserModel {
    val firstname:String
    val lastname:String
    val patronymic: String
    val phone: String
    val email:String
    val imageUrl:String?
    val role: RoleLevel
}