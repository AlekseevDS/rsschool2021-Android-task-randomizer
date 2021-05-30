package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment(),BackPressListener {

    private var backButton: Button? = null
    private var result: TextView? = null
    private var mListener: FragmentSecDataListener? = null
    private var res2: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()
        res2 = result?.text.toString().toInt()

        backButton?.setOnClickListener {
            // TODO: implement back
            mListener?.onSendDataFromFragment2(res2)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
        //for Kotlin less then 1.3
        //return Random().nextInt((max + 1) - min) + min
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is FragmentSecDataListener) {
            context
        } else {
            throw RuntimeException("$context must implement FragmentSecDataListener")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(SecondFragment.MIN_VALUE_KEY, min)
            args.putInt(SecondFragment.MAX_VALUE_KEY, max)

            fragment.arguments = args

            return fragment
        }

        const val MIN_VALUE_KEY = "MIN_VALUE"
        const val MAX_VALUE_KEY = "MAX_VALUE"
        @kotlin.jvm.JvmField
        var TAG: String = "com.rsschool.android2021.SecondFragment"

    }

    override fun onBackPressed() {
        mListener?.onSendDataFromFragment2(res2)
    }
}