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
 * display search result of project when user click list item
 */
public class DisplayProjectInfoDialog extends Dialog {

	public DisplayProjectInfoDialog(Context context) {

		super(context);
	}

	public DisplayProjectInfoDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private TextView projectTitle;
		private TextView projectCreater;
		private TextView projectKind;
		private TextView projectTime;
		private TextView projectContent;
		private TextView needSkill;
		private TextView learnSkill;

		public Builder(Context context) {
			this.context = context;
		}


		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public String getProjectTitle() {
			return projectTitle.getText().toString();
		}

		public void setProjectTitle(String projectTitle) {
			this.projectTitle.setText(projectTitle);
		}

		public String getLearnSkill() {
			return learnSkill.getText().toString();
		}

		public void setLearnSkill(String learnSkill) {
			this.learnSkill.setText(learnSkill);
		}

		public String getNeedSkill() {
			return needSkill.getText().toString();
		}

		public void setNeedSkill(String needSkill) {
			this.needSkill.setText(needSkill);
		}

		public String getProjectContent() {
			return projectContent.getText().toString();
		}

		public void setProjectContent(String projectContent) {
			this.projectContent.setText(projectContent);
		}

		public String getProjectCreater() {
			return projectCreater.getText().toString();
		}

		public void setProjectCreater(String projectCreater) {
			this.projectCreater.setText(projectCreater);
		}

		public String getProjectKind() {
			return projectKind.getText().toString();
		}

		public void setProjectKind(String projectKind) {
			this.projectKind.setText(projectKind);
		}

		public String getProjectTime() {
			return projectTime.getText().toString();
		}

		public void setProjectTime(String projectTime) {
			this.projectTime.setText(projectTime);
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

		public DisplayProjectInfoDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final DisplayProjectInfoDialog dialog = new DisplayProjectInfoDialog(context, R.style.Base_Theme_AppCompat_Dialog);
			View layout = inflater.inflate(R.layout.dialog_my_display_project_layout, null);
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
			projectTitle=(TextView)layout.findViewById(R.id.project_title);
			projectCreater=(TextView)layout.findViewById(R.id.project_creater);
			projectKind=(TextView)layout.findViewById(R.id.project_kind);
			projectTime=(TextView)layout.findViewById(R.id.project_time);
			needSkill=(TextView)layout.findViewById(R.id.project_need_skill);
			learnSkill=(TextView)layout.findViewById(R.id.project_learn_skill);
			projectContent=(TextView)layout.findViewById(R.id.project_content_msg);

			dialog.setContentView(layout);
			//dialog.setCanceledOnTouchOutside(false);

			return dialog;
		}

	}
}
