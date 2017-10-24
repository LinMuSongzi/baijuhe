package com.mr.huang.common;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.mr.huang.framwork.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：输入法工具
 */
public class InputUtil {

	public static final String TAG = InputUtil.class.getSimpleName();

	// ----------------------------------------------------------------------------------------------------

	/**
	 * 功能：弹出软键盘
	 */
	public static void showKeyboard(EditText editText) {
		Log.d(TAG, "showKeyboard: // ---------------------------------------------");
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager manager = (InputMethodManager) AppManager.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * 功能：弹出软键盘
	 */
	public static void showKeyboardForced(EditText editText) {
		Log.d(TAG, "showKeyboardForced: // ---------------------------------------------");
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		InputMethodManager manager = (InputMethodManager) AppManager.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * 功能：隐藏软键盘
	 */
	public static void closeKeyboard(Activity activity) {
		Log.d(TAG, "closeKeyboard: // ---------------------------------------------");
		try {
			InputMethodManager manager = (InputMethodManager) AppManager.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			IBinder token = activity.getCurrentFocus().getWindowToken();
			manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 功能：隐藏软键盘
	 */
	public static void closeKeyboardMethod(Activity activity) {
		Log.d(TAG, "closeKeyboardMethod: // ---------------------------------------------");
		InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder token = activity.getCurrentFocus().getWindowToken();
		manager.hideSoftInputFromInputMethod(token, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	// ----------------------------------------------------------------------------------------------------

	public static void restartKeyboard(EditText editText) {
		Log.d(TAG, "restartKeyboard: // ---------------------------------------------");
		InputMethodManager manager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.restartInput(editText);
	}

	public static void toggleKeyboard(Context context, int showFlags, int hideFlags) {
		Log.d(TAG, "restartKeyboard: // ---------------------------------------------");
		InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.toggleSoftInput(showFlags, hideFlags);
	}

	public static void toggleKeyboard(Context context, Activity activity, int showFlags, int hideFlags) {
		Log.d(TAG, "toggleKeyboard: // ---------------------------------------------");
		InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder token = activity.getCurrentFocus().getWindowToken();
		manager.toggleSoftInputFromWindow(token, showFlags, hideFlags);
	}

	// ----------------------------------------------------------------------------------------------------

	/**
	 * 功能：判断是否隐藏键盘
	 */
	public static boolean isHiden(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param text 原来词组
	 * @param specifiedTexts 被匹配的关键字
	 * @param color
     * @return
     */
	public static SpannableStringBuilder setSpecifiedTextsColor(String text, String specifiedTexts, int color) {
		List<Integer> sTextsStartList = new ArrayList<>();

		int sTextLength = specifiedTexts.length();
		String temp = text;
		int lengthFront = 0;//记录被找出后前面的字段的长度
		int start = -1;
		do {
			start = temp.indexOf(specifiedTexts);

			if (start != -1) {
				start = start + lengthFront;
				sTextsStartList.add(start);
				lengthFront = start + sTextLength;
				temp = text.substring(lengthFront);
			}

		} while (start != -1);

		SpannableStringBuilder styledText = new SpannableStringBuilder(text);
		for (Integer i : sTextsStartList) {
			styledText.setSpan(
					new ForegroundColorSpan(color),
					i,
					i + sTextLength,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return styledText;
	}

}
