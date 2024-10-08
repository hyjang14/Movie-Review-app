package ddwu.com.mobile.finalreport

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.MovieDao
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    val TAG = "SearchActivity"

    val searchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    lateinit var movies : ArrayList<MovieDto>

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(searchBinding.root)
        searchBinding.searchView.queryHint = "검색할 영화의 제목을 입력하세요."

        movies = movieDao.getAllMovies()
        val adapter = SearchAdapter(movies)
        searchBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        searchBinding.recyclerView.adapter = adapter

        /* 현재 항목 상세 리뷰 dialog 띄우기 */
        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val clickedMovie = movies.find { it.id == position }

                if (clickedMovie != null) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@SearchActivity).apply {
                        setTitle("[${clickedMovie.title}]의 상세 리뷰")
                        setMessage("${clickedMovie.review}")
                        setNegativeButton("창 닫기", null)
                    }
                    builder.show()
                } else {
                    Log.d(TAG, "해당 영화를 찾을 수 없음")
                }
            }
        })

        /* 검색하는 함수 */
        searchBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "검색 돋보기 눌림")
                query?.let {
                    adapter.filter(query)
                }
                adapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter.filter(it)
                }
                adapter.notifyDataSetChanged()
                return false
            }
        })
    }
}