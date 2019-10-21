package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;
import android.widget.CompoundButton;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.GETREFUNDLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 退回弹出框
 */
public class RefundlineAdapter extends BaseQuickAdapter<GETREFUNDLINE> {
    private int position;
    /**
     * 选中事件*
     */
    public OnCheckedChangeListener onCheckedChangeListener;



    public RefundlineAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(final BaseViewHolder helper, GETREFUNDLINE item) {
        helper.setText(R.id.itemnum_text_id, item.getITEMNUM());
        helper.setText(R.id.itemtype_text_id, item.getITEMTYPE());
        helper.setText(R.id.description_text, item.getDESCRIPTION());

        helper.setOnCheckedChangeListener(R.id.checkbox_text_id, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckedChangeListener.cOnCheckedChangeListener(b, helper.getPosition());
            }
        });
    }


    public interface OnCheckedChangeListener {
        public void cOnCheckedChangeListener(boolean b, int postion);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }


}
