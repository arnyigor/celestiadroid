package com.arny.celestiatools.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Utility;
import com.arny.celestiatools.R;
import com.arny.celestiatools.utils.astro.CircularMotion;
import com.arny.celestiatools.utils.astro.EllipseMotion;
import com.arny.celestiatools.utils.astro.OrbitCalcMotion;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrbitCalcFragment extends Fragment {

    private Context context;
    private EditText edtMass;
    private EditText edtRadius;
    private EditText edtHeightPeri;
    private EditText edtVelPeri;
    private TextView tvResult;
    private double mass;
    private double radius;
    private double Hp;
    private double Vp;

    public OrbitCalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orbit_calc, container, false);
        context = container.getContext();
        initUI(view);
        initListeners();
        return view;
    }

    private void initListeners() {
        edtMass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calc();
            }
        });
        edtRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calc();
            }
        });
        edtHeightPeri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calc();
            }
        });
        edtVelPeri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calc();
            }
        });
    }

    private void calc(){
        validateVariables();
        StringBuilder results = new StringBuilder();
        if (Vp <= 0) {
            CircularMotion circularMotion = OrbitCalcMotion.calcCircularOrbit(mass, radius, Hp);
            results.append("1я космическая:").append(MathUtils.round(circularMotion.getV1(),6)).append("м/с\n");
            results.append("2я космическая:").append(MathUtils.round(circularMotion.getV2(),6)).append("м/с\n");
            results.append("Ускорение:").append(MathUtils.round(circularMotion.getUskorenie(),6)).append("м/с\n");
            int hour = circularMotion.getHour();
            int min = circularMotion.getMin();
            int sec = circularMotion.getSec();
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour,min,sec)));
        }else{
            EllipseMotion ellipseMotion = OrbitCalcMotion.calcEllipseOrbit(mass, radius, Hp, Vp);
            results.append("1я космическая:").append(MathUtils.round(ellipseMotion.getV1(),6)).append("м/с\n");
            results.append("2я космическая:").append(MathUtils.round(ellipseMotion.getV2(),6)).append("м/с\n");
            results.append("Скорость апогея:").append(MathUtils.round(ellipseMotion.getVa(),6)).append("м/с\n");
            results.append("Эксцентриситет:").append(ellipseMotion.getEcc()).append("\n");
            results.append("Апогей:").append(MathUtils.round(ellipseMotion.getHa(),6)).append("\n");
            results.append("Ускорение:").append(MathUtils.round(ellipseMotion.getUskorenie(),6)).append("м/с2\n");
            int hour = ellipseMotion.getHour();
            int min = ellipseMotion.getMin();
            int sec = ellipseMotion.getSec();
            results.append("Период:").append(DateTimeUtils.convertTime(DateTimeUtils.convertTime(hour,min,sec)));
        }
        tvResult.setText(results.toString());
    }

    private void validateVariables() {
        String massStr = edtMass.getText().toString();
        String radStr = edtRadius.getText().toString();
        String hPeriStr = edtHeightPeri.getText().toString();
        String velPeriStr = edtVelPeri.getText().toString();
        mass = !Utility.empty(massStr) ? Double.parseDouble(massStr) : 0;
        radius = !Utility.empty(radStr) ? Double.parseDouble(radStr) : 0;
        Hp = !Utility.empty(hPeriStr) ? Double.parseDouble(hPeriStr) : 0;
        Vp = !Utility.empty(velPeriStr) ? Double.parseDouble(velPeriStr) : 0;
    }

    private void initUI(View view) {
        edtMass = view.findViewById(R.id.edtMass);
        edtRadius = view.findViewById(R.id.edtRadius);
        edtHeightPeri = view.findViewById(R.id.edtHeightPeri);
        edtVelPeri = view.findViewById(R.id.edtVelPeri);
        tvResult = view.findViewById(R.id.tvResult);
    }








}
