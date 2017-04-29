package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.a29149.ecollaboration.R;

/**
 * display new info from project master
 */
public class MyNewInfoDialog extends Dialog {

	public MyNewInfoDialog(Context context) {

		super(context);
	}

	public MyNewInfoDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
        private Activity activity;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

        //private MessageList messageList;
        private ScrollView scrollView;

		public Builder(Context context, Activity activity) {
			this.context = context;
            this.activity = activity;
		}


		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}


		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public MyNewInfoDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// instantiate the dialog with the custom Theme
			final MyNewInfoDialog dialog = new MyNewInfoDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_display_new_info_layout, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_commit))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_commit))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.info_fragment2_new_msg_bt_commit).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup).setVisibility(
						View.GONE);
			}

            scrollView=(ScrollView)layout.findViewById(R.id.info_fragment1_MsgTree);
			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);
			return dialog;
		}


        /**
         * 数据刷新
         */
        /*public void refreshDatas(String teamName)
        {
			SQLiteDBUtil.getSqLiteDBUtil().updataListByTeamName(teamName);

            scrollView.removeAllViews();

            ArrayList<Message> messageArrayList=new ArrayList<>();

			for (int i = SQLiteDBUtil.getSqLiteDBUtil().getId().size(); i>0; i--)
			{
				messageArrayList.add(new Message(SQLiteDBUtil.getSqLiteDBUtil().getId().get(i-1),
						SQLiteDBUtil.getSqLiteDBUtil().getName().get(i-1),
						SQLiteDBUtil.getSqLiteDBUtil().getTime().get(i-1),
						SQLiteDBUtil.getSqLiteDBUtil().getContent().get(i-1)));
			}

            messageArrayList.add(new Message(" "," "," "," "));

            if(messageList==null)
            {
                messageList=new MessageList();
            }
            messageList.setMessages(messageArrayList);

            Display display=activity.getWindowManager().getDefaultDisplay();
            MyMessgaeTip myMessgaeTip=new MyMessgaeTip(context,messageList.getMessages());
			myMessgaeTip.setScaleX(0.8f);
			myMessgaeTip.setScaleY(0.8f);
            myMessgaeTip.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            myMessgaeTip.setBackgroundColor(context.getResources().getColor(R.color.color_Fill_Trans));
            myMessgaeTip.setSize(display.getWidth(),display.getHeight()*2);

            scrollView.addView(myMessgaeTip);
        }*/
	}
}
