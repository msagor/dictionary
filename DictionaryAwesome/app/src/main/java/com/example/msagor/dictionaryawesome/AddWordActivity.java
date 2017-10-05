package com.example.msagor.dictionaryawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.PrintStream;

import stanford.androidlib.SimpleActivity;

public class AddWordActivity extends SimpleActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        setTraceLifecycle(true);

        Intent intent = getIntent();

        //String text = intent.getStringExtra("initialtext");  //FooBar
        //$TV(R.id.new_word).setText(text);    //just receiving an extra text

    }

    public void addThisWordClick(View view) {
        //add a given word and defn to the dictionary
        String newWord = $ET(R.id.new_word).getText().toString();  //getting the input from the xml EditText space
        String newDefn = $ET(R.id.new_defn).getText().toString();

        PrintStream output = new PrintStream(openFileOutput("added_word.txt", MODE_PRIVATE));
        output.println(newWord+"\t"+newDefn);
        output.close();

        //go back to start_activity AFTER FINISHed ADDING THE WORD-me
        Intent goback = new Intent();
        goback.putExtra("newword", newWord);
        goback.putExtra("newdefn", newDefn);
        setResult(RESULT_OK, goback);
        finish();




    }
}
