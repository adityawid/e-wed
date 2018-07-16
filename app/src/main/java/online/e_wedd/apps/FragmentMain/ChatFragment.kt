package online.e_wedd.apps.FragmentMain


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import online.e_wedd.apps.R
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    lateinit var rootView : View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.fragment_chat, container, false)
        toast("Cooming Soon")
        return rootView
    }

}// Required empty public constructor
