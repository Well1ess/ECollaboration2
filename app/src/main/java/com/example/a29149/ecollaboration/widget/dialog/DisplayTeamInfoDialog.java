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
public class DisplayTeamInfoDialog extends Dialog {

    public DisplayTeamInfoDialog(Context context) {

        super(context);
    }

    public DisplayTeamInfoDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private TextView teamName;
        private TextView teamNum;
        private TextView teamInfo;

        private Dialog dialog;


        public void setTeamNum(String teamNum) {
            this.teamNum.setText(teamNum);
        }

        public void setTeamName(String teamName) {
            this.teamName.setText(teamName);
        }

        public void setTeamInfo(String teamInfo) {
            this.teamInfo.setText(teamInfo);
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

        public DisplayTeamInfoDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final DisplayTeamInfoDialog dialog = new DisplayTeamInfoDialog(context, R.style.Base_Theme_AppCompat_Dialog);
            View layout = inflater.inflate(R.layout.dialog_my_team_info_layout, null);
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
            }

            teamName = (TextView) layout.findViewById(R.id.teamName);
            teamInfo = (TextView) layout.findViewById(R.id.teamInfo);
            teamNum = (TextView) layout.findViewById(R.id.teamNum);

            dialog.setContentView(layout);
            //dialog.setCanceledOnTouchOutside(false);
            this.dialog = dialog;
            return dialog;
        }

        public Dialog getDialog() {
            return dialog;
        }
    }
}
