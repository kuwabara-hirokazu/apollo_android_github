package com.example.apollo_android_github.view.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.apollo_android_github.databinding.FragmentGithubWebViewBinding
import com.example.apollo_android_github.ext.showError
import com.example.apollo_android_github.ext.switchLoading
import com.example.apollo_android_github.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubWebViewFragment : Fragment() {
    private val viewModel: GithubWebViewViewModel by viewModels()
    private var binding: FragmentGithubWebViewBinding by autoCleared()
    private val args: GithubWebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGithubWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.url = args.issue.url

        viewModel.failure.observe(viewLifecycleOwner) {
            showError(binding.root, it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.switchLoading(it)
        }
    }
}
