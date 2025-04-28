package com.papslabs.tilesmatch.utils

import android.app.Activity
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import com.papslabs.tilesmatch.R
import com.papslabs.tilesmatch.utils.extensions.getColorFromAttr
import com.google.android.material.R as mR

object TapTargets {

    data class Input(
        val view: View,
        val type: Type
    )

    data class Data(
        val view: View,
        val title: String,
        val description: String
    )

    enum class Type {
        SELECT,
        ASSIST,
        UNDO,
        RESET
    }

    /**
     * show tap target for single view
     *
     * @param activity activity context
     * @param input input data for the target
     */
    fun show(
        activity: Activity,
        input: Input
    ) {
        val data = build(input.type, input.view)
        TapTargetView.showFor(
            activity,
            TapTarget.forView(data.view, data.title, data.description)
                .transparentTarget(true)
                .textColorInt(activity.getColorFromAttr(mR.attr.colorSurface))
                .outerCircleColorInt(activity.getColorFromAttr(androidx.appcompat.R.attr.colorPrimary))
                .textTypeface(ResourcesCompat.getFont(activity, R.font.jet_brains_mono_regular))
                .cancelable(true)
        )
    }

    /**
     * show tap target for multiple views
     *
     * @param activity activity context
     * @param inputs list of input data for targets
     */
    fun show(
        activity: Activity,
        inputs: List<Input>
    ) {
        val targets = inputs.map {
            val data = build(it.type, it.view)
            TapTarget.forView(data.view, data.title, data.description)
                .transparentTarget(true)
                .textColorInt(activity.getColorFromAttr(mR.attr.colorSurface))
                .outerCircleColorInt(activity.getColorFromAttr(androidx.appcompat.R.attr.colorPrimary))
                .textTypeface(ResourcesCompat.getFont(activity, R.font.jet_brains_mono_regular))
                .cancelable(true)
        }
        TapTargetSequence(activity)
            .targets(targets)
            .continueOnCancel(true)
            .start()
    }

    /**
     * show tap target for single view
     *
     * @param type type of the target view
     * @param view reference of the view
     */
    private fun build(
        type: Type,
        view: View
    ): Data = when (type) {
        Type.SELECT -> {
            Data(
                view = view,
                title = "Select From Gallery",
                description = "Select image from your gallery to solve as custom puzzle!"
            )
        }
        Type.ASSIST -> {
            Data(
                view = view,
                title = "The Assist Switch",
                description = "Turn this on to see the what the final position of the tile should be!"
            )
        }
        Type.UNDO -> {
            Data(
                view = view,
                title = "The Undo Button",
                description = "We don't always make the best decisions in life. Hit undo, try again!"
            )
        }
        Type.RESET -> {
            Data(
                view = view,
                title = "Reset Button",
                description = "Hit this button to reset the game and start over!"
            )
        }
    }
}