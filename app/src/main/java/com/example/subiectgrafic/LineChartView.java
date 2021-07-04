package com.example.subiectgrafic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LineChartView extends View {

    private Map<String, List<Exercitiu>>mapa;
    private Paint paint;
    private Random random;
    private Context context;
    int val=0;
    private Rect rect=new Rect(100,100,100,100);
    float valy=(float)0.02;


    public LineChartView(Context context,   Map<String, List<Exercitiu>>mapa) {
        super(context);
        this.context=context;
        this.mapa=mapa;
        this.paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        random=new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mapa == null || mapa.isEmpty()) {
            return;
        }

        int W=getWidth();
        int H=getHeight();
        float currentPosition=0;
        Log.i("W", String.valueOf(W));
        Log.i("H", String.valueOf(H));

        float w=W/mapa.size();
        int m=0;
        int maxTotal=0;
        for(String label:mapa.keySet()){

            int max=0;
            for(Exercitiu exercitiu:mapa.get(label)){
                if(max<exercitiu.getNrRepetari()){
                    max=exercitiu.getNrRepetari();
                }

            }
            if(max>maxTotal)maxTotal=max;
        }

        for(String label:mapa.keySet()){
            m++;

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

            Log.i("MAXIM", String.valueOf(max));
            Log.i("MIN", String.valueOf(min));


            int i=0;

            int color=Color.argb(100,1+random.nextInt(254),
                    1+random.nextInt(254), 1+random.nextInt(254));
            paint.setColor(color);

            int k=0;
            float[]puncte=new float[2*mapa.get(label).size()];
            for(Exercitiu exercitiu:mapa.get(label)){

                int value=exercitiu.getNrRepetari();
                float x=w*currentPosition+i;
                //float x=w*currentPosition;
                Log.i("x", String.valueOf(x));

                float h=(float)(value*H)/(maxTotal);
                //float h=(float)(value*H)/(max+min);
                Log.i("h", String.valueOf(h));

                float y=(float) (H*0.8)-(float)(h*0.6);
                //float y=H-h;
                Log.i("y", String.valueOf(y));

                canvas.drawCircle(x,y,10,paint);
                paint.setTextSize((float)50);
                canvas.drawText(String.valueOf(value),x,y,paint);
                i+=50;
                puncte[k]=x;
                k++;
                puncte[k]=y;
                k++;
            }
            for(int j=0;j<puncte.length-3;j++){
                canvas.drawLine(puncte[j], puncte[j+1], puncte[j+2],puncte[j+3],paint);
            }

            //desenare legenda
            canvas.drawRect((float) (getWidth()-0.25*getWidth()), (float) (0.05*(m)*getHeight()+0.75*getHeight()) /*(getHeight() -0.2* valy*getHeight())*/, (float) (getWidth()-0.20*getWidth()) , (float) /*(getHeight() - 0.1*valy*getHeight()*/  (0.05*(m+1)*getHeight()+0.75*getHeight()), paint);
            paint.setTextSize((float)30);
            val+=50;
            canvas.drawText(label,(float) (getWidth()-0.19*getWidth()),(float) (0.05*(m+1)*getHeight()+0.75*getHeight()),paint);

            currentPosition++;
        }
    }
}
