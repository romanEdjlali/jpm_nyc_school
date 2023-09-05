package com.roman.jpmnycsch.view

import android.os.Bundle
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import androidx.appcompat.app.AppCompatActivity
import com.roman.jpmnycsch.databinding.ActivityMainBinding
import com.roman.jpmnycsch.viewmodel.ListViewModel

/*
* Class: MainActivity
* Owner: Roman Edjlali
* Date Created: 08/31/2023 18:59
*/
class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName

    //private lateinit var navController: NavController
    private  lateinit var binding: ActivityMainBinding

    lateinit var viewModel: ListViewModel
    private val schoolAdapter = SchoolAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        //navController = Navigation.findNavController(this@MainActivity, R.id.fragment)
        //NavigationUI.setupActionBarWithNavController(this@MainActivity,navController)
        setContentView(view)
    }

    /* override fun onSupportNavigateUp(): Boolean {
         return NavigationUI.navigateUp(navController, null)
     }*/
}