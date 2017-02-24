package com.timqi.collapsibletextview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.timqi.collapsibletextview.CollapsibleTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private CollapsibleTextView mText;

  private String
      mShortText = "I lost my shoe.",
      mLongText = "I swirl, dip, leap and step, To the rhythmic, rolling, reverberating melody, Of gleaming copper, and polished bronze; A shivering note, long held in the air. The deep, monotonous, shivering song, of shining, gleaming, chiming bells. I must leave before the twelfth gong. Prepare my pumpkin. I lost my shoe.";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mText = (CollapsibleTextView) findViewById(R.id.normal);
    mText.setOnClickListener(this);
    mText.setFullString(mLongText);
    findViewById(R.id.showShort).setOnClickListener(this);
    findViewById(R.id.showLong).setOnClickListener(this);
    findViewById(R.id.collapsed).setOnClickListener(this);
    findViewById(R.id.expanded).setOnClickListener(this);
    findViewById(R.id.redSuffix).setOnClickListener(this);
    findViewById(R.id.blueSuffix).setOnClickListener(this);
    findViewById(R.id.showLong).setOnClickListener(this);
    findViewById(R.id.collapsed1line).setOnClickListener(this);
    findViewById(R.id.collapsed2lines).setOnClickListener(this);
    findViewById(R.id.collapsedTextShowAll).setOnClickListener(this);
    findViewById(R.id.collapsedTextShowText).setOnClickListener(this);
    findViewById(R.id.expandedTextHide).setOnClickListener(this);
    findViewById(R.id.expandedTextHideText).setOnClickListener(this);
    findViewById(R.id.suffixTrigger).setOnClickListener(this);
    findViewById(R.id.fullTextTrigger).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.normal:
        Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
        break;
      case R.id.showShort:
        mText.setFullString(mShortText);
        break;
      case R.id.showLong:
        mText.setFullString(mLongText);
        break;
      case R.id.expanded:
        mText.setExpanded(true);
        break;
      case R.id.collapsed:
        mText.setExpanded(false);
        break;
      case R.id.redSuffix:
        mText.setSuffixColor(0xffff0000);
        break;
      case R.id.blueSuffix:
        mText.setSuffixColor(0xff0000ff);
        break;
      case R.id.collapsed1line:
        mText.setCollapsedLines(1);
        break;
      case R.id.collapsed2lines:
        mText.setCollapsedLines(2);
        break;
      case R.id.suffixTrigger:
        mText.setSuffixTrigger(true);
        break;
      case R.id.fullTextTrigger:
        mText.setSuffixTrigger(false);
        break;
      case R.id.collapsedTextShowAll:
        mText.setCollapsedText(" Show All");
        break;
      case R.id.collapsedTextShowText:
        mText.setCollapsedText(" Show Text");
        break;
      case R.id.expandedTextHideText:
        mText.setExpandedText(" Hide Text");
        break;
      case R.id.expandedTextHide:
        mText.setExpandedText(" Hide");
        break;
    }
  }
}
