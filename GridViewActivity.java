package com.example.appcudetoate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.appcudetoate.controaleVariate.Reteta;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {
    public static final int ADD_RECIPE_REQUEST_CODE = 222;
    public static final int EDIT_REQUEST_CODE = 333;
    public static final String EDIT_RETETA_KEY = "edit reteta key";
    private GridView gvRetete;
    private Button btnAdd;
    private List<Reteta> retetaList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initComponents();
    }
    private void initComponents(){
        gvRetete=findViewById(R.id.gv_retete);
        btnAdd=findViewById(R.id.btn_add);
        addGvAdapter();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivityForResult(intent, ADD_RECIPE_REQUEST_CODE);
            }
        });
        gvRetete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), AddRecipeActivity.class);
                intent.putExtra(EDIT_RETETA_KEY, retetaList.get(position));
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_RECIPE_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Reteta reteta= data.getParcelableExtra(AddRecipeActivity.RETATA_KEY);
            if(reteta!=null){
               retetaList.add(reteta);
                Log.i("Lista", retetaList.toString());
               notifyAdapter();
            }
        }
    }
    private void addGvAdapter(){
      //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                //R.array.grid_view_values,android.R.layout.simple_list_item_1,);
        ArrayAdapter<Reteta>adapter=new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1, retetaList);
        gvRetete.setAdapter(adapter);
    }
    private void notifyAdapter(){
        ArrayAdapter adapter= (ArrayAdapter) gvRetete.getAdapter();
        adapter.notifyDataSetChanged();
    }
}