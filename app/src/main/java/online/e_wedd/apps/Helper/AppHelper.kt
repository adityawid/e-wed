package online.e_wedd.apps.Helper

import java.text.SimpleDateFormat
import java.util.*

object AppHelper {
    var idProduk : String? = null
    var idUser : String? = null
    var nama : String? = null
    var lokasi: String? = null
    var tanggal : String? = null
    var keterangan : String? = null
    var email : String? = null
    var vendor: String? = null


    fun DatetoString(tgl: Date): String {
        val df = SimpleDateFormat("dd MMM yyyy")
        return df.format(tgl)
    }
}