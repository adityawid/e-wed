package online.e_wedd.apps.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import online.e_wedd.apps.Helper.AppHelper
import online.e_wedd.apps.R
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    override fun onClick(v: View?) {
       when(v){
           login_google ->{
               signIn()

           }

           login_phone -> {
               toast("Coming Soon")
           }
       }
    }

    var TAG : String = "LOGIN"
    lateinit var mProgressDialog : ProgressDialog

    // TODO LOGIN (1) googleAuth : Global Variabel
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mAuth: FirebaseAuth? = null
    private val REQUEST_CODE_SIGN_IN = 1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mProgressDialog = indeterminateProgressDialog("Loading...")
        mProgressDialog.dismiss()

        login_google.setOnClickListener(this)
        login_phone.setOnClickListener(this)

        //  TODO LOGIN (2) googleAuth : initGoogleSignIn GSO object
        initGoogleSignIn()
    }

    // TODO LOGIN (3) googleAuth : creat function SignIn
    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        mAuth = FirebaseAuth.getInstance()
    }
    // LOGIN TODO (3) googleAuth : creat function SignIn
    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }
    // LOGIN TODO (4) googleAuth : getResult dari ActivityResult function signIn
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent();
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // successful -> authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                // failed -> update UI
                Logout()
                Toast.makeText(applicationContext, "SignIn: failed!",
                        Toast.LENGTH_SHORT).show()
            }
        }else {
            /*// TODO LOGIN (4) FACEBOOK
            // LOGIN FACEBOOK
            callbackManager?.onActivityResult(requestCode, resultCode, data)*/
        }
    }
    // LOGIN TODO (5) googleAuth : Auth Google
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
//        Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mProgressDialog.show()
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Log.e(TAG, "signInWithCredential: Success!")
                        val user = mAuth!!.currentUser
//                        updateUI(user)
                        Log.d(TAG+"google name", user?.displayName)
                        Log.d(TAG+"google email", user?.email)
//                        Log.d(TAG+"google phone", user?.phoneNumber)
//                        Log.d(TAG+"google photo", user?.photoUrl.toString())
                        LoginGoogle(user?.displayName!!,user?.email!!,"1")

                    } else {
                        // Sign in fails
                        Log.w(TAG, "signInWithCredential: Failed!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!",
                                Toast.LENGTH_SHORT).show()
                        Logout()                    }
                }
    }


    private fun LoginGoogle(nama:String, email: String, metode_registrasi: String) {
        ShowDialog("Loading ...")
        HideMessage()
        Logout()

        // TODO LOGIN googleAuth
        // Jika ditemukan langsung login, jika tidak panggil registerGoogleFacebook
//        toast("Login GOOGLE Berhasil")
        registerGoogleFacebook(nama,email,metode_registrasi)

        // httprequest
        // TODO LOGIN httprequest send  data to server


    }

    fun registerGoogleFacebook(nama: String, email: String, metode_registrasi: String) {
        ShowDialog("Loading . . .")
        // TODO LOGIN httprequest send  data to server
        //URL untuk Register
        var URL = "https://script.google.com/macros/s/AKfycbxbZGE_eaefCwEEydPl9Llm48Alv8VR9ru_0TRE8lAic7a3i_4/exec?action=register&sheetName=user_data"
//        var URL = AppConfig.Register

        var stringRequest = object : StringRequest(Request.Method.POST,URL, Response.Listener {
            response ->
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status").equals("success")) {

                toast("" + jsonObject.getString("message"))
                AppHelper.email= email
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                toast("" + jsonObject.getString("message"))
                AppHelper.email= email
                startActivity(Intent(this, MainActivity::class.java))

            }
            //Respon dialog saat sukses mendaftar
//            responDialog(context ,getString(R.string.perhatian_dialog), getString(R.string.register_sukses))

        }, Response.ErrorListener {

            //Respon saat register error
        }) {
            override fun getParams() : Map<String,String> {
                val params = HashMap<String, String>()
                params.put("password", "")
                params.put("email", email)
                params.put("nama", nama)
                params.put("metode_login", metode_registrasi)
                return params

            }
        }

        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(stringRequest)
        HideMessage()
    }

    private fun signOutGoogle() {
        // sign out Firebase
        mAuth!!.signOut()

        // sign out Google
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {  }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("connection Google Failed")
    }

    // TODO Logout
    private fun Logout(){

        signOutGoogle()
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
