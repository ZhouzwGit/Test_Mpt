package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.INVBALANCES;
import com.mpt.hxqh.mpt_project.model.ITEM;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * INVBALANCES适配器
 */
public class InvbalancesAdapter extends BaseQuickAdapter<INVBALANCES> {
    private int position;
    public InvbalancesAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, INVBALANCES item) {
        helper.setText(R.id.po_ponum_title,"Bin:");
        helper.setText(R.id.num_text_id, item.getBINNUM());
        helper.setText(R.id.po_description_title,"Lot:");
        helper.setText(R.id.description_text, item.getLOTNUM());
    }


}
