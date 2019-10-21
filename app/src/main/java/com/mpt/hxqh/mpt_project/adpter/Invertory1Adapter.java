package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.INVENTORY;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * Inventory适配器
 */
public class Invertory1Adapter extends BaseQuickAdapter<INVENTORY> {
    private int position;

    public Invertory1Adapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, INVENTORY item) {
        helper.setText(R.id.po_ponum_title, "Storeroom:");
        helper.setText(R.id.num_text_id, item.getLOCATION());
        helper.setText(R.id.description_text, item.getSTOREROOM_NAME());
    }


}
