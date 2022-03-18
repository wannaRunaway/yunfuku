package com.aifubook.book.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.order.MyOrderDetailsActivity;
import com.aifubook.book.mine.order.bean.ItemsBean;
import com.aifubook.book.mine.order.bean.OrderListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiarui.base.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.aifubook.book.api.ApiService.IMAGE;

public class GrouponOrderAdapter extends BaseQuickAdapter<OrderListBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    private static final String TAG = "GrouponOrderAdapter";

    public GrouponOrderAdapter() {
        super(R.layout.fragment_groupbuy_order_list_item);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder holder, OrderListBean.ListBean item) {
        RelativeLayout ll_orderDetails = holder.getView(R.id.ll_orderDetails);
        TextView tv_bottom = holder.getView(R.id.tv_bottom);
        TextView tv_bottom2 = holder.getView(R.id.tv_bottom2);
        TextView tv_bottom3 = holder.getView(R.id.tv_bottom3);
        TextView tv_productPrice = holder.getView(R.id.tv_productPrice);
        TextView tv_freightPrice = holder.getView(R.id.tv_freightPrice);
        tv_productPrice.setText("¥" + Double.parseDouble(item.getDiscountTotalFee()) / 100 + "");
        tv_freightPrice.setText("¥" + Double.parseDouble(item.getLogisticsFee()) / 100 + "");
        List<ItemsBean> itemList = item.getItems();
        int totalCount = 0;
        for (int i = 0; i < itemList.size(); i++) {
            ItemsBean itemsBean = itemList.get(i);
            totalCount = totalCount + Integer.parseInt(itemsBean.getCount());
        }
        holder.setText(R.id.tv_goods_total, "共" + totalCount + "件商品");
        holder.setText(R.id.tv_total_price, "¥" + Double.parseDouble(item.getTotalFee()) / 100);

        String reduceNum = (Double.parseDouble(item.getDiscountTotalFee()) + Double.parseDouble(item.getLogisticsFee()) - Double.parseDouble(item.getTotalFee())) / 100 + "";
        holder.setText(R.id.tv_reduce, "-￥" + reduceNum);

        LinearLayout ll_products = holder.getView(R.id.ll_products);
        showProductList(ll_products, item.getItems());

        holder.setText(R.id.tv_storeName, item.getShop().getName());
        LogUtil.e(TAG, "status=" + item.getStatus());
        switch (item.getStatus()) {
            case "0"://待付款
                tv_bottom.setVisibility(View.GONE);
                tv_bottom2.setVisibility(View.VISIBLE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom2.setText("取消订单");
                tv_bottom3.setText("去支付");
                holder.setText(R.id.tv_orderState, "待付款");
                break;
            case "9"://拼团中
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.VISIBLE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom.setText("取消订单");
                tv_bottom2.setText("查看订单");
                tv_bottom3.setText("分享好友");
                holder.setText(R.id.tv_orderState, "拼团中");
                break;
            case "10"://待配送
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.GONE);
                tv_bottom.setText("申请售后");
                tv_bottom3.setText("确认收货");
                holder.setText(R.id.tv_orderState, "待发货");
                break;
            case "20"://待提货
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom.setText("申请售后");
//                        tv_bottom2.setText("去核销");
                if (item.getLogisticsType().equals("2") || item.getLogisticsType().equals("1")) {
                    tv_bottom2.setText("查看核销码");
                    tv_bottom2.setVisibility(View.VISIBLE);
                }
                tv_bottom3.setText("确认收货");
                String cancelCode = item.getCode();
                holder.setText(R.id.tv_orderState, "待收货");
                break;
            case "30"://已提货
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom.setText("删除订单");
                tv_bottom3.setText("再次购买");
                holder.setText(R.id.tv_orderState, "交易完成");
                break;
            case "40"://售后
                if (item.getRefundType() == null) {
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom2.setVisibility(View.VISIBLE);
                    tv_bottom3.setVisibility(View.GONE);
                    tv_bottom.setText("删除订单");
                    tv_bottom2.setText("查看售后");
                    holder.setText(R.id.tv_orderState, "退款失败");
                } else {
                    int status = item.getRefundStatus();
                    LogUtil.e(TAG, "refundStatus=" + status);
                    tv_bottom.setVisibility(View.GONE);
                    if ("1".equals(status + "")) {
                        holder.setText(R.id.tv_orderState, "退货申请中");
                    } else if ("2".equals(status + "")) {
                        holder.setText(R.id.tv_orderState, "退款成功");
                    } else if ("3".equals(status + "")) {
                        holder.setText(R.id.tv_orderState, "退款拒绝");
                    } else if ("4".equals(status + "")) {
                        holder.setText(R.id.tv_orderState, "待退货");
                        tv_bottom.setText("填写退货单号");
                        tv_bottom.setVisibility(View.VISIBLE);
                    } else if ("5".equals(status + "")) {
                        holder.setText(R.id.tv_orderState, "退货中");
                    }
                    tv_bottom2.setText("查看售后");
                    tv_bottom2.setVisibility(View.VISIBLE);
                    tv_bottom3.setVisibility(View.GONE);

                }
                break;
            case "97":
            case "98":
            case "99":
            case "100":
                tv_bottom.setVisibility(View.VISIBLE);
                tv_bottom2.setVisibility(View.GONE);
                tv_bottom3.setVisibility(View.VISIBLE);
                tv_bottom.setText("删除订单");
                tv_bottom3.setText("再次购买");
                holder.setText(R.id.tv_orderState, "已取消");
                break;


        }

        ll_orderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e(TAG,"item="+item.toString());
                Intent intent = new Intent(getContext(), MyOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", item.getId() + "");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });



    }

    private void showProductList(LinearLayout ll_products, List<ItemsBean> items) {
        ll_products.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_order_list_product_item, null);
            ImageView iv_product = itemView.findViewById(R.id.iv_product);
            TextView tv_product_name = itemView.findViewById(R.id.tv_product_name);
            TextView tv_price = itemView.findViewById(R.id.tv_price);
            TextView tv_product_number = itemView.findViewById(R.id.tv_product_number);
            TextView tv_sp = itemView.findViewById(R.id.tv_sp);
            LinearLayout ll_details = itemView.findViewById(R.id.ll_details);

            ItemsBean itemsBean = items.get(i);

            Glide.with(getContext()).load(IMAGE + itemsBean.getProductImage()).into(iv_product);
            tv_product_name.setText(itemsBean.getProductName());
            tv_price.setText("¥" + (itemsBean.getProductPrice()) / ApiService.onehundred);
            tv_product_number.setText("x" + itemsBean.getCount());
            tv_sp.setText(itemsBean.getProductSubName());
            ll_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MyOrderDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", itemsBean.getOrderId() + "");
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });

            ll_products.addView(itemView);
        }


    }
}
