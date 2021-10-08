package com.example.tilesmatch.utils.extensions

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.LayoutConfirmationDialogBinding
import com.google.android.material.snackbar.Snackbar

/**
 * show short snack bar for fragment
 */
fun Fragment.showShortSnackBar(message: String) = requireActivity().showShortSnackBar(message)

/**
 * show short snack bar for activity
 */
fun FragmentActivity.showShortSnackBar(message: String) {
    Snackbar.make(
        findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}

/**
 * build confirmation dialog with given param
 *
 * @param inflater instance of layout inflater
 * @param message message to be displayed on the dialog
 * @param positiveButton positive button text
 * @param negativeButton negative button text
 * @param positiveButtonClick positive button action
 * @param negativeButtonClick negative button action
 * @return instance of alter dialog
 */
fun AlertDialog.Builder.buildConfirmationDialog(
    inflater: LayoutInflater,
    message: String,
    positiveButton: String = "Yes",
    negativeButton: String = "No",
    positiveButtonClick: (view: View) -> Unit = { },
    negativeButtonClick: (view: View) -> Unit = { }
): AlertDialog {
    val binding: LayoutConfirmationDialogBinding =
        DataBindingUtil.inflate(inflater, R.layout.layout_confirmation_dialog, null, false)
    binding.txtDialogMsg.text = message
    binding.btnPositive.text = positiveButton
    binding.btnNegative.text = negativeButton

    val dialog = setView(binding.root).create()
        .apply { window?.decorView?.setBackgroundResource(android.R.color.transparent) }

    var isCancelled = true
    binding.btnPositive.setOnClickListener {
        isCancelled = false
        dialog.dismiss()
    }

    binding.btnNegative.setOnClickListener { dialog.dismiss() }

    dialog.setOnDismissListener {
        if (isCancelled) negativeButtonClick.invoke(binding.root)
        else positiveButtonClick.invoke(binding.root)
    }

    return dialog
}