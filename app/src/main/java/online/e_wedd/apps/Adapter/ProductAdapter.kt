package online.e_wedd.apps.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_product.view.*
import online.e_wedd.apps.Activities.DetailActivity
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.Helper.Template
import online.e_wedd.apps.Models.Produk
import online.e_wedd.apps.R

/**
 * Created by devjurnal on 5/31/18.
 */
class ProductAdapter(listProduk: ArrayList<Produk>, context: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    var TAG : String = "GANTIAVATARADAPTER"
    var listProduk : List<Produk>
    lateinit var itemView : View
    lateinit var context : Context
    // inisialisasi List
    init{
        this.context = context
        this.listProduk = listProduk
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_product, parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.bind(position,listProduk)
        holder?.ParentClick?.setOnClickListener {
            Log.d(TAG, listProduk.get(position).namaProduk)
            AppHelper.idProduk = listProduk.get(position).idProduk.toString()
            var i = Intent(context, DetailActivity::class.java)
            i.putExtra(Template.Produk.idProduk,listProduk.get(position).idProduk.toString())
            i.putExtra(Template.Produk.namaProduk,listProduk.get(position).namaProduk.toString())
            i.putExtra(Template.Produk.hargaProduk,listProduk.get(position).hargaProduk.toString())
            i.putExtra(Template.Produk.lokasiProduk,listProduk.get(position).lokasiProduk.toString())
            i.putExtra(Template.Produk.deskripsiProduk,listProduk.get(position).deskripsiProduk.toString())
            context.startActivity(i)

        }
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ParentClick = itemView?.IP_parent
        fun bind(position: Int, listAvatar: List<Produk>) {
            itemView.list_produk_harga.text = listAvatar.get(position).hargaProduk
            itemView.list_produk_nama.text = listAvatar.get(position).namaProduk
            itemView.list_produk_lokasi.text = listAvatar.get(position).lokasiProduk

//            listAvatar.get(position).gambarProduk?.let { itemView.IP_image.setImageResource(position) }
        }

    }
}

/*
* private fun simpanAvatar(namaAvatar: String?) {
        var stringRequest = object  : StringRequest(Request.Method.POST, AppConfig.Ganti_Avatar, Response.Listener {
            response ->
            Toast.makeText(context, "Sukses Ganti Avatar ", Toast.LENGTH_LONG).show()
            val i = Intent(context, TampungActivity::class.java)
            i.putExtra(Template.Intent.VIEW, Template.Tampung.PROFILEEDIT)
            // Hapus Stack activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)

        }, Response.ErrorListener {
            Toast.makeText(context, "Eror Update ", Toast.LENGTH_LONG).show()
        }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String,String>()
                params.put("token", AppHelper.TOKEN_EXTRA!!)
                params.put("photo",namaAvatar!!)

                return params
            }
        }
        val queue = Volley.newRequestQueue(context)
        @Suppress("UsePropertyAccessSyntax")
        stringRequest.setRetryPolicy(DefaultRetryPolicy(AppHelper.VolleyTimeOut * 1000, 10, 1.0f))
        queue.add(stringRequest)
    }*/