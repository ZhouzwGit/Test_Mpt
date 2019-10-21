package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Printer;
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
import com.mpt.hxqh.mpt_project.adpter.BaseQuickAdapter;
import com.mpt.hxqh.mpt_project.adpter.UdstockineScanAdapter;
import com.mpt.hxqh.mpt_project.api.HttpManager;
import com.mpt.hxqh.mpt_project.api.HttpRequestHandler;
import com.mpt.hxqh.mpt_project.api.JsonUtils;
import com.mpt.hxqh.mpt_project.bean.Results;
import com.mpt.hxqh.mpt_project.config.Constants;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.UDSCANSN;
import com.mpt.hxqh.mpt_project.model.UDSTOCKTLINE;
import com.mpt.hxqh.mpt_project.model.WebResult;
import com.mpt.hxqh.mpt_project.ui.widget.SwipeRefreshLayout;
import com.mpt.hxqh.mpt_project.unit.AccountUtils;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;
import com.mpt.hxqh.mpt_project.webserviceclient.AndroidClientService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 物资盘点列表
 **/
public class Material_Stocktaking_Activity extends BaseActivity {

    private static String TAG = "Material_Stocktaking_Activity";

    private static final int STOCKTAKING_CODE = 1090;
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
    /**
     * 适配器*
     */
    private UdstockineScanAdapter udstockineAdapter;

    private int page = 1;

    int num;

    ArrayList<UDSTOCKTLINE> items = new ArrayList<UDSTOCKTLINE>();

    private String stocktnum; //主表编号

