package com.tp.sharefile.ui.files

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tp.sharefile.R
import com.tp.sharefile.databinding.FilesFragmentBinding
import com.tp.sharefile.di.appComponent
import com.tp.sharefile.di.presentation.FilesComponent
import com.tp.sharefile.presentation.FilesPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.ui.download.DownloadFragment
import com.tp.sharefile.ui.upload.UploadFragment


class FilesFragment : MvpAppCompatFragment(R.layout.files_fragment), FilesView {
    private val binding: FilesFragmentBinding by viewBinding()

    @Inject
    @InjectPresenter
    lateinit var presenter: FilesPresenter


    @ProvidePresenter
    fun providePresenter(): FilesPresenter =
        FilesComponent.build(requireContext().appComponent).providePresenter()

    private var adapter: FilesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnListScroll()
        adapter = FilesAdapter().apply {
            clickListener = {
                DownloadFragment.newInstance(it).show(childFragmentManager, null)
            }
        }
        with(binding) {
            upload.setOnClickListener { UploadFragment().show(childFragmentManager, null) }
        }
        with(binding.list) {
            adapter = this@FilesFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initOnListScroll() {
        with(binding) {
            list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && upload.isShown) {
                        upload.hide()
                    }
                }
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        upload.show()
                    }
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    override fun submitData(data: List<FileInfo>) {
        adapter?.submitList(data)
    }
}