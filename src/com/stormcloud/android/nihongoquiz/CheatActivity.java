package com.stormcloud.android.nihongoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	private static final String TAG = "CheatActivity";
	
	public static final String EXTRA_ANSWER_IS_TRUE =
			"com.stormcloud.android.nihongoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = 
			"com.stormcloud.android.nihongoquiz.answer_is_shown";
	
	public static final String KEY_ANSWER_WAS_SHOWN = "answerWasShown";
	public static final String KEY_ANSWER_IS_TRUE = "answerIsTrue";
	
	private boolean mAnswerIsTrue;
	private boolean mAnswerWasShown = false;
	
	private TextView mAnswerTextView;
	private TextView mApiTextView;
	private Button mShowAnswer;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate() called");
		setContentView(R.layout.activity_cheat);
		
		if (savedInstanceState != null) {
			mAnswerWasShown = savedInstanceState.getBoolean(KEY_ANSWER_WAS_SHOWN, false);
						
			if (mAnswerWasShown) {
				mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_IS_TRUE, false);
				
				mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				
				setAnswerShownResult(true);
			}	
			
		}
					
		mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {					
			@Override
			public void onClick(View v) {
				
				mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
				mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
				
				mAnswerWasShown = true;
				
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
		
				setAnswerShownResult(true);
			}
		});
		
		mApiTextView = (TextView)findViewById(R.id.apiTextView);
	    mApiTextView.setText(android.os.Build.VERSION.SDK);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "OnSaveInstanceState");
		savedInstanceState.putBoolean(KEY_ANSWER_WAS_SHOWN, mAnswerWasShown);
		savedInstanceState.putBoolean(KEY_ANSWER_IS_TRUE, mAnswerIsTrue);
	}
}
