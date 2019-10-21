package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.DISMANTLELINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 资产适配器
 */
public class DismantleLineAdapter extends BaseQuickAdapter<DISMANTLELINE> {
    private int position;

    public DismantleLineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, DISMANTLELINE item) {
        helper.setText(R.id.type_text_id, item.getASSETNUM());
        helper.setText(R.id.item_text_id, item.getSERIAL());
        helper.setText(R.id.desc_text_id, item.getDESCRIPTION());
        helper.setText(R.id.storeroom_text_id, item.getUDTOLOC());
    }


}
