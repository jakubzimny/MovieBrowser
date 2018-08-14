package com.jzimny.moviebrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchBarActivity extends AppCompatActivity {

    public final static String EXTRA_TITLE = "com.jzimny.moviebrowser.TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
    }

    public void search(View view){
        Intent intent = new Intent(this,ResultListActivity.class);
        EditText editText = findViewById(R.id.titleID);
        String title = editText.getText().toString();
        intent.putExtra(EXTRA_TITLE, title);
        startActivity(intent);
    }
}
