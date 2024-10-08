package ddwu.com.mobile.finalreport

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.data.MovieDto
import ddwu.com.mobile.finalreport.databinding.ListMovieBinding

class MovieAdapter (val movies: ArrayList<MovieDto>)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val TAG = "MovieAdapter"

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder.itemBinding.imageView.setImageResource( movies[position].photo )
        holder.itemBinding.tvMovie.text = movies[position].title
        holder.itemBinding.tvDirector.text = movies[position].director
        holder.itemBinding.tvScore.text = "평점: ${movies[position].score}점"
    }

    inner class MovieViewHolder(val itemBinding: ListMovieBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            val TAG = "FoodViewHolder"

            init {
                // 1. 클릭해서 수정하는 기능
                itemBinding.root.setOnClickListener {
                    listener?.onItemClick(it, adapterPosition)
                    Log.d(TAG, "클릭")
                }
                // 2. 롱클릭해서 삭제하는 기능
                itemBinding.root.setOnLongClickListener {
                    Log.d(TAG, "롱클릭")
                    val result = longClickListener?.onItemLongClick(it, adapterPosition)
                    true
                }
            }
        }

    /* 1. 클릭 리스너 */
    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
        // position: 몇 번째 함수를 선택했는지
    }

    var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    /* 2. 롱클릭 리스너 */
    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int) : Boolean
    }

    var longClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener (listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

}