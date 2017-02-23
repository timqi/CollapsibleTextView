package com.timqi.collapsibletextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Copyright 2017 Tim Qi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CollapsibleTextView extends TextView {

  private int mSuffixColor = 0xff0000ff;

  private int mCollapsedLines = 1;

  private boolean mSuffixTrigger = false;

  private CharSequence mText;

  private OnClickListener mCustomClickListener;

  private String
      mCollapsedText = " Show All",
      mExpandedText = " Hide";

  public CollapsibleTextView(Context context) {
    this(context, null);
  }

  public CollapsibleTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CollapsibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray attributes = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView, defStyleAttr, 0);

    mSuffixColor = attributes.getColor(R.styleable.CollapsibleTextView_suffixColor, 0xff0000ff);
    mCollapsedLines = attributes.getInt(R.styleable.CollapsibleTextView_collapsedLines, 1);
    mCollapsedText = attributes.getString(R.styleable.CollapsibleTextView_collapsedText);
    if (TextUtils.isEmpty(mCollapsedText)) mCollapsedText = " Show All";
    mExpandedText = attributes.getString(R.styleable.CollapsibleTextView_expandedText);
    if (TextUtils.isEmpty(mExpandedText)) mExpandedText = " Hide";
    mSuffixTrigger = attributes.getBoolean(R.styleable.CollapsibleTextView_suffixTrigger, false);

    this.mText = getText();
    setMovementMethod(LinkMovementMethod.getInstance());
    getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
  }

  private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener
      = new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
      } else {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }

      CollapsibleTextView.super.setOnClickListener(mClickListener);
      if (getLineCount() > mCollapsedLines) {
        handleState();
      }
    }
  };

  private OnClickListener mClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      if (!mSuffixTrigger) {
        handleState();
      }

      if (mCustomClickListener != null) {
        mCustomClickListener.onClick(v);
      }
    }
  };

  private ClickableSpan mClickSpanListener
      = new ClickableSpan() {
    @Override
    public void onClick(View widget) {
      if (mSuffixTrigger) {
        handleState();
      }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      super.updateDrawState(ds);
      ds.setUnderlineText(false);
    }
  };

  private void handleState() {
    if (TextUtils.isEmpty(mText)) return;

    String note = mText.toString(), suffix;
    if (isExpanded()) {
      if (mCollapsedLines - 1 < 0) {
        throw new RuntimeException("CollapsedLines must equal or greater than 1");
      }
      int lineEnd = getLayout().getLineEnd(mCollapsedLines - 1);
      suffix = mCollapsedText;
      int newEnd = lineEnd - suffix.length() - 1;
      int end = newEnd > 0 ? newEnd : lineEnd;

      TextPaint paint = getPaint();
      int maxWidth = mCollapsedLines * (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
      while (paint.measureText(note.substring(0, end) + suffix) > maxWidth)
        end--;
      note = note.substring(0, end);
    } else {
      suffix = mExpandedText;
    }

    SpannableString str = new SpannableString(note + suffix);
    str.setSpan(new ForegroundColorSpan(mSuffixColor),
        note.length(),
        note.length() + suffix.length(),
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
    if (mSuffixTrigger) {
      str.setSpan(mClickSpanListener,
          note.length(),
          note.length() + suffix.length(),
          SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    setText(str);
  }

  public boolean isExpanded() {
    return getLineCount() > mCollapsedLines;
  }

  public void setCollapsedString(String str) {
    this.mText = str;
    getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    setText(mText);
  }

  @Override
  public void setOnClickListener(OnClickListener l) {
    mCustomClickListener = l;
  }

  public int getSuffixColor() {
    return mSuffixColor;
  }

  public void setSuffixColor(int mSuffixColor) {
    this.mSuffixColor = mSuffixColor;
  }

  public int getCollapsedLines() {
    return mCollapsedLines;
  }

  public void setCollapsedLines(int mCollapsedLines) {
    this.mCollapsedLines = mCollapsedLines;
  }

  public boolean isSuffixTrigger() {
    return mSuffixTrigger;
  }

  public void setSuffixTrigger(boolean mSuffixTrigger) {
    this.mSuffixTrigger = mSuffixTrigger;
  }

  public String getCollapsedText() {
    return mCollapsedText;
  }

  public void setCollapsedText(String mCollapsedText) {
    this.mCollapsedText = mCollapsedText;
  }

  public String getExpandedText() {
    return mExpandedText;
  }

  public void setExpandedText(String mExpandedText) {
    this.mExpandedText = mExpandedText;
  }
}
