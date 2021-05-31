package com.example.messagezemoga.ui.descriptionpost

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messagezemoga.R
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.room.entities.UserEntity
import com.example.messagezemoga.origindata.viewmodel.comment.CommentViewModel
import com.example.messagezemoga.origindata.viewmodel.post.PostViewModel
import com.example.messagezemoga.origindata.viewmodel.user.UserViewModel
import com.example.messagezemoga.transversal.constants.CommonsConstants
import com.example.messagezemoga.transversal.helperimage.HelperImage
import kotlinx.android.synthetic.main.fragment_descripcion_post.*
import kotlinx.android.synthetic.main.item_post_complete.*
import kotlinx.android.synthetic.main.shrimer_load.*
import java.util.*


class DescripcionPostFragment : Fragment() {


    //region Properties
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var recyclerCommentAdapter: RecyclerCommentAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var postViewModel: PostViewModel
    private var idPost: Int = 0
    private var idUser: Int = 0
    private lateinit var bodyPost: String
    private lateinit var postEntity: PostEntity
    private lateinit var menu: Menu
    //endregion


    //region Overload fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPost = arguments?.getInt(CommonsConstants.BUNDLE_ID_POST)!!
        idUser = arguments?.getInt(CommonsConstants.BUNDLE_ID_USER)!!
        bodyPost = arguments?.getString(CommonsConstants.BUNDLE_DESCRIPTION_POST)!!
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        return inflater.inflate(R.layout.fragment_descripcion_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Post"
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                activity?.onBackPressed()
                true
            }
            R.id.favPost -> {
                setFavPost()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
        inflater.inflate(R.menu.menu_fav_post, menu)
    }

    override fun onResume() {
        super.onResume()
        shimmerloaddata.startShimmer()
    }

    //endregion

    //region Own methods
    private fun initView() {
        initRecyclerView()
        getPost()
        getInfoUser()
        obtainAllComments()
    }

    private fun getPost() {
        postViewModel.getPostById(idPost).observe(viewLifecycleOwner, {
            tv_body_post_comment.text = it.body
            postEntity = it
            updateUiFav()
            setPostIsRead()
        })
    }

    private fun updateUiFav() {
        val drawable: Drawable = if (postEntity.isFavorite)
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24)!!
        else
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24)!!
        menu.getItem(0).icon = drawable
    }

    private fun setFavPost(): Boolean {
        postEntity.isFavorite = !postEntity.isFavorite
        postViewModel.updatePost(postEntity)
        return true
    }

    private fun setPostIsRead() {
        if (!postEntity.isRead) {
            postEntity.isRead = true
            postViewModel.updatePost(postEntity)
        }
    }

    private fun getInfoUser() {
        userViewModel.getUserById(idUser).observe(viewLifecycleOwner, {
            loadUiUser(it)
        })
    }

    private fun loadUiUser(userEntity: UserEntity) {
        profile_image_commet.setImageResource(HelperImage.getRandoProfilePicture())
        tv_name_user.text = userEntity.name
        tv_email_user.text = userEntity.email
        tv_phone_user.text = userEntity.phone
        tv_website_user.text = userEntity.website
    }

    private fun obtainAllComments() {
        commentViewModel.getAllPost(idPost.toString()).observe(viewLifecycleOwner, { listComment ->
            shimmerloaddata.stopShimmer()
            shimmerloaddata.visibility = View.GONE
            recycler_comments.visibility = View.VISIBLE
            tv_view_size_comments.text =
                String.format(requireContext().getString(R.string.size_comments), listComment.size)
            recyclerCommentAdapter.setListComment(listComment)
            recyclerCommentAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        recycler_comments.layoutManager = LinearLayoutManager(context)
        recyclerCommentAdapter = RecyclerCommentAdapter(LinkedList(), requireContext())
        recycler_comments.addItemDecoration(
            DividerItemDecoration(
                recycler_comments.context,
                (recycler_comments.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycler_comments.adapter = recyclerCommentAdapter

    }
    //endregion
}