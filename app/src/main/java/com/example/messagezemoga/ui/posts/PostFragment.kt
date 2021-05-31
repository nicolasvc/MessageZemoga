package com.example.messagezemoga.ui.posts

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messagezemoga.R
import com.example.messagezemoga.databinding.FragmentPostBinding
import com.example.messagezemoga.entities.Post
import com.example.messagezemoga.origindata.room.entities.PostEntity
import com.example.messagezemoga.origindata.viewmodel.post.PostViewModel
import com.example.messagezemoga.transversal.constants.CommonsConstants
import com.example.messagezemoga.transversal.constants.SharedConstants
import com.example.messagezemoga.transversal.sharedpreferences.SharedManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.shrimer_load.*
import java.util.*

class PostFragment : Fragment(), RecyclerPostAdapter.IlistenerPost {

    //region Properties
    private lateinit var postViewModel: PostViewModel
    private var _binding: FragmentPostBinding? = null
    private lateinit var recyclerPostAdapter: RecyclerPostAdapter
    private val binding get() = _binding!!
    private var navController: NavController? = null
    private lateinit var listPostInit: List<PostEntity>
    private var isDeletePost: Boolean = false
    private var amountOfpost = 20
    //endregion

    //region Overload fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_reload_post, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initView()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reloadPost -> {
                getAllPost()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        shimmerloaddata.startShimmer()
    }

    //endregion


    //region Own methods
    private fun initView() {
        initRecyclerView()
        initlistenerFabIcon()
        initanimatedRecycler()
        obtainAllPost()
        initListenerScrollView()
    }

    private fun initListenerScrollView() {
        scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                amountOfpost += 20
                obtainTweentyPost(amountOfpost)
            }
        })
    }

    private fun initlistenerFabIcon() {
        floatingActionButton.setOnClickListener { showMessage() }
    }

    private fun showMessage() {
        val dialogoCierre = AlertDialog.Builder(context)
        dialogoCierre.setMessage(requireContext().getString(R.string.ask_delete_all_post))
            .setTitle(requireContext().getString(R.string.title_delete_post))
        dialogoCierre.setPositiveButton(requireContext().getString(R.string.title_delete_btn)) { dialog, _ ->
            dialog.dismiss()
            postViewModel.deleteAllPost()
            showEmptyPost(true)
        }
        dialogoCierre.setNegativeButton(requireContext().getString(R.string.title_cancel_btn)) { dialog, _ -> dialog.dismiss() }
        dialogoCierre.create()
        dialogoCierre.show()
    }

    private fun showEmptyPost(isEmpty: Boolean) {
        if (isEmpty)
            screen_empty_posts.visibility = View.VISIBLE
        else
            screen_empty_posts.visibility = View.GONE
    }

    private fun initanimatedRecycler() {
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(recycler_view)
    }

    private fun getAllPost(): Boolean {
        postViewModel.deleteAllPost()
        showEmptyPost(false)
        getAllPostService()
        return true
    }

    private fun getAllPostService() {
        shimmerloaddata.stopShimmer()
        shimmerloaddata.visibility = View.VISIBLE
        postViewModel.getAllPost().observe(viewLifecycleOwner, { listPosts ->
            almacenarPost(listPosts)
            SharedManager.obtenerInstancia()
                .almacenar(SharedConstants.INGRESO_PRIMERA_VEZ, true)
            obtainPostRoom()
        })
    }

    private fun obtainAllPost() {
        val firstLogin = SharedManager.obtenerInstancia()
            .obtener(SharedConstants.INGRESO_PRIMERA_VEZ, Boolean::class.java)
        if (firstLogin == null || !firstLogin) {
            getAllPostService()
        } else {
            obtainPostRoom()
        }
    }

    private fun obtainPostRoom() {
        postViewModel.getAllPostRom().observe(viewLifecycleOwner, { listPost ->
            shimmerloaddata.stopShimmer()
            shimmerloaddata.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            showEmptyPost(false)
            listPostInit = listPost
            obtainTweentyPost(20)
            if (!isDeletePost)
                recycler_view.scheduleLayoutAnimation()
        })
    }


    private fun obtainTweentyPost(cantidadRegistros: Int) {
        val newList = mutableListOf<PostEntity>()
        if (listPostInit.isEmpty()) showEmptyPost(true)
        listPostInit.forEachIndexed { index, postEntity ->
            if (index <= cantidadRegistros - 1)
                newList.add(postEntity)
        }
        recyclerPostAdapter.setListPost(newList)
        recyclerPostAdapter.notifyDataSetChanged()
    }


    private fun almacenarPost(listPostApi: List<Post>) {
        listPostApi.forEach { post ->
            postViewModel.insertPost(
                PostEntity(
                    post.body,
                    post.title,
                    post.userId,
                    post.id,
                    isRead = false,
                    isFavorite = false
                )
            )
        }
        postViewModel.getAllPost().removeObservers(viewLifecycleOwner)
    }


    private fun initRecyclerView() {
        listPostInit = LinkedList()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recyclerPostAdapter = RecyclerPostAdapter(listPostInit, requireContext(), this)
        recycler_view.adapter = recyclerPostAdapter
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

    //region CallbackRecyclerAdapter
    override fun onClickPost(post: PostEntity) {
        val bundle = bundleOf(
            CommonsConstants.BUNDLE_ID_POST to post.id,
            CommonsConstants.BUNDLE_ID_USER to post.userId
        )
        navController!!.navigate(R.id.action_navigation_home_to_descripcionPostFragment, bundle)
    }
    //endregion


}