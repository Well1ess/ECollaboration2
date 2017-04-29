package com.example.a29149.ecollaboration.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.widget.MyEditText;

/**
 * display hobby project install
 */
public class MyNewHobbyDialog extends Dialog {

	public MyNewHobbyDialog(Context context) {

		super(context);
	}

	public MyNewHobbyDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}


		private MyEditText name;
		private MyEditText need;
		private MyEditText skill;
		private MyEditText content;

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public String getContent() {
			return content.getText().toString();
		}

		public String getName() {
			return name.getText().toString();
		}

		public String getNeed() {
			return need.getText().toString();
		}

		public String getSkill() {
			return skill.getText().toString();
		}

		public void setContent(String content) {
			this.content.setText(content);
		}

		public void setName(String name) {
			this.name.setText(name);
		}

		public void setNeed(String need) {
			this.need.setText(need);
		}

		public void setSkill(String skill) {
			this.skill.setText(skill);
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

		public MyNewHobbyDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final MyNewHobbyDialog dialog = new MyNewHobbyDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_hobby_project_layout, null);
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

			name=(MyEditText)layout.findViewById(R.id.project_name_edit);
			need=(MyEditText)layout.findViewById(R.id.project_need_edit);
			skill=(MyEditText)layout.findViewById(R.id.project_learn_skill_edit);
			content=(MyEditText)layout.findViewById(R.id.project_content_edit);

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
