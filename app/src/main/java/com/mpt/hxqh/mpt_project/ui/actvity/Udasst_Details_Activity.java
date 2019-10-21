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
import com.mpt.hxqh.mpt_project.adpter.UdasstrepAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.UDASST;
import com.mpt.hxqh.mpt_project.model.UDASSTREP;
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
public class Udasst_Details_Activity extends BaseActivity {

    private static final String TAG = "Transfer_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView orderTextView; //Order
    private TextView descriptionTextView; //description
    private TextView locationTextView; //location
    private TextView statusTextView; //status
    private TextView installDateTextView; //Install Date

    private UDASST udasst;

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
    private UdasstrepAdapter udasstrepAdapter;

    private int page = 1;

    ArrayList<UDASSTREP> items = new ArrayList<UDASSTREP>();

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private NormalListDialog normalListDialog;
    private String[] optionList = new String[]{"Back", "Route", "AddLine"};

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udasst_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        udasst = (UDASST) getIntent().getSerializableExtra("udasst");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        orderTextView = (TextView) findViewById(R.id.order_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        locationTextView = (TextView) findViewById(R.id.location_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        installDateTextView = (TextView) findViewById(R.id.install_date_text_id);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
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
        titleTextView.setText(R.string.asset_repair_text);

        buttonLayout.setVisibility(View.VISIBLE);
        if (udasst != null) {
            orderTextView.setText(udasst.getREPAIRNUM());
            descriptionTextView.setText(udasst.getDESCRIPTION());
            locationTextView.setText(udasst.getLOCATION());
            statusTextView.setText(udasst.getUDSTATUS());
            installDateTextView.setText(udasst.getUDREPDATE());
        }


        layoutManager = new LinearLayoutManager(Udasst_Details_Activity.this);
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
        initAdapter(new ArrayList<UDASSTREP>());
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
            final NormalDialog dialog = new NormalDialog(Udasst_Details_Activity.this);
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
                            AppManager.AppExit(Udasst_Details_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            normalListDialog = new NormalListDialog(Udasst_Details_Activity.this, optionList);
            normalListDialog.title("Option")
                    .showAnim(mBasIn)//
                    .dismissAnim(mBasOut)//
                    .show();
            normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0://Back
                            normalListDialog.superDismiss();
                            finish();
                            break;
                        case 1://Route
                            normalListDialog.superDismiss();
                            if (udasst.getUDSTATUS().equals(Constants.ASSETREP_START)) {//启动工作流
                                MaterialDialogOneBtn();
                            } else if (!udasst.getUDSTATUS().equals(Constants.ASSETREP_END)) {//审批工作流
                                EditDialog();
                            } else {
                                MessageUtils.showMiddleToast(Udasst_Details_Activity.this, "Workflow is finished; cannot start again");
                            }
                            break;
                        case 2://AddLine
                            normalListDialog.superDismiss();
                            Intent intent=getIntent();
                            intent.setClass(Udasst_Details_Activity.this, UdasstLine_AddNew_Activity.class);
                            intent.putExtra("repairnum", udasst.getREPAIRNUM());
                            startActivity(intent);

                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(Udasst_Details_Activity.this);
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
        mProgressDialog = ProgressDialog.show(Udasst_Details_Activity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.startwf(Udasst_Details_Activity.this,
                        "ASSETREP", "UDASST", udasst.getUDASSTNUM(), "UDASSTNUM", AccountUtils.getpersonId(Udasst_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    MessageUtils.showMiddleToast(Udasst_Details_Activity.this,"starting success!");
                } else {
                    MessageUtils.showMiddleToast(Udasst_Details_Activity.this,s.errorMsg);
                }
            }
        }.execute();
    }

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(Udasst_Details_Activity.this);
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
        mProgressDialog = ProgressDialog.show(Udasst_Details_Activity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.approve(Udasst_Details_Activity.this,
                        "ASSETREP", "UDASST", udasst.getUDASSTID() + "", "UDASSTID", zx, desc,
                        AccountUtils.getpersonId(Udasst_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    MessageUtils.showMiddleToast(Udasst_Details_Activity.this, s.errorMsg);
                } else if (s.wonum.equals(udasst.getUDASSTID() + "") && s.errorMsg != null) {
                    statusTextView.setText(s.errorMsg);
                    udasst.setUDSTATUS(s.errorMsg);
                    MessageUtils.showMiddleToast(Udasst_Details_Activity.this, "Approval success!");
                } else {
                    MessageUtils.showMiddleToast(Udasst_Details_Activity.this, s.errorMsg);
                }
                mProgressDialog.dismiss();
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
    private void initAdapter(final List<UDASSTREP> list) {
        nodatalayout.setVisibility(View.GONE);
        udasstrepAdapter = new UdasstrepAdapter(Udasst_Details_Activity.this, R.layout.list_udasstrep_item, list);
        recyclerView.setAdapter(udasstrepAdapter);
        udasstrepAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(Udasst_Details_Activity.this, HttpManager.getUDASSTREPURL(udasst.getREPAIRNUM(), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDASSTREP> item = JsonUtils.parsingUDASSTREP(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<UDASSTREP>();
                            initAdapter(items);
                        }
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                    initAdapter(items);
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
    private void addData(final List<UDASSTREP> list) {
        udasstrepAdapter.addData(list);
    }

}
