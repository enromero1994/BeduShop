package DataClass

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (

    var data: Data


    //val sprites : sprites? = null

)

data class Data(

    val id: Int?,
    val email: String?,
    val first_name: String?,
    val last_name: String?,
    val avatar: String?,


)