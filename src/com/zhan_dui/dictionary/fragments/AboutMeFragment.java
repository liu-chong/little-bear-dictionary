package com.zhan_dui.dictionary.fragments;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.umeng.fb.UMFeedbackService;
import com.zhan_dui.dictionary.R;
import com.zhan_dui.dictionary.utils.Constants;

public class AboutMeFragment extends SherlockFragment implements
		OnClickListener {

	private ActionBar mActionBar;
	private static Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SherlockFragmentActivity sherlockFragmentActivity = (SherlockFragmentActivity) getActivity();
		mActionBar = sherlockFragmentActivity.getSupportActionBar();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.about, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mContext = view.getContext();
		mActionBar.setTitle(R.string.about);
		Button weiboButton = (Button) view.findViewById(R.id.weibo);
		Button feedbackButton = (Button) view.findViewById(R.id.feedback);
		weiboButton.setOnClickListener(this);
		feedbackButton.setOnClickListener(this);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Boolean followed = sharedPreferences.getBoolean("follow", false);
		if (followed) {
			weiboButton.setText("感谢您的关注");
			weiboButton.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weibo:
			try {
				Class.forName("com.weibo.sdk.android.sso.SsoHandler");
			} catch (ClassNotFoundException e) {
			}

			break;
		case R.id.feedback:
			UMFeedbackService.openUmengFeedbackSDK(getActivity());
			break;
		default:
			break;
		}
	}

	private static Handler FollowHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(mContext, "关注成功", Toast.LENGTH_SHORT).show();
				Editor editor = PreferenceManager.getDefaultSharedPreferences(
						mContext).edit();
				editor.putBoolean("follow", true);
				editor.commit();
				break;
			case -1:
				Toast.makeText(mContext, "关注失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
