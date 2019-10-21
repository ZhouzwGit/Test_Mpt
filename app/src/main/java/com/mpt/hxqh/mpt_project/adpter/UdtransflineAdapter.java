package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDTRANSFLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 资产移动
 */
public class UdtransflineAdapter extends BaseQuickAdapter<UDTRANSFLINE> {
    private int position;

    public UdtransflineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, UDTRANSFLINE item) {
        helper.setText(R.id.sn_text_id, item.getASSETNUM());
        helper.setText(R.id.location_text_id, item.getFROMSITE());
        helper.setText(R.id.date_text_id, item.getTOSITE());
    }


}
