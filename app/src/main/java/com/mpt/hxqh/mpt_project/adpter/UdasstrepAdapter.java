package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDASSTREP;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 转移适配器
 */
public class UdasstrepAdapter extends BaseQuickAdapter<UDASSTREP> {
    private int position;

    public UdasstrepAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, UDASSTREP item) {
        helper.setText(R.id.sn_text_id, item.getUDSERIALNUM());
        helper.setText(R.id.location_text_id, item.getUDREPLOC());
        helper.setText(R.id.date_text_id, item.getUDREPDATE());
    }


}
