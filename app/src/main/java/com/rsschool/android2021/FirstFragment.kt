package com.rsschool.android202

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.android2021.FragmentDataListener
import com.rsschool.android2021.R

class FirstFragment: Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var mListener: FragmentDataListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min: EditText? = view.findViewById(R.id.min_value)
        val max: EditText? = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            if (preChecker(min, max)) {
                if (checker(
                        min?.text.toString().toInt(),
                        max?.text.toString().toInt()
                    )
                ) {
                    val minToTrans = min?.text.toString().toInt()
                    val maxToTrans = max?.text.toString().toInt()
                    mListener?.openSecondFragment(minToTrans, maxToTrans)
                }
            }
        }

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is FragmentDataListener) {
            context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    //PreCheck for empty or toooooLong
    private fun preChecker(min: EditText?, max: EditText?): Boolean {
        return when {
            (min?.text.toString().toLongOrNull() == null || max?.text.toString().toLongOrNull() == null) -> {
                Toast.makeText(activity, "Empty or incorrect input, introduced too much value", Toast.LENGTH_LONG).show()
                false
            }
            (min?.text.toString().toLong() > Integer.MAX_VALUE || max?.text.toString().toLong() > Integer.MAX_VALUE) -> {
                Toast.makeText(activity, "Introduced too much value", Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }

    private fun checker(min: Int, max: Int): Boolean {
        return when {

            min > max -> {
                Toast.makeText(activity, "The minimum value is greater than the maximum value", Toast.LENGTH_LONG).show()
                false
            }
            min == max -> {
                Toast.makeText(activity, "The minimum equals maximum value", Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }
        /*@kotlin.jvm.JvmField
        var TAG: String = "com.rsschool.android2021.FirstFragment"*/
        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}