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

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private Random random=new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        Log.i("CHECK", "isWord: does it work");return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix.equals(""))
        {
            int rstart=random.nextInt(words.size());
            return words.get(rstart);
        }
        else
        {
            int beg=0,end=words.size()-1;
            while(beg<=end)
            {
                int mid=(beg+end)/2;
                String temp2=words.get(mid);
                String temp2ss=temp2.substring(0,prefix.length()-1);
                if(prefix.compareToIgnoreCase(temp2ss)<0)
                {
                    end=mid-1;
                } else if (prefix.compareToIgnoreCase(temp2ss) > 0) {

                    beg=mid+1;
                } else
                {
                    if(!prefix.equals(temp2))
                    {   return temp2;}
                    else
                    {
                        String temp3=words.get(mid+1);
                        String temp3ss=temp3.substring(0,prefix.length()-1);
                        if(temp3ss.equals(prefix))
                        {
                            return temp3;
                        }
                        else
                        {
                            return null;
                        }
                    }

                }

            }
        }

        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}
