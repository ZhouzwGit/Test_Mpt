package com.mpt.hxqh.mpt_project.ui.actvity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.mpt.hxqh.mpt_project.adpter.UdretirelineAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.UDRETIRE;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.WorkFlowResult;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

/**
 * 报废详情
 **/
public class Udretire_Details_Activity extends BaseActivity {

    private static final String TAG = "Udretire_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView retireNotView; //	Retire No
    private TextView descriptionTextView; //description
    private TextView locationTextView; //location
    private TextView statusTextView; //status
    private TextView retireDateTextView; //	Retire Date

    private UDRETIRE udretire;

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
    private UdretirelineAdapter udretirelineAdapter;

    private int page = 1;

    ArrayList<UDRETIRELINE> items = new ArrayList<UDRETIRELINE>();

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private String[] optionList = new String[]{"Back", "Route", "Scan", "AddLine"};

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udretire_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        udretire = (UDRETIRE) getIntent().getSerializableExtra("udretire");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        retireNotView = (TextView) findViewById(R.id.retire_no_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        locationTextView = (TextView) findViewById(R.id.location_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        retireDateTextView = (TextView) findViewById(R.id.retire_date_text_id);

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
        titleTextView.setText(R.string.asset_retirement_text);

        buttonLayout.setVisibility(View.VISIBLE);
        if (udretire != null) {
            retireNotView.setText(udretire.getRETIRENUM());
            descriptionTextView.setText(udretire.getDESCRIPTION());
            locationTextView.setText(udretire.getLOCATION());
            statusTextView.setText(udretire.getSTATUS());
            retireDateTextView.setText(udretire.getRETIREDATE());
        }


        layoutManager = new LinearLayoutManager(Udretire_Details_Activity.this);
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
        initAdapter(new ArrayList<UDRETIRELINE>());
        getData();

        quit.setOnClickListener(quitOnClickListener);
        option.setOnClickListener(optionOnClickListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
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
            final NormalDialog dialog = new NormalDialog(Udretire_Details_Activity.this);
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
                            AppManager.AppExit(Udretire_Details_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Udretire_Details_Activity.this, optionList);
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
                            if (udretire.getSTATUS().equals(Constants.ASSTRETIRE_START)) {//启动工作流
                                MaterialDialogOneBtn();
                            } else if (!udretire.getSTATUS().equals(Constants.ASSTRETIRE_END)) {//审批工作流
                                EditDialog();
                            } else {
                                MessageUtils.showMiddleToast(Udretire_Details_Activity.this, "Workflow is finished; cannot start again");
                            }
                            break;
                        case 2:
                            normalListDialog.superDismiss();
                            if ("WHAPPR".equals(udretire.STATUS) || "APPR".equals(udretire.STATUS)) {
                                Intent lineintent = new Intent(Udretire_Details_Activity.this, UdretireLine_Activity.class);
                                lineintent.putExtra("assetnum", udretire.getRETIRENUM());
                                lineintent.putExtra("status",udretire.getSTATUS());
                                startActivity(lineintent);
                            }else {
                                Toast.makeText(Udretire_Details_Activity.this,"Not in scaning status",Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case 3://AddLine
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(Udretire_Details_Activity.this, UdretireLine_AddNew_Activity.class);
                            intent.putExtra("repairnum", udretire.getRETIRENUM());
                            intent.putExtra("LOCATION", udretire.getLOCATION());
                            startActivity(intent);
                            break;
                    }
//                    normalListDialog.dismiss();
                }
            });
        }
    };

    private void MaterialDialogOneBtn() {//开始工作流
        final MaterialDialog2 dialog = new MaterialDialog2(Udretire_Details_Activity.this);
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
        mProgressDialog = ProgressDialog.show(Udretire_Details_Activity.this, null,
                getString(R.string.start), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.startwf(Udretire_Details_Activity.this,
                        "ASSTRETIRE", "UDRETIRE", udretire.getRETIRENUM(), "RETIRENUM", AccountUtils.getpersonId(Udretire_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                if (s != null && s.errorMsg != null && s.errorMsg.equals("工作流启动成功")) {
                    MessageUtils.showMiddleToast(Udretire_Details_Activity.this, "starting success!");
                } else {
                    MessageUtils.showMiddleToast(Udretire_Details_Activity.this, s.errorMsg);
                }

            }
        }.execute();
    }

    private void EditDialog() {//输入审核意见
        final NormalEditTextDialog dialog = new NormalEditTextDialog(Udretire_Details_Activity.this);
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
        mProgressDialog = ProgressDialog.show(Udretire_Details_Activity.this, null,
                getString(R.string.approve), true, true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        new AsyncTask<String, String, WorkFlowResult>() {
            @Override
            protected WorkFlowResult doInBackground(String... strings) {
                WorkFlowResult result = AndroidClientService.approve(Udretire_Details_Activity.this,
                        "ASSTRETIRE", "UDRETIRE", udretire.getUDRETIREID() + "", "UDRETIREID", zx, desc,
                        AccountUtils.getpersonId(Udretire_Details_Activity.this));
                return result;
            }

            @Override
            protected void onPostExecute(WorkFlowResult s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                if (s == null || s.wonum == null || s.errorMsg == null) {
                    MessageUtils.showMiddleToast(Udretire_Details_Activity.this, s.errorMsg);
                } else if (s.wonum.equals(udretire.getUDRETIREID() + "") && s.errorMsg != null) {
                    statusTextView.setText(s.errorMsg);
                    udretire.setSTATUS(s.errorMsg);
                    MessageUtils.showMiddleToast(Udretire_Details_Activity.this, "Approval success!");
                } else {
                    MessageUtils.showMiddleToast(Udretire_Details_Activity.this, s.errorMsg);
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
    private void initAdapter(final List<UDRETIRELINE> list) {
        nodatalayout.setVisibility(View.GONE);
        udretirelineAdapter = new UdretirelineAdapter(Udretire_Details_Activity.this, R.layout.list_transfer_item, list);
        recyclerView.setAdapter(udretirelineAdapter);
        udretirelineAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {
       // Log.i(TAG, "num=" + udretire.getRETIRENUM());
        HttpManager.getDataPagingInfo(Udretire_Details_Activity.this, HttpManager.getUDRETIRELINEURL(udretire.getRETIRENUM(), page, 20, ""), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDRETIRELINE> item = JsonUtils.parsingUDRETIRELINE(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                        if (item != null || item.size() != 0) {
                            if (page == 1) {
                                items = new ArrayList<UDRETIRELINE>();
                                initAdapter(items);
                            }
                            if (page > totalPages) {
                                MessageUtils.showMiddleToast(Udretire_Details_Activity.this, getString(R.string.have_load_out_all_the_data));
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
    private void addData(final List<UDRETIRELINE> list) {
        udretirelineAdapter.addData(list);
    }

}
