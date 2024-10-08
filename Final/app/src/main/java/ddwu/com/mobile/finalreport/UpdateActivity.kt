package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.data.MovieDao
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    val updateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(updateBinding.root)

        /* RecyclerView 에서 선택하여 전달한 dto 를 확인 */
        val dto = intent.getSerializableExtra("dto") as MovieDto

        /* 전달받은 데이터들을 화면에 채워주는 중 */
        updateBinding.etUpdateMovie.setText(dto.title)
        updateBinding.etUpdateDirector.setText(dto.director)
        updateBinding.ratingBar2.setRating(dto.score.toFloat())
        updateBinding.etUpdateReview.setText(dto.review)
        updateBinding.imageUpdate.setImageResource(dto.photo) // 전달받은 이미지 채우기

        /* 1. 수정 버튼 누를 시 */
        updateBinding.updateBt.setOnClickListener{
            if (TextUtils.isEmpty(updateBinding.etUpdateMovie.text) ||
                updateBinding.ratingBar2.rating == 0.0f ||
                TextUtils.isEmpty(updateBinding.etUpdateDirector.text))
                Toast.makeText(this, "필수 입력 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
            else {
                dto.title = updateBinding.etUpdateMovie.text.toString()
                dto.director = updateBinding.etUpdateDirector.text.toString()
                dto.score = updateBinding.ratingBar2.rating.toString()
                dto.review = updateBinding.etUpdateReview.text.toString()

                // 수정 성공한 경우
                if (movieDao.updateMovie(dto) > 0) {
                    setResult(RESULT_OK)
                }
                // 수정 실패한 경우
                else {
                    setResult(RESULT_CANCELED)
                }

                // MainActivity로 돌아가기
                finish()
            }
        }

        /* 2. 취소 버튼 누를 시 */
        updateBinding.updateCanBt.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}