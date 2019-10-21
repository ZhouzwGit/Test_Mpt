package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.INVENTORY;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

/**
 * 物料转移新增行
 **/
public class MaainvuseLine_AddNew_Activity extends BaseActivity {

    private static final String TAG = "TransferLine_AddNew_Activity";
    public static final int TRANSFERLINE_CODE = 2000;

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private Button submit;

    //    private TextView orderTextView; //Order
    private TextView usetypeTextView; //usetype
    private TextView linetypeTextView; //linetype
    private TextView itemnumTextView; //itemnum
    private TextView formbinTextView;//formbin
    private TextView tositeTextView;//tosite
    private TextView tostorelocTextView; //tostoreloc
    private TextView rotassetnumTextView; //rotassetnum
    private TextView issuetoTextView; //issueto
    private EditText quantityTextView; //quantity
    private TextView tobinTextView;//tobin
    private EditText conversionTextView;//conversion

    private String invusenum;
    private String storeroom;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back", "Save"};

    private String[] linetypeList = new String[]{"Item", "Tool"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maainvuseline_addnew);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        invusenum = getIntent().getStringExtra("invusenum");
        storeroom = getIntent().getStringExtra("storeroom");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        submit = (Button) findViewById(R.id.sbmit_id);
//        orderTextView = (TextView) findViewById(R.id.order_text_id);
        usetypeTextView = (TextView) findViewById(R.id.usetype_text_id);
        linetypeTextView = (TextView) findViewById(R.id.linetype_text_id);
        itemnumTextView = (TextView) findViewById(R.id.itemnum_text_id);
        formbinTextView = (TextView) findViewById(R.id.frombin_text_id);
        tositeTextView = (TextView) findViewById(R.id.tositeid_text_id);
        tostorelocTextView = (TextView) findViewById(R.id.tostoreloc_text_id);
        rotassetnumTextView = (TextView) findViewById(R.id.rotassetnum_text_id);
        issuetoTextView = (TextView) findViewById(R.id.issueto_text_id);
        quantityTextView = (EditText) findViewById(R.id.quantity_text_id);
        tobinTextView = (TextView) findViewById(R.id.tobin_text_id);
        conversionTextView = (EditText) findViewById(R.id.conversion_text_id);
//        statusTextView = (TextView) findViewById(R.id.status_text_id);

        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        backImageView.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        titleTextView.setText(R.string.asset_transferline);
        submit.setText("save");
//        submit.setVisibility(View.VISIBLE);

        usetypeTextView.setText("Transfer");
        linetypeTextView.setOnClickListener(linetypeOnClickListener);
        itemnumTextView.setOnClickListener(itemnumOnClickListener);
        formbinTextView.setOnClickListener(formbinOnClickListener);
        tositeTextView.setOnClickListener(siteOnClickListener);
        tostorelocTextView.setOnClickListener(locationTextViewOnClickListener);
        rotassetnumTextView.setOnClickListener(rotassetnumOnClickListener);
        issuetoTextView.setOnClickListener(issuetoOnClickListener);
        tobinTextView.setOnClickListener(tobinOnClickListener);
//        from_storeroomTextView.setOnClickListener(locationTextViewOnClickListener);
//        inventory_ownerTextView.setOnClickListener(ownerOnClickListener);
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
            final NormalDialog dialog = new NormalDialog(MaainvuseLine_AddNew_Activity.this);
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
                            AppManager.AppExit(MaainvuseLine_AddNew_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(MaainvuseLine_AddNew_Activity.this, optionList);
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
     * linetype
     **/
    private View.OnClickListener linetypeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            linetypeDialog();
        }
    };

    /**
     * itemnum
     **/
    private View.OnClickListener itemnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, InventoryChooseActivity.class);
            intent.putExtra("LOCATION", storeroom);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 位置
     **/
    private View.OnClickListener locationTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, LocationChooseActivity.class);
            intent.putExtra("type", "=STOREROOM");
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener formbinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, InvbalancesChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener tobinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, InvbalancesChooseActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    private View.OnClickListener siteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, SiteChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * rotassetnum
     **/
    private View.OnClickListener rotassetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, AssetChooseActivity.class);
            intent.putExtra("CODE", TRANSFERLINE_CODE);
            intent.putExtra("LOCATION", storeroom);
            intent.putExtra("ITEMNUM", itemnumTextView.getText().toString());
            startActivityForResult(intent, 0);
        }
    };

    /**
     * issueto
     **/
    private View.OnClickListener issuetoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, PersonChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 仓管员
     **/
    private View.OnClickListener ownerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MaainvuseLine_AddNew_Activity.this, LocationChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private void linetypeDialog() {
        final NormalListDialog normalListDialog = new NormalListDialog(MaainvuseLine_AddNew_Activity.this, linetypeList);
        normalListDialog.title("Usage Type")
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                linetypeTextView.setText(linetypeList[position]);
                normalListDialog.dismiss();
            }
        });
    }

    /**
     * 提交数据*
     */
    private void submitDataInfo() {
        final NormalDialog dialog = new NormalDialog(MaainvuseLine_AddNew_Activity.this);
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
                WebResult reviseresult = AndroidClientService.AddMatoutbLin(MaainvuseLine_AddNew_Activity.this, invusenum, itemnumTextView.getText().toString(),
                        formbinTextView.getText().toString(), usetypeTextView.getText().toString(), linetypeTextView.getText().toString()
                        , tositeTextView.getText().toString(), rotassetnumTextView.getText().toString(), quantityTextView.getText().toString()
                        , tostorelocTextView.getText().toString(), tobinTextView.getText().toString(), issuetoTextView.getText().toString()
                        , AccountUtils.getpersonId(MaainvuseLine_AddNew_Activity.this), conversionTextView.getText().toString(), Constants.TRANSFER_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                if (workResult == null) {
                    Toast.makeText(MaainvuseLine_AddNew_Activity.this, "false", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MaainvuseLine_AddNew_Activity.this, workResult.returnStr, Toast.LENGTH_SHORT).show();
//                    setResult(100);
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
            case InventoryChooseActivity.INVENTORY_CODE: //ITEM选择
                INVENTORY inventory = (INVENTORY) data.getSerializableExtra("Inventory");
                itemnumTextView.setText(inventory.getITEMNUM());
                break;
            case InvbalancesChooseActivity.INVBALANCES_CODE:
                String binnum = data.getExtras().getString("binnum");
                if (requestCode == 0) {
                    formbinTextView.setText(binnum);
                } else {
                    tobinTextView.setText(binnum);
                }
                break;
            case SiteChooseActivity.SITE_CODE:
                String siteid = data.getExtras().getString("siteid");
                tositeTextView.setText(siteid);
                break;
            case LocationChooseActivity.LOCATION_CODE:
                String location = data.getExtras().getString("Location");
                tostorelocTextView.setText(location);
                break;
            case AssetChooseActivity.ASSET_CODE:
                String asset = data.getExtras().getString("Assetnum");
                String itemnum = data.getExtras().getString("Itemnum");
                rotassetnumTextView.setText(asset);
                itemnumTextView.setText(itemnum);
                break;
            case PersonChooseActivity.PERSON_CODE:
                String personid = data.getExtras().getString("personid");
                issuetoTextView.setText(personid);
                break;
//            case RESULT_OK:
//                String result = data.getExtras().getString("result");
//                snTextView.setText(result);
//                break;
        }
    }

}
