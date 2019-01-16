package com.arny.celestiadroid.presenter.planet

import com.arny.celestiadroid.data.models.Planet
import com.arny.celestiadroid.data.source.MainRepositoryImpl
import com.arny.celestiadroid.data.spaceutils.astro.getExpVal
import com.arny.celestiadroid.data.spaceutils.astro.getNotExpVal
import com.arny.celestiadroid.data.spaceutils.astro.getPlanetRadius
import com.arny.celestiadroid.data.utils.*
import com.arny.celestiadroid.presenter.base.BaseMvpPresenterImpl

class PlanetPresenter : BaseMvpPresenterImpl<PlanetContract.View>(), PlanetContract.Presenter {
    private val repositoryImpl = MainRepositoryImpl.instance
    private var currentID: Long? = null

    override fun initState(id: Long?) {
        if (id == null) {
        } else {
            initPlanetState(id)
        }
    }

    override fun removeItem() {
        if (currentID == null) {
            mView?.onBackPress()
        } else {
            launchAsync({
                repositoryImpl.removePlanet(currentID!!)
            }, {
                mView?.toastSuccess("Планета удалена")
                mView?.setResultOk(true)
                mView?.onBackPress()
            }, {
                mView?.toastError(it.message)
            })
        }
    }

    private fun initPlanetState(id: Long) {
        launchAsync({
            repositoryImpl.loadPlanet(id)
        }, {
            currentID = it?.id
            if (it == null) {
                mView?.toastError("Запрошенная планета не найдена в базе")
            } else {
                mView?.setBtnText("Save")
                mView?.showPlanetName(it.name)
                val radius = it.radius
                val round = MathUtils.round(radius, 3)
                mView?.showPlanetRadius(round.toString())
                val mass = it.mass
                val exp = getExpVal(mass)
                val massTxt = getNotExpVal(mass)
                mView?.showPlanetMassExp(exp)
                mView?.showPlanetMass(massTxt)
            }
        }, {
            mView?.toastError(it.message)
        })
    }


    override fun addPlanet(name: String?, radius: String?, mass: String?, exponent: String) {
        if (name.empty()) {
            mView?.toastError("Некорректное название")
            return
        }
        val parseRadius = radius.parseDouble()
        if (radius.empty() || parseRadius == null || parseRadius.empty()) {
            mView?.toastError("Некорректное значение радиуса")
            return
        }
        val parseMass = mass.parseDouble()
        if (mass.empty() || parseMass == null || parseMass.empty()) {
            mView?.toastError("Некорректное значение массы")
            return
        }
        val expVal = exponent.parseInt()
        val planetMass = getPlanetRadius(parseMass, expVal)
        val planet = Planet()
        if (currentID != null) {
            planet.id = currentID!!
        }
        planet.name = name
        planet.radius = parseRadius
        planet.mass = planetMass
        launchAsync({
            repositoryImpl.addPlanet(planet)
        }, {
            if (it != null) {
                mView?.toastSuccess("Планета $name добавлена")
                mView?.result(it)
            } else {
                mView?.toastError("Планета $name не добавлена")
            }
        }, {
            mView?.toastError(it.message)
        })
    }
}