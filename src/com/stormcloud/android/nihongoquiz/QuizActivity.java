package com.stormcloud.android.nihongoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_IS_CHEATER = "isCheater";
	
	private Button mTrueButton;
	private Button mFalseButton;
	
	private ImageButton mPrevButton;
	private ImageButton mNextButton;
	
	private Button mCheatButton;
	
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_beauty_parlor, false),
			new TrueFalse(R.string.question_to_sweep, true),
			new TrueFalse(R.string.question_tableware, false),
			new TrueFalse(R.string.question_to_gather, true),
			new TrueFalse(R.string.question_to_get_dirty, true),
			new TrueFalse(R.string.question_drawer, true),
	};
	
	private int mCurrentIndex = 0;
	private boolean mIsCheater = false;
	
	private void updateQuestion() {
		//Log.d(TAG,"Updating question text for question #" + mCurrentIndex, new Exception());
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		
		if (mIsCheater) {
			messageResId = R.string.judgement_toast;
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}
		
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
		
			ActionBar actionBar = getActionBar();
			actionBar.setSubtitle("Bodies of Water");
		}
		
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
		}
		
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clicking TextView goes to next question
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		
		
		//region Buttons
		
		mTrueButton = (Button)findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Checks to see if answer is correctly true
				checkAnswer(true);
			}
		});
		
		mFalseButton = (Button)findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Checks to see if answer is correctly false
				checkAnswer(false);
			}
		});
		
		mPrevButton = (ImageButton)findViewById(R.id.prev_button);
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Goes to the previous question in array
				if (mCurrentIndex == 0){
					mCurrentIndex = mQuestionBank.length;
				}
				mCurrentIndex--;
				updateQuestion();
			}
		});
		
		mNextButton = (ImageButton)findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button)findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Start CheatActivity
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,  answerIsTrue);
				startActivityForResult(i, 0);
			}
		});
		
		updateQuestion();
	}	// End of onCreate(Bundle)
	
		//endregion Buttons
	
	//region LoggingMethods
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	//endregion LoggingMethods
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "OnSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
	}
}
