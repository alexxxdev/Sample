package com.github.alexxxdev.sample.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.alexxxdev.sample.R
import com.github.alexxxdev.sample.ui.base.BaseScreen
import kotlinx.android.synthetic.main.main_screen.*
import pl.aprilapps.easyphotopicker.EasyImage
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AppSettingsDialog
import pl.aprilapps.easyphotopicker.DefaultCallback
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.alexxxdev.sample.data.ImageResult
import java.io.File
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu


class MainScreen : BaseScreen<MainPresenter>(), MainView, EasyPermissions.PermissionCallbacks {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    override fun providePresenter(): MainPresenter = presenterProvider.get()

    companion object {
        private const val TYPE_OPEN_IMAGE: Int = 123
        private const val RC_WRITE_EXTERNAL_STORAGE_PERM: Int = 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        initViews()
    }

    override fun showInitView() {
        selectButton.visibility = View.VISIBLE
        mainGroup.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    override fun showImage(file: File) {
        selectButton.visibility = View.GONE
        mainGroup.visibility = View.VISIBLE

        Glide.with(this@MainScreen)
                .load(file)
                .apply(RequestOptions().centerCrop())
                .into(imageView)
    }

    override fun useImage(file: File) {
        Glide.with(this@MainScreen)
                .load(file)
                .apply(RequestOptions().centerCrop())
                .into(imageView)
    }

    override fun add(imageResult: ImageResult) {
        if((recyclerView.adapter as ImageAdapter).itemCount == 0) {
            recyclerView.visibility = View.VISIBLE
        }
        (recyclerView.adapter as ImageAdapter).addItem(imageResult)
        recyclerView.scrollToPosition(recyclerView.adapter.itemCount-1)
    }

    override fun showSubMenu(pos: Int) {
        recyclerView.post {
            val popup = PopupMenu(this, recyclerView.findViewHolderForAdapterPosition(pos).itemView, Gravity.RIGHT)
            popup.menuInflater.inflate(R.menu.popup, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete -> presenter.onDeleteItem()
                    R.id.action_use -> presenter.onUseItem()
                }

                true
            }

            popup.show()
        }
    }

    override fun deleteItem(pos: Int) {
        (recyclerView.adapter as ImageAdapter).removeItem(pos)
    }

    private fun initViews() {
        selectButton.setOnClickListener {
            openImageTask()
        }

        rotateButton.setOnClickListener { presenter.onRotateImage() }
        translationGammaButton.setOnClickListener { presenter.onTranslationGammaImage() }
        mirrorReflectionButton.setOnClickListener { presenter.onMirrorReflectionImage() }

        recyclerView.layoutManager = LinearLayoutManager(this@MainScreen, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this@MainScreen, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = ImageAdapter{ pos, ir ->
            presenter.onItemClick(pos, ir)
        }
    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE_PERM)
    private fun openImageTask() {
        if (EasyPermissions.hasPermissions(this@MainScreen, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyImage.openChooserWithGallery(this@MainScreen, getString(R.string.btn_select_image), TYPE_OPEN_IMAGE)
        } else {
            EasyPermissions.requestPermissions(
                    this@MainScreen,
                    getString(R.string.rationale_write_storage),
                    RC_WRITE_EXTERNAL_STORAGE_PERM,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this@MainScreen, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                EasyImage.openChooserWithGallery(this@MainScreen, getString(R.string.btn_select_image), TYPE_OPEN_IMAGE)
            }
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                    Snackbar.make(imageView, e?.toString()?:getString(R.string.error), Snackbar.LENGTH_LONG).show()
                }

                override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                    presenter.onImagesPicked(imagesFiles)
                }
            })
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = Unit
}