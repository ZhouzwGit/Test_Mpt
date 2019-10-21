package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ITEM;
import com.mpt.hxqh.mpt_project.model.PERSON;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * PERSON适配器
 */
public class PersonAdapter extends BaseQuickAdapter<PERSON> {
    private int position;
    public PersonAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, PERSON item) {
        helper.setText(R.id.po_ponum_title,"Id:");
        helper.setText(R.id.num_text_id, item.getPERSONID());
        helper.setText(R.id.description_text, item.getDISPLAYNAME());
    }


}
