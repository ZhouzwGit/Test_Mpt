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
import com.mpt.hxqh.mpt_project.adpter.Matusetrans_ScanAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.MATUSETRANS;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzw on 2018/12/18.
 */

public class MatusetransScan_Activity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "UdretireLine_Activity";

    private static final int TRANSFERLINE_CODE = 1093;
    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView , itemnum;

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
    private Matusetrans_ScanAdapter matusetrans_scanAdapter;
    private int page = 1;
    ArrayList<MATUSETRANS> items = new ArrayList<MATUSETRANS>();
    String mainnum;
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
        itemnum = (TextView) findViewById(R.id.assetnum_id);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(this);
        quit.setOnClickListener(this);
        option.setOnClickListener(this);
        scanButton.setOnClickListener(this);
        itemnum.setText("Itemnum");
        titleTextView.setText("Material Outbound");
        buttonLayout.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(MatusetransScan_Activity.this);
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
        initAdapter(new ArrayList<MATUSETRANS>());
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
                Intent scanIntent = new Intent(MatusetransScan_Activity.this, MipcaActivityCapture.class);
                scanIntent.putExtra("mark", 1); //扫码标识
                startActivityForResult(scanIntent, TRANSFERLINE_CODE);
                break;
            case R.id.quit:
                final NormalDialog dialog = new NormalDialog(MatusetransScan_Activity.this);
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
                                AppManager.AppExit(MatusetransScan_Activity.this);
                            }
                        });
                break;
            case R.id.option:
                final NormalListDialog normalListDialog = new NormalListDialog(MatusetransScan_Activity.this, optionlist);
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
    private void initAdapter(final List<MATUSETRANS> list) {
        nodatalayout.setVisibility(View.GONE);
        matusetrans_scanAdapter = new Matusetrans_ScanAdapter(MatusetransScan_Activity.this, R.layout.udretireline_item, list, status);
        recyclerView.setAdapter(matusetrans_scanAdapter);
        matusetrans_scanAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
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
        final NormalEditTextDialog dialog = new NormalEditTextDialog(MatusetransScan_Activity.this);
        dialog.title("Please write the remark");
        dialog.btnNum(2).btnText(new String[]{"CANCEL", "CONFIRM"}).showAnim(mBasIn).dismissAnim(mBasOut).content(items.get(position).UDREMARK == null ? "" : items.get(position).UDREMARK).show();
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
        HttpManager.getDataPagingInfo(MatusetransScan_Activity.this, HttpManager.getMATUSETRANSURL(mainnum, page, 20, ""), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<MATUSETRANS> item = JsonUtils.parsingMATUSETRANS(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1) {
                        items = new ArrayList<MATUSETRANS>();
                        initAdapter(items);
                    }
                    if (page > totalPages) {
                        MessageUtils.showMiddleToast(MatusetransScan_Activity.this, getString(R.string.have_load_out_all_the_data));
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
    private void addData(final List<MATUSETRANS> list) {
        matusetrans_scanAdapter.addData(list);
    }

    private void initData() {
        mainnum = getIntent().getStringExtra("assetnum");
        status = getIntent().getStringExtra("status");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TRANSFERLINE_CODE) {
            if (data != null) {
                String results = data.getExtras().getString("result");
                //String results = "2018122111111111";
                isExistSN(results);
                Toast.makeText(MatusetransScan_Activity.this, results, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isExistSN(final String result) {
        HttpManager.getDataPagingInfo(MatusetransScan_Activity.this, HttpManager.getMATUSETRANSURL(mainnum, page, 1, result), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {

            }

            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {
                ArrayList<MATUSETRANS> item = JsonUtils.parsingMATUSETRANS(data.getResultlist());
                if (item == null || item.isEmpty()) {
                    final NormalDialog normalDialog = new NormalDialog(MatusetransScan_Activity.this);
                    normalDialog.title("");
                    normalDialog.content("There is no item's SN like " + result).showAnim(mBasIn).dismissAnim(mBasOut).btnNum(1).btnText("Confirm").show();
                    normalDialog.setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            normalDialog.dismiss();
                        }
                    });
                } else {
                    if ("APPR".equals(status)) {
                        if (item.get(0).getSCANSN() != null && item.get(0).getSCANSN().equals(item.get(0).getSERIALNUM())) {
                            Toast.makeText(MatusetransScan_Activity.this, "The SN is already scaned", Toast.LENGTH_SHORT).show();
                        } else {
                            showConfirmDialog(item.get(0));
                        }
                    } else {
                        if (item.get(0).SECSCAN != null && item.get(0).SECSCAN.equals(item.get(0).getSERIALNUM())) {
                            Toast.makeText(MatusetransScan_Activity.this, "The SN is already scaned", Toast.LENGTH_SHORT).show();
                        } else {
                            showConfirmDialog(item.get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                final NormalDialog normalDialog = new NormalDialog(MatusetransScan_Activity.this);
                normalDialog.setTitle("");
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

    private void showConfirmDialog(final MATUSETRANS invuseline) {
        final NormalDialog normalDialog = new NormalDialog(MatusetransScan_Activity.this);
        normalDialog.setTitle("");
        normalDialog.content("The SN is Exist! \n Sure to save the sn " + invuseline.getSERIALNUM()).showAnim(mBasIn).dismissAnim(mBasOut).show();
        normalDialog.btnText(new String[]{"Cancel","Confirm"});
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
                            if ((items.get(i).getSERIALNUM()==null?"":items.get(i).getSERIALNUM()).equals(invuseline.getSERIALNUM())) {
                                if ("APPR".equals(status)) {
                                    items.get(i).setSCANSN(invuseline.getSERIALNUM());
                                    invuseline.setSCANSN(invuseline.getSERIALNUM());
                                    flag = true;
                                    break;
                                } else {
                                    items.get(i).setSECSCAN(invuseline.getSERIALNUM());
                                    invuseline.setSECSCAN(invuseline.getSERIALNUM());
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            if ("APPR".equals(status)) {
                                invuseline.setSCANSN(invuseline.SERIALNUM);
                            } else {
                                invuseline.setSECSCAN(invuseline.SERIALNUM);
                            }
                            items.add(invuseline);
                        }
                        initAdapter(items);
                        saveUdRetireLine(invuseline);
                        normalDialog.dismiss();
                    }
                });
    }

    public void saveUdRetireLine(final MATUSETRANS matusetrans) {
        new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... strings) {
                String results = AndroidClientService.UpdateWO(MatusetransScan_Activity.this, matusetrans.toString(), "MATUSETRANS", "MATUSETRANSID", matusetrans.MATUSETRANSID, Constants.WORK_URL);
                return results;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null && s.equals("成功")){
                    Toast.makeText(MatusetransScan_Activity.this, "Succeed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MatusetransScan_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
