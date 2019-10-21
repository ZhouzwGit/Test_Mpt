package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.POLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 采购单行适配器
 */
public class PoLineAdapter extends BaseQuickAdapter<POLINE> {
    private int position;

    public PoLineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, POLINE item) {
        helper.setText(R.id.type_text_id, item.getLINETYPE());
        helper.setText(R.id.item_text_id, item.getSERIALNUM());
        helper.setText(R.id.desc_text_id, item.getLOCATION());
        helper.setText(R.id.storeroom_text_id, item.getSTORELOC());
    }


}
