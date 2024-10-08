package ddwu.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "FoodDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_PHOTO = "photo"
        const val COL_TITLE = "title"
        const val COL_DIRECTOR = "director"
        const val COL_SCORE = "score"
        const val COL_REVIEW = "review"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TITLE TEXT, $COL_DIRECTOR TEXT,  $COL_SCORE TEXT, $COL_REVIEW TEXT, $COL_PHOTO INTEGER)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '인터스텔라', '크리스토퍼 놀란', '5.0', '최고!', 0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '인사이드 아웃2', '켈시 맨', '4.5', '눈물 없이 보기 힘든 영화!', 0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '마션', '리들리 스콧', '4.0', ' SF 영화 중 최고입니다.', 0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '미녀와 야수', '빌 콘돈', '4.5', '영화로 보니 새롭네요.', 0)")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '겨울왕국2', '크리스 벅, 제니퍼 리', '4.5', " +
                "'너무 재밌어요! 정말 최고의 영화입니다!! 남녀노소 누구나 좋아할만한 영화이며, 정말 재미있고 감동적입니다. 가족과 함께 보러 가기 정말 좋아요!" +
                "강추합니다!! 모두 보러가세요!', 0)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}