    private LinearLayout buttonLayout;
    private Button quit;
    private Button option;
    private TextView snnew;
    private String[] optionList = new String[]{"Back", "Confirm"};
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private List<UDSTOCKTLINE> stocktList=new ArrayList<UDSTOCKTLINE>();
    ArrayList<String> list = new ArrayList<>();
    private String status;
    private int showCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udstocktaing_details);
        initData();
        findViewById();
        initView();

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
     }

     private void onClick(){

     }
    private void initData() {
        if (getIntent().getExtras()!=null) {
            stocktnum = getIntent().getExtras().getString("stocktnum");
            status = getIntent().getExtras().getString("status");
        }
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
        snnew = (TextView) findViewById(R.id.new_sn_text_id);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.material_stocktaking_text);

        scanButton.setOnClickListener(scanButtonOnClickListener);

        buttonLayout.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(Material_Stocktaking_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        refresh_layout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refresh_layout.setRefreshing(true);

        refresh_layout.setOnRefreshListener(refreshOnRefreshListener);
        refresh_layout.setOnLoadListener(refreshOnLoadListener);

        refresh_layout.setRefreshing(true);
        refresh_layout.setLoading(true);
        initAdapter(new ArrayList<UDSTOCKTLINE>());
        getData();

        quit.setOnClickListener(quitOnClickListener);
        option.setOnClickListener(optionOnClickListener);
    }

    /**
     * 二维码扫描
     **/
    private View.OnClickListener scanButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(status!=null && status.equals("STAKING")){
                Intent intent = new Intent(Material_Stocktaking_Activity.this, MipcaActivityCapture.class);
                intent.putExtra("mark", 1); //扫码标识
                startActivityForResult(intent, STOCKTAKING_CODE);
            }else {
                Toast.makeText(Material_Stocktaking_Activity.this,"The state is not in staking!!",Toast.LENGTH_SHORT).show();
            }

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
           /* page++;
            //getData();*/
           refresh_layout.setLoading(false);
        }
    };

    /**
     * 获取数据*
     */
    private void initAdapter(final List<UDSTOCKTLINE> list) {
        nodatalayout.setVisibility(View.GONE);
        udstockineAdapter = new UdstockineScanAdapter(Material_Stocktaking_Activity.this, R.layout.list_stocktaking_item, list);
        udstockineAdapter.setStatus(status);
        recyclerView.setAdapter(udstockineAdapter);
        udstockineAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }


    /**
     * 获取数据*
     */
    private void getData() {
        HttpManager.getDataPagingInfo2(Material_Stocktaking_Activity.this, HttpManager.getUDSTOCKTLINEURL(stocktnum, page, 500), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results data) {
            }
            @Override
            public void onSuccess(Results data, int totalPages, int currentPage) {
                ArrayList<UDSTOCKTLINE> item = JsonUtils.parsingUDSTOCKTLINE(data.getResultlist());
                showCount = item.size();
                Log.e(TAG, "onSuccess: "+showCount );
                HttpManager.getDataPagingInfo(Material_Stocktaking_Activity.this, HttpManager.getUDSTOCKTLINEURL(stocktnum, page, showCount),new HttpRequestHandler<Results>() {
                    @Override
                    public void onSuccess(Results results) {
                    }
                    @Override
                    public void onSuccess(Results results, int totalPages, int currentPage) {
                        ArrayList<UDSTOCKTLINE> item = JsonUtils.parsingUDSTOCKTLINE(results.getResultlist());
                        Log.e(TAG, "onSuccess: " +item.size() );
                        refresh_layout.setRefreshing(false);
                        refresh_layout.setLoading(false);
                        if (item == null || item.isEmpty()) {
                            nodatalayout.setVisibility(View.VISIBLE);
                        } else {
                            if (item != null || item.size() != 0) {
                                if (page == 1) {
                                    items = new ArrayList<UDSTOCKTLINE>();
                                    initAdapter(items);
                                }
                                if (page > totalPages) {
                                    MessageUtils.showMiddleToast(Material_Stocktaking_Activity.this, getString(R.string.have_load_out_all_the_data));
                                }
                                addData(item);
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

            @Override
            public void onFailure(String error) {

            }
        });
      /*  HttpManager.getDataPagingInfo(Material_Stocktaking_Activity.this, HttpManager.getUDSTOCKTLINEURL(stocktnum, page, 20),new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }
            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDSTOCKTLINE> item = JsonUtils.parsingUDSTOCKTLINE(results.getResultlist());
                Log.e(TAG, "onSuccess: " +item.size() );
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (item == null || item.isEmpty()) {
                    nodatalayout.setVisibility(View.VISIBLE);
                } else {
                    if (item != null || item.size() != 0) {
                        if (page == 1) {
                            items = new ArrayList<UDSTOCKTLINE>();
                            initAdapter(items);
                        }
                        if (page > totalPages) {
                            MessageUtils.showMiddleToast(Material_Stocktaking_Activity.this, getString(R.string.have_load_out_all_the_data));
                        }
                        addData(item);
                    }
                    nodatalayout.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });*/

    }


    /**
     * 添加数据*
     */
    private void addData(final List<UDSTOCKTLINE> list) {
        udstockineAdapter.addData(list);
    }


    //定义RecyclerView分割线
    class MyItemDecoration extends RecyclerView.ItemDecoration {
        /**
         * @param outRect 边界
         * @param view    recyclerView ItemView
         * @param parent  recyclerView
         * @param state   recycler 内部数据管理
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case STOCKTAKING_CODE:
                if (data!=null){
                    String results = data.getExtras().getString("result");
                    isExistSN(results);
                }
                break;
        }
    }


    /**
     * 根据SN号查询资产表是否存在
     **/
    private void isExistSN(final String serialnum) {

        HttpManager.getDataPagingInfo(Material_Stocktaking_Activity.this, HttpManager.getAssetUrl(serialnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<ASSET> item = JsonUtils.parsingASSET(results.getResultlist());
                if (item == null || item.isEmpty()) {
                    update1(serialnum);
                } else {
                    update(item.get(0).getSERIALNUM());
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });


    }


    private void line() {

        HttpManager.getDataPagingInfo1(Material_Stocktaking_Activity.this, HttpManager.getLineUrl(stocktnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<UDSCANSN> item = JsonUtils.parsingUDSCANSN(results.getResultlist());
//                if (item == null || item.isEmpty()) {
//                } else {
//                    ArrayList<String> list1 = new ArrayList<>();
                    ArrayList<Integer> lis1 = new ArrayList<>();
                    for(UDSCANSN scfdcgzview : item) {
                        if (scfdcgzview.getLINE()== null){
                            scfdcgzview.setLINE("0");
                        }
                        String bbb = scfdcgzview.getSERIALNUM();
                        String aaa = scfdcgzview.getLINE();
                        lis1.add(Integer.parseInt(aaa));
                        list.add(bbb);
                    }
                     if (lis1.size()==0){
                         num=1;
                     }else {
                         int max = 0;
                         for (int i=0;i<lis1.size();i++){
                             if (max < lis1.get(i)){
                                 max=lis1.get(i);
                             }
                         }
                         num = max + 1;
                     }

            }

            @Override
            public void onFailure(String error) {
            }
        });


    }


    /**
     * 判断与子表中的数据对应
     * 如果存在，则将SN加入到相应的NEW SN字段里面
     * 并将背景颜色改成绿色
     **/

    private void update(String serialnum) {
        int j = 0;
        List<UDSTOCKTLINE> ulist = udstockineAdapter.getData();
        for (int i = 0; i < ulist.size(); i++) {
            String sn = ulist.get(i).getSERIALNUM();
            if (sn != null){
                if (sn.trim().equals(serialnum.trim())) {
                    j = j + 1;
                    ulist.get(i).setCHECKSERIAL(sn);  //将SN加入到new sn 中
                    ulist.get(i).setSTKRESULT("MATCH");
                    ulist.get(i).setISSCAN(1);//1显示绿色，0显示默认颜色
                    Date  date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
                    String now = format.format(date);
                    ulist.get(i).setSTOCKTAKER(AccountUtils.getpersonId(this));
                    ulist.get(i).setQTYINSTK("1");
                    ulist.get(i).setCHECKDATE(now);
                    stocktList.add(ulist.get(i));
                    updateAdapter(i, ulist.get(i));
                    break;
                }
            }
        }
        if (j==0){
            isSave(serialnum);
            Toast.makeText(Material_Stocktaking_Activity.this, "NOT MATCH", Toast.LENGTH_SHORT).show();
        }
    }
    private void update1(String serialnum) {
        int j = 0;
        List<UDSTOCKTLINE> ulist = udstockineAdapter.getData();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Material_Stocktaking_Activity.this);
        for (int i = 0; i < ulist.size(); i++) {
            String sn = ulist.get(i).getSERIALNUM();
            if (sn != null){
                if (sn.equals(serialnum)) {
                    j = j+1;
                    ulist.get(i).setCHECKSERIAL(serialnum);  //将SN加入到new sn 中
                    ulist.get(i).setSTKRESULT("MATCH");
                    Date  date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
                    String now = format.format(date);
                    ulist.get(i).setCHECKDATE(now);
                    ulist.get(i).setQTYINSTK("1");
                    ulist.get(i).setSTOCKTAKER(AccountUtils.getpersonId(this));
                    ulist.get(i).setISSCAN(1);//1显示绿色，0显示默认颜色

                    stocktList.add(ulist.get(i));
                    updateAdapter(i, ulist.get(i));
                }
            }

        }
        if (j == 0){
            isSave(serialnum);
            Toast.makeText(Material_Stocktaking_Activity.this, "NOT MATCH", Toast.LENGTH_SHORT).show();
        }
    }


    private void startAsyncTask(final String serialnum) {
        new AsyncTask<String, String, WebResult>() {
            @Override
            protected WebResult doInBackground(String... strings) {
                line();
                int b = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains(serialnum)) {
                        b = b + 1;
                    }
                }
                if (b == 0) {
                    WebResult reviseresult = AndroidClientService.udScanSN(Material_Stocktaking_Activity.this, stocktnum, serialnum, AccountUtils.getuserName(Material_Stocktaking_Activity.this), String.valueOf(num), Constants.TRANSFER_URL);
                    return reviseresult;
                }
                return null;
            }


            @Override
            protected void onPostExecute(WebResult workResult) {
                super.onPostExecute(workResult);
//                closeProgressDialog();
                if (workResult == null) {
                    MessageUtils.showMiddleToast(Material_Stocktaking_Activity.this, "Already exist");
                } else {
//                    MessageUtils.showMiddleToast(Material_Stocktaking_Activity.this, workResult.returnStr);
                    MessageUtils.showMiddleToast(Material_Stocktaking_Activity.this, getString(R.string.recoder_item_text));
//                    finish();
                }

             closeProgressDialog();
            }
        }.execute();

    }
    public void isSave(final String str){
        final NormalDialog dialog = new NormalDialog(this);
        dialog.content("The Sn \""+ str +"\" is not exist！\nSure to save?")//
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
                        startAsyncTask(str);
                        dialog.dismiss();
                    }
                });
    }


    /**
     * 根据position刷新Adapter
     **/
    private void updateAdapter(int postition, UDSTOCKTLINE udstocktline) {
        udstockineAdapter.remove(postition);

        udstockineAdapter.add(postition, udstocktline);

        udstockineAdapter.notifyDataSetChanged();

    }


    /**退出按钮**/
    private View.OnClickListener quitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalDialog dialog = new NormalDialog(Material_Stocktaking_Activity.this);
            dialog.content("Sure to exit?")//
                    .showAnim(mBasIn)//vc
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
                            AppManager.AppExit(Material_Stocktaking_Activity.this);
                        }
                    });

        }
    };


    /**操作按钮**/
    private View.OnClickListener optionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final NormalListDialog  normalListDialog = new NormalListDialog(Material_Stocktaking_Activity.this, optionList);
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
                        case 1://Confirm
                            normalListDialog.superDismiss();
                            boolean flag = false;
                                for (int i = 0;i<udstockineAdapter.getItemCount();i++){
                                    UDSTOCKTLINE udstocktline = udstockineAdapter.getItem(i);
                                    if (udstocktline.getSERIALNUM()!=null && !udstocktline.getSERIALNUM().equals("")){
                                        if (udstocktline.getCHECKSERIAL() != null && !udstocktline.getCHECKSERIAL().equals("")){
                                            if (!udstocktline.getSERIALNUM().trim().equalsIgnoreCase(udstocktline.getCHECKSERIAL().trim())){
                                                flag = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (flag){
                                    Toast.makeText(Material_Stocktaking_Activity.this, "There are some records not macthed",Toast.LENGTH_SHORT).show();
                                }else {
                                    submitData();
                                    showProgressDialog("waiting....");
                                }

//                            }
                            break;

                    }
                }
            });
        }
    };


    /**提交盘点的数据**/
    private void submitData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                List<UDSTOCKTLINE> ulist = udstockineAdapter.getData();
                Log.e(TAG,ulist.size()+"kkkkk");
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:dd");
                String now = format.format(date);
                   for (int i = 0; i < ulist.size(); i++) {
                       UDSTOCKTLINE item = ulist.get(i);
                       String sn = ulist.get(i).getCHECKSERIAL();
                       String sn2 = ulist.get(i).getSERIALNUM();
                       item.setCHECKDATE(now);
                       stocktList.add(item);
                   }

                StringBuffer reviseresult = new StringBuffer("");
                for (int i = 0; i < stocktList.size(); i++) {
                    stocktList.get(i).setSTOCKTAKER(AccountUtils.getpersonId(Material_Stocktaking_Activity.this));
                    UDSTOCKTLINE line = stocktList.get(i);
                    Log.i(TAG,"UDSTOCKTLINEID="+line.getUDSTOCKTLINEID() + stocktList.size());
                    String result = AndroidClientService.UpdateWO(Material_Stocktaking_Activity.this, JsonUtils.encapsulationUdstocktline(line), "UDSTOCKTLINE",
                            "UDSTOCKTLINEID", line.getUDSTOCKTLINEID()
                            , Constants.WORK_URL);
                    if (result==null|| result.equals("")){
                        reviseresult.append("%%fail");
                    }else {
                        reviseresult.append("%%"+result);
                    }
                }

                return reviseresult.toString();
            }

            @Override
            protected void onPostExecute(String workResult) {
                super.onPostExecute(workResult);
                String[] results = workResult.split("%%");
                StringBuffer errormessage = new StringBuffer();
                boolean flag = true;
                for (int i = 0;i < results.length;i++){
                    if (results[i].equals("fail")){
                        errormessage.append("The  " + i + " record confim failed!\n");
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    Toast.makeText(Material_Stocktaking_Activity.this, errormessage + "Please check the records or contact the administrator", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Material_Stocktaking_Activity.this, "Confim successfully", Toast.LENGTH_SHORT).show();
                }
                closeProgressDialog();
            }
        }.execute();
    }
}
