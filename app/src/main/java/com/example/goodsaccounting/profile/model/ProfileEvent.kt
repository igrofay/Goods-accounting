package com.example.goodsaccounting.profile.model

import com.example.goodsaccounting.common.model.UIEvent

internal sealed class ProfileEvent : UIEvent(){
    class SelectImage(val uri: String) : ProfileEvent()
    class InputFirstname(val firstname: String) : ProfileEvent()
    class InputLastname(val lastname: String): ProfileEvent()
    class InputPatronymic(val patronymic: String): ProfileEvent()
    class InputPhone(val phone:String) : ProfileEvent()
//    class InputEmail(val email: String) : ProfileEvent()
    object Save : ProfileEvent()
}