package com.example.subiectgrafic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_EX_REQUEST_CODE = 222;
    private Button btnAdd;
    private Button btnNormalizare;
    private List<Exercitiu>exercitiuList=new ArrayList<>();
    private Map<String, List<Exercitiu>>mapa=new HashMap<>();
    private Fragment currentFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exercitiuList.add(new Exercitiu("abdomene",Grupa.valueOf("abdomen"),50,400,"naspa"));
        exercitiuList.add(new Exercitiu("abdomene",Grupa.valueOf("abdomen"),12,400,"naspa"));
        exercitiuList.add(new Exercitiu("abdomene",Grupa.valueOf("abdomen"),9,400,"naspa"));
        exercitiuList.add(new Exercitiu("abdomene",Grupa.valueOf("abdomen"),7,400,"naspa"));
        exercitiuList.add(new Exercitiu("floatari",Grupa.valueOf("triceps"),30,400,"naspa"));
        exercitiuList.add(new Exercitiu("floatari",Grupa.valueOf("triceps"),76,400,"naspa"));
        exercitiuList.add(new Exercitiu("ex brat",Grupa.valueOf("biceps"),15,400,"naspa"));
        exercitiuList.add(new Exercitiu("ex brat",Grupa.valueOf("biceps"),10,400,"naspa"));
        if(!exercitiuList.isEmpty()){
            mapa=construireMapa(exercitiuList);
            openDefaultFragment(savedInstanceState);
        }
        initComponents();
    }
    private void initComponents(){
        btnAdd=findViewById(R.id.btn_add_ex);
        btnNormalizare=findViewById(R.id.btn_normalizare);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddExercitiuActivity.class);
                startActivityForResult(intent, ADD_EX_REQUEST_CODE);
            }
        });
        btnNormalizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String label:mapa.keySet()){
                    int max=0;
                    for(Exercitiu exercitiu:mapa.get(label)){
                        if(max<exercitiu.getNrRepetari()){
                            max=exercitiu.getNrRepetari();
                        }
                    }
                    int min=9999;
                    for(Exercitiu exercitiu:mapa.get(label)){
                        if(min>exercitiu.getNrRepetari()){
                            min=exercitiu.getNrRepetari();
                        }
                    }

                    Log.i(" MAX",String.valueOf(max));
                    Log.i(" MIN",String.valueOf(min));

                   int iMax=0;
                   int iMin=0;
                   for(int i=0;i<mapa.get(label).size();i++){
                       if (mapa.get(label).get(i).getNrRepetari()==min){
                           iMin=i;
                       }
                   }
                   //minim
                    mapa.get(label).remove(iMin);
                    for(int i=0;i<mapa.get(label).size();i++){
                        if (mapa.get(label).get(i).getNrRepetari()==max){
                            iMax=i;
                        }
                    }
                    //maxim
                    mapa.get(label).remove(iMax);
                }
               Log.i("Dupa normalizare", mapa.toString());
                if (currentFragment instanceof LineChartFragment) {
                    ((LineChartFragment) currentFragment).notifyInternalAdapter();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_EX_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Exercitiu exercitiu= data.getParcelableExtra(AddExercitiuActivity.EXERCITIU_KEY);
            if(exercitiu!=null){
                exercitiuList.add(exercitiu);
                mapa=construireMapa(exercitiuList);
                currentFragment = LineChartFragment.newInstance((HashMap<String, List<Exercitiu>>) mapa);
                openFragment();
            }
        }
    }

    private Map<String, List<Exercitiu>> construireMapa(List<Exercitiu>exercitii){
        if(exercitii==null ||exercitii.isEmpty()){
            return null;
        }
        Map<String, List<Exercitiu>>source=new HashMap<>();
        List<Exercitiu>exAb=new ArrayList<>();
        List<Exercitiu>exBiceps=new ArrayList<>();
        List<Exercitiu>exTriceps=new ArrayList<>();
        for(Exercitiu exercitiu:exercitii){
            if(exercitiu.getGrupa()==Grupa.biceps){
                exBiceps.add(exercitiu);
                source.put(exercitiu.getGrupa().toString(),exBiceps);
            }
            else if(exercitiu.getGrupa()==Grupa.triceps){
                exTriceps.add(exercitiu);
                source.put(exercitiu.getGrupa().toString(),exTriceps);
            }
            else if(exercitiu.getGrupa()==Grupa.abdomen){
                exAb.add(exercitiu);
                source.put(exercitiu.getGrupa().toString(),exAb);
            }
        }
        return source;
    }


    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = LineChartFragment.newInstance((HashMap<String, List<Exercitiu>>) mapa);
            openFragment();
        }
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame,currentFragment)
                .commit();
    }
}