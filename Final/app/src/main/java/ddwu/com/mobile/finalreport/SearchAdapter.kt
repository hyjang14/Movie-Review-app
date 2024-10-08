package ddwu.com.mobile.finalreport

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ListMovieBinding

class SearchAdapter(val movies: ArrayList<MovieDto>)
    : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    val TAG = "SearchAdapter"

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemBinding =
            ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(itemBinding)
    }

    private var filteredList = ArrayList(movies)

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        if (position < filteredList.size) {
            holder.itemBinding.imageView.setImageResource(filteredList[position].photo)
            holder.itemBinding.tvMovie.text = filteredList[position].title
            holder.itemBinding.tvDirector.text = filteredList[position].director
            holder.itemBinding.tvScore.text = "평점: ${filteredList[position].score}점"
        }
    }

    inner class SearchViewHolder(val itemBinding: ListMovieBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            /* 클릭해서 상세내용 띄우기 */
            itemBinding.root.setOnClickListener {
                val currentItemId = filteredList[adapterPosition].id
                listener?.onItemClick(it, currentItemId)
                Log.d(TAG, "검색창 클릭 ${currentItemId}")
            }
        }
    }
    /* 클릭 리스너 */
    interface OnItemClickListener {
        fun onItemClick(view : View, id : Int)
        // id: 클릭한 아이템의 id
    }

    var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    /* 검색 필터링 함수 */
    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase()
        filteredList = if (query.isEmpty()) {
            Log.d(TAG, "filter함수 글자 없음")
            ArrayList(movies)
        } else {
            val filteredResults = movies.filter {
                it.title.lowercase().contains(lowerCaseQuery)
            }
            Log.d(TAG, "filter함수 글자 있음 $filteredList")
            ArrayList(filteredResults)

        }
        notifyDataSetChanged()
    }

}

