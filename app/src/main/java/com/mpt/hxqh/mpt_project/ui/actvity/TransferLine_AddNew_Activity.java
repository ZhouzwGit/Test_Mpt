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
import com.mpt.hxqh.mpt_project.model.INVUSE;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

/**
 * 转移新增行
 **/
public class TransferLine_AddNew_Activity extends BaseActivity {
    public static final int TRANSFERLINE_CODE=2000;

    private static final String TAG = "TransferLine_AddNew_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private Button submit;

    //    private TextView orderTextView; //Order
    private TextView usetypeTextView; //usetype
    private TextView linetypeTextView; //linetype
    private TextView itemnumTextView; //itemnum
    private TextView tostorelocTextView; //tostoreloc
    private TextView rotassetnumTextView; //rotassetnum
    private TextView issuetoTextView; //issueto
    private EditText quantityTextView; //quantity

    private String invusenum;
    private String storeroom;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back", "Save"};

    private String[] usetypeList = new String[]{"Issue", "Return", "Transfer"};

    private String[] linetypeList = new String[]{"Item", "Tool"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferline_addnew);
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
        tostorelocTextView = (TextView) findViewById(R.id.tostoreloc_text_id);
        rotassetnumTextView = (TextView) findViewById(R.id.rotassetnum_text_id);
        issuetoTextView = (TextView) findViewById(R.id.issueto_text_id);
        quantityTextView = (EditText) findViewById(R.id.quantity_text_id);
//        statusTextView = (TextView) findViewById(R.id.status_text_id);

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

        usetypeTextView.setOnClickListener(usetypeOnClickListener);
        linetypeTextView.setOnClickListener(linetypeOnClickListener);
        itemnumTextView.setOnClickListener(itemnumOnClickListener);
        tostorelocTextView.setOnClickListener(locationTextViewOnClickListener);
        rotassetnumTextView.setOnClickListener(rotassetnumOnClickListener);
        issuetoTextView.setOnClickListener(issuetoOnClickListener);
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
            final NormalDialog dialog = new NormalDialog(TransferLine_AddNew_Activity.this);
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
                            AppManager.AppExit(TransferLine_AddNew_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(TransferLine_AddNew_Activity.this, optionList);
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
     * usetype
     **/
    private View.OnClickListener usetypeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            usetypeDialog();
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
     * 位置
     **/
    private View.OnClickListener locationTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(TransferLine_AddNew_Activity.this, LocationChooseActivity.class);
            intent.putExtra("type","=STOREROOM");
            startActivityForResult(intent, 0);
        }
    };

    /**
     * rotassetnum
     **/
    private View.OnClickListener rotassetnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(TransferLine_AddNew_Activity.this, AssetChooseActivity.class);
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
            Intent intent = new Intent(TransferLine_AddNew_Activity.this, PersonChooseActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * itemnum
     **/
    private View.OnClickListener itemnumOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(TransferLine_AddNew_Activity.this, InventoryChooseActivity.class);
            intent.putExtra("LOCATION", storeroom);
            startActivityForResult(intent, 0);
        }
    };

    private void usetypeDialog() {
        final NormalListDialog normalListDialog = new NormalListDialog(TransferLine_AddNew_Activity.this, usetypeList);
        normalListDialog.title("Usage Type")
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                usetypeTextView.setText(usetypeList[position]);
                normalListDialog.dismiss();
            }
        });
    }

    private void linetypeDialog() {
        final NormalListDialog normalListDialog = new NormalListDialog(TransferLine_AddNew_Activity.this, linetypeList);
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
        final NormalDialog dialog = new NormalDialog(TransferLine_AddNew_Activity.this);
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
                WebResult reviseresult = AndroidClientService.AddAssetTrsLin(TransferLine_AddNew_Activity.this, invusenum, usetypeTextView.getText().toString(),
                        linetypeTextView.getText().toString(), itemnumTextView.getText().toString(), tostorelocTextView.getText().toString()
                        , rotassetnumTextView.getText().toString(), issuetoTextView.getText().toString(), quantityTextView.getText().toString(), Constants.TRANSFER_URL);
                return reviseresult;
            }

            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
                closeProgressDialog();
                if (workResult == null) {
                    MessageUtils.showMiddleToast(TransferLine_AddNew_Activity.this, workResult.returnStr);
                } else {
                    MessageUtils.showMiddleToast(TransferLine_AddNew_Activity.this, workResult.returnStr);
                    finish();
                }

            }
        }.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case LocationChooseActivity.LOCATION_CODE:
                String location = data.getExtras().getString("Location");
                tostorelocTextView.setText(location);
                break;
            case InventoryChooseActivity.INVENTORY_CODE: //ITEM选择
                INVENTORY inventory = (INVENTORY) data.getSerializableExtra("Inventory");
                itemnumTextView.setText(inventory.getITEMNUM());
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
