package com.arny.celestiatools.presenter.planet

import com.arny.celestiatools.data.models.Planet
import com.arny.celestiatools.data.source.MainRepositoryImpl
import com.arny.celestiatools.data.utils.MathUtils
import com.arny.celestiatools.data.utils.empty
import com.arny.celestiatools.data.utils.launchAsync
import com.arny.celestiatools.data.utils.parseDouble
import com.arny.celestiatools.presenter.base.BaseMvpPresenterImpl

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
                val radiusTxt = MathUtils.round(it.radius, 3).toString()
                mView?.showPlanetRadius(radiusTxt)
                val massTxt = MathUtils.round(it.mass, 3).toString()
                mView?.showPlanetMass(massTxt)
            }
        }, {
            mView?.toastError(it.message)
        })
    }


    override fun addPlanet(name: String?, radius: String?, mass: String?) {
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
        val planet = Planet()
        if (currentID != null) {
            planet.id = currentID!!
        }
        planet.name = name
        planet.radius = parseRadius
        planet.mass = parseMass
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