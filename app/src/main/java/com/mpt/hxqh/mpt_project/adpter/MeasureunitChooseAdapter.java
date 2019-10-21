package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.MEASUREUNIT;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * MEASUREUNIT适配器
 */
public class MeasureunitChooseAdapter extends BaseQuickAdapter<MEASUREUNIT> {
    private int position;
    public MeasureunitChooseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, MEASUREUNIT item) {
        helper.setText(R.id.po_ponum_title,"Asset:");
        helper.setText(R.id.measure_text_id, item.getMEASUREUNITID());
        helper.setText(R.id.abbreviation_text, item.getABBREVIATION());
        helper.setText(R.id.description_text, item.getDESCRIPTION());
    }


}
