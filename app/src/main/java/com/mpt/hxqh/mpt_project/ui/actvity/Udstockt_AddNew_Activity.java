package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.UdretirelineAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.COMPANIES;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.UDSTOCKT;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 物资盘点新增
 **/
public class Udstockt_AddNew_Activity extends BaseActivity {

    private static final String TAG = "Udstockt_AddNew_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private Button submit;

    private EditText descriptionTextView; //description
    private TextView vendorView; //Vendor
    private TextView locationTextView; //location

    private UDSTOCKT udstockt;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back", "Save"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udstockt_addnew);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        udstockt = (UDSTOCKT) getIntent().getSerializableExtra("udstockt");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submit = (Button) findViewById(R.id.sbmit_id);

        descriptionTextView = (EditText) findViewById(R.id.description_text_id);
        vendorView = (TextView) findViewById(R.id.vendor_text_id);
        locationTextView = (TextView) findViewById(R.id.location_text_id);

        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        backImageView.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        titleTextView.setText(R.string.material_stocktaking_text);
        submit.setText("save");
//        submit.setVisibility(View.VISIBLE);

        locationTextView.setOnClickListener(locationTextViewOnClickListener);
        vendorView.setOnClickListener(vendortextviewonclicklistener);
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
            final NormalDialog dialog = new NormalDialog(Udstockt_AddNew_Activity.this);
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
                            AppManager.AppExit(Udstockt_AddNew_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Udstockt_AddNew_Activity.this, optionList);
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
     * Vendor
     **/
    private View.OnClickListener vendortextviewonclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Udstockt_AddNew_Activity.this, CompaniesChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 位置
     **/
    private View.OnClickListener locationTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Udstockt_AddNew_Activity.this, LocationChooseActivity.class);
            intent.putExtra("type", "!=HOLDING");
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(Udstockt_AddNew_Activity.this);
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
                WebResult reviseresult = AndroidClientService.AddMatSto(Udstockt_AddNew_Activity.this, descriptionTextView.getText().toString(),
                        locationTextView.getText().toString(), vendorView.getText().toString(), AccountUtils.getpersonId(Udstockt_AddNew_Activity.this),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Constants.TRANSFER_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                closeProgressDialog();
                if (workResult == null) {
                    MessageUtils.showMiddleToast(Udstockt_AddNew_Activity.this, "false");
                } else {
                    MessageUtils.showMiddleToast(Udstockt_AddNew_Activity.this, workResult.returnStr);
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
            case LocationChooseActivity.LOCATION_CODE:
                String location = data.getExtras().getString("Location");
                locationTextView.setText(location);
                break;
            case CompaniesChooseActivity.COMPANIES_CODE:
                COMPANIES companies = (COMPANIES) data.getSerializableExtra("companies");
                vendorView.setText(companies.getCOMPANY());
                break;
        }
    }
}
