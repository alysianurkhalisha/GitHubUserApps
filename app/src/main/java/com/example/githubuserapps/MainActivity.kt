package com.example.githubuserapps
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.databinding.ActivityMainBinding
import com.example.githubuserapps.databinding.ActivityUserBinding
import com.example.githubuserapps.databinding.ActivityUserDetailBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: ViewModel

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.fragfollowers,
            R.string.fragfollowing
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, UserDetail::class.java)
                intent.putExtra(UserDetail.EXTRA_USER, data)
                startActivity(intent)
            }

        })

        binding.rvuser.layoutManager=LinearLayoutManager(this@MainActivity)
        binding.rvuser.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ViewModel::class.java)
        viewModel.loadSearchUser().observe(this@MainActivity, { loadUsers ->
            if (loadUsers != null) {
                adapter.setData(loadUsers)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.searchview)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                viewModel.searchUser(query!!)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> startActivity(Intent(this@MainActivity, com.example.githubuserapps.Settings::class.java))
            R.id.love -> startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}