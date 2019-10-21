package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.UDASST;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.unit.DateTimeSelect;
import com.mpt.hxqh.mpt_project.unit.DateTimeSelect2;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

/**
 * 资产维修新增行
 **/
public class UdasstLine_AddNew_Activity extends BaseActivity {

    public static final int UDASSLINE_CODE=2001; //资产维修新增行

    private static final String TAG = "UdasstLine_AddNew_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private Button submit;

//    private TextView orderTextView; //Order
    private TextView udasstnumTextView; //udasstnum
    private TextView udrepdateTextView; //udrepdate

    private String repairnum;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back","Save"};

    private String location; //位置

    private UDASST udasst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udasstline_addnew);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        repairnum = getIntent().getStringExtra("repairnum");
        udasst = (UDASST) getIntent().getSerializableExtra("udasst");
        location=udasst.getLOCATION();
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submit = (Button) findViewById(R.id.sbmit_id);
//        orderTextView = (TextView) findViewById(R.id.order_text_id);
        udasstnumTextView = (TextView) findViewById(R.id.assetnum_text_id);
        udrepdateTextView = (TextView) findViewById(R.id.udrepdate_text_id);

        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.asset_transferline);
        backImageView.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        submit.setText("save");
//        submit.setVisibility(View.VISIBLE);

        udasstnumTextView.setOnClickListener(assetnumOnClickListener);
        udrepdateTextView.setOnClickListener(DateOnClickListener);
        submit.setOnClickListener(submitOnClickListener);

        quit.setOnClickListener(quitOnClickListener);
        option.setOnClickListener(optionOnClickListener);
    }

    /**
     * 返回按钮
     **/
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitDataInfo();
        }
    };

    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog dialog = new NormalDialog(UdasstLine_AddNew_Activity.this);
            dialog.content("Sure to exit?")//
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            AppManager.AppExit(UdasstLine_AddNew_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(UdasstLine_AddNew_Activity.this, optionList);
            normalListDialog.title("Option")
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    switch (position){
                        case 0://Back
                            normalListDialog.superDismiss();
                            finish();
                            break;
                        case 1://Save
                            normalListDialog.superDismiss();
                            submitDataInfo();
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    /**
     * rotassetnum
     **/
    private View.OnClickListener assetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(UdasstLine_AddNew_Activity.this, AssetChooseActivity.class);
            intent.putExtra("CODE", UDASSLINE_CODE);
            intent.putExtra("LOCATION",location);
            startActivityForResult(intent, 0);
        }
    };

    /**
     *
     **/
    private View.OnClickListener DateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DateTimeSelect2(UdasstLine_AddNew_Activity.this, udrepdateTextView).showDialog();
        }
    };

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(UdasstLine_AddNew_Activity.this);
        dialog.content("Sure to save?")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        showProgressDialog("Waiting...");
                        startAsyncTask();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 提交数据*
     */
    private void startAsyncTask() {
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                WebResult reviseresult = AndroidClientService.AddRepairLine(UdasstLine_AddNew_Activity.this, repairnum,
                        udrepdateTextView.getText().toString(), udasstnumTextView.getText().toString()
                        , Constants.TRANSFER_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null) {
                    MessageUtils.showMiddleToast(UdasstLine_AddNew_Activity.this, "false");
                } else {
                    MessageUtils.showMiddleToast(UdasstLine_AddNew_Activity.this, workResult.returnStr);
                    finish();
                }
                closeProgressDialog();
            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case AssetChooseActivity.ASSET_CODE:
                String asset = data.getExtras().getString("Assetnum");
                udasstnumTextView.setText(asset);
                break;
//            case RESULT_OK:
//                String result = data.getExtras().getString("result");
//                snTextView.setText(result);
//                break;
        }
    }

}
