package com.example.msagor.dictionaryawesome;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class DictionaryActivity extends SimpleActivity {


    //created a map-me
    private Map<String, String> dictionary = new HashMap<>();
    private List<String> words = new ArrayList<>();
    MediaPlayer mp;
    private int points = 0;


    //scanner reads data from raw resource and puts it into the dictionary map-me
    public void readFileData(){
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords));
        while(scan.hasNextLine()){
            String line1 = scan.nextLine();
            String[] parts1 = line1.split("\t");  //    "\t" means tab
            dictionary.put(parts1[0], parts1[1]);
            words.add(parts1[0]);

            //try reading added_word.txt file and adding those words in the map
            try{
                Scanner scan2 = new Scanner(openFileInput("added_word.txt"));
                while(scan.hasNextLine()) {
                    String line2 = scan2.nextLine();
                    String[] parts2 = line2.split("\t");  //    "\t" means tab
                    dictionary.put(parts2[0], parts2[1]);
                    words.add(parts2[0]);
                }
            }catch(Exception exc){
                //do nothing
            }
        }
    }


    //pick the words...find five random def of the words...show all of them on screen
    public void chooseWords(){
        Random randy = new Random();    //random object
        int randomIndex = randy.nextInt(words.size());   //random number
        String theWord = words.get(randomIndex);      //pick any random word-correct word
        String theDefn = dictionary.get(theWord);     // pick the defn for the chosen random word-correct answer

        List<String> defns = new ArrayList<>(dictionary.values());   //make a list of size dictionary hashmap
        defns.remove(theDefn);      //remove the correct defn from the list
        Collections.shuffle(defns); //give the defns list a shuffle
        defns = defns.subList(0,4); //cut the defns list into four item
        defns.add(theDefn);         //add the correct defn in the defns list
        Collections.shuffle(defns); //give it another shuffle

        $TV(R.id.the_word).setText(theWord);  //assign corect word to the the_word id
        SimpleList.with(this).setItems(R.id.word_list, defns);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        setTraceLifecycle(true);

        //call this function on start-me
        readFileData();  //read words from text files-me
        chooseWords();   //choose five random words-me

        //media file - me
        mp = MediaPlayer.create(this, R.raw.jeopardy);
        mp.start();

        //put the dictionary words in adapter to be viewed in list-me
        ListView list = $(R.id.word_list);

        //this code runs when each word pressed-me
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                //look up definition of the word clicked and display as a toast
                String defnClicked = parent.getItemAtPosition(position).toString();
                String correctWord = $TV(R.id.the_word).getText().toString();
                String correctDefn = dictionary.get(correctWord);
                if(defnClicked.equals(correctDefn)){
                    toast("Hurray!!!..awesome");
                    points++;
                }else{
                    toast(":-( Loooolol duuh!!!");
                    points--;
                }
                chooseWords();
            }
        });
    }

    //AddWord button during dictionary game - me
    public void addAwordClick(View view) {
        //go to the Add Word activity
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);

    }


    @Override //-me
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override //-me
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    //saves the instance of the activities current status
    @Override //-me
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Points", points);
    }

    //restores the instance of the activities current status
    @Override //-me
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getInt("Points");
    }


}

