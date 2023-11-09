package desriel.kiki.ojekku

import android.app.Application
import android.content.Context
import desriel.kiki.core.data.OjekkuContainerImpl
import desriel.kiki.core.domain.OjekkuContainer
import desriel.kiki.ojekku.presentation.screen.profile.LocaleManager

class OjekkuApplication : Application() {

    lateinit var ojekkuContainer : OjekkuContainer




    override fun onCreate() {
        super.onCreate()
        ojekkuContainer = OjekkuContainerImpl(context = applicationContext)
    }
}