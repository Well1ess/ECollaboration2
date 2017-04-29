package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.a29149.ecollaboration.R;

import java.util.Calendar;

/**
 * display date selected dialog for task making
 */

public class DateSelectedDialog extends Dialog {

	public DateSelectedDialog(Context context) {

		super(context);
	}

	public DateSelectedDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private DatePicker datePicker;
		private String dateStr;

		public Builder(Context context) {
			this.context = context;
		}

		public String getDate()
		{
			return dateStr;
		}

		public void setDate(String s)
		{
			dateStr=s;
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

		public DateSelectedDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final DateSelectedDialog dialog = new DateSelectedDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_date_layout, null);
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

			// 获取日历对象
			Calendar calendar = Calendar.getInstance();
			// 获取当前对应的年、月、日的信息
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			dateStr=year + "-" + (month+1) + "-" + day;

			datePicker=(DatePicker) dialog.findViewById(R.id.datePicker);
			datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					dateStr=year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
				}
			});

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
