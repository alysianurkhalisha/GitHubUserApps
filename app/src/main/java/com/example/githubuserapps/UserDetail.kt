package com.example.githubuserapps


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapps.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.snackbar.Snackbar

class UserDetail : AppCompatActivity(){
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user : User
    private val checked: Int = R.drawable.ic_favorite
    private val unChecked: Int = R.drawable.ic_baseline_favoriteborder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        val PagerAdapter = PagerAdapter(this@UserDetail, user)
        binding.viewPager.adapter = PagerAdapter

        val viewModel : ViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ViewModel::class.java)
        var statusFav = false

        showLoading(true)
        user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        viewModel.findUser(user.username)!!.observe(this, { findDB ->
            user.name?.let { setActionBarTittle(it) }
            if (findDB != null && findDB.username == user.username) {
                userDetail(findDB)
                statusFav = true
                binding.favorite.setImageResource(checked)
            } else {
                showLoading(true)
                viewModel.getUserDetail(user)
                viewModel.loadUserDetail().observe(this, { userDetail ->
                    if (userDetail != null) {
                        user = userDetail
                        userDetail(user)
                        showLoading(false)
                    }
                })
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(MainActivity.TAB_TITLES[position])
        }.attach()

        binding.favorite.setOnClickListener { view ->

            if (!statusFav) {
                viewModel.insert(user)
                binding.favorite.setImageResource(checked)
                Snackbar.make(view, "Added to Favorite", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.delete(user)
                binding.favorite.setImageResource(unChecked)
                Snackbar.make(view, "Removed from Favorite", Snackbar.LENGTH_SHORT).show()
            }
            statusFav = !statusFav
        }

    }

    private fun userDetail(user : User){
        binding.dtvUsername.text = user.username
        binding.dtvName.text = user.name
        Glide.with(this@UserDetail)
            .load(user.photo)
            .apply(RequestOptions().override(350, 350))
            .into(binding.dtpp)
        binding.dtvLoc.text = getString(R.string.loc,user.location)
        binding.dtvRep.text = getString(R.string.rep,user.repository)
        binding.dtvComp.text = getString(R.string.comp,user.company)

    }

    private fun setActionBarTittle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.love ->
                startActivity(Intent(this, FavoriteActivity::class.java))
        }
        if (item.itemId == R.id.setting) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }


}