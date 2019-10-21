package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.LocationAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.model.LOCATIONS;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择项
 **/

public class LocationChooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static final String TAG = "LocationChooseActivity";


    public static final int LOCATION_CODE = 1000;

    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 返回按钮
     */
    private ImageView backImageView;

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
    private LocationAdapter locationAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;

    ArrayList<LOCATIONS> items = new ArrayList<LOCATIONS>();

    private String type; //类型
    private String status; //状态

    ImageView searchImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        initData();
        findViewById();
        initView();

    }

    private void initData() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
        }
        if (getIntent().hasExtra("status")) {
            status = getIntent().getStringExtra("status");
        }
    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        search = (EditText) findViewById(R.id.search_edit);

        searchImage = (ImageView) findViewById(R.id.searh_button);
    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText("Location");
        setSearchEdit();

        layoutManager = new LinearLayoutManager(LocationChooseActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);

        initAdapter(new ArrayList<LOCATIONS>());
        items = new ArrayList<>();
        getData(searchText);
        searchImage.setOnClickListener(searchClick);
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener searchClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            searchText = search.getText().toString();
            locationAdapter.removeAll(locationAdapter.getData());
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            getData(searchText);

        }
    };

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onLoad() {
        page++;

        getData(searchText);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(searchText);

    }


    private void setSearchEdit() {
        SpannableString msp = new SpannableString("XXSearch");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_search);
        msp.setSpan(new ImageSpan(drawable), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        search.setHint(msp);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    locationAdapter.removeAll(items);
                    items = new ArrayList<LOCATIONS>();
                    nodatalayout.setVisibility(View.GONE);
                    refresh_layout.setRefreshing(true);
                    page = 1;
                    getData(searchText);
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData(String search) {
        String url = null;
        if (null == type) {
            url = HttpManager.getLocationUrl0(search, page, 20);
        } else {
            if (null == status) {
                url = HttpManager.getLocation1Url(search, type, page, 20);
            } else {
                url = HttpManager.getLocation2Url(search, type, status, page, 20);
            }
        }
        HttpManager.getDataPagingInfo(LocationChooseActivity.this, url, new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<LOCATIONS> item = JsonUtils.parsingLOCATIONS(LocationChooseActivity.this, results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<LOCATIONS>();
                            initAdapter(new ArrayList<LOCATIONS>());
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(LocationChooseActivity.this, getString(R.string.have_load_out_all_the_data));
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
    private void initAdapter(final List<LOCATIONS> list) {
        locationAdapter = new LocationAdapter(LocationChooseActivity.this, R.layout.list_item, list);
        recyclerView.setAdapter(locationAdapter);
        locationAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LOCATIONS locations = (LOCATIONS) locationAdapter.getData().get(position);
                Intent intent = getIntent();
                intent.putExtra("Location", locations.getLOCATION());
                intent.putExtra("Invowner", locations.getINVOWNER());
                intent.putExtra("siteid", locations.getSITEID());
                setResult(LOCATION_CODE, intent);
                finish();

            }
        });
    }

    /**
     * 添加数据*
     */
    private void addData(final List<LOCATIONS> list) {
        locationAdapter.addData(list);
    }
}