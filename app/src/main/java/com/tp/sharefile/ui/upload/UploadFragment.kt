package com.tp.sharefile.ui.upload

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import by.kirich1409.viewbindingdelegate.viewBinding
import com.tp.sharefile.R
import com.tp.sharefile.databinding.UploadFragmentBinding
import com.tp.sharefile.di.appComponent
import com.tp.sharefile.di.presentation.UploadComponent
import com.tp.sharefile.presentation.UploadPresenter
import moxy.MvpBottomSheetDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.io.File
import javax.inject.Inject


class UploadFragment : MvpBottomSheetDialogFragment(), UploadView {
    private val binding: UploadFragmentBinding by viewBinding(UploadFragmentBinding::bind)

    @Inject
    @InjectPresenter
    lateinit var presenter: UploadPresenter

    @ProvidePresenter
    fun providePresenter(): UploadPresenter =
        UploadComponent.build(requireContext().appComponent).providePresenter()

    private lateinit var handlePathOz: HandlePathOz

    private val listener = object : HandlePathOzListener.SingleUri {
        override fun onRequestHandlePathOz(pathOz: PathOz, tr: Throwable?) {
            presenter.upload(File(pathOz.path))
            tr?.let {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.upload_fragment, container, false)
    }

    private val filePicker = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: kotlin.run { return@registerForActivityResult }
        handlePathOz.getRealPath(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlePathOz = HandlePathOz(requireContext(), listener)
        with(binding) {
            upload.setOnClickListener { filePicker.launch("*/*") }
            uploadingFinished.setOnClickListener { dismiss() }
            root.layoutTransition = LayoutTransition().apply { setAnimateParentHierarchy(false) }
        }
    }

    override fun onDestroyView() {
        handlePathOz.onDestroy()
        super.onDestroyView()
    }

    override fun onUploadIdle() = with(binding) {
        upload.isVisible = true
        uploadingFinished.isVisible = false
        uploadingStart.isVisible = false
        progressLayout.isVisible = false
        error.isVisible = false
    }

    override fun onFinish() = with(binding) {
        upload.isVisible = false
        uploadingFinished.isVisible = true
        uploadingStart.isVisible = false
        progressLayout.isVisible = false
        isCancelable = true
        error.isVisible = false
    }

    override fun onUploadStart() = with(binding) {
        upload.isVisible = false
        uploadingFinished.isVisible = false
        uploadingStart.isVisible = true
        progressLayout.isVisible = true
        isCancelable = false
        error.isVisible = false
    }

    override fun onProgressUpdate(percents: Int) = with(binding) {
        progress.progress = percents
        progressPercents.text = "$percents %"
    }

    override fun onError(e: Throwable) = with(binding) {
        upload.isVisible = false
        uploadingFinished.isVisible = false
        uploadingStart.isVisible = false
        progressLayout.isVisible = false
        error.isVisible = true
        isCancelable = true
    }
}

