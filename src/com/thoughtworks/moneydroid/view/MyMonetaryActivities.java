package com.thoughtworks.moneydroid.view;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.thoughtworks.R;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker.Expense;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker.Vendor;

public class MyMonetaryActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView myMonetaryViews = (TextView) findViewById(R.id.my_monetary_view);

		String[] projections = new String[] { Expense._DATE, Expense._AMOUNT, Expense._BALANCE, Expense._VENDOR_ID, Vendor._NAME };
		Cursor cursor = managedQuery(ExpenseTracker.CONTENT_URI, projections, null, null, Expense._DATE + " DESC");

		if (cursor.moveToFirst()) {
			myMonetaryViews.append(String.format("found %d transactions:",cursor.getCount()));
			myMonetaryViews.append("-----------------------------------------");
			do {
				myMonetaryViews.append(String.format("Date:%s|", cursor.getString(cursor.getColumnIndex(Expense._DATE))));
				myMonetaryViews.append(String.format(" Amount:%s\n", cursor.getString(cursor.getColumnIndex(Expense._AMOUNT))));
				myMonetaryViews.append(String.format(" Vendor:%s\n", cursor.getString(cursor.getColumnIndex(Vendor._NAME))));
			} while (cursor.moveToNext());
		}
	}
}