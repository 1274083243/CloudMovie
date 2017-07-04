package cloudmovie.ike.com.cloudmovie.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import cloudmovie.ike.com.cloudmovie.R;

/**
* author ike
* create time 14:58 2017/7/4
* function: 自定义加载对话框
**/
public class ClondDialog extends ProgressDialog{
    public ClondDialog(Context context) {
        this(context,0);
    }

    public ClondDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view);
        setCancelable(false);

    }
}
