package ddwu.com.mobile.finalreport.data

import java.io.Serializable

data class MovieDto(val id: Int, var title: String, var director: String, var score: String, var review: String, val photo: Int): Serializable {

}