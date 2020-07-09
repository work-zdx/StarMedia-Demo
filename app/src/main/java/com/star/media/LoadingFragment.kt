package com.star.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class LoadingFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_loading, container, false)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().remove(this).commit()
        super.show(manager, tag)
    }
}