package desriel.kiki.ojekku

import android.app.Application
import androidx.room.Room
import desriel.kiki.core.data.OjekkuContainerImpl
import desriel.kiki.core.data.source.local.room.OjekkuDatabase
import desriel.kiki.core.domain.OjekkuContainer

class OjekkuApplication : Application() {

    lateinit var ojekkuContainer : OjekkuContainer

    val appDatabase by lazy {
        Room.databaseBuilder(this, OjekkuDatabase::class.java, "my-database-name")
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        ojekkuContainer = OjekkuContainerImpl(context = applicationContext)
    }
}