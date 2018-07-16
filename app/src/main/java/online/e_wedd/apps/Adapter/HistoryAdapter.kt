package online.e_wedd.apps.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.history_item.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.Models.History
import online.e_wedd.apps.R

class HistoryAdapter (listProduk: ArrayList<History>, context: Context) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    var TAG : String = "HistoryAdapter"
    var listProduk : List<History>
    lateinit var itemView : View
    lateinit var context : Context
    // inisialisasi List
    init{
        this.context = context
        this.listProduk = listProduk
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent?.context).inflate(R.layout.history_items, parent,false)
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
            /*var i = Intent(context, DetailActivity::class.java)
            i.putExtra(Template.Produk.idProduk,listProduk.get(position).idProduk.toString())
            i.putExtra(Template.Produk.namaProduk,listProduk.get(position).namaProduk.toString())
            i.putExtra(Template.Produk.hargaProduk,listProduk.get(position).hargaProduk.toString())
            i.putExtra(Template.Produk.lokasiProduk,listProduk.get(position).lokasiProduk.toString())
            i.putExtra(Template.Produk.deskripsiProduk,listProduk.get(position).deskripsiProduk.toString())
            context.startActivity(i)*/

        }
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ParentClick = itemView?.IP_parent
        fun bind(position: Int, listAvatar: List<History>) {
            itemView.cart_invoice.text = listAvatar.get(position).idInvoice
            itemView.cart_Tanggal.text = listAvatar.get(position).tanggal
            itemView.cart_vendor.text = listAvatar.get(position).vendor

            if (listAvatar.get(position).status.equals("0")) {
                itemView.cart_pembayaran.text = "UNPAID"
            }else{
                itemView.cart_pembayaran.text = "PAID"
            }

//            listAvatar.get(position).gambarProduk?.let { itemView.IP_image.setImageResource(position) }
        }

    }
}