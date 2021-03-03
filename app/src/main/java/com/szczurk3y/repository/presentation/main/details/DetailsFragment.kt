package com.szczurk3y.repository.presentation.main.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.szczurk3y.repository.R
import com.szczurk3y.repository.databinding.FragmentDetailsBinding
import com.szczurk3y.repository.domain.RawRepo
import com.szczurk3y.repository.framework.navigation.INavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject lateinit var navigator: INavigator

    private val viewmodel by viewModels<DetailsViewModel>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        val repo = arguments?.getParcelable<RawRepo>("repo")
        if (repo != null) {
            viewmodel.getRepoDetails(repo.owner.login, repo.name)
        } else {
            navigator.goBack()
        }

        val adapter = CommitsAdapter()
        val uri = Uri.parse(repo!!.url)
        val viewRepoIntent = Intent(Intent.ACTION_VIEW, uri)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, repo.url)
            type = "text/plain"
        }

        viewmodel.commitsLiveData.observe(viewLifecycleOwner) { commits ->
            binding.apply {
                rvCommits.adapter = adapter.apply { submitList(commits) }
                progressBar.visibility = View.GONE
                rlHeader.visibility = View.VISIBLE
                rlContent.visibility = View.VISIBLE
                tvBack.setOnClickListener { navigator.goBack() }
                tvAuthorName.text = repo.owner.login
                tvStarsNumber.text = repo.stargazersCount.toString()
                tvRepoTitle.text = repo.name
                tvViewOnline.setOnClickListener { context?.startActivity(viewRepoIntent) }
                containerShareRepo.setOnClickListener {
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context?.startActivity(shareIntent)
                }
                Glide.with(view)
                    .load(repo.owner.avatarUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivAuthorBackground)
            }
        }

        viewmodel.errorsLiveData.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}