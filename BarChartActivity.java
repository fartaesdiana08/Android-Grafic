package com.example.appcudetoate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appcudetoate.database.model.Livrare;
import com.example.appcudetoate.grafice.BarChartView;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Livrare> livrari= (List<Livrare>) getIntent().getSerializableExtra(IstoricLivrariActivity.LIVRARI_FOR_BARCHART);
        setContentView(new BarChartView(getApplicationContext(),construireMapa(livrari)));
    }
    private Map<String,Integer> construireMapa(List<Livrare>livrari){
        if(livrari==null ||livrari.isEmpty()){
            return null;
        }
        Map<String, Integer>source=new HashMap<>();
        for(Livrare livrare:livrari){
            if(source.containsKey(livrare.getTip().toString())){
                Integer currentValue=source.get(livrare.getTip().toString());
                Integer newValue;
                if(currentValue!=null){
                    newValue=currentValue+1;
                }else{
                    newValue=1;
                }
                source.put(livrare.getTip().toString(),newValue);
            }else{
                source.put(livrare.getTip().toString(),1);
            }
        }
        return source;
    }
}