package com.ike.commonutils.UiBase;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ike.commonutils.baseMvp.BasePresenter;
import com.ike.commonutils.baseMvp.BaseView;
import com.ike.commonutils.net.retrofitnetutils.commonUtils.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
* author ike
* create time 8:50 2017/7/1
* function: mvpactivity的基类
**/

public abstract class BaseMvpActivity<T extends ViewDataBinding,P extends BasePresenter<V>,V extends BaseView> extends AppCompatActivity {
    private static final int REQUEST_CODE_PIC=0;
    private static final String Tag="BaseMvpActivity";
    private List<String> denyPermission=new ArrayList<>();//被拒绝的权限集合
    List<String> requestPermission;
    public T viewDataBinding;
    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         viewDataBinding = DataBindingUtil.setContentView(this, getLayout());
        Type genType = this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genType).getActualTypeArguments();
        final Class clazz = ClassUtil.getClass(actualTypeArguments[1], 1);
        try {
            presenter= (P) ClassUtil.newInstance(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化布局信息
     * @return
     */
    public abstract int getLayout();

    /**
     * 初始化控件信息
     */
    public abstract void initView();

    /**
     * 初始化事件监听
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();
    protected void checkAndRequestPermission(List<String> requestPermission) {
        this.requestPermission=requestPermission;
        if (requestPermission.size()==0){
            return;
        }
        String[] checkHasPermission = checkHasPermission(requestPermission);
        if (checkHasPermission.length>0){
            ActivityCompat.requestPermissions(this,checkHasPermission,REQUEST_CODE_PIC);
        }
    }

    /**
     * 检查是否有该权限
     * @param permessions
     */
    private String[] checkHasPermission(List<String> permessions){
        List<String> denyPermissions=new ArrayList<>();//没有被授权的权限集合
        for (String permission:permessions) {
            //未获得权限
            if (ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED){
                denyPermissions.add(permission);
            }
        }
        String[] neesRequestPermission=new String[]{};
        if (!denyPermissions.isEmpty()){
            neesRequestPermission= denyPermissions.toArray(neesRequestPermission);
        }
        return neesRequestPermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_PIC:
                if (grantResults.length>0){
                    for (int i=0;i<grantResults.length;i++){
                        if (grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            denyPermission.add(permissions[i]);
                        }
                    }
                }
                if (denyPermission.isEmpty()){
                    Toast.makeText(this,"拍照授权成功",Toast.LENGTH_SHORT).show();
                }else {
                    showNoticeDialog();
                }
                break;
        }
    }
    AlertDialog alertDialog = null;
    boolean isAwaysDeny=false;
    /**
     * 显示提醒的对话框
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNoticeDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        StringBuilder strBuilder=new StringBuilder();
        strBuilder.append("亲，我们需要你权限哦:");

        for (String str:denyPermission){
            strBuilder.append(str+"、");
            if (!shouldShowRequestPermissionRationale(str)){
                Log.e(Tag,"是否永远拒绝:"+(!shouldShowRequestPermissionRationale(str)));
                isAwaysDeny=true;
            }
        }
        String substring = strBuilder.substring(0,strBuilder.toString().lastIndexOf("、") );
        builder.setTitle("你未授予我们足够的权限哦")
                .setMessage(substring)
                .setPositiveButton("授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(Tag,"isAwaysDeny:"+isAwaysDeny);
                        if (isAwaysDeny){//表示用户勾选了权限的不再提醒，此时授权应当开启设置界面进行授权
                            isAwaysDeny=false;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", BaseMvpActivity.this.getPackageName(), null);
                            intent.setData(uri);
                            BaseMvpActivity.this.startActivityForResult(intent, 1);

                        }else {
                            checkAndRequestPermission(requestPermission);
                        }

                    }
                })
                .setNegativeButton("取消", null).create().show();
        denyPermission.clear();
    }
}
