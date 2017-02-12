package xyz.vfhhu.simplepermissionchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_PERMISSION_CODE = 1;
    public static final String[] PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.CAMERA
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //MLog.d(TAG, "dexter onActivityResult");
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if(resultCode==RESULT_OK){
                int v=data.getIntExtra(PerMissionActivity.TAG_RESULT_TYPE,PerMissionActivity.VALUE_RESULT_ERROR);
                if(v==PerMissionActivity.VALUE_RESULT_SUCCESS){
                    init();
                    return;
                }
                if(v==PerMissionActivity.VALUE_RESULT_REJECT){
                    finish();
                    return;
                }
                if(v==PerMissionActivity.VALUE_RESULT_ERROR){
                    Log.d(TAG,"permission error:"+data.getStringExtra(PerMissionActivity.TAG_RESULT_MSG));
                    return;
                }
            }
            checkPermissions();
        }

    }
    public void checkPermissions(){
        try{
            if(!PerMissionActivity.checkPermissions(this,PERMISSIONS)){
                Intent it= new Intent(this,PerMissionActivity.class);
                it.putExtra(PerMissionActivity.TAG_PERMISSION,PERMISSIONS);
                startActivityForResult(it,REQUEST_PERMISSION_CODE);
            }else init();
        }catch(Exception e){e.printStackTrace();}

    }
    public void init(){

    }
}
