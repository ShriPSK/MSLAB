package com.example.circle;


import android.animation.ValueAnimator;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private View circle_view,triangle_view,square_view,sqd;
    private Button btn;
    private int count=1,i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.button);
        sqd=findViewById(R.id.square_display);
        circle_view = findViewById(R.id.circle);
        triangle_view = findViewById(R.id.triangle);
        square_view=findViewById(R.id.square);


        circle_view.setVisibility(View.INVISIBLE);
        triangle_view.setVisibility(View.INVISIBLE);
        square_view.setVisibility(View.INVISIBLE);

        Handler h=new Handler();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i==0)
                        {
                            sqd.setVisibility(View.VISIBLE);
                            circle_view.setVisibility(View.INVISIBLE);
                            triangle_view.setVisibility(View.INVISIBLE);
                            square_view.setVisibility(View.INVISIBLE);
                            Random r=new Random();
                            int color=Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256));
                            sqd.setBackgroundColor(color);
                        }

                        else if(i==1)
                        {
                            if(count==1)
                            {
                                circle_animation();
                                count++;
                            }
                            else if(count==2)
                            {
                                triangle_animation();
                                count++;
                            }
                            else if(count==3)
                            {
                                square_animation();
                                count=1;
                            }
                        }
                        i=0;
                    }
                },500);
            }
        });
    }

    private void circle_animation() {

        Path p = new Path();

        circle_view.setVisibility(View.VISIBLE);
        triangle_view.setVisibility(View.INVISIBLE);
        square_view.setVisibility(View.INVISIBLE);
        sqd.setVisibility(View.INVISIBLE);
        float centerX = 540;
        float centerY = 1127;
        float radius = 200;

        p.addCircle(centerX, centerY, radius, Path.Direction.CW);

        final PathMeasure pm = new PathMeasure(p, false);

        ValueAnimator animator = ValueAnimator.ofFloat(0, pm.getLength());
        final float[] pos = new float[2];

        animator.addUpdateListener(animation -> {
            float v = (float) animation.getAnimatedValue();
            pm.getPosTan(v, pos, null);
            circle_view.setTranslationX(pos[0]);
            circle_view.setTranslationY(pos[1]);
        });

        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void triangle_animation()
    {
        circle_view.setVisibility(View.INVISIBLE);
        triangle_view.setVisibility(View.VISIBLE);
        square_view.setVisibility(View.INVISIBLE);
        sqd.setVisibility(View.INVISIBLE);
        Path p = new Path();
        p.moveTo(500,1000 );
        p.lineTo(800, 1300);
        p.lineTo(200, 1300);
        p.lineTo(500, 1000);
        PathMeasure pathMeasure = new PathMeasure(p, false);
        ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            float[] position = new float[2];
            pathMeasure.getPosTan(value, position, null);
            triangle_view.setTranslationX(position[0]);
            triangle_view.setTranslationY(position[1]);
        });
        animator.start();
    }

    private void square_animation()
    {
        circle_view.setVisibility(View.INVISIBLE);
        triangle_view.setVisibility(View.INVISIBLE);
        square_view.setVisibility(View.VISIBLE);
        sqd.setVisibility(View.INVISIBLE);
        Path p=new Path();
        p.moveTo(270,665);
        p.lineTo(770,665);
        p.lineTo(770,1165);
        p.lineTo(270,1165);
        p.lineTo(270,665);
        PathMeasure pathMeasure = new PathMeasure(p, false);
        ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            float[] position = new float[2];
            pathMeasure.getPosTan(value, position, null);
            square_view.setTranslationX(position[0]);
            square_view.setTranslationY(position[1]);
        });
        animator.start();
    }
}
