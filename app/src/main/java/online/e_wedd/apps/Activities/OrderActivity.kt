package online.e_wedd.apps.Activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_order.*
import online.e_wedd.apps.Adapter.ProductAdapter
import online.e_wedd.apps.Models.Produk
import online.e_wedd.apps.R
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {

    var listCategory : ArrayList<Produk> = ArrayList()
    lateinit var adapter: ProductAdapter
    lateinit var mProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        mProgressDialog = indeterminateProgressDialog("Loading ...")
        rv_listproduk.layoutManager= LinearLayoutManager(applicationContext)
        getListProduct()
    }

    private fun getListProduct() {
        ShowDialog("Loading ...")
        listCategory.clear()
        var sUrl = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=read&sheetName=produkjasa"
        val stringRequest = StringRequest(Request.Method.GET, sUrl , Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            Log.d("HOME", jsonObject.toString())
            val array = jsonObject.getJSONArray("produkjasa")

            for (i in 0 until array.length()) {
                val json = array.getJSONObject(i)
                val cat = Produk()
                if(json.getString("category").equals("Wardrobe") ||json.getString("category").equals("Paket") ) {
                    cat.idProduk = json.getString("id_produk")
                    cat.namaProduk = json.getString("nama_produk")
                    cat.hargaProduk = json.getString("harga")
                    cat.lokasiProduk = json.getString("lokasi")
                    cat.deskripsiProduk = json.getString("deskripsi")
                    cat.gambarProduk = "http://www.fshoppers.com/wp-content/uploads/2016/08/Blossom-Red-V-Neck-Solid-Lace-Floor-Length-Ball-Gown-Party-Dress-For-Women01.jpg"
                }
                listCategory.add(cat)
                Log.d("list", listCategory.toString())
            }
            adapter = ProductAdapter(listCategory,this)
            rv_listproduk.adapter = adapter
//            rv_listproduk.layoutManager
            adapter.notifyDataSetChanged()

        }, Response.ErrorListener {
            toast("eror connection")
        })
        val requestQueue = Volley.newRequestQueue(applicationContext)
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
}
