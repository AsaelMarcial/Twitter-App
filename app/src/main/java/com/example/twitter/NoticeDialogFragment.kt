package com.example.twitter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class NoticeDialogFragment() : DialogFragment() {


    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(tweet: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            var newTweetView = inflater.inflate(R.layout.dialog_addtweet, null)
            val tweet = newTweetView.findViewById<EditText>(R.id.txtTweet)
            builder.setView(newTweetView).setPositiveButton(
                    getString(R.string.save),
                    DialogInterface.OnClickListener { dialog, which ->
                        listener.onDialogPositiveClick(tweet.text.toString())
                    })
                .setNegativeButton(getString(R.string.cancel),
                    DialogInterface.OnClickListener { dialog, which ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}