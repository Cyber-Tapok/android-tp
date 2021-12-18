package com.tp.sharefile.ui.download

import android.animation.LayoutTransition
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tp.sharefile.R
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.databinding.DownloadFragmentBinding
import com.tp.sharefile.di.appComponent
import com.tp.sharefile.di.presentation.DownloadComponent
import com.tp.sharefile.presentation.DownloadPresenter
import com.tp.sharefile.ui.utils.newFragmentInstance
import moxy.MvpBottomSheetDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class DownloadFragment : MvpBottomSheetDialogFragment(), DownloadView {
    private val binding: DownloadFragmentBinding by viewBinding(DownloadFragmentBinding::bind)

    @Inject
    @InjectPresenter
    lateinit var presenter: DownloadPresenter

    @ProvidePresenter
    fun providePresenter(): DownloadPresenter =
        DownloadComponent.build(requireContext().appComponent).providePresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.download_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val info = getInfo()
            download.setOnClickListener { presenter.download(info.id) }
            open.setOnClickListener { openDownloads() }
            name.text = info.fileName
            author.text = info.author
            root.layoutTransition = LayoutTransition().apply { setAnimateParentHierarchy(false) }
        }
    }

    private fun getInfo() = requireArguments().getParcelable<FileInfo>(FILE_INFO)!!

    private fun openDownloads() {
        val chooser = Intent(Intent.ACTION_VIEW)
        chooser.setDataAndType(
            Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile.toString()),
            "*/*"
        )
        startActivity(Intent.createChooser(chooser, "Выберите чем открыть"))
    }

    override fun downloadState(isDownload: Boolean) = with(binding) {
        download.isVisible = !isDownload
        isCancelable = !isDownload
        progressLayout.isVisible = isDownload
        open.isVisible = !isDownload
    }

    override fun onProgress(percents: Int) = with(binding) {
        progress.progress = percents
        progressPercents.text = "$percents %"
    }

    override fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val FILE_INFO = "fileInfo"

        @JvmStatic
        fun newInstance(file: FileInfo): DownloadFragment =
            newFragmentInstance(FILE_INFO to file)

    }
}