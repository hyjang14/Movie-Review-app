package ddwu.com.mobile.finalreport
// 과제명: 영화 정보 관리 앱
// 분반: 02 분반
// 학번: 20220800 성명: 장하연
// 제출일: 2024년 6월 23일

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport.data.MovieDao
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    val binding by lazy {
            ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var movies : ArrayList<MovieDto>

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        movies = movieDao.getAllMovies()
        val adapter = MovieAdapter(movies)
        binding.recyclerView.adapter = adapter

        /* 1. 현재 항목 DB 수정 & 넘기기 */
        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", movies.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        })

        /* 2. 현재 항목 DB 삭제 & 화면 갱신 */
        adapter.setOnItemLongClickListener(object: MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("영화 리뷰 삭제")
                    setMessage("영화 [${movies[position].title}] 의 리뷰를 삭제하시겠습니까?")
                    setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        if(movieDao.deleteMovie(movies[position]) > 0) {
                            movies.clear()
                            movies.addAll(movieDao.getAllMovies())
                            binding.recyclerView.adapter?.notifyDataSetChanged()
                        }
                    })
                    setNegativeButton("취소", null)
                }
                builder.show()

                return true
            }
        })
    }

    /* 옵션메뉴 생성하기 */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // 1. 영화 리뷰 추가 activity 띄우기
            R.id.item1 -> {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            // 2. 개발자 소개 activity 띄우기
            R.id.item2 -> {
                val intent = Intent(this@MainActivity, IntroActivity::class.java)
                startActivity(intent)
            }
            // 3. 앱 종료 dialog 띄우기
            R.id.item3 -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this).apply {
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니까?")
                    setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        finishAffinity() // 앱 종료
                    })
                    setNegativeButton("취소", null)
                }
                builder.show()
            }
            // 4. 영화 리뷰 검색하기
            R.id.search -> {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            /* 1. DB 수정 후 화면 갱신 */
            REQ_UPDATE -> {
                if (resultCode == RESULT_OK) {
                    movies.clear()
                    movies.addAll(movieDao.getAllMovies())
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                } else {
//                    Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
                }
            }

            /* 2. 추가했을 때 화면 갱신 */
            REQ_ADD -> {
                if (resultCode == RESULT_OK) {
                    // 기존 항목 제거하기
                    movies.clear()
                    movies.addAll(movieDao.getAllMovies())
                    // RecyclerView 갱신
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                } else {
//                    Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}