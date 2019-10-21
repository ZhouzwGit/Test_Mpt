package com.mpt.hxqh.mpt_project.adpter;
import android.content.Context;
import android.widget.LinearLayout;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;

public class Udtransfline_ScanAdapter extends BaseQuickAdapter<UDTRANSFLINE> {
    private String status;
    public Udtransfline_ScanAdapter(Context context, int layoutResId, List data, String status) {
        super(context, layoutResId, data);
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, UDTRANSFLINE item) {
        LinearLayout linearLayout  = helper.getView(R.id.parent_id);
        if (item.SCANSN!=null && item.SCANSN.equals(item.SERIALNUM)){
            if (item.SECSCAN!=null && item.SECSCAN.equals(item.SERIALNUM)){
                linearLayout.setBackgroundResource(R.color.title_color);
            }else {
                linearLayout.setBackgroundResource(R.color.blue);
            }
        }else {
            linearLayout.setBackgroundResource(R.color.white);
        }
        helper.setText(R.id.assetnum_id, item.getASSETNUM());
        helper.setText(R.id.serial_id, item.getSERIALNUM());
        if (item.SCANSN!= null && !item.SCANSN.equals("")){
            helper.setText(R.id.scan_serial_id,item.getSCANSN());
        }
        helper.setText(R.id.match_id,item.getUDREMARK());
        helper.setOnClickListener(R.id.match_id,new OnItemChildClickListener());
    }


}
