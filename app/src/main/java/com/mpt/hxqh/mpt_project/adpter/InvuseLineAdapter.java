package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.INVUSELINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 转移适配器
 */
public class InvuseLineAdapter extends BaseQuickAdapter<INVUSELINE> {
    private int position;

    public InvuseLineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, INVUSELINE item) {
        helper.setText(R.id.type_text_id, item.getINVUSELINENUM());
        helper.setText(R.id.item_text_id, item.getITEMNUM());
        helper.setText(R.id.desc_text_id, item.getUSETYPE());
        helper.setText(R.id.storeroom_text_id, item.getQUANTITY());
    }


}
