package online.e_wedd.apps.Helper

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by devjurnal on 5/31/18.
 */
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        Fabric.with(this, Crashlytics())
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(this)
        // Initialize Realm. Should only be done once when the application starts.
        //Realm.init(this)

        /*
        val realmConfiguration = RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        */

        //Start Normal

//        Realm.setDefaultConfiguration(realmConfiguration)
        //End Normal



        //JIKA TERJADI ERROR REALM SCEMA BUKA KOMEN DI BAWAH RUN KEMUDIAN TUTUP KOMEN LAGI
        //Realm.deleteRealm(c.build())

    }

    fun getContextStatic() : Context {
        return super.getApplicationContext()
    }
}