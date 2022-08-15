package com.example.suitmediatest.screens.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.suitmediatest.databinding.ActivityGuestBinding
import com.example.suitmediatest.helper.PageHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GuestActivity : AppCompatActivity() {

    private val viewModel: GuestViewModel by viewModels()
    private lateinit var binding: ActivityGuestBinding

    private val guestAdapter = GuestAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        setupRecyclerView()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.run {
            txtTitle.text = "Guests"
            btnSearch.visibility = GONE
            btnMap.visibility = GONE
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun observeViewModel() {
        this.lifecycleScope.launch {
            viewModel.getRegresList().collectLatest {
                guestAdapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        val paging = PageHelper(binding.recyclerView).apply {
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = guestAdapter
            addOnScrollListener(paging)
        }
    }

}