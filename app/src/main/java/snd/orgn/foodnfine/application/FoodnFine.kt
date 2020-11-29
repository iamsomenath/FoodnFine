package snd.orgn.foodnfine.application

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.squareup.picasso.Picasso
import snd.orgn.foodnfine.data.room.database.AppDatabase
import snd.orgn.foodnfine.data.shared_presferences.AppSharedPreferences
import snd.orgn.foodnfine.util.PicassoProvider

class FoodnFine : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        //Setting App Context
        appContext = applicationContext

        //init AppSharedPreferences
        appSharedPreference = AppSharedPreferences.getInstance(appContext)

        //init Database
        appDatabase = AppDatabase.getInstance(appContext)
        //init NetworkAuthentication
    }

    companion object {
        //Getting AppDatabase
        var appContext: Context? = null
            private set

        //Getting AppSharedPreferences
        @JvmStatic
        var appSharedPreference: AppSharedPreferences? = null
            private set
        private val picassoWithOAuth: Picasso? = null
        private val picasso: Picasso? = null

        //Getting AppDatabase
        @JvmStatic
        var appDatabase: AppDatabase? = null
            private set

        fun getPicasso(context: Context?): Picasso? {
            return picasso ?: PicassoProvider().getPicasso(context)
        }
    }
}