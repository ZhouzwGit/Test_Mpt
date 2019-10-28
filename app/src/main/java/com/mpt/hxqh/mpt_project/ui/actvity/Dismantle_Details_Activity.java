package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
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
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.adpter.DismantleLineAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.DISMANTLE;
import com.mpt.hxqh.mpt_project.model.DISMANTLELINE;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产拆除详情
 **/
public class Dismantle_Details_Activity extends BaseActivity {

    private static final String TAG = "Dismantle_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView ordernum; //order
    private TextView descriptionTextView; //description
    private TextView statusTextView; //fromstoreroom
    private TextView createdate; //invowner
    private TextView location;

    private DISMANTLE dismantle;
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
    private DismantleLineAdapter dismantleLineAdapter;
    private DismantleLineAdapter udreplaceAdapter;

    private Button planButton;
    private Button actualButton;

    private int page = 1;
    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;

    ArrayList<DISMANTLELINE> item1 = new ArrayList<DISMANTLELINE>();
    ArrayList<DISMANTLELINE> item2 = new ArrayList<DISMANTLELINE>();
    private int position = 0;

    //    private FloatingActionButton addButton;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private String[] optionList = new String[]{"Back","Scan"};
    private String  line ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismantle_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        dismantle = (DISMANTLE) getIntent().getSerializableExtra("dismantle");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        ordernum = (TextView) findViewById(R.id.ordernum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        createdate = (TextView) findViewById(R.id.create_date_text_id);
        location = (TextView) findViewById(R.id.location_text_id);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);

        planButton = (Button) findViewById(R.id.dismantle_button);
        actualButton = (Button) findViewById(R.id.replacement_button);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);
//        addButton = (FloatingActionButton) findViewById(R.id.add_flaButton);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        backImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.Material_outbound_text);

        buttonLayout.setVisibility(View.VISIBLE);
        if (dismantle != null) {
            ordernum.setText(dismantle.getUDORDERNUM());
            descriptionTextView.setText(dismantle.getDESCRIPTION());
            statusTextView.setText(dismantle.getSTATUS());
            createdate.setText(dismantle.getCREATEDATE());
            location.setText(dismantle.getLOCATION());
        }


        layoutManager = new LinearLayoutManager(Dismantle_Details_Activity.this);
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
        initAdapter1(new ArrayList<DISMANTLELINE>());
        getData1();

        planButton.setOnClickListener(planOnClickListener);
        actualButton.setOnClickListener(actualOnClickListener);
//        addButton.setOnClickListener(addOnClickListener);

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
            final NormalDialog dialog = new NormalDialog(Dismantle_Details_Activity.this);
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
                            AppManager.AppExit(Dismantle_Details_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Dismantle_Details_Activity.this, optionList);
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
//                        case 1://Route
//                            break;
                        case 1://Scan
                            if ("WHAPPR".equals(dismantle.getSTATUS()) || "APPR".equalsIgnoreCase(dismantle.getSTATUS())){
                                Intent lineIntent = new Intent(Dismantle_Details_Activity.this, DismantleScan_Activity.class);
                                lineIntent.putExtra("assetnum",dismantle.getUDORDERNUM());
                                lineIntent.putExtra("status",dismantle.getSTATUS());
                                lineIntent.putExtra("line",line);
                                startActivity(lineIntent);
                            }else {
                                Toast.makeText(Dismantle_Details_Activity.this,"Not in scaning status",Toast.LENGTH_SHORT).show();
                            }
                            break;

//                            normalListDialog.superDismiss();
//                            break;
                        case 2://Add actural
                            normalListDialog.superDismiss();
                            break;
                        case 3:

                    }
                    normalListDialog.dismiss();
                }
            });
        }
    };

    private View.OnClickListener planOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) { //
            position = 0;
            line = "Asset";
            initAdapter1(new ArrayList<DISMANTLELINE>());
            getData1();
            planButton.setBackgroundResource(R.drawable.button_selector);
            actualButton.setBackgroundResource(R.drawable.button_selector2);
        }
    };

    private View.OnClickListener actualOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) { //实际
            line = "Replace";
            position = 1;
            initAdapter2(new ArrayList<DISMANTLELINE>());
            getData2();
            planButton.setBackgroundResource(R.drawable.button_selector2);
            actualButton.setBackgroundResource(R.drawable.button_selector);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            if (position == 0) {
                getData1();
            } else {
                getData2();
            }
        }
    };

    private SwipeRefreshLayout.OnLoadListener refreshOnLoadListener = new SwipeRefreshLayout.OnLoadListener() {
        @Override
        public void onLoad() {
            page++;
            if (position == 0) {
                getData1();
            } else {
                getData2();
            }
        }
    };


    /**
     * 获取数据*
     */
    private void initAdapter1(final List<DISMANTLELINE> list) {
        nodatalayout.setVisibility(View.GONE);
        dismantleLineAdapter = new DismantleLineAdapter(Dismantle_Details_Activity.this, R.layout.list_transfer_item, list);
        recyclerView.setAdapter(dismantleLineAdapter);
    }

    /**
     * 获取数据*
     */
    private void initAdapter2(final List<DISMANTLELINE> list) {
        nodatalayout.setVisibility(View.GONE);
        udreplaceAdapter = new DismantleLineAdapter(Dismantle_Details_Activity.this, R.layout.list_transfer_item, list);
        recyclerView.setAdapter(udreplaceAdapter);
    }


    /**
     * 获取数据*
     */
    private void getData1() {
        HttpManager.getDataPagingInfo(Dismantle_Details_Activity.this, HttpManager.getDismantleLineURL(dismantle.getUDORDERNUM(), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<DISMANTLELINE> item = JsonUtils.parsingDismantleLine(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            item1 = new ArrayList<DISMANTLELINE>();
                            initAdapter1(item1);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(Dismantle_Details_Activity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            addData(item);
                        }
                    }
                    nodatalayout.setVisibility(View.GONE);

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
     * 获取数据*
     */
    private void getData2() {
        HttpManager.getDataPagingInfo(Dismantle_Details_Activity.this, HttpManager.getUdreplaceURL(dismantle.getUDORDERNUM(), page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<DISMANTLELINE> item = JsonUtils.parsingDismantleLine(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            item2 = new ArrayList<DISMANTLELINE>();
                            initAdapter2(item2);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(Dismantle_Details_Activity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            addData2(item);
                        }
                    }
                    nodatalayout.setVisibility(View.GONE);
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
    private void addData(final List<DISMANTLELINE> list) {
        dismantleLineAdapter.addData(list);
    }

    /**
     * 添加数据*
     */
    private void addData2(final List<DISMANTLELINE> list) {
        udreplaceAdapter.addData(list);
    }

}
