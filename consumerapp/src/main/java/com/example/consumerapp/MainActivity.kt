package com.example.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.MainActivity
import com.example.consumerapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var  viewModel: FavoriteActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            FavoriteActivity::class.java
        )

        binding.apply {
            rvusercus.layoutManager = LinearLayoutManager(this@MainActivity)
            rvusercus.setHasFixedSize(true)
            rvusercus.adapter = adapter
        }

        viewModel.setFavoriteUsers(this)

        viewModel.getFavoriteUsers().observe(this, Observer{
            if(it != null){
                adapter.setUser(it)
            }
        })
    }
}