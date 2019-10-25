package com.mpt.hxqh.mpt_project.adpter;
import android.content.Context;
import android.widget.LinearLayout;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.DISMANTLELINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;

public class Dismantle_ScanAdapter extends BaseQuickAdapter<DISMANTLELINE> {
    private String status;
    public Dismantle_ScanAdapter(Context context, int layoutResId, List data, String status) {
        super(context, layoutResId, data);
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, DISMANTLELINE item) {
        LinearLayout linearLayout  = helper.getView(R.id.parent_id);
        if (item.getSCANSN()!=null && item.getSCANSN().equals(item.getSERIAL())){
            if (item.getSECSCAN()!=null && item.getSECSCAN().equals(item.getSERIAL())){
                linearLayout.setBackgroundResource(R.color.title_color);
            }else {
                linearLayout.setBackgroundResource(R.color.blue);
            }
        }else {
            linearLayout.setBackgroundResource(R.color.white);
        }
        helper.setText(R.id.assetnum_id, item.getASSETNUM());
        helper.setText(R.id.serial_id, item.getSERIAL());
        if (item.getSECSCAN()!= null && !item.getSECSCAN().equals("")){
            helper.setText(R.id.scan_serial_id,item.getSECSCAN());
        }
        helper.setText(R.id.match_id,item.getUDREMARK());
        helper.setOnClickListener(R.id.match_id,new OnItemChildClickListener());
    }


}
