package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.model.WPMATERIAL;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 物料计划
 */
public class WpmaterialAdapter extends BaseQuickAdapter<WPMATERIAL> {
    private int position;

    public WpmaterialAdapter(Context context, int layoutResId, List data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        position = index;
        if (index < 5)
            anim.setStartDelay(index * 150);
    }

    @Override
    protected void convert(BaseViewHolder helper, WPMATERIAL item) {
        helper.setText(R.id.sn_text_id, item.getITEMNUM());
        helper.setText(R.id.location_text_id, item.getITEMQTY());
        helper.setText(R.id.date_text_id, item.getLOCATION());
    }


}
