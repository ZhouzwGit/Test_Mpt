package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDRETIRELINE;
import com.mpt.hxqh.mpt_project.model.UDSTOCKTLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 物料盘点行适配器
 */
public class UdstockineAdapter extends BaseQuickAdapter<UDSTOCKTLINE> {
    private int position;

    public UdstockineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, UDSTOCKTLINE item) {
            helper.setText(R.id.type_text_id, item.getLINE());
            helper.setText(R.id.item_text_id, item.getASSETNUM());
            helper.setText(R.id.desc_text_id, item.getCATEGORY());
            helper.setText(R.id.storeroom_text_id, item.getCONFIGURE());

    }


}
