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
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_pesan.*
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.Models.Produk
import online.e_wedd.apps.R
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PesanActivity : AppCompatActivity(), View.OnClickListener {

    private var datePickerDialog: DatePickerDialog? = null
    var tanggal_lahir : String = ""
    private var dateFormatter: SimpleDateFormat? = null

    override fun onClick(v: View?) {
        when(v){
            btn_continueCheck->{
                var nama = order_nama.text.toString()
                var tanggal = order_tgl.text.toString()
                var lokasi = order_lokasi.text.toString()
                var keterangan  = order_keterangan.text.toString()

                AppHelper.keterangan = keterangan
                AppHelper.lokasi = lokasi
                AppHelper.nama = nama
                AppHelper.tanggal = tanggal
                var i = Intent(applicationContext, CekOrderFormActivity::class.java)
                startActivity(i)
            }

            order_tgl -> {
                showDateDialog()
            }
        }
    }

    lateinit var mProgressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)
        // format tanggal
        dateFormatter = SimpleDateFormat("yyyy-MM-dd")

        mProgressDialog = indeterminateProgressDialog("Loading ...")
        mProgressDialog.dismiss()



        btn_continueCheck.setOnClickListener(this)
        order_tgl.setOnClickListener(this)
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


    // TODO  PROFILEDIT : (3) DONE Datepicker Create function
    private fun showDateDialog() {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            newCalendar.set(year, monthOfYear, dayOfMonth)
            tanggal_lahir = dateFormatter?.format(newCalendar.time).toString()
            order_tgl.setText(AppHelper.DatetoString(newCalendar.time))
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog?.show()
    }
}
