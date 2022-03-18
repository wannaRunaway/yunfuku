package com.aifubook.book.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.mine.coupons.MemberCouponsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.utils.LogUtil;
import com.jiarui.base.utils.TimeUtil;

import org.jetbrains.annotations.NotNull;

public class CouponAdapter extends BaseQuickAdapter<MemberCouponsBean.ListBean, BaseViewHolder> {

    private int listType;

    public CouponAdapter(int type) {
//        super(R.layout.layout_coupons_fragment_item);
        super(R.layout.item_coupon_shopcar);
        this.listType = type;

    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MemberCouponsBean.ListBean bean) {
        MemberCouponsBean.ListBean.CouponBean item = bean.getCoupon();
        String t = item.getType();
        int type = Integer.parseInt(t);

        if (0 == type) {
            holder.setText(R.id.tv_type, "店铺满减券");
            holder.setText(R.id.tv_dis, "满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
            holder.setText(R.id.tv_money,  "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
            if (bean.getShop()!=null) {
                holder.setText(R.id.tv_info, bean.getShop().getName());
            }
        } else if (1 == type) {
            holder.setText(R.id.tv_type, "无敌券");
            holder.setText(R.id.tv_dis, "无门槛红包");
            holder.setText(R.id.tv_money, "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
            holder.setText(R.id.tv_info, "无门槛");
        } else if (2 == type) {
            holder.setText(R.id.tv_type, "全站通用满减券");
            holder.setText(R.id.tv_dis, "满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
            holder.setText(R.id.tv_money, "¥"+(Double.parseDouble(item.getReduceFee() + "") / 100));
            holder.setText(R.id.tv_info, "全平台通用");
        } else if (3 == type) {
            holder.setText(R.id.tv_type, "店铺折扣券");
            holder.setText(R.id.tv_money, (Double.parseDouble(item.getDiscountRate() + "") / 10) + "折");
            if (bean.getShop()!=null) {
                holder.setText(R.id.tv_info, bean.getShop().getName());
            }
        }
        holder.setText(R.id.tv_deadline, TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getStartTime())) + "--" + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getEndTime())));
        TextView tv_get = holder.getView(R.id.tv_get);
        if (listType == 0) {
            tv_get.setEnabled(true);
            tv_get.setText("去使用");
            ((TextView)holder.getView(R.id.tv_money)).setTextColor(getContext().getResources().getColor(R.color.red_F7553B));
            ((TextView)holder.getView(R.id.tv_dis)).setTextColor(getContext().getResources().getColor(R.color.red_F7553B));
            ((TextView)holder.getView(R.id.tv_type)).setTextColor(getContext().getResources().getColor(R.color.black));
            ((TextView)holder.getView(R.id.tv_info)).setTextColor(getContext().getResources().getColor(R.color.color666666));
            ((TextView)holder.getView(R.id.tv_deadline)).setTextColor(getContext().getResources().getColor(R.color.color666666));
            ((TextView)holder.getView(R.id.tv_get)).setVisibility(View.VISIBLE);
            holder.getView(R.id.outofdate_orused_couponimage).setVisibility(View.GONE);
        } else if (listType == 1) {
            tv_get.setEnabled(false);
            tv_get.setText("已使用");
            ((TextView)holder.getView(R.id.tv_money)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_dis)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_type)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_info)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_deadline)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_get)).setVisibility(View.GONE);
            holder.getView(R.id.outofdate_orused_couponimage).setVisibility(View.VISIBLE);
            ((ImageView)holder.getView(R.id.outofdate_orused_couponimage)).setImageResource(R.mipmap.coupon_used);
        } else if (listType == 2) {
            tv_get.setEnabled(false);
            tv_get.setText("已过期");
            ((TextView)holder.getView(R.id.tv_money)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_dis)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_type)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_info)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_deadline)).setTextColor(getContext().getResources().getColor(R.color.colorcccccc));
            ((TextView)holder.getView(R.id.tv_get)).setVisibility(View.GONE);
            holder.getView(R.id.outofdate_orused_couponimage).setVisibility(View.VISIBLE);
            ((ImageView)holder.getView(R.id.outofdate_orused_couponimage)).setImageResource(R.mipmap.coupon_outoftime);
        }
    }
}
