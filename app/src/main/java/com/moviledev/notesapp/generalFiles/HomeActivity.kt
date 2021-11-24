package com.moviledev.notesapp.generalFiles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.moviledev.notesapp.R
import com.moviledev.notesapp.databinding.ActivityHomeBinding
import com.moviledev.notesapp.generalFiles.fragments.HomeFragment
import com.moviledev.notesapp.generalFiles.fragments.TaskUserFragment
import com.moviledev.notesapp.generalFiles.fragments.UsersFragment
import com.moviledev.notesapp.generalFiles.manage.CreateNoteActivity
import kotlinx.coroutines.InternalCoroutinesApi

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager
            .findFragmentById(R.id.fragNavHost) as NavHostFragment

        navController=navHostFragment.navController
        val btnNav=binding.btnNav

        NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(btnNav,navController)

        btnNav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem):Boolean{

       when (item.itemId) {
            R.id.homeFragment -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragNavHost, HomeFragment()).commit()
            }
            R.id.usersFragment->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragNavHost, UsersFragment()).commit()
            }
            R.id.taskUserFragment->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragNavHost, TaskUserFragment()).commit()
            }
        }
        return true
    }
}