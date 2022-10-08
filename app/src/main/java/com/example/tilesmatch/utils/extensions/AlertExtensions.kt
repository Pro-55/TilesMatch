package com.example.tilesmatch.utils.extensions

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
 * shows confirmation dialog with given param
 *
 * @param icon icon to be displayed on the dialog
 * @param title title to be displayed on the dialog
 * @param message message to be displayed on the dialog
 * @param positiveButton positive button text
 * @param negativeButton negative button text
 * @param positiveButtonClick positive button action
 * @param negativeButtonClick negative button action
 * @return instance of alter dialog
 */
fun FragmentActivity.showConfirmationDialog(
    @DrawableRes icon: Int,
    title: String,
    message: String,
    positiveButton: String = "Yes",
    negativeButton: String = "No",
    positiveButtonClick: () -> Unit = { },
    negativeButtonClick: () -> Unit = { }
) {
    MaterialAlertDialogBuilder(this)
        .setIcon(icon)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButton) { _, _ -> positiveButtonClick.invoke() }
        .setNegativeButton(negativeButton) { _, _ -> negativeButtonClick.invoke() }
        .show()
}