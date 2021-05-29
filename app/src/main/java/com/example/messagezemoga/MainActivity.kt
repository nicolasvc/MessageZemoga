package com.example.messagezemoga

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.messagezemoga.databinding.ActivityMainBinding
import com.example.messagezemoga.origindata.viewmodel.user.UserViewModel
import com.example.messagezemoga.transversal.constants.SharedConstants
import com.example.messagezemoga.transversal.sharedpreferences.SharedManager

class MainActivity : AppCompatActivity() {

    //region Properties
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MessageZemoga)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateInfoUser()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun validateInfoUser(){
        val firstLogin = SharedManager.obtenerInstancia()
            .obtener(SharedConstants.INGRESO_PRIMERA_VEZ, Boolean::class.java)
        if(firstLogin == null){
            userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            userViewModel.getAllUser()
        }
    }
}