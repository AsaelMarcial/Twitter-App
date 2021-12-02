package models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("token")
    var token: String

)
