package com.rahul_lohra.redditstar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.rahul_lohra.redditstar.R;
import com.rahul_lohra.redditstar.activity.WebViewActivity;
import com.rahul_lohra.redditstar.Utility.MyUrl;

import static com.rahul_lohra.redditstar.Utility.MyUrl.AUTH_URL;
import static com.rahul_lohra.redditstar.Utility.MyUrl.CLIENT_ID;
import static com.rahul_lohra.redditstar.Utility.MyUrl.REDIRECT_URI;
import static com.rahul_lohra.redditstar.Utility.MyUrl.STATE;

/**
 * Created by rkrde on 25-01-2017.
 */

public class AddAccountDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.add_new_account)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        addNewAccount();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void addNewAccount(){
        String scopeArray[] = getResources().getStringArray(R.array.scope);
        String scope = MyUrl.getProperScope(scopeArray);
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI, scope);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
