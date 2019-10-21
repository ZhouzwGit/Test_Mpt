package com.mpt.hxqh.mpt_project.ui.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.MaterialDialog2;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.InvuseLineAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.INVUSE;
import com.mpt.hxqh.mpt_project.model.INVUSELINE;
import com.mpt.hxqh.mpt_project.model.WorkFlowResult;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

/**
 * 转移详情
 **/
public class Transfer_Details_Activity extends BaseActivity {

    private static final String TAG = "Transfer_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView orderTextView; //Order
    private TextView descriptionTextView; //description
    private TextView from_storeroomTextView; //from_storeroom
    private TextView inventory_ownerTextView; //inventory_owner
    private TextView statusTextView; //status

    private INVUSE invuse;

    /**
     * 行表
     **/
    LinearLayoutManager layoutManager;
    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout = null;
    /**
     * 适配器*
     */
    private InvuseLineAdapter invuseLineAdapter;

    private int page = 1;

    ArrayList<INVUSELINE> items = new ArrayList<INVUSELINE>();

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private String[] optionList = new String[]{"Back", "Route", "Scan","AddLine"};
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        invuse = (INVUSE) getIntent().getSerializableExtra("invuse");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        orderTextView = (TextView) findViewById(R.id.order_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        from_storeroomTextView = (TextView) findViewById(R.id.from_storeroom_text_id);
        inventory_ownerTextView = (TextView) findViewById(R.id.inventory_owner_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);

        recyclerView = (RecyclerView) findViewById(R.id.dqgz10_recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        backImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.asset_transfer);

        buttonLayout.setVisibility(View.VISIBLE);
        if (invuse != null) {
            orderTextView.setText(invuse.getINVUSENUM());
            descriptionTextView.setText(invuse.getDESCRIPTION());
            from_storeroomTextView.setText(invuse.getFROMSTORELOC());
            inventory_ownerTextView.setText(invuse.getINVOWNER());
            statusTextView.setText(invuse.getSTATUS());
        }


        layoutManager = new LinearLayoutManager(Transfer_Details_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(refreshOnRefreshListener);
        refresh_layout.setOnLoadListener(refreshOnLoadListener);

        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<INVUSELINE>());
        getData();

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

    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog dialog = new NormalDialog(Transfer_Details_Activity.this);
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
                            AppManager.AppExit(Transfer_Details_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Transfer_Details_Activity.this, optionList);
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
                        case 1://Route
                            normalListDialog.superDismiss();
                            if (invuse.getSTATUS().equals(Constants.ASTTRANS_START)) {//启动工作流
                                MaterialDialogOneBtn();
                            } else if (!invuse.getSTATUS().equals(Constants.ASTTRANS_END)) {//审批工作流
                                EditDialog();
                            } else {
                                MessageUtils.showMiddleToast(Transfer_Details_Activity.this, "Workflow is finished; cannot start again");
                            }
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                           if ("WHAPPR".equals(invuse.getSTATUS()) || "APPR".equals(invuse.getSTATUS())){
                               Intent lineIntent = new Intent(Transfer_Details_Activity.this, TransferLine_Activity.class);
                               lineIntent.putExtra("assetnum",invuse.getINVUSENUM());
                               lineIntent.putExtra("status",invuse.getSTATUS());
                               startActivity(lineIntent);
                           }else {
                               Toast.makeText(Transfer_Details_Activity.this,"Not in scaning status",Toast.LENGTH_SHORT).show();
                           }
                            break;
                        case 3://AddLine
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(Transfer_Details_Activity.this, TransferLine_AddNew_Activity.class);
                            intent.putExtra("invusenum", invuse.getINVUSENUM());
                            intent.putExtra("storeroom", invuse.getFROMSTORELOC());
                            startActivity(intent);

                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(Transfer_Details_Activity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("Route Workflow?")//
                .btnText("No", "Yes")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
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
                        startWF();
                        dialog.dismiss();
                    }
                }
        );
    }

    /**
     * 开始工作流
     */
    private void startWF() {
        mProgressDialog = ProgressDialog.show(Transfer_Details_Activity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.startwf(Transfer_Details_Activity.this,
                        "ASTTRANS", "INVUSE", invuse.getINVUSENUM(), "INVUSENUM", AccountUtils.getpersonId(Transfer_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    Toast.makeText(Transfer_Details_Activity.this, "starting success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Transfer_Details_Activity.this, "boot failure", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }.execute();
    }

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(Transfer_Details_Activity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.isTitleShow(false)//
                .btnNum(3)
                .content("pass")//
                .btnText("cancel", "pass", "no pass")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("1", text);
                        dialog.dismiss();
                    }
                },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        wfgoon("0", text.equals("pass") ? "no pass" : text);
                        dialog.dismiss();
                    }
                }
        );
    }

    /**
     * 审批工作流
     *
     * @param zx
     */
    private void wfgoon(final String zx, final String desc) {
        mProgressDialog = ProgressDialog.show(Transfer_Details_Activity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.approve(Transfer_Details_Activity.this,
                        "ASTTRANS", "INVUSE", invuse.getINVUSEID() + "", "INVUSEID", zx, desc,
                        AccountUtils.getpersonId(Transfer_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    MessageUtils.showMiddleToast(Transfer_Details_Activity.this, s.errorMsg);
                } else if (s.wonum.equals(invuse.getINVUSEID() + "") && s.errorMsg != null) {
                    statusTextView.setText(s.errorMsg);
                    invuse.setSTATUS(s.errorMsg);
                    MessageUtils.showMiddleToast(Transfer_Details_Activity.this, "Approval success!");
                } else {
                    MessageUtils.showMiddleToast(Transfer_Details_Activity.this, s.errorMsg);
                }
            }
        }.execute();
    }


    private SwipeRefreshLayout.OnRefreshListener refreshOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            getData();
        }
    };

    private SwipeRefreshLayout.OnLoadListener refreshOnLoadListener = new SwipeRefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            page++;
            getData();
        }
    };


    /**
     * 获取数据*
     */
    private void initAdapter(final List<INVUSELINE> list) {
        nodatalayout.setVisibility(View.GONE);
        invuseLineAdapter = new InvuseLineAdapter(Transfer_Details_Activity.this, R.layout.list_transfer_item, list);
        recyclerView.setAdapter(invuseLineAdapter);
        invuseLineAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(Transfer_Details_Activity.this, HttpManager.getINVUSELINEURL(invuse.getINVUSENUM(), page, 20,""), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<INVUSELINE> item = JsonUtils.parsingINVUSELINE(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<INVUSELINE>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(Transfer_Details_Activity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            for (int i = 0; i < item.size(); i++) {
                                items.add(item.get(i));
                            }
                            addData(item);
                        }
                    }
                    nodatalayout.setVisibility(View.GONE);
                    // initAdapter(items);
                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * 添加数据*
     */
    private void addData(final List<INVUSELINE> list) {
        invuseLineAdapter.addData(list);
    }

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Transfer_Details_Activity.this, TransferLine_AddNew_Activity.class);
            intent.putExtra("invusenum", invuse.getINVUSENUM());
            intent.putExtra("storeroom", invuse.getFROMSTORELOC());
            startActivity(intent);
        }
    };

}
