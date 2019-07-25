package com.tvi.tvitracker.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.tvi.tvitracker.Interface.Leave_Filter_Listener;
import com.tvi.tvitracker.R;

public class Leave_Filter extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    EditText medicinename,enterqty;
    Button add,cancel;

    private Leave_Filter_Listener mCallback;

    public Leave_Filter(Activity a, Leave_Filter_Listener callback) {
        super(a);
        this.c = a;
        this.mCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_leave_filter);

//        medicinename = findViewById(R.id.medicinename);
//        add = findViewById(R.id.add);
//        cancel = findViewById(R.id.cancel);

//        add.setOnClickListener(this);
//        cancel.setOnClickListener(this);

    }

    public boolean isValid(){
        if (medicinename.getText().toString().isEmpty()){
            medicinename.setError("Please Enter Medicine name");
            medicinename.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.add :
//                if (isValid()){
//                    mCallback.selectfilter(medicinename.getText().toString());
//                    dismiss();
//                }
//                break;
//            case R.id.cancel:
//                dismiss();
//                break;
        }
    }
}
