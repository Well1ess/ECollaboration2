package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;

/**
 * display applicant info
 */

public class InsertNewTaskDialog extends Dialog {

	public InsertNewTaskDialog(Context context) {

		super(context);
	}

	public InsertNewTaskDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {

		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private TextView startTime;
		private TextView endTime;
		private EditText content;

		private int flag=-1;

		private DateSelectedDialog.Builder dateSelectedDialog;
		public Builder(Context context) {
			this.context = context;
			dateSelectedDialog=new DateSelectedDialog.Builder(context);
			initDialog();
		}

		public String getStartTime() {
			return startTime.getText().toString();
		}

		public void setStartTime(String startTime) {
			this.startTime.setText(startTime);
		}

		public String getEndTime() {
			return endTime.getText().toString();
		}

		public void setEndTime(String endTime) {
			this.endTime.setText(endTime);
		}

		public String getContent() {
			return content.getText().toString();
		}

		public void setContent(String content) {
			this.content.setHint(content);
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		private void initDialog()
		{
			dateSelectedDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					flag = -1;
				}
			});

			dateSelectedDialog.setPositiveButton("保      存", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (flag == 0)
					{
						startTime.setText(dateSelectedDialog.getDate());
					}
					else
					{
						endTime.setText(dateSelectedDialog.getDate());
					}
					flag = -1;
					dialog.dismiss();
				}
			});

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

		public InsertNewTaskDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final InsertNewTaskDialog dialog = new InsertNewTaskDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_insert_new_task_layout, null);
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

			startTime=(TextView)dialog.findViewById(R.id.task_start_date);
			endTime=(TextView)dialog.findViewById(R.id.task_end_date);
			content=(EditText) dialog.findViewById(R.id.task_content);

			startTime.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					flag=0;
					dateSelectedDialog.create().show();
				}
			});

			endTime.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (getStartTime().equals(""))
					{
						Toast.makeText(context, "开始时间不能为空", Toast.LENGTH_SHORT).show();
					}
					else
					{
						flag=1;
						dateSelectedDialog.create().show();
					}
				}
			});

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
