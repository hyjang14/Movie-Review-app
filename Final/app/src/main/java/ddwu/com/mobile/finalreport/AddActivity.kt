package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.data.MovieDao
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)

        /* 이미지 임의 설정 */
        addBinding.addImage.setImageResource(R.mipmap.movie_image)
        addBinding.addImage.scaleType = ImageView.ScaleType.CENTER_CROP

        // 1. 추가 버튼을 눌렀을 경우
        addBinding.addBt.setOnClickListener {
            val rating = addBinding.ratingBar.rating

            if (TextUtils.isEmpty(addBinding.etAddMovie.text) ||
                rating == 0.0f ||
                TextUtils.isEmpty(addBinding.etAddDirector.text))
                Toast.makeText(this, "필수 입력 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            else {
                val title = addBinding.etAddMovie.text.toString()
                val director = addBinding.etAddDirector.text.toString()
                val score = rating.toString()
                val review = addBinding.etAddReview.text.toString()

                if (movieDao.addMovie( MovieDto(0, title, director, score, review, 0)) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }
        // 2. 취소 버튼을 눌렀을 경우
        addBinding.cancelBt.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

}