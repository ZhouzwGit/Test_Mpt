package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.model.ITEM;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * ITEM适配器
 */
public class AssetChooseAdapter extends BaseQuickAdapter<ASSET> {
    private int position;
    public AssetChooseAdapter(Context context, int layoutResId, List data) {
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
    protected void convert(BaseViewHolder helper, ASSET item) {
        helper.setText(R.id.po_ponum_title,"Asset:");
        helper.setText(R.id.num_text_id, item.getASSETNUM());
        helper.setText(R.id.description_text, item.getDESCRIPTION());
    }


}
