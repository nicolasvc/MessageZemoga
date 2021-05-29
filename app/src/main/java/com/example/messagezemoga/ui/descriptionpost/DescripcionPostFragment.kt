package com.example.messagezemoga.ui.descriptionpost

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messagezemoga.R
import com.example.messagezemoga.origindata.room.entities.UserEntity
import com.example.messagezemoga.origindata.viewmodel.comment.CommentViewModel
import com.example.messagezemoga.origindata.viewmodel.user.UserViewModel
import com.example.messagezemoga.transversal.constants.CommonsConstants
import com.example.messagezemoga.transversal.helperimage.HelperImage
import kotlinx.android.synthetic.main.fragment_descripcion_post.*
import kotlinx.android.synthetic.main.item_post_complete.*
import java.util.*


class DescripcionPostFragment : Fragment() {


    //region Properties
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var recyclerCommentAdapter: RecyclerCommentAdapter
    private lateinit var userViewModel: UserViewModel
    private var idPost: Int = 0
    private var idUser: Int = 0
    private lateinit var bodyPost :String
    //endregion


    //region Overload fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPost = arguments?.getInt(CommonsConstants.BUNDLE_ID_POST)!!
        idUser = arguments?.getInt(CommonsConstants.BUNDLE_ID_USER)!!
        bodyPost= arguments?.getString(CommonsConstants.BUNDLE_DESCRIPTION_POST)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
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
                updatePost()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fav_post, menu)
    }
    //endregion

    //region Own methods
    private fun initView() {
        initRecyclerView()
        getInfoUser()
        obtainAllComments()
    }

    private fun updatePost(): Boolean {


        return true
    }

    private fun getInfoUser() {
        userViewModel.getUserById(idUser).observe(viewLifecycleOwner, {
            loadUiUser(it)
        })
    }


    private fun loadUiUser(userEntity: UserEntity) {
        tv_body_post_comment.text = bodyPost
        profile_image_commet.setImageResource(HelperImage.getRandoProfilePicture())
        tv_name_user.text =userEntity.name
        tv_email_user.text =userEntity.email
        tv_phone_user.text =userEntity.phone
        tv_website_user.text =userEntity.website
    }

    private fun obtainAllComments() {
        commentViewModel.getAllPost(idPost.toString()).observe(viewLifecycleOwner, { listComment ->
            tv_view_size_comments.text =
                String.format(requireContext().getString(R.string.size_comments), listComment.size)
            recyclerCommentAdapter.setListComment(listComment)
            recyclerCommentAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        recycler_comments.layoutManager = LinearLayoutManager(context)
        recyclerCommentAdapter = RecyclerCommentAdapter(LinkedList(), requireContext())
        recycler_comments.adapter = recyclerCommentAdapter

    }
    //endregion
}