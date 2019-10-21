package com.mpt.hxqh.mpt_project.adpter;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.POLINE;
import com.mpt.hxqh.mpt_project.model.UDBOQLIST;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;

public class UdBoqlist_ScanAdapter extends BaseQuickAdapter<UDBOQLIST> {
    public UdBoqlist_ScanAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UDBOQLIST item) {
        LinearLayout linearLayout  = helper.getView(R.id.parent_id);
        if (item.SCANSN!=null && item.SCANSN.equals(item.SERIALNUM)){
            if (item.SECSCAN!=null && item.SECSCAN.equals(item.SERIALNUM)){
                linearLayout.setBackgroundResource(R.color.title_color);
            }else {
                linearLayout.setBackgroundResource(R.color.blue);
            }
        }else {
            if (item.SECSCAN!=null && item.SECSCAN.equals(item.SERIALNUM)){
                linearLayout.setBackgroundColor(Color.YELLOW);
            }else {
                linearLayout.setBackgroundResource(R.color.white);
            }

        }
        helper.setText(R.id.assetnum_id, item.ITEMNUM);
        helper.setText(R.id.serial_id, item.getSERIALNUM());
       // helper.setText(R.id.scan_serial_id, item.SCANSN);
        if (item.SCANSN!= null && !item.SCANSN.equals("")){
            helper.setText(R.id.scan_serial_id,item.getSCANSN());
        }
        helper.setText(R.id.match_id,item.getREMARK());
        helper.setOnClickListener(R.id.match_id,new OnItemChildClickListener());
    }
}
