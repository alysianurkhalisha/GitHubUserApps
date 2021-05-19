import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.databinding.ActivityFragmentBinding


class FragmentActivity : Fragment() {
    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_PARCEL = "section_parcel"
        @JvmStatic
        fun newInstance(index: Int, data: User) =
            FragmentActivity().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putParcelable(ARG_SECTION_PARCEL, data)
                }
            }
    }

    private lateinit var binding : ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ActivityFragmentBinding.bind(view)
        binding.rvFrag.layoutManager = LinearLayoutManager(this.context)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable<User>(ARG_SECTION_PARCEL)

        val mainViewModel: ViewModel = ViewModelProvider(this@FragmentActivity, ViewModelProvider.NewInstanceFactory()).get(ViewModel::class.java)
        if (user != null) {
        }

        val adapter = UserAdapter()
        binding.rvFrag.adapter = adapter

        when (index) {
            1 -> {
                if (user != null) {
                    showLoading(true)
                    mainViewModel.getUserFollowing(user)
                }
                mainViewModel.loadUserFollowing().observe(viewLifecycleOwner, { loadFollowing ->
                    if (loadFollowing != null) {
                        adapter.setData(loadFollowing)
                        showLoading(false)
                    }
                })

            }

            2 -> {
                if (user != null) {
                    showLoading(true)
                    mainViewModel.getUserFollowers(user)
                }
                mainViewModel.loadUserFollowers().observe(viewLifecycleOwner, { loadFollowers ->
                    if (loadFollowers != null) {
                        adapter.setData(loadFollowers)
                        showLoading(false)
                    }
                })

            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbFrag.visibility = View.VISIBLE
        } else {
            binding.pbFrag.visibility = View.GONE
        }
    }


}