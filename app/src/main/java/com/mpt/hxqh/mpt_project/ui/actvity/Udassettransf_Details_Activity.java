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
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.UdtransflineAdapter;
import com.mpt.hxqh.mpt_project.adpter.Udtransfline_ScanAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.UDASSETTRANSF;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 移动详情
 **/
public class Udassettransf_Details_Activity extends BaseActivity {

    private static final String TAG = "Udassettransf_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView orderTextView; //order
    private TextView descriptionTextView; //description
    private TextView depTextView; //From Department
    private TextView fromlocTextView;//
    private TextView tositeTextView;//
    private TextView statusTextView; //status

    private UDASSETTRANSF udassettransf;

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
    private UdtransflineAdapter udtransflineAdapter;

    private int page = 1;

    ArrayList<UDTRANSFLINE> items = new ArrayList<UDTRANSFLINE>();

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;

    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    private String[] optionList = new String[]{"Back", "Scan", "AddLine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udassettransf_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void initData() {
        udassettransf = (UDASSETTRANSF) getIntent().getSerializableExtra("udassettransf");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);

        orderTextView = (TextView) findViewById(R.id.order_text_id);
        descriptionTextView = (TextView) findViewById(R.id.description_text_id);
        depTextView = (TextView) findViewById(R.id.from_department_text_id);
        fromlocTextView = (TextView) findViewById(R.id.fromloc_text_id);
        tositeTextView = (TextView) findViewById(R.id.tosite_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);

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
        titleTextView.setText(R.string.asset_management_text);

        buttonLayout.setVisibility(View.VISIBLE);
        if (udassettransf != null) {
            orderTextView.setText(udassettransf.getASSETTRANNUM());
            descriptionTextView.setText(udassettransf.getDESCRIPTION());
            depTextView.setText(udassettransf.getFROMDEPT());
            fromlocTextView.setText(udassettransf.getFROMLOC());
            tositeTextView.setText(udassettransf.getTOSITE());
            statusTextView.setText(udassettransf.getSTATUS());
        }


        layoutManager = new LinearLayoutManager(Udassettransf_Details_Activity.this);
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
        initAdapter(new ArrayList<UDTRANSFLINE>());
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
            final NormalDialog dialog = new NormalDialog(Udassettransf_Details_Activity.this);
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
                            AppManager.AppExit(Udassettransf_Details_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Udassettransf_Details_Activity.this, optionList);
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
                        case 1://Scan
                            normalListDialog.superDismiss();
                           if ( "WHAPPR".equals(udassettransf.STATUS) || "APPR".equals(udassettransf.STATUS)){
                               Intent lineIntent = new Intent(Udassettransf_Details_Activity.this, Udtransfline_Activity.class);
                               lineIntent.putExtra("assetnum", udassettransf.getASSETTRANNUM());
                               lineIntent.putExtra("status", udassettransf.getSTATUS());
                               startActivity(lineIntent);
                           }else {
                               Toast.makeText(Udassettransf_Details_Activity.this,"Not in scaning status",Toast.LENGTH_SHORT).show();
                           }
                            break;
                        case 2://AddLine
                            normalListDialog.superDismiss();
                            Intent intent = new Intent(Udassettransf_Details_Activity.this, UdassettransfLine_AddNew_Activity.class);
                            intent.putExtra("assettrannum", udassettransf.getASSETTRANNUM());
                            intent.putExtra("LOCATION", udassettransf.getFROMLOC());
                            startActivity(intent);
                            break;
                    }
                    normalListDialog.dismiss();
                }
            });
        }
    };

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
    private void initAdapter(final List<UDTRANSFLINE> list) {
        nodatalayout.setVisibility(View.GONE);
        udtransflineAdapter = new UdtransflineAdapter(Udassettransf_Details_Activity.this, R.layout.list_udassettransf_item, list);
        recyclerView.setAdapter(udtransflineAdapter);
        udtransflineAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo(Udassettransf_Details_Activity.this, HttpManager.getUDTRANSFLINEURL(udassettransf.getASSETTRANNUM(), page, 20, ""), new HttpRequestHandler<Results>() {
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

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<UDTRANSFLINE>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(Udassettransf_Details_Activity.this, getString(R.string.have_load_out_all_the_data));
                        } else {
                            for (int i = 0; i < item.size(); i++) {
                                items.add(item.get(i));
                            }
                            addData(item);
                        }
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
        udtransflineAdapter.addData(list);
    }

}
