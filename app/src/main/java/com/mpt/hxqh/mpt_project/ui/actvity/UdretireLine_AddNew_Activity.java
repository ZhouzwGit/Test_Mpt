package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.unit.DateTimeSelect;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

/**
 * 资产报废新增行
 **/
public class UdretireLine_AddNew_Activity extends BaseActivity {

    private static final String TAG = "UdretireLine_AddNew_Activity";
    private static final int LOCATION_CODE = 3000;

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private Button submit;

    //    private TextView orderTextView; //Order
    private TextView assetnumTextView; //assetnum
    private TextView retirelocTextView; //retireloc_text_id
    private TextView retiredateTextView; //retiredate

    private String repairnum;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back", "Save"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udretireline_addnew);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        repairnum = getIntent().getStringExtra("repairnum");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submit = (Button) findViewById(R.id.sbmit_id);
        assetnumTextView = (TextView) findViewById(R.id.assetnum_text_id);
        retirelocTextView = (TextView) findViewById(R.id.retireloc_text_id);
        retiredateTextView = (TextView) findViewById(R.id.retiredate_text_id);

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

        assetnumTextView.setOnClickListener(assetnumOnClickListener);
        retirelocTextView.setOnClickListener(retirelocOnClickListener);
        retiredateTextView.setOnClickListener(DateOnClickListener);
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
            final NormalDialog dialog = new NormalDialog(UdretireLine_AddNew_Activity.this);
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
                            AppManager.AppExit(UdretireLine_AddNew_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(UdretireLine_AddNew_Activity.this, optionList);
            normalListDialog.title("Option")
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    linetypeTextView.setText(linetypeList[position]);
                    switch (position) {
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
            Intent intent = getIntent();
            intent.setClass(UdretireLine_AddNew_Activity.this, AssetChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };
    /**
     * rotassetnum
     **/
    private View.OnClickListener retirelocOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(UdretireLine_AddNew_Activity.this, LocationChooseActivity.class);
            intent.putExtra("type", "!=HOLDING");
            startActivityForResult(intent, LOCATION_CODE);
        }
    };

    /**
     *
     **/
    private View.OnClickListener DateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DateTimeSelect(UdretireLine_AddNew_Activity.this, retiredateTextView).showDialog();
        }
    };

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(UdretireLine_AddNew_Activity.this);
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
                WebResult reviseresult = AndroidClientService.AddRetireLine(UdretireLine_AddNew_Activity.this, repairnum,
                        assetnumTextView.getText().toString(), retiredateTextView.getText().toString()
                        , retirelocTextView.getText().toString(), Constants.TRANSFER_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null) {
                    Toast.makeText(UdretireLine_AddNew_Activity.this, "false", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UdretireLine_AddNew_Activity.this, workResult.returnStr, Toast.LENGTH_SHORT).show();
                    finish();
                }
                closeProgressDialog();
            }
        }.execute();
        //}else {
        closeProgressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case AssetChooseActivity.ASSET_CODE:
                String asset = data.getExtras().getString("Assetnum");
                assetnumTextView.setText(asset);
                break;
            case LocationChooseActivity.LOCATION_CODE:
                String location = data.getExtras().getString("Location");
                retirelocTextView.setText(location);
                break;
//            case RESULT_OK:
//                String result = data.getExtras().getString("result");
//                snTextView.setText(result);
//                break;
        }
    }

}
