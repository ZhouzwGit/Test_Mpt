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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.DismantleAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.DISMANTLE;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产拆除
 * Created by zzw on 2019/9/23.
 */

public class Asset_Dismantle_Activity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static String TAG = "Asset_Dismantle_Activity";

    /**
     * 返回按钮
     */
    private ImageView backImageView;
    /**
     * 标题
     */
    private TextView titleTextView;
    /**
     * 新增按钮
     **/
    private ImageView addBtn;

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
    private DismantleAdapter dismantleAdapter;
    /**
     * 编辑框*
     */
    private EditText search;
    /**
     * 查询条件*
     */
    private String searchText = "";
    private int page = 1;

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;

    ArrayList<DISMANTLE> items = new ArrayList<DISMANTLE>();


    private String[] optionList = new String[]{"Back", "Add"};
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    ImageView searchTmage;
    private View.OnClickListener searchClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 先隐藏键盘
            ((InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            Asset_Dismantle_Activity.this.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            searchText = search.getText().toString();
            dismantleAdapter.removeAll(items);
            items = new ArrayList<DISMANTLE>();
            nodatalayout.setVisibility(View.GONE);
            refresh_layout.setRefreshing(true);
            page = 1;
            getData(searchText);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }


    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        addBtn = (ImageView) findViewById(R.id.title_add);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        search = (EditText) findViewById(R.id.search_edit);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        quit = (Button) findViewById(R.id.quit);
        option = (Button) findViewById(R.id.option);
        searchTmage = (ImageView) findViewById(R.id.searh_button);
    }

    @Override
    protected void initView() {
        setSearchEdit();
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.asset_repair_text);
//        addBtn.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(Asset_Dismantle_Activity.this);
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

        refresh_layout.setRefreshing(true);
        initAdapter(new ArrayList<DISMANTLE>());
        getData(searchText);

        addBtn.setOnClickListener(addOnClickListener);
        quit.setOnClickListener(quitOnClickListener);
        option.setOnClickListener(optionOnClickListener);
        searchTmage.setOnClickListener(searchClick);
    }

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Asset_Dismantle_Activity.this, Udasst_AddNew_Activity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog dialog = new NormalDialog(Asset_Dismantle_Activity.this);
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
                            AppManager.AppExit(Asset_Dismantle_Activity.this);
                        }
                    });

        }
    };

    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog normalListDialog = new NormalListDialog(Asset_Dismantle_Activity.this, optionList);
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
                            finish();
                            normalListDialog.superDismiss();
                            break;
                        case 1://Add
/*                            Intent intent = new Intent(Asset_Dismantle_Activity.this, Udasst_AddNew_Activity.class);
                            startActivity(intent);*/
                            normalListDialog.superDismiss();
                            break;
                    }
                    normalListDialog.dismiss();
                }
            });
        }
    };

    private void setSearchEdit() {
        SpannableString msp = new SpannableString(getString(R.string.search_text));
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
                                    Asset_Dismantle_Activity.this.getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    searchText = search.getText().toString();
                    dismantleAdapter.removeAll(items);
                    items = new ArrayList<DISMANTLE>();
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
        HttpManager.getDataPagingInfo(Asset_Dismantle_Activity.this, HttpManager.getDismantleURL(search, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<DISMANTLE> item = JsonUtils.parsingDISMANTLE(results.getResultlist());
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {

                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<DISMANTLE>();
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
     * 获取数据*
     */
    private void initAdapter(final List<DISMANTLE> list) {
        nodatalayout.setVisibility(View.GONE);
        dismantleAdapter = new DismantleAdapter(Asset_Dismantle_Activity.this, R.layout.list_asset_transfer, list);
        recyclerView.setAdapter(dismantleAdapter);
        dismantleAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Asset_Dismantle_Activity.this, Dismantle_Details_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dismantle", items.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }
    /**
     * 添加数据*
     */
    private void addData(final List<DISMANTLE> list) {
        dismantleAdapter.addData(list);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
