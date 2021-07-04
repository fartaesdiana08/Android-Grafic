package com.example.subiectgrafic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class AddExercitiuActivity extends AppCompatActivity {
    public static final String EXERCITIU_KEY = "exercitiu key";
    private EditText etdenumire;
    private Spinner spnGrupa;
    private SeekBar seekRepetari;
    private TextView tvSeek;
    private EditText etCalorii;
    private EditText etIndicatii;
    private Button btnAdauga;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercitiu);
        intent=getIntent();
        initComponents();
        populateSpinner();
    }
    private  void initComponents(){
        etdenumire=findViewById(R.id.et_denumire);
        spnGrupa=findViewById(R.id.spn_grupa);
        seekRepetari=findViewById(R.id.seek_bar_repetari);
        tvSeek=findViewById(R.id.tv_seek);
        etCalorii=findViewById(R.id.et_calorii_arse);
        etIndicatii=findViewById(R.id.et_indicatii);
        btnAdauga=findViewById(R.id.btn_adauga);
        seekRepetari.setOnSeekBarChangeListener(showSeekBarProgress());
        btnAdauga.setOnClickListener(addExercitiu());
    }

    private View.OnClickListener addExercitiu() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Exercitiu exercitiu=createExercitiu();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(EXERCITIU_KEY, (Parcelable) exercitiu);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        };
    }

    private boolean validate(){
        if(etdenumire.getText()==null || etdenumire.getText().toString().trim().length()==0){
            Toast.makeText(getApplicationContext(), R.string.denumire_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if(seekRepetari.getProgress()==0){
            Toast.makeText(getApplicationContext(), R.string.repetari_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if(etCalorii.getText()==null || etCalorii.getText().toString().trim().length()==0){
            Toast.makeText(getApplicationContext(), R.string.calorii_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if(etIndicatii.getText()==null || etIndicatii.getText().toString().trim().length()==0){
            Toast.makeText(getApplicationContext(), R.string.indicatii_error, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private Exercitiu createExercitiu(){

        String denumire=etdenumire.getText().toString();
        Grupa grupa=Grupa.valueOf(spnGrupa.getSelectedItem().toString());
        int nrRepetari=seekRepetari.getProgress();
        int caloriiArse=Integer.parseInt(etCalorii.getText().toString());
        String indicatii=etIndicatii.getText().toString();
        return new Exercitiu(denumire,grupa,nrRepetari,caloriiArse,indicatii);
    }


    private SeekBar.OnSeekBarChangeListener showSeekBarProgress() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeek.setText(String.valueOf(seekRepetari.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }
    private void populateSpinner(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getApplicationContext(),R.array.grupa_muschi,
                android.R.layout.simple_spinner_dropdown_item);
        spnGrupa.setAdapter(adapter);
    }
}