package com.example.messagezemoga.ui.descriptionpost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messagezemoga.R
import com.example.messagezemoga.entities.CommentPost
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.transversal.helperimage.HelperImage
import kotlin.random.Random

class RecyclerCommentAdapter (
    private var listComment:List<CommentPost>,
    private val context:Context
        ) : RecyclerView.Adapter<RecyclerCommentAdapter.ViewHolder>() {


    //region Override RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_comment_post, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listComment.isEmpty()) return
        val onlyPost = listComment[position]
        inflateUI(holder, onlyPost)
    }

    override fun getItemCount(): Int = listComment.size
    //endregion


    //region ViewHolder
    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val iv_profile: ImageView = view!!.findViewById(R.id.profile_image_commet)
        val tv_user_name: TextView = view!!.findViewById(R.id.tv_username_comment)
        val tv_time_coment: TextView = view!!.findViewById(R.id.tv_post_comment)
        val tv_comment: TextView = view!!.findViewById(R.id.tv_comment)
        val tv_email : TextView = view!!.findViewById(R.id.tv_email_comment)
    }
    //endregion

    //region Own methods
    private fun inflateUI(holder: ViewHolder, comment: CommentPost) {
        val randomTime = List(1){ Random.nextInt(0,10)}
        holder.tv_user_name.text = comment.name.substring(0,10)
        holder.tv_comment.text = comment.body.replace("\n", "")
        holder.tv_time_coment.text = String.format("%sh ago",randomTime[0])
        holder.iv_profile.setImageResource(HelperImage.getRandoProfilePicture())
        holder.tv_email.text = comment.email
    }

    fun setListComment(listComment: List<CommentPost>) {
        this.listComment = listComment
    }
    //endregion

    //region CallBackAdapter
    interface IlistenerPost {
        fun onClickPost(post: PostEntity)
    }
    //endregion
}