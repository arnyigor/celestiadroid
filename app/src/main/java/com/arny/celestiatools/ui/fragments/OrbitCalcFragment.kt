package com.arny.celestiatools.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arny.arnylib.utils.CalculatorDialog
import com.arny.arnylib.utils.DateTimeUtils
import com.arny.arnylib.utils.MathUtils
import com.arny.arnylib.utils.Utility
import com.arny.celestiatools.R
import com.arny.celestiatools.utils.astro.AstroConst
import com.arny.celestiatools.utils.astro.OrbitCalc
import kotlinx.android.synthetic.main.fragment_orbit_calc.*

class OrbitCalcFragment : Fragment() {

    private var mass: Double = 0.0
    private var radius: Double = 0.0
    private var Hp: Double = 0.0
    private var Ha: Double = 0.0
    private var Vp: Double = 0.0
    private var ecc: Double = 0.0
    private var sma: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orbit_calc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        edtMass.setOnClickListener({ view ->
            CalculatorDialog(context, "Масса", edtMass.text.toString()) { result ->
                edtMass.setText(result)
                calc()
            }.show()
        })
        edtRadius.setOnClickListener({ view ->
            CalculatorDialog(context, "Радиус", edtRadius.text.toString()) { result ->
                edtRadius.setText(result)
                calc()
            }.show()
        })
        edtHeightPeri.setOnClickListener({ view ->
            CalculatorDialog(context, "Перигей", edtHeightPeri.text.toString()) { result ->
                edtVelPeri.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtHeightPeri.setText(result)
                calc()
            }.show()
        })
        edtApoHeight.setOnClickListener({ view ->
            CalculatorDialog(context, "Апогей", edtApoHeight.text.toString()) { result ->
                edtVelPeri.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtApoHeight.setText(result)
                calc()
            }.show()
        })
        edtVelPeri.setOnClickListener({ view ->
            CalculatorDialog(context, "Скорость перигея", edtVelPeri.text.toString()) { result ->
                edtApoHeight.setText("")
                edtEcc.setText("")
                edtSma.setText("")
                edtVelPeri.setText(result)
                calc()
            }.show()
        })
        edtEcc.setOnClickListener({ view ->
            CalculatorDialog(context, "Эксцентриситет", edtEcc.text.toString()) { result ->
                edtVelPeri.setText("")
                edtApoHeight.setText("")
                edtHeightPeri.setText("")
                edtEcc.setText(result)
                calc()
            }.show()
        })
        edtSma.setOnClickListener({ view ->
            CalculatorDialog(context, "Большая полуось", edtSma.text.toString()) { result ->
                edtVelPeri.setText("")
                edtApoHeight.setText("")
                edtHeightPeri.setText("")
                edtSma.setText(result)
                calc()
            }.show()
        })
        btnFill.setOnClickListener({ view ->
            edtMass.setText(AstroConst.Emass.toString())
            edtRadius.setText(AstroConst.R_Earth.toString())
        })
    }

    private fun calc() {
        validateVariables()
        val results = StringBuilder()
        if (Vp > 0 || Ha > 0 || ecc != 0.0 || sma != 0.0) {
            val ellipseMotion = OrbitCalc.calcEllipseOrbit(mass, radius, Hp, Ha, Vp, ecc, sma)
            results.append("1я космическая:").append(MathUtils.round(ellipseMotion.v1, 3)).append("(м/с)\n")
            results.append("2я космическая:").append(MathUtils.round(ellipseMotion.v2, 3)).append("(м/с)\n")
            results.append("Скорость апогея:").append(MathUtils.round(ellipseMotion.va, 3)).append("(м/с)\n")
            results.append("Скорость перигея:").append(MathUtils.round(ellipseMotion.vp, 3)).append("(м/с)\n")
            results.append("Большая полуось:").append(MathUtils.toExpo(ellipseMotion.sma / 1e3, 6)).append("(км)\n")
            results.append("Эксцентриситет:").append(ellipseMotion.ecc).append("\n")
            edtEcc.setText(ellipseMotion.ecc.toString())
            edtApoHeight.setText(MathUtils.round(ellipseMotion.ha, 3).toString())
            edtHeightPeri.setText(MathUtils.round(ellipseMotion.hp, 3).toString())
            edtVelPeri.setText(MathUtils.round(ellipseMotion.vp, 3).toString())
            edtSma.setText(MathUtils.round(ellipseMotion.sma, 3).toString())
            results.append("Перигей:").append(MathUtils.toExpo(Hp / 1e3, 6)).append("(км) \n")
            results.append("Апогей:").append(MathUtils.toExpo(ellipseMotion.ha / 1e3, 6)).append("(км) \n")
            results.append("Ускорение:").append(MathUtils.round(ellipseMotion.uskorenie, 3)).append("(м/с2)\n")
            val hour = ellipseMotion.hour
            val min = ellipseMotion.min
            val sec = ellipseMotion.sec
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour, min, sec))).append(" : ").append(ellipseMotion.ptime.toLong().toString()).append("(сек)")
        } else {
            val circularMotion = OrbitCalc.calcCircularOrbit(mass, radius, Hp)
            results.append("1я космическая:").append(MathUtils.round(circularMotion.v1, 3)).append("(м/с)\n")
            results.append("2я космическая:").append(MathUtils.round(circularMotion.v2, 3)).append("(м/с)\n")
            results.append("Ускорение:").append(MathUtils.round(circularMotion.uskorenie, 3)).append("(м/с2)\n")
            edtEcc.setText(circularMotion.ecc.toString())
            val hour = circularMotion.hour
            val min = circularMotion.min
            val sec = circularMotion.sec
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour, min, sec))).append(" : ").append(circularMotion.ptime.toLong().toString()).append("(сек)")
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
    }

}
