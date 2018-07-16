package online.e_wedd.apps.Activities

import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.activity_cek_order_form.*
import kotlinx.android.synthetic.main.activity_detail.*
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.Models.Produk
import online.e_wedd.apps.R
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class CekOrderFormActivity : AppCompatActivity(), View.OnClickListener {
    val cat = Produk()


    override fun onClick(v: View?) {
        when(v){
            btn_ConfirmOrder->{
                submitOrder()
            }
        }
    }


    lateinit var mProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_order_form)
        mProgressDialog = indeterminateProgressDialog("Loading ...")
        getDetailProduk()
        getProfil()

        btn_ConfirmOrder.setOnClickListener(this)
    }

    private fun submitOrder() {
        ShowDialog("Loading . . .")
        // TODO LOGIN httprequest send  data to server
        //URL untuk Register
        var URL = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=addorder&sheetName=Pesanan"
//        var URL = AppConfig.Register

        var stringRequest = object : StringRequest(Request.Method.POST,URL, Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            toast(""+jsonObject.getString("hasil"))
            var i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            /*if (jsonObject.getString("status").equals("success")) {

                toast("" + jsonObject.getString("message"))
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                toast("GAGAL")
//                startActivity(Intent(this, MainActivity::class.java))

            }*/
            //Respon dialog saat sukses mendaftar
//            responDialog(context ,getString(R.string.perhatian_dialog), getString(R.string.register_sukses))

        }, Response.ErrorListener {

            //Respon saat register error
        }) {
            override fun getParams() : Map<String,String> {
                val params = HashMap<String, String>()
                params.put("nama", AppHelper.nama!!)
                params.put("email", AppHelper.email!!)
                params.put("id", AppHelper.idUser!!)
                params.put("idproduk", AppHelper.idProduk!!)
                params.put("keterangan", AppHelper.keterangan!!)
                params.put("lokasi", AppHelper.lokasi!!)
                params.put("tanggal", AppHelper.tanggal!!)
                params.put("vendor", AppHelper.vendor!!)

                return params

            }
        }

        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(stringRequest)
        HideMessage()
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
                    check_namaLengkap.text = json.getString("nama_pencari")
                    check_rekening.text = json.getString("no_rekening")
                    check_hp.text = json.getString("no_hp")
                    check_provinsi.text = json.getString("provinsi")
                    check_kota.text = json.getString("kota")
                    check_alamat.text = json.getString("alamat")
                    AppHelper.idUser = json.getString("id_pencari")
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
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add<String>(stringRequest)
        HideMessage()
    }

    private fun getDetailProduk() {
        ShowDialog("Loading ...")
        var sUrl = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=read&sheetName=produkjasa"
        val stringRequest = StringRequest(Request.Method.GET, sUrl , Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            Log.d("HOME", jsonObject.toString())
            val array = jsonObject.getJSONArray("produkjasa")
            for (i in 0 until array.length()) {
                val json = array.getJSONObject(i)

                if(json.getString("id_produk") == AppHelper.idProduk) {
                    check_Produk.text = json.getString("nama_produk")
                    check_vendor.text = json.getString("vendor")
                    check_kategori.text = json.getString("category")
                    check_tgl.text = AppHelper.tanggal
                    check_harga.text = json.getString("harga")
                    check_lokasi.text = AppHelper.lokasi
                    COF_check_ket.text = AppHelper.keterangan
                    AppHelper.vendor = json.getString("vendor")

                    cat.idProduk = json.getString("id_produk")
                    cat.namaProduk = json.getString("nama_produk")
                    cat.hargaProduk = json.getString("harga")
                    cat.vendor = json.getString("vendor")
                    cat.lokasiProduk = json.getString("lokasi")
                    cat.deskripsiProduk = json.getString("deskripsi")
                }
            }





        }, Response.ErrorListener {
            toast("eror connection")
        })
        val requestQueue = Volley.newRequestQueue(applicationContext)
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



}
