package online.e_wedd.apps.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_order.*
import online.e_wedd.apps.Adapter.ProductAdapter
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.Helper.Template
import online.e_wedd.apps.Models.Produk
import online.e_wedd.apps.R
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.json.JSONObject

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            btn_chat->{
                toast("Comming Soon")
            }

            btn_order ->{
                var i = Intent(applicationContext, PesanActivity::class.java)
                startActivity(i)
            }
        }
    }

    lateinit var mProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mProgressDialog = indeterminateProgressDialog("Loading ...")

        getDetailProduk()

        btn_order.setOnClickListener(this)
        btn_chat.setOnClickListener(this)
    }

    private fun getDetailProduk() {
        ShowDialog("Loading ...")
        var sUrl = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=read&sheetName=produkjasa"
        val stringRequest = StringRequest(Request.Method.GET, sUrl , Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            Log.d("HOME", jsonObject.toString())
            val array = jsonObject.getJSONArray("produkjasa")
            val cat = Produk()
            for (i in 0 until array.length()) {
                val json = array.getJSONObject(i)

                if(json.getString("id_produk") == AppHelper.idProduk) {
                    cat.idProduk = json.getString("id_produk")
                    cat.namaProduk = json.getString("nama_produk")
                    cat.hargaProduk = json.getString("harga")
                    cat.lokasiProduk = json.getString("lokasi")
                    cat.deskripsiProduk = json.getString("deskripsi")
                    cat.gambarProduk = "http://www.fshoppers.com/wp-content/uploads/2016/08/Blossom-Red-V-Neck-Solid-Lace-Floor-Length-Ball-Gown-Party-Dress-For-Women01.jpg"
                }
            }


            detail_produk_nama.text = cat.namaProduk
            detail_produk_harga.text = cat.hargaProduk
            detail_produk_lokasi.text = cat.lokasiProduk
            detail_produk_deskripsi.text = cat.deskripsiProduk


        }, Response.ErrorListener {
            toast("eror connection")
        })
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add<String>(stringRequest)
        HideMessage()
    }

    /*private fun showDetailProduk() {
        detail_produk_nama.text = intent.getStringExtra(Template.Produk.namaProduk)
        detail_produk_harga.text = intent.getStringExtra(Template.Produk.hargaProduk)
        detail_produk_lokasi.text = intent.getStringExtra(Template.Produk.lokasiProduk)
        detail_produk_deskripsi.text = intent.getStringExtra(Template.Produk.deskripsiProduk)
        detail_produk_nama.text = intent.getStringExtra(Template.Produk.namaProduk)
    }*/
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
}
