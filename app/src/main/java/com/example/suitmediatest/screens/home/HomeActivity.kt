package com.example.suitmediatest.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediatest.databinding.ActivityHomeBinding
import com.example.suitmediatest.room.UserDB
import com.example.suitmediatest.screens.event.EventActivity
import com.example.suitmediatest.screens.guest.GuestActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val db by lazy { UserDB(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()
        loadData()
    }


    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.userDao().getUser(1)[0]
            binding.txtName.text = user.name
        }
    }

    private fun setOnClickListener() {
        binding.run {
            btnGuest.setOnClickListener {
                startActivity(Intent(applicationContext, GuestActivity::class.java))
            }
            btnEvent.setOnClickListener {
                startActivity(Intent(applicationContext, EventActivity::class.java))
            }
        }
    }



}