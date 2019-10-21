package com.mpt.hxqh.mpt_project.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.manager.AppManager;
import com.mpt.hxqh.mpt_project.unit.MessageUtils;

/**
 * 主菜单
 **/
public class MainActivity extends BaseActivity {

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private ImageView assetImagView;//资产查询

    private ImageView udasstImagView;//资产维修

    private ImageView transferImagView;//资产转移

    private ImageView udretireImagView;//资产报废

    private ImageView movement;//资产移动

    private ImageView workorderImagView;//物料出库

    private ImageView mainvuseImagView;//物料退库

    private ImageView udstocktImagView;//物料盘点

    private ImageView poImagView;//采购接收

    private ImageView maainvuseImagView;//物料转移

    private ImageView dismantleImageView;//资产拆除


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Toast.makeText(MainActivity.this,user,Toast.LENGTH_SHORT).show();
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        assetImagView = (ImageView) findViewById(R.id.asset_image_id);
        udasstImagView = (ImageView) findViewById(R.id.udasst_image_id);
        transferImagView = (ImageView) findViewById(R.id.transfer_image_id);
        udretireImagView = (ImageView) findViewById(R.id.udretire_image_id);
        movement = (ImageView) findViewById(R.id.udassettransf_image_id);
        workorderImagView = (ImageView) findViewById(R.id.workorder_image_id);
        mainvuseImagView = (ImageView) findViewById(R.id.mainvuse_image_id);
        udstocktImagView = (ImageView) findViewById(R.id.udstockt_image_id);
        poImagView = (ImageView) findViewById(R.id.po_image_id);
        maainvuseImagView = (ImageView) findViewById(R.id.maainvuse_image_id);
        dismantleImageView = (ImageView) findViewById(R.id.assetDismantle_image_id);
    }

    @Override
    protected void initView() {
        backImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.main_text);
        assetImagView.setOnClickListener(assetImagViewOnClickListener);
        udasstImagView.setOnClickListener(udasstImagViewOnClickListener);
        transferImagView.setOnClickListener(transferImagViewOnClickListener);
        udretireImagView.setOnClickListener(udretireImagViewOnClickListener);
        movement.setOnClickListener(udassettransfImagViewOnClickListener);
        workorderImagView.setOnClickListener(workorderImagViewOnClickListener);
        mainvuseImagView.setOnClickListener(mainvuseImagViewOnClickListener);
        udstocktImagView.setOnClickListener(udstocktImagViewOnClickListener);
        poImagView.setOnClickListener(poImagViewOnClickListener);
        maainvuseImagView.setOnClickListener(maainvuseImagViewOnClickListener);
        dismantleImageView.setOnClickListener(dismantleImagViewOnClickListener);
    }

    /**
     * 资产查询
     **/
    private View.OnClickListener assetImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Search_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 资产维修
     **/
    private View.OnClickListener udasstImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Udasst_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 资产转移
     **/
    private View.OnClickListener transferImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Transfer_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 资产报废
     **/
    private View.OnClickListener udretireImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Udretire_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 资产移动
     **/
    private View.OnClickListener udassettransfImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Udassettransf_Activity.class);
            startActivityForResult(intent, 0);
        }
    };
    /**
     * 物料出库
     **/
    private View.OnClickListener workorderImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Material_Workorder_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 物料回退
     **/
    private View.OnClickListener mainvuseImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Material_Mainvuse_Activity.class);
            startActivityForResult(intent, 0);

        }
    };

    /**
     * 物料盘点
     **/
    private View.OnClickListener udstocktImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Material_Udstockt_Activity.class);

            startActivityForResult(intent, 0);

        }
    };
    /**
     * 采购接收
     **/
    private View.OnClickListener poImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Material_PO_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 物料转移
     **/
    private View.OnClickListener maainvuseImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Material_Maainvuse_Activity.class);
            startActivityForResult(intent, 0);

        }
    };
    /**
     * 资产拆除
     **/
    private View.OnClickListener dismantleImagViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Asset_Dismantle_Activity.class);
            startActivityForResult(intent, 0);

        }
    };

    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MessageUtils.showMiddleToast(this, getResources().getString(R.string.exit_text));
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }
}
