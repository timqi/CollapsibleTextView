package com.timqi.collapsibletextview.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.timqi.collapsibletextview.CollapsibleTextView;

public class MainActivity extends AppCompatActivity {

  private CollapsibleTextView mTextNormal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mTextNormal = (CollapsibleTextView) findViewById(R.id.normal);
    mTextNormal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
      }
    });

    findViewById(R.id.showShort).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTextNormal.setCollapsedString("Found a workaround that is quite straight forward. Define ClickableSpan on all the text areas");
      }
    });


    findViewById(R.id.showLong).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTextNormal.setCollapsedString("Found a workaround that is quite straight forward. Define ClickableSpan on all the text areas that are not part of the links and handle the click on them as if the text view was clicked");
      }
    });


  }
}
