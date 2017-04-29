package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;

/**
 * display applicant info
 */

public class ApplyInfoDialog extends Dialog {

	public ApplyInfoDialog(Context context) {

		super(context);
	}

	public ApplyInfoDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private TextView applyCreator;
		private TextView applyKind;
		private TextView applyTarget;
		private TextView applyContent;
		private TextView applyInfo;

		public void setApplyContent(String applyContent) {
			this.applyContent.setText(applyContent);
		}

		public void setApplyCreator(String applyCreator) {
			this.applyCreator.setText(applyCreator);
		}

		public void setApplyInfo(String applyInfo) {
			this.applyInfo.setText(applyInfo);
		}

		public void setApplyKind(String applyKind) {
			this.applyKind.setText(applyKind);
		}

		public void setApplyTarget(String applyTarget) {
			this.applyTarget.setText(applyTarget);
		}

		public Builder(Context context) {
			this.context = context;
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

		public ApplyInfoDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final ApplyInfoDialog dialog = new ApplyInfoDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_display_apply_layout, null);
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
				layout.findViewById(R.id.info_fragment2_new_msg_bt_commit).setVisibility(
						View.GONE);
			}
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
				layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup).setVisibility(
						View.GONE);
			}


			applyCreator=(TextView)layout.findViewById(R.id.apply_creator);
			applyKind=(TextView)layout.findViewById(R.id.apply_kind);
			applyTarget=(TextView)layout.findViewById(R.id.apply_target);
			applyContent=(TextView)layout.findViewById(R.id.apply_content);
			applyInfo=(TextView)layout.findViewById(R.id.apply_info);

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
