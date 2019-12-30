package com.example.burger.burgerserver;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    //delay for ingredient drop
    private double delay;
    //score
    private int score;
    //reference to scoreboard
    private TextView display;
    //boolean to tell if game is running
    private boolean isRunning;
    //stores high score
    private int highScore;
    //reference for UI gameboard
    private Canvas UI;
    //special handler for setting score
    private Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message m)
        {
            String message = (String) m.obj;
            TextView mseeg = (TextView) findViewById(R.id.textView);
            mseeg.setText(message);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize private data members
        delay = 1000;
        score = 0;
        display = (TextView) findViewById(R.id.textView);
        isRunning = false;
        highScore=0;
        //setup UI elements
        //UI stack
        Bitmap b = Bitmap.createBitmap(150, 250, Bitmap.Config.ARGB_8888);
        UI= new Canvas(b);
        ImageView imageview= (ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(b);
        //cheatbutton color
        Button cheatBut = (Button)findViewById(R.id.cheatButton);
        cheatBut.setBackgroundColor(Color.RED);
    }

    public void onReset(View v)
    {
        //reset game elements
        removeAll();
        GameFunction.eraseIngredients();
        Button b = (Button) v;
        b.setVisibility(View.GONE);
        Button cheatBut = (Button)findViewById(R.id.cheatButton);
        cheatBut.setBackgroundColor(Color.RED);
        score=0;
        delay = 1000;
        TextView scoreTemp = (TextView) findViewById(R.id.textView);
        scoreTemp.setTextSize(60);
        //start scorekeeping
        String messy = Integer.toString(score);
        Message msgee = Message.obtain(); // Creates an new Message instance
        msgee.obj = messy;
        hand.sendMessage(msgee);
        //start game thread, which drops ingedients and sets the score
        isRunning=true;
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                while(isRunning)
                {
                    //set score
                    String mess = Integer.toString(score);
                    Message msg = Message.obtain(); // Creates an new Message instance
                    msg.obj = mess;
                    hand.sendMessage(msg);
                    dropIngredient();
                    try
                    {
                        //wait delay milliseconds
                        Thread.sleep((long)delay);
                    }
                    catch(InterruptedException e)
                    {
                        //nothing
                    }
                    //decrease delay
                    delay = ((int)delay*0.70)+100;
                }
            }
        };
        //start game thread
        Thread gameThread = new Thread(r);
        gameThread.start();
    }
    public void onPress(View v)
    {
        Button foodButton = (Button) v;
        String e = foodButton.getContentDescription().toString();
        //prevents action if game not running
        if(!isRunning)
        {
            return;
        }
        //prevents array out of bounds
        if(GameFunction.ingredients.size()==0)
        {
            dropIngredient();
            score--;
            setMessage(Integer.toString(score));
        }
        //if-else block for correct press
        //lettuce
        if(e.equals("lettuce")&&GameFunction.ingredients.get(0)==0)
        {
            GameFunction.removeIngredient();
            removeBottom();
            score++;
            setMessage(Integer.toString(score));
        }
        //cheese
        else if(e.equals("cheese")&&GameFunction.ingredients.get(0)==1)
        {
            GameFunction.removeIngredient();
            removeBottom();
            score++;
            setMessage(Integer.toString(score));
        }
        //patty
        else if(e.equals("patty")&&GameFunction.ingredients.get(0)==2)
        {
            GameFunction.removeIngredient();
            removeBottom();
            score++;
            setMessage(Integer.toString(score));
        }
        //bun
        else if(e.equals("bun")&&GameFunction.ingredients.get(0)==3)
        {
            GameFunction.removeIngredient();
            removeBottom();
            score++;
            setMessage(Integer.toString(score));

        }
        //punishment
        else
        {
            //add one more ingredient
            dropIngredient();
            //decrease score :O
            score--;
            setMessage(Integer.toString(score));
        }
    }
    //game board and GUI helping methods
    private void dropIngredient()
    {
        //drops ingredient into virtual board and UI
        GameFunction.dropIngredient();
        placeIngredient(GameFunction.ingredients.size()-1, GameFunction.ingredients.get(GameFunction.ingredients.size()-1));
        if(GameFunction.ingredients.size()>=8)
        {
            isRunning = false;
            doLose();
        }
    }
    //removes the bottom ingredient from the UI
    private void removeBottom()
    {
        for(int i=0; i<GameFunction.ingredients.size(); i++)
        {
            placeIngredient(i, GameFunction.ingredients.get(i));
        }
        placeIngredient(GameFunction.ingredients.size(), 4);
    }
    //places an specified ingredient at a specified position in the UI
    private void placeIngredient(int pos, int ingred)
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        UI.drawRect(20F, 300F, 180F, 400F, paint);
        //selects color
        switch (ingred)
        {
            case 0: paint.setColor(0xff99cc00);
                break;
            case 1: paint.setColor(0xffffbb33);
                break;
            case 2: paint.setColor(0xff7e4f4f);
                break;
            case 3: paint.setColor(0xffd1975d);
                break;
            case 4: paint.setColor(Color.WHITE);
                break;
        }
        //selects position
        switch(pos)
        {
            case 0: UI.drawRect(0F, 220F, 150F, 250F, paint);
                break;
            case 1: UI.drawRect(0F, 190F, 150F, 220F, paint);
                break;
            case 2: UI.drawRect(0F, 160F, 150F, 190F, paint);
                break;
            case 3: UI.drawRect(0F, 130F, 150F, 160F, paint);
                break;
            case 4: UI.drawRect(0F, 100F, 150F, 130F, paint);
                break;
            case 5: UI.drawRect(0F, 70F, 150F, 100F, paint);
                break;
            case 6: UI.drawRect(0F, 40F, 150F, 70F, paint);
                break;
            case 7: UI.drawRect(0F, 10F, 150F, 40F, paint);
                break;
        }
    }
    //sets UI view to white, removing all the ingredients
    private void removeAll()
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        UI.drawRect(0F, 0F, 150F, 250F, paint);
    }
    //run when lose
    private void doLose()
    {
        runOnUiThread(new Runnable()
                      {
                          @Override
                          public void run()
                          {
                              //reinstate reset button
                              Button rButton = (Button) findViewById(R.id.reset);
                              rButton.setVisibility(View.VISIBLE);
                              //lose face
                              setMessage(":(");
                              //set high score
                              TextView highScor= (TextView) findViewById(R.id.hsView);
                              if(highScore<score)
                              {
                                  highScor.setText("Highscore: "+score);
                                  highScore=score;
                              }

                          }
                      }
        );

    }
    //allows access to scoreboard easier
    private void setMessage(String msg)
    {
        display.setText(msg);
    }







    //cheat method for the losing "line"
    public void onCheat(View v)
        {
            //disable if not running
            if(!isRunning)
            {
                return;
            }
            Button b = (Button) v;
            //add 50 to score
            score+=50;
            //change color of cheat button
            b.setBackgroundColor(GameFunction.getNextColor());
            setMessage(Integer.toString(score));
    }
}

