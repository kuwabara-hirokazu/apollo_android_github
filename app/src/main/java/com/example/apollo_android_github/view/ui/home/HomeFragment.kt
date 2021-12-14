package com.example.apollo_android_github.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apollo_android_github.databinding.FragmentHomeBinding
import com.example.apollo_android_github.ext.BindingAdapters.setImageUrl
import com.example.apollo_android_github.ext.showError
import com.example.apollo_android_github.ext.switchLoading
import com.example.apollo_android_github.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.failure.observe(viewLifecycleOwner) {
            showError(binding.root, it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.switchLoading(it)
        }

        viewModel.githubInfo.observe(viewLifecycleOwner) { info ->
            binding.githubName.text = info.user
            info.avatarUrl?.let { avatar -> binding.githubIcon.setImageUrl(avatar) }
            setRepositories()
        }

        viewModel.getGithubInfo()

        binding.repositoryNames.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.githubInfo.value?.let { setIssues(position) }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setRepositories() {
        val repositories = viewModel.getRepositoryList().map { it.repositoryName }.toTypedArray()
        createArrayAdapter(repositories, binding.repositoryNames)
    }

    private fun setIssues(position: Int) {
        val issues = viewModel.getIssueList(position).map { it.title }.toTypedArray()
        createArrayAdapter(issues, binding.issueNames)
    }

    private fun <T> createArrayAdapter(items: Array<T>, spinner: Spinner) {
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = this
        }
    }
}
