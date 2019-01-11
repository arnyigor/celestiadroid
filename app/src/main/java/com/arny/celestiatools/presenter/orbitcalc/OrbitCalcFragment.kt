package com.arny.celestiatools.presenter.orbitcalc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arny.celestiatools.R
import com.arny.celestiatools.data.spaceutils.astro.AstroUtils
import com.arny.celestiatools.data.spaceutils.astro.OrbitCalc
import com.arny.celestiatools.data.utils.DateTimeUtils
import com.arny.celestiatools.data.utils.MathUtils
import com.arny.celestiatools.data.utils.Utility
import com.arny.celestiatools.data.utils.calculator.CalculatorDialog
import com.arny.celestiatools.data.utils.getExtra
import com.arny.celestiatools.presenter.main.MainActivity
import com.arny.celestiatools.presenter.planets.PlanetsFragment
import kotlinx.android.synthetic.main.fragment_orbit_calc.*

class OrbitCalcFragment : Fragment() {
    private var mass: Double = 0.0
    private var radius: Double = 0.0
    private var resultMass: Double? = null
    private var resultRadius: Double? = null
    private var Hp: Double = 0.0
    private var Ha: Double = 0.0
    private var Vp: Double = 0.0
    private var ecc: Double = 0.0
    private var sma: Double = 0.0
    private var period: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orbit_calc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        activity?.title = "Калькулятор орбит"
    }

    override fun onResume() {
        super.onResume()
        if (resultMass != null) {
            edtMass.setText(resultMass.toString())
        }
        if (resultRadius != null) {
            edtRadius.setText(resultRadius.toString())
        }
    }

    private fun initListeners() {
        edtMass.setOnClickListener { view ->
            CalculatorDialog(context, "Масса", edtRadius.text.toString()) { result ->
                edtMass.setText(result)
                calc()
            }.show()
        }
        edtRadius.setOnClickListener { view ->
            CalculatorDialog(context, "Радиус", edtRadius.text.toString()) { result ->
                edtRadius.setText(result)
                calc()
            }.show()
        }
        edtHeightPeri.setOnClickListener { view ->
            CalculatorDialog(context, "Перигей", edtHeightPeri.text.toString()) { result ->
                edtVelPeri.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtPeriod.setText("")
                edtHeightPeri.setText(result)
                calc()
            }.show()
        }
        edtApoHeight.setOnClickListener { view ->
            CalculatorDialog(context, "Апогей", edtApoHeight.text.toString()) { result ->
                edtVelPeri.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtPeriod.setText("")
                edtApoHeight.setText(result)
                calc()
            }.show()
        }
        edtVelPeri.setOnClickListener { view ->
            CalculatorDialog(context, "Скорость перигея", edtVelPeri.text.toString()) { result ->
                edtApoHeight.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtPeriod.setText("")
                edtVelPeri.setText(result)
                calc()
            }.show()
        }
        edtEcc.setOnClickListener { view ->
            CalculatorDialog(context, "Эксцентриситет", edtEcc.text.toString()) { result ->
                edtVelPeri.setText("")
                edtApoHeight.setText("")
                edtHeightPeri.setText("")
                edtPeriod.setText("")
                edtEcc.setText(result)
                calc()
            }.show()
        }
        edtSma.setOnClickListener { view ->
            CalculatorDialog(context, "Большая полуось", edtSma.text.toString()) { result ->
                edtVelPeri.setText("")
                edtApoHeight.setText("")
                edtHeightPeri.setText("")
                edtPeriod.setText("")
                edtSma.setText(result)
                calc()
            }.show()
        }
        edtPeriod.setOnClickListener { view ->
            CalculatorDialog(context, "Период", edtPeriod.text.toString()) { result ->
                edtVelPeri.setText("")
                edtApoHeight.setText("")
                edtHeightPeri.setText("")
                edtPeriod.setText(result)
                calc()
            }.show()
        }
        btn_request_planet.setOnClickListener { view ->
            val activity = activity
            if (activity is MainActivity) {
                activity.startFragmentWithTargetFragment(this, PlanetsFragment.newInstance(), 101)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(OrbitCalcFragment::class.java.simpleName, "onActivityResult: requestCode:$requestCode,resultCode:$resultCode,data:" + Utility.dumpIntent(data))
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    resultMass = data?.getExtra<Double>("mass")
                    resultRadius = data?.getExtra<Double>("radius")
                }
            }
        }
    }

    private fun calc() {
        validateVariables()
        val results = StringBuilder()
        if (Vp > 0 || Ha > 0 || ecc != 0.0 || sma != 0.0 || period != 0.0) {
            val ellipseMotion = OrbitCalc.calcEllipseOrbit(mass, radius, Hp, Ha, Vp, ecc, sma,period)
            results.append("1я космическая:").append(MathUtils.round(ellipseMotion.v1, 3)).append("(м/с)\n")
            results.append("2я космическая:").append(MathUtils.round(ellipseMotion.v2, 3)).append("(м/с)\n")
            results.append("Скорость апогея:").append(MathUtils.round(ellipseMotion.va, 3)).append("(м/с)\n")
            results.append("Скорость перигея:").append(MathUtils.round(ellipseMotion.vp, 3)).append("(м/с)\n")
            results.append("Большая полуось:").append(AstroUtils.getSmartDistance(ellipseMotion.sma)).append("\n")
            results.append("Эксцентриситет:").append(ellipseMotion.ecc).append("\n")
            edtEcc.setText(ellipseMotion.ecc.toString())
            edtApoHeight.setText(MathUtils.round(ellipseMotion.ha, 3).toString())
            edtHeightPeri.setText(MathUtils.round(ellipseMotion.hp, 3).toString())
            edtVelPeri.setText(MathUtils.round(ellipseMotion.vp, 3).toString())
            edtSma.setText(MathUtils.round(ellipseMotion.sma, 3).toString())
            results.append("Перигей:").append(AstroUtils.getSmartDistance(ellipseMotion.hp)).append("\n")
            results.append("Апогей:").append(AstroUtils.getSmartDistance(ellipseMotion.ha)).append("\n")
//            results.append("Ускорение:").append(MathUtils.round(ellipseMotion.uskorenie, 3)).append("(м/с2)\n")
            val hour = ellipseMotion.hour
            val min = ellipseMotion.min
            val sec = ellipseMotion.sec
            val ptime = ellipseMotion.ptime
            val timeSec = ptime.toLong().toString()
            edtPeriod.setText(timeSec)
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour, min, sec))).append(" : ").append(timeSec).append("(сек)")
        } else {
            val circularMotion = OrbitCalc.calcCircularOrbit(mass, radius, Hp)
            results.append("1я космическая:").append(MathUtils.round(circularMotion.v1, 3)).append("(м/с)\n")
            results.append("2я космическая:").append(MathUtils.round(circularMotion.v2, 3)).append("(м/с)\n")
            results.append("Ускорение:").append(MathUtils.round(circularMotion.uskorenie, 3)).append("(м/с2)\n")
            edtEcc.setText(circularMotion.ecc.toString())
            val hour = circularMotion.hour
            val min = circularMotion.min
            val sec = circularMotion.sec
            val ptime = circularMotion.ptime
            val timeSec = ptime.toLong().toString()
            edtPeriod.setText(timeSec)
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour, min, sec))).append(" : ").append(timeSec).append("(сек)")
        }
        tvResult.text = results.toString()
    }

    private fun validateInputDouble(editText: EditText): Double {
        var s: String? = editText.text.toString()
        if (Utility.empty(s)) {
            return 0.0
        }
        s = s?.trim { it <= ' ' }
        s = Utility.match(s, "-?\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?", 0)
        return java.lang.Double.parseDouble(s)
    }

    private fun validateVariables() {
        mass = validateInputDouble(edtMass)
        radius = validateInputDouble(edtRadius)
        Hp = validateInputDouble(edtHeightPeri)
        Ha = validateInputDouble(edtApoHeight)
        Vp = validateInputDouble(edtVelPeri)
        ecc = validateInputDouble(edtEcc)
        sma = validateInputDouble(edtSma)
        period = validateInputDouble(edtPeriod)
    }

}
