package xyz.people.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import xyz.people.R
import xyz.people.util.ResultState

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    Log.e("loading", ":)")
                }
                is ResultState.Success -> {
                    Log.e("loading", it.toString())
                }
                is ResultState.Error -> {
                    Log.e("loading", ":)")
                }
            }
        })
    }

}