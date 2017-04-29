package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.example.a29149.ecollaboration.R;

/**
 * display dialog for commit message to my project 's teams
 */
public class EditSendMsgDialog extends Dialog {

	public EditSendMsgDialog(Context context) {

		super(context);
	}

	public EditSendMsgDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private EditText msg;
		
		public Builder(Context context) {
			this.context = context;
		}


		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public String getMsg()
		{
			return msg.getText().toString();
		}
		
		public void setMsg(String _msg)
		{
			msg.setText(_msg);
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

		public EditSendMsgDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final EditSendMsgDialog dialog = new EditSendMsgDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_msg_commit_layout, null);
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

			msg=(EditText)layout.findViewById(R.id.info_fragment2_new_msg_content);

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
