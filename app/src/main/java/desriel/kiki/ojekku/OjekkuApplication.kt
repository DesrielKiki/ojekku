package desriel.kiki.ojekku

import android.app.Application
import desriel.kiki.core.data.OjekkuContainerImpl
import desriel.kiki.core.domain.OjekkuContainer

class OjekkuApplication : Application() {

    lateinit var ojekkuContainer: OjekkuContainer


    override fun onCreate() {
        super.onCreate()
        ojekkuContainer = OjekkuContainerImpl(context = applicationContext)
    }
}