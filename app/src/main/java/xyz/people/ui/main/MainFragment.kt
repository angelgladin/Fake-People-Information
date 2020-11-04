package xyz.people.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dagger.hilt.android.AndroidEntryPoint
import xyz.people.R
import xyz.people.databinding.MainFragmentBinding
import xyz.people.ui.main.entity.UserView
import xyz.people.util.ResultState

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: MainFragmentBinding? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUi()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    Log.e("loading", ":)")
                }
                is ResultState.Success -> {
                    bindData(it.data)
                }
                is ResultState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
            binding?.swiperefresh!!.isRefreshing = false
        })
    }

    private fun setupUi() {
        binding?.let { it.swiperefresh.setOnRefreshListener {
            viewModel.retry()

        } }
    }

    private fun bindData(user: UserView) {
        binding?.let {
            Glide.with(binding!!.root)
                .load(user.pictureUrl)
                .transform(CircleCrop())
                .into(binding!!.imageView2)

            it.textView2.text = user.name
            it.textView.text = user.info
        }
    }
}