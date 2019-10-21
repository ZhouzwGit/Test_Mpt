package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.content.Context;

import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.ASSET;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.List;


/**
 * Created by apple on 15/10/26
 * 资产适配器
 */
public class AssetAdapter extends BaseQuickAdapter<ASSET> {
    private int position;

    public AssetAdapter(Context context, int layoutResId, List data) {
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
        helper.setText(R.id.location_text_id, item.getLOCATION());
        helper.setText(R.id.sn_text_id, item.getSERIALNUM());
        helper.setText(R.id.category_text_id, item.getCONFIGURE());
        helper.setText(R.id.item_text_id, item.getITEMNUM());
        helper.setText(R.id.type_text_id, item.getASSETTYPE());
    }


}
