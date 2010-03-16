package com.thoughtworks.moneydroid.view;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.thoughtworks.R;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker.Expense;
import com.thoughtworks.moneydroid.transaction.ExpenseTracker.Vendor;

public class MyMonetaryActivities extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showAsList();
		registerForContextMenu(getListView());
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 1:
			Toast.makeText(this, "You clicked Categorize", 2000).show();
			return true;
		case 2:
			Toast.makeText(this, "You clicked Wassup", 2000).show();
			return true;
			default:
				
				return super.onContextItemSelected(item); 
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		
		menu.add(ContextMenu.NONE,1,ContextMenu.NONE, "Categorize");
		menu.add(ContextMenu.NONE,2,ContextMenu.NONE, "Wassup");
		
	}

	private void showAsList() {
		String[] projections = new String[] { Expense._EXPENSE_ID_WITH_TABLE, Expense._DATE, Expense._AMOUNT, Expense._BALANCE, Expense._VENDOR_ID, Vendor._NAME };
		Cursor cursor = managedQuery(ExpenseTracker.CONTENT_URI, projections, null, null, Expense._DATE + " DESC");

		setListAdapter(new SimpleCursorAdapter(this, R.layout.main, cursor, new String[] { Vendor._NAME, Expense._DATE , Expense._AMOUNT }, new int[] { R.id.vendor, R.id.amount, R.id.purchaseDate}));
		getListView().setTextFilterEnabled(true);
	}
	
	
}