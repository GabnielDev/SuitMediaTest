package com.example.suitmediatest.screens.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmediatest.databinding.ActivityEventBinding
import com.example.suitmediatest.model.User
import com.example.suitmediatest.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EventActivity
    : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding

    private val db by lazy { UserDB(this) }

    lateinit var user: List<User>
    var id: Int? = 0
    var name: String? = null
    var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        loadData()
        setOnClickListener()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            user = db.userDao().getListUser()
            withContext(Dispatchers.IO) {
                name = user[0].name
                image = user[0].image
                setupRecyclerView(user)
            }
        }
    }

    private fun setupRecyclerView(user: List<User>) {
        val eventAdapter = EventAdapter().apply {
            setNewInstance(user.toMutableList())
        }
        binding.RecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = eventAdapter
        }

    }

    private fun setupToolbar() {
        binding.toolbar.run {
            txtTitle.text = "Events"
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun setOnClickListener() {
        binding.toolbar.btnMap.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        id = user[0].id
        val dialog = BottomSheetFragment(0, name, image)
        dialog.show(supportFragmentManager, "dialog")
    }
}