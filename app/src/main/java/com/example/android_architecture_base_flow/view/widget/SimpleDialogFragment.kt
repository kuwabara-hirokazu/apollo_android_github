package com.example.android_architecture_base_flow.view.widget

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner

class SimpleDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_TITLE_ID = "title_id"
        private const val ARG_MESSAGE = "message"
        private const val ARG_MESSAGE_ID = "message_id"
        private const val ARG_POSITIVE_LABEL = "positive_label"
        private const val ARG_NEGATIVE_LABEL = "negative_label"
        private const val ARG_POSITIVE_LISTENER = "positive_listener"
        private const val ARG_NEGATIVE_LISTENER = "negative_listener"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context).apply {

            setTitle(arguments?.getString(ARG_TITLE))
            getResourceId(arguments?.getInt(ARG_TITLE_ID))?.let { setTitle(it) }

            setMessage(arguments?.getString(ARG_MESSAGE))
            getResourceId(arguments?.getInt(ARG_MESSAGE_ID))?.let { setMessage(it) }

            getResourceId(arguments?.getInt(ARG_POSITIVE_LABEL, 0))?.let {
                setPositiveButton(it) { _, _ ->
                    dismiss()
                    setFragmentResult(ARG_POSITIVE_LISTENER, bundleOf())
                }
            }
            getResourceId(arguments?.getInt(ARG_NEGATIVE_LABEL, 0))?.let {
                setNegativeButton(it) { _, _ ->
                    dismiss()
                    setFragmentResult(ARG_NEGATIVE_LISTENER, bundleOf())
                }
            }
        }
        return builder.create()
    }

    private fun getResourceId(id: Int?): Int? {
        return if (id != 0) id else null
    }

    class Builder private constructor(
        private val fragment: Fragment? = null,
        private val activity: AppCompatActivity? = null
    ) {
        companion object {
            fun from(fragment: Fragment): Builder = Builder(fragment = fragment)
            fun from(activity: AppCompatActivity): Builder = Builder(activity = activity)
        }

        private val bundle = Bundle()

        private val fragmentManager: FragmentManager =
            fragment?.childFragmentManager ?: activity?.supportFragmentManager
                ?: throw IllegalStateException("Required value was null.")

        private val lifecycleOwner: LifecycleOwner =
            fragment?.viewLifecycleOwner ?: activity
                ?: throw IllegalStateException("Required value was null.")

        private fun setText(key: String, text: String): Builder {
            return this.apply {
                bundle.putString(key, text)
            }
        }

        private fun setResourceId(key: String, @StringRes resourceId: Int): Builder {
            return this.apply {
                bundle.putInt(key, resourceId)
            }
        }

        fun setTitle(title: String): Builder {
            return setText(ARG_TITLE, title)
        }

        fun setTitle(@StringRes titleId: Int): Builder {
            return setResourceId(ARG_TITLE_ID, titleId)
        }

        fun setMessage(message: String): Builder {
            return setText(ARG_MESSAGE, message)
        }

        fun setMessage(@StringRes messageId: Int): Builder {
            return setResourceId(ARG_MESSAGE_ID, messageId)
        }

        fun setPositiveButton(
            @StringRes messageId: Int,
            listener: (() -> Unit)? = null
        ): Builder {
            fragmentManager.setFragmentResultListener(
                ARG_POSITIVE_LISTENER,
                lifecycleOwner
            ) { _, _ -> listener?.invoke() }
            return setResourceId(ARG_POSITIVE_LABEL, messageId)
        }

        fun setNegativeButton(
            @StringRes messageId: Int,
            listener: (() -> Unit)? = null
        ): Builder {
            fragmentManager.setFragmentResultListener(
                ARG_NEGATIVE_LISTENER,
                lifecycleOwner
            ) { _, _ -> listener?.invoke() }
            return setResourceId(ARG_NEGATIVE_LABEL, messageId)
        }

        fun build(): SimpleDialogFragment {
            return SimpleDialogFragment().apply {
                arguments = bundle
            }
        }
    }
}
