package com.srirachapps.smartycalc;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StandardFragment extends Fragment implements OnClickListener {

	private static final int ADD = 0, SUBTRACT = 1, MULTIPLY = 2, DIVIDE = 3, SQUARE = 4,
							 SQRT = 5, PERCENT = 6, INVERT = 7, NEGATE = 8;
	
	private Button mMc, mMr, mMs, mMp, mMm, 				// Row 1
				mSqrt, mSquared, mPercent, mInvert, mNegate, // Row 2
				mSeven, mEight, mNine, mDel, mAc, 			// Row 3
				mFour, mFive, mSix, mMultiply, mDivide, 	// Row 4
				mOne, mTwo, mThree, mAdd, mSubtract, 		// Row 5
				mZero, mDecimal, mEquals; 					// Row 6 (Bottom Row)

	private Double mTotal, mLhs, mRhs;
	
	private int mFunctionPressed;
	
	private Equations mEquations; // Used to call equations on numbers.
	
	private Vibrator mVb;

	private TextView mNumberPanel, mFunctionPanel, mMemoryPanel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_standard, container, false);
		
		initializeResources(v); // Initialize the resources and onClickListeners.
		initializeCalculator(); // Initialize the calculator to a default starting position.
		
		return v;
	}
	
	/*
	 * Every time a digit button is pressed this is called.
	 * @param: Integer digit: The digit that was pressed.
	 */
	private void digitPressed(Integer digit) {
		// Check if the LHS or RHS needs to be updated.
		if(mFunctionPressed < 0) { // LHS
			if(mLhs == null)
				mLhs = (double) digit;
			else
				mLhs = mLhs*10+digit;
			setNumberPanel(mLhs);
		}
		else { // RHS
			if(mRhs == null)
				mRhs = (double) digit;
			else
				mRhs = mRhs*10+digit;
			setNumberPanel(mRhs);
		}
	}
	
	/*
	 * This is called every time a function button is pressed.
	 * @param: int function: uses function enum values.
	 * 	   -1 == no function
	 * 		0 == add
	 * 		1 == subtract
	 * 		2 == multiply
	 * 		3 == divide
	 * 		4 == square
	 * 		5 == sqrt
	 * 		6 == percent
	 * 		7 == invert
	 * 		8 == negate
	 */
	private void setFunctionPressed(int function) {
		mFunctionPressed = function;
	}
	
	/*
	 *	This is called every time equals is pressed.
	 * 	   -1 == no function
	 * 		0 == add
	 * 		1 == subtract
	 * 		2 == multiply
	 * 		3 == divide
	 * 		4 == square
	 * 		5 == sqrt
	 * 		6 == percent
	 * 		7 == invert
	 * 		8 == negate
	 */
	private void equalsPressed() {
		if(mLhs == null) { // If no digit has been pressed, leave display as 0.
			mLhs = 0.;
			return;
		}
		switch(mFunctionPressed) {
		case -1:
			break;
		case 0:	// Add
			if(mRhs == null) // If no RHS digit has been specified reuse LHS digit.
				mTotal = mEquations.add(mLhs, mLhs);
			else // Add the RHS to the LHS
				mTotal = mEquations.add(mLhs, mRhs);
			setNumberPanel(mTotal);
			mLhs = mTotal;
			break;
		case 1: // Subtract
			if(mRhs == null) // If no RHS digit has been specified reuse LHS digit.
				mTotal = mEquations.subtract(mLhs, mLhs);
			else // Add the RHS to the LHS
				mTotal = mEquations.subtract(mLhs, mRhs);
			setNumberPanel(mTotal);
			mLhs = mTotal;
			break;
		case 2: // Multiply
			if(mRhs == null) // If no RHS digit has been specified reuse LHS digit.
				mTotal = mEquations.multiply(mLhs, mLhs);
			else // Add the RHS to the LHS
				mTotal = mEquations.multiply(mLhs, mRhs);
			setNumberPanel(mTotal);
			mLhs = mTotal;
			break;
		case 3: // Divide
			if(mRhs == null) // If no RHS digit has been specified reuse LHS digit.
				if(mLhs != 0) // Make sure not dividing by zero.
					mTotal = mEquations.divide(mLhs, mLhs);
				else {
					mNumberPanel.setText("Cannot divide by zero.");
					break;
				}
			else // Add the RHS to the LHS
				if(mRhs != 0) // Make sure not dividing by zero.
					mTotal = mEquations.divide(mLhs, mRhs);
				else {
					mNumberPanel.setText("Cannot divide by zero.");
					break;
				}
			setNumberPanel(mTotal);
			mLhs = mTotal;
			break;
		case 4: // Square
			mTotal = mEquations.squared(mLhs);
			mLhs = mTotal;
			setNumberPanel(mTotal);
			break;
		case 5: // Sqrt
			mTotal = mEquations.sqrt(mLhs);
			mLhs = mTotal;
			setNumberPanel(mTotal);
		case 6: // Percent
			// TODO:
			break;
		case 7: // Invert
			if(mLhs == 0) break; // Make sure not inverting 0.
			mTotal = mEquations.recip(mLhs);
			mLhs = mTotal;
			setNumberPanel(mTotal);
			break;
		case 8: // Negate
			mTotal = mEquations.negate(mLhs);
			mLhs = mTotal;
			setNumberPanel(mTotal);
			break;
		}
	}
	
	private void allClearPressed() {
		// Set the computation values to default 0.
		mTotal = 0.;
		mLhs = null;
		mRhs = null;
		
		// Set the number display to mTotal.
		setNumberPanel(mTotal);
		
		// No function has been pressed yet.
		mFunctionPressed = -1;
	}
	
	/*
	 * This sets the number panel display to number.
	 * @param: Double number: Sets the number panel to this.
	 */
	private void setNumberPanel(Double number) {
		// TODO: format number.
		mNumberPanel.setText(number.toString());
	}

	/*
	 * This sets all values of calculator to the default starting position.
	 */
	private void initializeCalculator() {
		// Create a new Equations object.
		mEquations = new Equations();
		
		// Set the computation values to default 0.
		mTotal = 0.;
		mLhs = null;
		mRhs = null;
		
		// Set the number display to mTotal.
		setNumberPanel(mTotal);
		
		// No function has been pressed yet.
		mFunctionPressed = -1;
	}
	
	/*
	 * Initializes the resources as Java objects.
	 * @param: View v = the parent view for the entire display.
	 */
	private void initializeResources(View v) {
		//********************** Display Panel *********************************************
		// Number Display
		mNumberPanel = (TextView) v.findViewById(R.id.panel_number);
		
		// Function Display
		mFunctionPanel = (TextView) v.findViewById(R.id.panel_function);
		mFunctionPanel.setVisibility(View.INVISIBLE);
		
		// Memory Display
		mMemoryPanel = (TextView) v.findViewById(R.id.panel_memory);
		mMemoryPanel.setVisibility(View.INVISIBLE);
		
		//********************** Buttons ***************************************************
		// Row 1:
		mMc = (Button) v.findViewById(R.id.standard_mem_clear);
		mMc.setOnClickListener(this);

		mMr = (Button) v.findViewById(R.id.standard_mem_return);
		mMr.setOnClickListener(this);

		mMs = (Button) v.findViewById(R.id.standard_mem_store);
		mMs.setOnClickListener(this);

		mMp = (Button) v.findViewById(R.id.standard_mem_plus);
		mMp.setOnClickListener(this);

		mMm = (Button) v.findViewById(R.id.standard_mem_subtract);
		mMm.setOnClickListener(this);

		// Row 2:
		mSqrt = (Button) v.findViewById(R.id.standard_sqrt);
		mSqrt.setOnClickListener(this);

		mSquared = (Button) v.findViewById(R.id.standard_squared);
		mSquared.setOnClickListener(this);

		mPercent = (Button) v.findViewById(R.id.standard_percent);
		mPercent.setOnClickListener(this);

		mInvert = (Button) v.findViewById(R.id.standard_invert);
		mInvert.setOnClickListener(this);

		mNegate = (Button) v.findViewById(R.id.standard_negate);
		mNegate.setOnClickListener(this);

		// Row 3:
		mSeven = (Button) v.findViewById(R.id.standard_7);
		mSeven.setOnClickListener(this);

		mEight = (Button) v.findViewById(R.id.standard_8);
		mEight.setOnClickListener(this);

		mNine = (Button) v.findViewById(R.id.standard_9);
		mNine.setOnClickListener(this);

		mDel = (Button) v.findViewById(R.id.standard_delete);
		mDel.setOnClickListener(this);

		mAc = (Button) v.findViewById(R.id.standard_all_clear);
		mAc.setOnClickListener(this);

		// Row 4:
		mFour = (Button) v.findViewById(R.id.standard_4);
		mFour.setOnClickListener(this);

		mFive = (Button) v.findViewById(R.id.standard_5);
		mFive.setOnClickListener(this);

		mSix = (Button) v.findViewById(R.id.standard_6);
		mSix.setOnClickListener(this);

		mMultiply = (Button) v.findViewById(R.id.standard_multiply);
		mMultiply.setOnClickListener(this);

		mDivide = (Button) v.findViewById(R.id.standard_divide);
		mDivide.setOnClickListener(this);

		// Row 5:
		mOne = (Button) v.findViewById(R.id.standard_1);
		mOne.setOnClickListener(this);

		mTwo = (Button) v.findViewById(R.id.standard_2);
		mTwo.setOnClickListener(this);

		mThree = (Button) v.findViewById(R.id.standard_3);
		mThree.setOnClickListener(this);

		mAdd = (Button) v.findViewById(R.id.standard_add);
		mAdd.setOnClickListener(this);

		mSubtract = (Button) v.findViewById(R.id.standard_subtract);
		mSubtract.setOnClickListener(this);

		// Row 6:
		mZero = (Button) v.findViewById(R.id.standard_0);
		mZero.setOnClickListener(this);

		mDecimal = (Button) v.findViewById(R.id.standard_decimal);
		mDecimal.setOnClickListener(this);

		mEquals = (Button) v.findViewById(R.id.standard_equals);
		mEquals.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {		
		//mVb.vibrate(10);
		switch (v.getId()) {
		// Row 1
		case R.id.standard_mem_clear:
			
			break;
		case R.id.standard_mem_return:
			
			break;
		case R.id.standard_mem_store:
			
			break;
		case R.id.standard_mem_plus:
			
			break;
		case R.id.standard_mem_subtract:
			
			break;

		// Row 2:
		case R.id.standard_sqrt:
			setFunctionPressed(SQRT);
			equalsPressed();
			break;
		case R.id.standard_squared:
			setFunctionPressed(SQUARE);
			equalsPressed();
			break;
		case R.id.standard_percent:
			setFunctionPressed(PERCENT);
			break;
		case R.id.standard_invert:
			setFunctionPressed(INVERT);
			equalsPressed();
			break;
		case R.id.standard_negate:
			setFunctionPressed(NEGATE);
			equalsPressed();
			break;

		// Row 3:
		case R.id.standard_7:
			digitPressed(7);
			break;
		case R.id.standard_8:
			digitPressed(8);
			break;
		case R.id.standard_9:
			digitPressed(9);
			break;
		case R.id.standard_delete:
			
			break;
		case R.id.standard_all_clear:
			allClearPressed();
			break;

		// Row 4:
		case R.id.standard_4:
			digitPressed(4);
			break;
		case R.id.standard_5:
			digitPressed(5);
			break;
		case R.id.standard_6:
			digitPressed(6);
			break;
		case R.id.standard_multiply:
			setFunctionPressed(MULTIPLY);
			break;
		case R.id.standard_divide:
			setFunctionPressed(DIVIDE);
			break;

		// Row 5:
		case R.id.standard_1:
			digitPressed(1);
			break;
		case R.id.standard_2:
			digitPressed(2);
			break;
		case R.id.standard_3:
			digitPressed(3);
			break;
		case R.id.standard_add:
			setFunctionPressed(ADD);
			break;
		case R.id.standard_subtract:
			setFunctionPressed(SUBTRACT);
			break;

		// Row 6:
		case R.id.standard_0:
			digitPressed(0);
			break;
		case R.id.standard_decimal:
			
			break;
		case R.id.standard_equals:
			equalsPressed();
			break;
		}
	}
}