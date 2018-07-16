package online.e_wedd.apps.FragmentMain


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_account.*
import online.e_wedd.apps.Helper.AppHelper

import online.e_wedd.apps.R
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {
    lateinit var rootView : View
    lateinit var mProgressDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.fragment_account, container, false)
        mProgressDialog = indeterminateProgressDialog("Loading...")
        mProgressDialog.dismiss()

        getProfil()

        return rootView
    }

    private fun getProfil() {
        ShowDialog("Loading ...")
        var sUrl = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=read&sheetName=pencari"
        val stringRequest = StringRequest(Request.Method.GET, sUrl , Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            Log.d("HOME", jsonObject.toString())
            val array = jsonObject.getJSONArray("pencari")
            for (i in 0 until array.length()) {
                val json = array.getJSONObject(i)

                if(json.getString("email") == AppHelper.email) {
                    profile_nama.setText(json.getString("nama_pencari"))
                    profile_email.setText(json.getString("email"))
                    profile_hp.setText(json.getString("no_hp"))
                    profile_ktp.setText(json.getString("no_ktp"))
                    profile_rekening.setText(json.getString("no_rekening"))
                    profile_provinsi.setText(json.getString("provinsi"))
                    profile_kota.setText(json.getString("kota"))
                    profile_alamat.setText(json.getString("alamat"))

                    /*rootView.text = json.getString("nama_pencari")
                    check_rekening.text = json.getString("no_rekening")
                    check_hp.text = json.getString("no_hp")
                    check_provinsi.text = json.getString("provinsi")
                    check_kota.text = json.getString("kota")
                    check_alamat.text = json.getString("alamat")
                    AppHelper.idUser = json.getString("id_pencari")*/
                    /*cat.idProduk = json.getString("id_produk")
                    cat.namaProduk = json.getString("nama_produk")
                    cat.hargaProduk = json.getString("harga")
                    cat.lokasiProduk = json.getString("lokasi")
                    cat.deskripsiProduk = json.getString("deskripsi")
                    cat.gambarProduk = "http://www.fshoppers.com/wp-content/uploads/2016/08/Blossom-Red-V-Neck-Solid-Lace-Floor-Length-Ball-Gown-Party-Dress-For-Women01.jpg"*/
                }
            }





        }, Response.ErrorListener {
            toast("eror connection")
        })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add<String>(stringRequest)
        HideMessage()
    }


    override fun onPause() {
        super.onPause()
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
