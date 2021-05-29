package com.example.messagezemoga.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.messagezemoga.origindata.viewmodel.post.PostViewModel1
import com.example.messagezemoga.transversal.constants.SharedConstants
import com.example.messagezemoga.transversal.sharedpreferences.SharedManager
import com.mlsdev.animatedrv.AnimatedRecyclerView
import kotlinx.android.synthetic.main.fragment_post.*
import java.util.*

class PostFragment : Fragment() , RecyclerPostAdapter.IlistenerPost {

    //region Properties
    private lateinit var postViewModel1: PostViewModel1
    private var _binding: FragmentPostBinding? = null
    private lateinit var recyclerPostAdapter: RecyclerPostAdapter
    private val binding get() = _binding!!
    var navController: NavController? = null
    //endregion

    //region Overload fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postViewModel1 = ViewModelProvider(this).get(PostViewModel1::class.java)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
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
    //endregion


    //region Own methods
    private fun initView() {
        initRecyclerView()
        initanimatedRecycler()
        obtainAllPost()
    }

    private fun initanimatedRecycler() {
        val touchHelper = ItemTouchHelper(itemTouchHelperCallback)
        touchHelper.attachToRecyclerView(recycler_view)
    }


    private fun obtainAllPost() {
        val firstLogin = SharedManager.obtenerInstancia()
            .obtener(SharedConstants.INGRESO_PRIMERA_VEZ, Boolean::class.java)
        if (firstLogin == null || !firstLogin) {
            postViewModel1.getAllPost().observe(viewLifecycleOwner, { listPosts ->
                almacenarPost(listPosts)
                SharedManager.obtenerInstancia()
                    .almacenar(SharedConstants.INGRESO_PRIMERA_VEZ, true)
                obtainPostRoom();
            })
        } else {
            obtainPostRoom();
        }
    }

    private fun obtainPostRoom() {
        postViewModel1.getAllPostRom().observe(viewLifecycleOwner, { listPost ->
            recyclerPostAdapter.setListPost(listPost)
            recyclerPostAdapter.notifyDataSetChanged()
            recycler_view.scheduleLayoutAnimation()
        })
    }


    private fun almacenarPost(listPostApi: List<Post>) {
        listPostApi.forEach { post ->
            postViewModel1.insertPost(
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
        postViewModel1.getAllPost().removeObservers(viewLifecycleOwner)
    }


    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recyclerPostAdapter = RecyclerPostAdapter(LinkedList(), requireContext(),this)
        recycler_view.adapter = recyclerPostAdapter
    }
    //endregion


    //region ListenerTouchRecycler
    val itemTouchHelperCallback = object :
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
                    //eliminarCiudad(position)
                }
                ItemTouchHelper.RIGHT -> {
                    //eliminarCiudad(position)
                }
            }
        }
    }

    //endregion

    //region CallbackRecyclerAdapter
    override fun onClickPost(post: PostEntity) {
        navController!!.navigate(R.id.action_navigation_home_to_descripcionPostFragment)
    }
    //endregion
}