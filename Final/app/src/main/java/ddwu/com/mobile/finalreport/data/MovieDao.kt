package ddwu.com.mobile.finalreport.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import ddwu.com.mobile.finalreport.R

class MovieDao(val context: Context) {
    /* 1. MainActivity에서 DB 접근하는 함수 : getAllMovies & deleteMovie */
    // DB 삭제하는 함수
    fun deleteMovie(dto: MovieDto) : Int {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        // 몇개가 삭제되는지 반환
        val deleteCount = db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)

        return deleteCount
    }

    @SuppressLint("Range")
    fun getAllMovies() : ArrayList<MovieDto> {
        val helper = MovieDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDto>()
        with (cursor) {
            while (moveToNext()) { // 다음 레코드가 있으면 들어온다.
                val id = getInt( getColumnIndex(BaseColumns._ID) )
                val title = getString ( getColumnIndex(MovieDBHelper.COL_TITLE) )
                val director = getString ( getColumnIndex(MovieDBHelper.COL_DIRECTOR) )
                val score = getString ( getColumnIndex(MovieDBHelper.COL_SCORE) )
                val review = getString ( getColumnIndex(MovieDBHelper.COL_REVIEW) )

                val dto = MovieDto(id, title, director, score, review, R.mipmap.movie_image)
                movies.add(dto)
            }
        }
        return movies
    }

    /* 2. AddActivity에서 DB 접근하는 함수 : addMovie */
    // DB에 전달받은 데이터 추가하는 함수
    fun addMovie(newDto: MovieDto): Long {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val newValue = ContentValues()
        newValue.put(MovieDBHelper.COL_TITLE, newDto.title)
        newValue.put(MovieDBHelper.COL_DIRECTOR, newDto.director)
        newValue.put(MovieDBHelper.COL_SCORE, newDto.score)
        newValue.put(MovieDBHelper.COL_REVIEW, newDto.review)
        newValue.put(MovieDBHelper.COL_PHOTO, newDto.photo)

        val newCount = db.insert(MovieDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }

    /* 3. UpdateActivity에서 DB 접근하는 함수 : updateMovie */
    // DB 수정하기
    fun updateMovie(dto: MovieDto): Int {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val updateValue = ContentValues()

        updateValue.put(MovieDBHelper.COL_TITLE, dto.title)
        updateValue.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        updateValue.put(MovieDBHelper.COL_SCORE, dto.score)
        updateValue.put(MovieDBHelper.COL_REVIEW, dto.review)
        updateValue.put(MovieDBHelper.COL_PHOTO, dto.photo)

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount =  db.update(MovieDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()

        return resultCount
    }
}