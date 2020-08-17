package com.openclassrooms.realestatemanager.ui.dialog.photo

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Photo
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PhotoDialogFragment
private constructor(val completionHandler: (Photo) -> Unit) : DialogFragment() {
    companion object {
        fun newInstance(completionHandler: (Photo) -> Unit) = PhotoDialogFragment(completionHandler)

        private const val PERM = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val GET_STORAGE_PERMS = 872
        private const val RESULT_LOAD_IMAGE = 169

        @Suppress("unused")
        private val TAG = PhotoDialogFragment::class.java.simpleName
    }

    private lateinit var descriptionTextView: TextView
    private lateinit var photoImageButton: ImageButton
    private var image: String? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setTitle(getString(R.string.add_photo))

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        view.apply {
            findViewById<Button>(R.id.add_photo_done_button).setOnClickListener { dismiss() }
            photoImageButton = findViewById(R.id.add_photo_photo_image_button)
            descriptionTextView = findViewById(R.id.add_photo_description)
        }

        photoImageButton.setOnClickListener(this::imageButtonTapped)
    }

    private fun imageButtonTapped(@Suppress("UNUSED_PARAMETER") v: View) {
        if (EasyPermissions.hasPermissions(requireContext(), PERM))
            fetchImageInLibrary()
        else EasyPermissions.requestPermissions(
                this,
                getString(R.string.need_storage_permission_message),
                GET_STORAGE_PERMS,
                PERM
        )
    }

    @AfterPermissionGranted(GET_STORAGE_PERMS)
    private fun fetchImageInLibrary() {
        startActivityForResult(
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                RESULT_LOAD_IMAGE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
            data.data?.let {
                setImage(it)
            }
    }

    private fun setImage(uri: Uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        photoImageButton.setImageDrawable(placeholder)
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        photoImageButton.setImageBitmap(resource)
                        saveToInternalStorage(resource)
                    }
                })
    }

    override fun onDismiss(dialog: DialogInterface) {
        val description = descriptionTextView.text.toString()
        if (image != null && description.isNotEmpty())
            completionHandler(Photo(0, 0, image.toString(), description))

        super.onDismiss(dialog)
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap) {
        val directory = context?.filesDir
        val fileName = UUID.randomUUID().toString() + ".png"
        val photoPath = File(directory, fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(photoPath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        image = fileName
    }
}