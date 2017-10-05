package com.example.msagor.dictionaryawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Dictionary;

import stanford.androidlib.SimpleActivity;

public class StartMenuActivity extends SimpleActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
        setTraceLifecycle(true);
    }

    public void playTheGameClick(View view) {
        //dictionary activity
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);

    }

    public void addANewWordClick(View view) {
        //add a word activity
        Intent intent = new Intent(this, AddWordActivity.class);
        //intent.putExtra("initialtext", "FooBar");    //just passing an extra text
        startActivityForResult(intent, 1234);
        
        

    }

     //finishes and comes back to me
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,  resultCode, intent);

        if(requestCode == 1234){
            //intent
            String newWord = intent.getStringExtra("newword");
            String newDefn = intent.getStringExtra("newdefn");
            toast("You added the word: " + newWord + " with definition: " + newDefn);
        }

    }

}
