/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;

    private TextView text ;
    private TextView label;
    private boolean userTurn = false;
    private String word="";
    private Random random = new Random();
    private InputStream is = null;
    SimpleDictionary sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            is=assetManager.open("words.txt");
            SimpleDictionary sd=new SimpleDictionary(is);
            } catch (IOException e) {
            e.printStackTrace();
        }
       onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn =true;// random.nextBoolean();
        text = (TextView) findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);
        text.setText("");

        if (userTurn) {
            label.setText(USER_TURN);



        } else {
            label.setText(COMPUTER_TURN);
            //computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        label.setText(COMPUTER_TURN);
        AssetManager assetManager = getAssets();

        // Do computer turn stuff then make it the user's turn again
        if(word.length()>=4&&sd.isWord(word))
        {
            label.setText("You lose the game,this is a word");
        }
        else if(sd.getAnyWordStartingWith(word)==null)
        {
            label.setText("You lose the game,no word can be formed");
        }
        else if(sd.getAnyWordStartingWith(word)!=null)
        {
            String temp1=sd.getAnyWordStartingWith(word);
            word=word+temp1.substring(word.length(),word.length()+1);
            text.setText(word);
        }
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        //return pressedKey;
            text = (TextView) findViewById(R.id.ghostText);
            label = (TextView) findViewById(R.id.gameStatus);
            char pressedKey= (char)event.getUnicodeChar();
            String word1;
            if ((pressedKey >='a' && pressedKey <='z') || (pressedKey >='A' && pressedKey <='Z') )
        {

            word1=text.getText().toString()+pressedKey;
            text.setText(word1);
            Log.i("CHECK1", "onKeyUp: does it work");
            if(dictionary.isWord(word1)) {label.setText(word1);}
            //computerTurn();
            Log.i("CHECK2", "onKeyUp: does it work");
            return true;
        }else;
            return super.onKeyUp(keyCode, event);
    }
}
