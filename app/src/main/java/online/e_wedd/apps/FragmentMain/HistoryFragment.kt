package online.e_wedd.apps.FragmentMain


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.view.*
import online.e_wedd.apps.Adapter.HistoryAdapter
import online.e_wedd.apps.Adapter.ProductAdapter
import online.e_wedd.apps.Models.History
import online.e_wedd.apps.Models.Produk

import online.e_wedd.apps.R
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {
    lateinit var rootView : View
    lateinit var mProgressDialog : ProgressDialog
    lateinit var adapter: HistoryAdapter
    var listCategory : ArrayList<History> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.fragment_history, container, false)
        mProgressDialog = indeterminateProgressDialog("Loading...")
        mProgressDialog.dismiss()
        rootView.rv_listHistory.layoutManager= LinearLayoutManager(context)

        getHistoryOrder()
        return rootView
    }

    private fun getHistoryOrder() {
        ShowDialog("Loading ...")
        listCategory.clear()
        var sUrl = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=read&sheetName=Pesanan"
        val stringRequest = StringRequest(Request.Method.GET, sUrl , Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            Log.d("HOME", jsonObject.toString())
            val array = jsonObject.getJSONArray("Pesanan")
            val cat = History()

            for (i in 0 until array.length()) {
                val json = array.getJSONObject(i)
//                if(json.getString("category").equals("Wardrobe") ||json.getString("category").equals("Paket") ) {
                    cat.status = json.getInt("status_pembayaran").toString()
                    cat.idInvoice = json.getString("no_invoice")
                    cat.vendor = json.getString("vendor")
                    cat.tanggal = json.getString("tgl_pesanan")
//                }
                listCategory.add(cat)
                Log.d("list", listCategory.toString())
            }
            adapter = HistoryAdapter(listCategory,context)
            rootView.rv_listHistory.adapter = adapter
//            rv_listproduk.layoutManager
            adapter.notifyDataSetChanged()

        }, Response.ErrorListener {
            toast("eror connection")
        })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
        HideMessage()
    }

    override fun onResume() {
        super.onResume()
        HideMessage()
    }

    fun ShowDialog(message: String){
        if (!mProgressDialog.isShowing){
            mProgressDialog.setMessage(message)
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }
    }
    fun HideMessage(){
        if (mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
    }

}// Required empty public constructor
