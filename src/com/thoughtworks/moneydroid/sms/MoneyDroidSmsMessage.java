package com.thoughtworks.moneydroid.sms;


import android.telephony.gsm.SmsMessage;
import android.util.Log;

import com.thoughtworks.moneydroid.sms.handlers.PurchaseSmsHandler;
import com.thoughtworks.moneydroid.transaction.NullTransaction;
import com.thoughtworks.moneydroid.transaction.Transaction;

public final class MoneyDroidSmsMessage {

	private final SmsMessage sms;
	
	private enum SMS_SENDER{
		STANDARD_CHARTERED("DM-StanChrt"), HACK_FOR_TESTING("1234");
		
		private final String name;

		SMS_SENDER(String name){
			this.name = name;
		}

		public boolean isSenderOf(SmsMessage sms) {
			return name.equals(sms.getOriginatingAddress());
		}
	}

	public MoneyDroidSmsMessage(android.telephony.gsm.SmsMessage sms) {
		this.sms = sms;
	}
	
	public final boolean isFromMyBank() {
		Log.d("MessageSPAM", sms.getDisplayOriginatingAddress());
		Log.d("MessageSPAM", sms.getOriginatingAddress());
		
		boolean isFromMyBank = SMS_SENDER.STANDARD_CHARTERED.isSenderOf(sms) || SMS_SENDER.HACK_FOR_TESTING.isSenderOf(sms);
		Log.d("MessageSPAM", String.format("is from my bank: %s", String.valueOf(isFromMyBank)));
		return isFromMyBank;
	}

	public final boolean isAWithdrawal() {
		return isFromMyBank() && sms.getDisplayMessageBody().startsWith("You have withdrawn");
	}

	public final boolean isNotAWithdrawal() {
		return !isAWithdrawal();
	}

	public Transaction getTransaction() {
		if(isAWithdrawal())
			return new Withdrawal();
		
		if(isPurchase())
			return new PurchaseSmsHandler(this).createTransaction();
		
		return new NullTransaction();
	}

	public boolean isPurchase() {
		return isFromMyBank() && sms.getMessageBody().startsWith("You have done a debit purchase");
	}

	public String getMessage() {
		return sms.getMessageBody();
	}
}