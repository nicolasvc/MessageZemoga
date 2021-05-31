package com.example.messagezemoga.ui.favoritepost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messagezemoga.databinding.FragmentFavoriteBinding
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.viewmodel.post.PostViewModel
import com.example.messagezemoga.ui.posts.RecyclerPostAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.shrimer_load.*
import java.util.*

class FavoriteFragment : Fragment(), RecyclerPostAdapter.IlistenerPost {

    //region Propiedades
    private lateinit var postViewModel: PostViewModel
    private lateinit var recyclerPostAdapter: RecyclerPostAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var listPostInit: List<PostEntity>
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
        //navController = Navigation.findNavController(view)
        initView()
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
    //endregion

    //region Callback RecyclerPost
    override fun onClickPost(post: PostEntity) {
        TODO("Not yet implemented")
    }
    //endregion
}