package com.example.messagezemoga.ui.posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.messagezemoga.R
import com.example.messagezemoga.origindata.room.entities.PostEntity

class RecyclerPostAdapter(
    private var listPost: List<PostEntity>,
    private val context: Context,
    private val callbackAdapter: IlistenerPost
) : RecyclerView.Adapter<RecyclerPostAdapter.ViewHolder>() {


    //region Override RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listPost.isEmpty()) return
        val onlyPost = listPost[position]
        inflateUI(holder, onlyPost)
    }

    override fun getItemCount(): Int = listPost.size
    //endregion


    //region ViewHolder
    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val tv_tittle_post: TextView = view!!.findViewById(R.id.tv_title_post)
        val tv_body_post: TextView = view!!.findViewById(R.id.tv_body_post)
        val iv_fav_post: ImageView = view!!.findViewById(R.id.iv_fav_post)
        val iv_unread_post: ImageView = view!!.findViewById(R.id.iv_unread_post)
        val cardPost: CardView = view!!.findViewById(R.id.cardPost)
    }
    //endregion

    //region Own methods
    private fun inflateUI(holder: ViewHolder, post: PostEntity) {
        holder.tv_tittle_post.text = post.title
        holder.tv_body_post.text = post.body.replace("\n", "")
        holder.cardPost.setOnClickListener { callbackAdapter.onClickPost(post) }
        if (!post.isFavorite)
            holder.iv_fav_post.visibility = View.GONE
        if (post.isRead)
            holder.iv_unread_post.visibility = View.GONE
    }

    fun setListPost(listPosts: List<PostEntity>) {
        this.listPost = listPosts
    }
    //endregion

    //region CallBackAdapter
    interface IlistenerPost {
        fun onClickPost(post: PostEntity)
    }
    //endregion


}