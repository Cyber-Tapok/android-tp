package com.tp.sharefile.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.tp.sharefile.data.model.FileInfo
import com.tp.sharefile.ui.download.DownloadFragment
import com.tp.sharefile.ui.files.FilesFragment
import com.tp.sharefile.ui.login.LoginFragment
import com.tp.sharefile.ui.upload.UploadFragment

object Screens {

    fun loginScreen() = FragmentScreen { LoginFragment() }
    fun filesScreen() = FragmentScreen { FilesFragment() }
//    fun uploadScreen() = FragmentScreen { UploadFragment() }
    fun downloadScreen(file: FileInfo) = FragmentScreen { DownloadFragment.newInstance(file) }

}