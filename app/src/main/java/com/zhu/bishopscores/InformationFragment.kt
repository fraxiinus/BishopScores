package com.zhu.bishopscores

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class InformationFragment: Fragment(), View.OnClickListener {

    companion object {
        fun newInstance(title: String, body: String): InformationFragment {
            val fragment = InformationFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("body", body)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        var title = arguments?.getString("title")
        var body = arguments?.getString("body")

        if(title == null) {
            title = "Error"
        }
        if(body == null) {
            body = "Error"
        }

        // Inflate the layout
        val layout = inflater.inflate(R.layout.information_fragment, container, false)

        layout.findViewById<TextView>(R.id.title_tv).text = title
        layout.findViewById<TextView>(R.id.info_body).text = body
        layout.findViewById<Toolbar>(R.id.infoToolbar).setNavigationOnClickListener(this)

        return layout
    }

    override fun onClick(v: View?) {
        activity?.onBackPressed()
    }

}