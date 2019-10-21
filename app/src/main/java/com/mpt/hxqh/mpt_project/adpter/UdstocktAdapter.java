package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDSTOCKT;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 物料盘点
 */
public class UdstocktAdapter extends BaseQuickAdapter<UDSTOCKT> {
    private int position;

    public UdstocktAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, UDSTOCKT item) {
        helper.setText(R.id.num_text_id, item.getSTOCKTNUM());
        helper.setText(R.id.description_text, item.getDESCRIPTION());
        helper.setText(R.id.location_text, item.getLOCATION());
        helper.setText(R.id.status_text_id, item.getSTATUS());
    }


}
