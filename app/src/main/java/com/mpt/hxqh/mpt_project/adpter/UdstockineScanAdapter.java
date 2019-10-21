package com.mpt.hxqh.mpt_project.adpter;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnEditClickL;
import com.flyco.dialog.widget.NormalEditTextDialog;
import com.mpt.hxqh.mpt_project.R;
import com.mpt.hxqh.mpt_project.model.UDSTOCKTLINE;
import com.mpt.hxqh.mpt_project.ui.widget.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;


/**
 * Created by apple on 15/10/26
 * 物料盘点行适配器
 */
public class UdstockineScanAdapter extends BaseQuickAdapter<UDSTOCKTLINE> {
    private static final String TAG = "UdstockineScanAdapter";
    private int position;
    private BaseAnimatorSet mBasIn = new BounceTopEnter();
    private BaseAnimatorSet mBasOut= new SlideBottomExit();
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public UdstockineScanAdapter(Context context, int layoutResId, List data) {
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
    protected synchronized void convert(final BaseViewHolder helper, final UDSTOCKTLINE item) {
        Log.i(TAG, "item=" + item.getUDSTOCKTLINEID());

        if (item.getSTKRESULT() != null && item.getSTKRESULT().equals("MATCH")) {
            helper.setBackgroundRes(R.id.card_container, R.color.blue);
        } else {
            helper.setBackgroundRes(R.id.card_container, R.color.white);
        }
        String sn = item.getSERIALNUM();
        helper.setText(R.id.item_text_id, item.getASSETNUM());
        helper.setText(R.id.sn_text_id, item.getSERIALNUM());
        helper.setText(R.id.new_sn_text_id, item.getCHECKSERIAL());
        helper.setText(R.id.QTY_text_id, item.getQUANTITY());
        helper.setText(R.id.quantiy_text_id, item.getQTYINSTK());
        if (sn != null && !sn.equals("") && sn.equals(item.getCHECKSERIAL())) {
            item.setSTKRESULT("MATCH");
            item.setISSCAN(1);
        }

        helper.setText(R.id.stkresult_text_id, item.getSTKRESULT());
        helper.setText(R.id.stockremark_text_id, item.getREMARK());
        //final TextView t = (TextView) helper.getView(R.id.new_sn_text_id);
        EditText r = (EditText) helper.getView(R.id.stockremark_text_id);
        EditText p = (EditText) helper.getView(R.id.quantiy_text_id);
        if (!"STAKING".equalsIgnoreCase(status)){
            r.setEnabled(false);
            p.setEnabled(false);
        }else {
            helper.setOnClickListener(R.id.new_sn_text_id, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final NormalEditTextDialog normalEditTextDialog = new NormalEditTextDialog(mContext);
                    TextView newSn = (TextView) helper.getView(R.id.new_sn_text_id);
                    normalEditTextDialog.title(helper.getLayoutPosition() + "").content(newSn.getText().toString()).showAnim(mBasIn).dismissAnim(mBasOut).show();
                    final int layoutPosition = helper.getLayoutPosition();
                    normalEditTextDialog.setOnBtnClickL(
                            new OnBtnEditClickL() {
                                @Override
                                public void onBtnClick(String text) {
                                    normalEditTextDialog.dismiss();
                                }
                            },
                            new OnBtnEditClickL() {
                                @Override
                                public void onBtnClick(String text) {
                                    normalEditTextDialog.dismiss();
                                    String sn = getItem(helper.getLayoutPosition()).getSERIALNUM();
                                    List<UDSTOCKTLINE> list = getData();
                                    if (sn != null && !sn.equals("") && sn.equals(text)) {
                                        list.get(layoutPosition).setCHECKSERIAL(text);
                                        list.get(layoutPosition).setSTKRESULT("MATCH");
                                        list.get(layoutPosition).setISCHECK("Y");
                                        Log.e("SN IN STOCKTAKING", text);
                                    } else if (text != null && !text.equals("")) {
                                        list.get(layoutPosition).setCHECKSERIAL(text);
                                        list.get(layoutPosition).setSTKRESULT("NOT MATCH");
                                        list.get(layoutPosition).setISCHECK("Y");
                                        Log.e("什么鬼", "onBtnClick: " + list.get(helper.getLayoutPosition()).toString() + "====" + layoutPosition);
                                    } else {
                                        list.get(layoutPosition).setSTKRESULT("");
                                        list.get(layoutPosition).setCHECKSERIAL("");
                                    }
                                    UdstockineScanAdapter.this.notifyItemChanged(layoutPosition);
                                    Log.e(TAG, "onBtnClick: " + layoutPosition);


                                }
                            });
                }
            });
        }
       /* TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String as = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean flag = false;
                List<UDSTOCKTLINE> data = getData();
                String assetnum = null;
                String sn = null;
                String thisnum = ((TextView) helper.getView(R.id.item_text_id)).getText().toString();
                for (int i = 0; i < data.size(); i++) {
                    assetnum = data.get(i).getASSETNUM();
                    sn = data.get(i).getSERIALNUM();
                    if (assetnum != null && assetnum.equals(thisnum)) {
                        if (sn != null && !sn.equals("") && sn.equals(s.toString())) {
                            data.get(i).setCHECKSERIAL(s.toString());
                            data.get(i).setSTKRESULT("MATCH");
                            helper.setText(R.id.stkresult_text_id, "MATCH");
                            data.get(i).setISCHECK("Y");
                            Log.e("SN IN STOCKTAKING", s.toString());
                        } else if (s.toString() != null && !s.toString().equals("")) {
                            data.get(i).setCHECKSERIAL(s.toString());
                            data.get(i).setSTKRESULT("NOT MATCH");
                            data.get(i).setISCHECK("Y");
                            helper.setText(R.id.stkresult_text_id, "NOT MATCH");
                        } else {
                            data.get(i).setSTKRESULT("");
                            helper.setText(R.id.stkresult_text_id, data.get(i).getSTKRESULT());
                        }
                    }
                }
                ((EditText) helper.getView(R.id.new_sn_text_id)).append(s.toString());
            }
        };
        t.removeTextChangedListener(textWatcher);
        t.addTextChangedListener(textWatcher);*/
        r.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<UDSTOCKTLINE> data = getData();
                String assetnum = null;
                String thisnum = ((TextView)helper.getView(R.id.item_text_id)).getText().toString();
                for (int i = 0;i<data.size();i++){
                    assetnum = data.get(i).getASSETNUM();
                    if (assetnum!=null && assetnum.equals(thisnum)){
                        data.get(i).setREMARK(s.toString());
                        Log.e("UDREMARK",s.toString());
                    }
                }
            }
        });
        p.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<UDSTOCKTLINE> data = getData();
                String assetnum = null;
                String thisnum = ((TextView)helper.getView(R.id.item_text_id)).getText().toString();
                for (int i = 0;i<data.size();i++){
                    assetnum = data.get(i).getASSETNUM();
                    if (assetnum!=null && assetnum.equals(thisnum)){
                        data.get(i).setQTYINSTK(s.toString());
                        Log.e("QUANTITY",s.toString());
                    }
                }
            }
        });
    }

}
