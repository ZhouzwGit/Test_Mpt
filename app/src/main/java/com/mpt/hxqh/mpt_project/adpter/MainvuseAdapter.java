package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.MAINVUSE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 物料退库
 */
public class MainvuseAdapter extends BaseQuickAdapter<MAINVUSE> {
    private int position;

    public MainvuseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, MAINVUSE item) {
        helper.setText(R.id.num_text_id, item.getINVUSENUM());
        helper.setText(R.id.description_text, item.getDESCRIPTION());
        helper.setText(R.id.status_text_id, item.getSTATUS());
    }


}
