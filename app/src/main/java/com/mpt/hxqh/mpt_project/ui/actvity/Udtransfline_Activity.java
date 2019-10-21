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
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.Udretireline_ScanAdapter;
import com.mpt.hxqh.mpt_project.adpter.Udtransfline_ScanAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzw on 2018/12/18.
 */

public class Udtransfline_Activity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "UdretireLine_Activity";

    private static final int UDTRANSFLINE_CODE = 1093;
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;

    /**
     * 扫描
     **/
    private Button scanButton;


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
    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private Udtransfline_ScanAdapter udtransflineScanAdapter;
    private int page = 1;
    ArrayList<UDTRANSFLINE> items = new ArrayList<UDTRANSFLINE>();
    String retirenum;
    String status;
    String[] optionlist = {"Back"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udretireline_detail);
        findViewById();
        initView();
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        scanButton = (Button) findViewById(R.id.snscan_button_id);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(this);
        quit.setOnClickListener(this);
        option.setOnClickListener(this);
        scanButton.setOnClickListener(this);

        titleTextView.setText(R.string.asset_management_text);
        buttonLayout.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(Udtransfline_Activity.this);
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
        initData();
        initAdapter(new ArrayList<UDTRANSFLINE>());
        getData();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_id:
                finish();
                break;
            case R.id.snscan_button_id:
                Intent scanIntent = new Intent(Udtransfline_Activity.this, MipcaActivityCapture.class);
                scanIntent.putExtra("mark", 1); //扫码标识
                startActivityForResult(scanIntent, UDTRANSFLINE_CODE);
                break;
            case R.id.quit:
                final NormalDialog dialog = new NormalDialog(Udtransfline_Activity.this);
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
                                AppManager.AppExit(Udtransfline_Activity.this);
                            }
                        });
                break;
            case R.id.option:
                final NormalListDialog normalListDialog = new NormalListDialog(Udtransfline_Activity.this, optionlist);
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
                       /*     case 1:
                                normalListDialog.superDismiss();
                                Toast.makeText(UdretireLine_Activity.this, "提交", Toast.LENGTH_SHORT).show();
                                break;*/
                        }
                    }
                });
                break;
        }
    }

    /**
     * 获取数据*
     */
    private void initAdapter(final List<UDTRANSFLINE> list) {
        nodatalayout.setVisibility(View.GONE);
        udtransflineScanAdapter = new Udtransfline_ScanAdapter(Udtransfline_Activity.this, R.layout.udretireline_item, list, status);
        recyclerView.setAdapter(udtransflineScanAdapter);
        udtransflineScanAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.getItem(position);
                if (view.getId() == R.id.match_id) {
                    showEditDialog(position);
                }
            }
        });
    }

    private void showEditDialog(final int position) {
        final NormalEditTextDialog dialog = new NormalEditTextDialog(Udtransfline_Activity.this);
        dialog.title("Please write the remark");
        dialog.btnNum(2).btnText(new String[]{"CANCEL", "CONFIRM"}).showAnim(mBasIn).dismissAnim(mBasOut).content(items.get(position).UDREMARK==null?"":items.get(position).UDREMARK).show();
        dialog.setOnBtnClickL(new OnBtnEditClickL() {
                                  @Override
                                  public void onBtnClick(String text) {
                                      dialog.dismiss();
                                  }
                              },
                new OnBtnEditClickL() {
                    @Override
                    public void onBtnClick(String text) {
                        dialog.dismiss();
                        items.get(position).setUDREMARK(text);
                        initAdapter(items);
                        saveUdRetireLine(items.get(position));
                    }
                });
    }

    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(Udtransfline_Activity.this, HttpManager.getUDTRANSFLINEURL(retirenum, page, 20, ""), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDTRANSFLINE> item = JsonUtils.parsingUDTRANSFLINE(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1) {
                        items = new ArrayList<UDTRANSFLINE>();
                        initAdapter(items);
                    }
                    if (page > totalPages) {
                        MessageUtils.showMiddleToast(Udtransfline_Activity.this, getString(R.string.have_load_out_all_the_data));
                    } else {
                        for (int i = 0; i < item.size(); i++) {
                            items.add(item.get(i));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);
                    //initAdapter(items);
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
    private void addData(final List<UDTRANSFLINE> list) {
        udtransflineScanAdapter.addData(list);
    }

    private void initData() {
        retirenum = getIntent().getStringExtra("assetnum");
        status = getIntent().getStringExtra("status");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UDTRANSFLINE_CODE) {
            if (data != null) {
                String results = data.getExtras().getString("result");
                isExistSN(results);
                Toast.makeText(Udtransfline_Activity.this, results, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isExistSN(final String result) {
        HttpManager.getDataPagingInfo(Udtransfline_Activity.this, HttpManager.getUDTRANSFLINEURL(retirenum, page, 1, result), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {

            }

            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {
                ArrayList<UDTRANSFLINE> item = JsonUtils.parsingUDTRANSFLINE(data.getResultlist());
                if (item == null || item.isEmpty()) {
                    final NormalDialog normalDialog = new NormalDialog(Udtransfline_Activity.this);
                    normalDialog.setTitle("0");
                    normalDialog.content("There is no item's SN like " + result).showAnim(mBasIn).dismissAnim(mBasOut).btnNum(1).btnText("Confirm").show();
                    normalDialog.setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            normalDialog.dismiss();
                        }
                    });
                } else {
                    if ("WHAPPR".equals(status)) {
                        if (item.get(0).getSCANSN() != null && item.get(0).getSCANSN().equals(item.get(0).getSERIALNUM())) {
                            Toast.makeText(Udtransfline_Activity.this, "The SN is already scaned", Toast.LENGTH_SHORT).show();
                        } else {
                            showConfirmDialog(item.get(0));
                        }
                    } else {
                        if (item.get(0).SECSCAN != null && item.get(0).SECSCAN.equals(item.get(0).getSERIALNUM())) {
                            Toast.makeText(Udtransfline_Activity.this, "The SN is already scaned", Toast.LENGTH_SHORT).show();
                        } else {
                            showConfirmDialog(item.get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                final NormalDialog normalDialog = new NormalDialog(Udtransfline_Activity.this);
                normalDialog.setTitle("1");
                normalDialog.content("There is no item's SN like " + result).showAnim(mBasIn).dismissAnim(mBasOut).btnNum(1).btnText("Confirm").show();
                normalDialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        normalDialog.dismiss();
                    }
                });
            }
        });
    }

    private void showConfirmDialog(final UDTRANSFLINE udretireline) {
        final NormalDialog normalDialog = new NormalDialog(Udtransfline_Activity.this);
        normalDialog.setTitle("");
        normalDialog.content("The SN is Exist! \n Sure to save the sn " + udretireline.getSERIALNUM()).showAnim(mBasIn).dismissAnim(mBasOut).show();
        normalDialog.setOnBtnClickL(new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            normalDialog.dismiss();
                                        }
                                    },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        boolean flag = false;
                        for (int i = 0; i < items.size(); i++) {
                            if ((items.get(i).SERIALNUM==null?"":items.get(i).SERIALNUM).equals(udretireline.getSERIALNUM())) {
                                if ("WHAPPR".equals(status)) {
                                    items.get(i).setSCANSN(udretireline.getSERIALNUM());
                                    udretireline.setSCANSN(udretireline.getSERIALNUM());
                                    flag = true;
                                    break;
                                } else {
                                    items.get(i).setSECSCAN(udretireline.getSERIALNUM());
                                    udretireline.setSECSCAN(udretireline.getSERIALNUM());
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            if ("WHAPPR".equals(status)) {
                                udretireline.setSCANSN(udretireline.SERIALNUM);
                            } else {
                                udretireline.setSCANSN(udretireline.SERIALNUM);
                                udretireline.setSECSCAN(udretireline.SERIALNUM);
                            }
                            items.add(udretireline);
                        }
                        initAdapter(items);
                        saveUdRetireLine(udretireline);
                        normalDialog.dismiss();
                    }
                });
    }

    public void saveUdRetireLine(final UDTRANSFLINE udretireline) {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... strings) {
                String results = AndroidClientService.UpdateWO(Udtransfline_Activity.this, udretireline.toString(), "UDTRANSFLINE", "UDTRANSFLINEID", udretireline.UDTRANSFLINEID, Constants.WORK_URL);
                return results;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(Udtransfline_Activity.this, "成功".equals(s)?"Succeed":"Fail", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
