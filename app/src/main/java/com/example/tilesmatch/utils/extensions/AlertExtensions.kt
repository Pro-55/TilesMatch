package com.example.tilesmatch.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.tilesmatch.R
import com.example.tilesmatch.databinding.LayoutConfirmationDialogBinding
import com.google.android.material.snackbar.Snackbar

fun Fragment.showShortSnackBar(message: String) = requireActivity().showShortSnackBar(message)

fun FragmentActivity.showShortSnackBar(message: String) {
    Snackbar.make(
        findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_SHORT
    ).show()
}

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

    binding.btnPositive.setOnClickListener {
        positiveButtonClick.invoke(it)
        dialog.dismiss()
    }

    binding.btnNegative.setOnClickListener {
        negativeButtonClick.invoke(it)
        dialog.dismiss()
    }

    return dialog
}