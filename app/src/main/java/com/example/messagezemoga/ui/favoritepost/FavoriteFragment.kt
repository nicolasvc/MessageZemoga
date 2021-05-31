package com.example.messagezemoga.ui.favoritepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagezemoga.R
import com.example.messagezemoga.databinding.FragmentFavoriteBinding
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.viewmodel.post.PostViewModel
import com.example.messagezemoga.transversal.constants.CommonsConstants
import com.example.messagezemoga.ui.posts.RecyclerPostAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.shrimer_load.*
import java.util.*

class FavoriteFragment : Fragment(), RecyclerPostAdapter.IlistenerPost {

    //region Propiedades
    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerPostAdapter: RecyclerPostAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var listPostInit: List<PostEntity>
    private var navController: NavController? = null
    private var isDeletePost: Boolean = false
    //endregion

    //region Override methos
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        shimmerloaddata.startShimmer()
    }

    //endregion


    //region Own methods
    private fun initView() {
        initRecyclerView()
        obtainAllPost()
        initanimatedRecycler()
    }


    private fun initanimatedRecycler() {
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(recycler_view_fav)
    }

    private fun initRecyclerView() {
        listPostInit = LinkedList()
        recycler_view_fav.layoutManager = LinearLayoutManager(context)
        recyclerPostAdapter = RecyclerPostAdapter(listPostInit, requireContext(), this)
        recycler_view_fav.adapter = recyclerPostAdapter
    }

    private fun obtainAllPost() {
        postViewModel.getAllPostFav().observe(viewLifecycleOwner, { listPosts ->
            shimmerloaddata.stopShimmer()
            shimmerloaddata.visibility = View.GONE
            listPostInit = listPosts
            validateListPost(listPosts)

        })
    }

    private fun validateListPost(listPosts: List<PostEntity>) {
        if (listPosts.isEmpty()) {
            layout_empty_post.visibility = View.VISIBLE
        } else {
            recycler_view_fav.visibility = View.VISIBLE
            recyclerPostAdapter.setListPost(listPosts)
            recyclerPostAdapter.notifyDataSetChanged()
        }
    }

    private fun deletePost(position: Int) {
        isDeletePost = true
        Snackbar.make(
            requireView(),
            requireContext().getString(R.string.post_delete),
            Snackbar.LENGTH_LONG
        ).show()
        postViewModel.deletePostById(listPostInit[position].id)
    }
    //endregion

    //region Callback RecyclerPost
    override fun onClickPost(post: PostEntity) {
        val bundle = bundleOf(
            CommonsConstants.BUNDLE_ID_POST to post.id,
            CommonsConstants.BUNDLE_ID_USER to post.userId
        )
        navController!!.navigate(R.id.action_navigation_dashboard_to_descripcionPostFragment, bundle)
    }
    //endregion

    //region ListenerTouchRecycler
    private val itemTouchHelperCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    deletePost(position)
                }
                ItemTouchHelper.RIGHT -> {
                    deletePost(position)
                }
            }
        }
    }

    //endregion
}