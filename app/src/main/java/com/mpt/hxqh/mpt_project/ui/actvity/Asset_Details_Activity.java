package com.mpt.hxqh.mpt_project.ui.actvity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;

/**
 * 资产详情
 **/
public class Asset_Details_Activity extends BaseActivity {

    private static final String TAG = "Asset_Details_Activity";

    private ImageView backImageView; //返回按钮

    private TextView titleTextView; //标题

    private TextView snTextView; //SN
    private TextView locationTextView; //Location
    private TextView desccrtionTextView; //desccrtion
    private TextView statusTextView; //status
    private TextView itemsTextView; //Items
    private TextView verdorTextView; //Verdor
    private TextView installdateTextView; //installdate
    private TextView patdateTextView; //patdate
    private TextView configureTextView; //configure
    private TextView phaseTextView; //phase

    private ASSET asset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        initData();
        findViewById();
        initView();
    }

    private void initData() {
        asset = (ASSET) getIntent().getSerializableExtra("asset");
    }

    @Override
    protected void findViewById() {

        backImageView = (ImageView) findViewById(R.id.title_back_id);
        titleTextView = (TextView) findViewById(R.id.title_name);
        snTextView = (TextView) findViewById(R.id.sn_text_id);
        locationTextView = (TextView) findViewById(R.id.location_text_id);
        desccrtionTextView = (TextView) findViewById(R.id.description_text_id);
        statusTextView = (TextView) findViewById(R.id.status_text_id);
        itemsTextView = (TextView) findViewById(R.id.item_text_id);
        verdorTextView = (TextView) findViewById(R.id.vendor_text_id);
        installdateTextView = (TextView) findViewById(R.id.install_date_text_id);
        patdateTextView = (TextView) findViewById(R.id.pat_date_text_id);
        configureTextView = (TextView) findViewById(R.id.configure_text_id);
        phaseTextView = (TextView) findViewById(R.id.phase_text_id);

    }

    @Override
    protected void initView() {
        backImageView.setOnClickListener(backImageViewOnClickListener);
        titleTextView.setText(R.string.asset_text);

        if (asset != null) {
            snTextView.setText(asset.getSERIALNUM());
            locationTextView.setText(asset.getLOCATION());
            desccrtionTextView.setText(asset.getDESCRIPTION());
            statusTextView.setText(asset.getSTATUS());
            itemsTextView.setText(asset.getITEMNUM());
            verdorTextView.setText(asset.getVENDOR());
            installdateTextView.setText(asset.getINSTALLDATE());
            patdateTextView.setText(asset.getPATDATE());
            configureTextView.setText(asset.getCONFIGURE());
            phaseTextView.setText(asset.getPHASE());
        }

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


}
