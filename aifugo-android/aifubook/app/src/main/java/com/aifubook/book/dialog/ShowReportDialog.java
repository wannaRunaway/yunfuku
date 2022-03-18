package com.aifubook.book.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aifubook.book.R;
import com.aifubook.book.api.ApiService;
import com.aifubook.book.base.CouponBean;
import com.aifubook.book.base.CouponBeans;
import com.aifubook.book.bean.ProductDetailBean;
import com.aifubook.book.bean.TypeBean;
import com.aifubook.book.mine.coupons.MyCouponsBean;
import com.jiarui.base.fresco.CommonImage;
import com.jiarui.base.utils.ImageUtil;
import com.jiarui.base.utils.StringUtils;
import com.jiarui.base.utils.TimeUtil;
import com.jiarui.base.utils.ToastUtil;

import java.util.List;

import androidx.core.app.ActivityCompat;

public class ShowReportDialog {

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {

        void onConfirm();

        void onCancel();
    }

    private OnValueClickListener valueClickListener;

    public void setOnValueListener(OnValueClickListener listener) {
        this.valueClickListener = listener;
    }

    public interface OnValueClickListener {

        void onConfirm(Object bean);

        void onCancel();
    }

    public Dialog showGrouponResultDialog(final Activity activity, String content, int type) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_groupon_result, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_close = view.findViewById(R.id.tv_close);
        TextView tv_join = view.findViewById(R.id.tv_join);
        ImageView iv_center = view.findViewById(R.id.iv_center);
        tv_content.setText(content);
        if (type == 1) {
            iv_center.setImageResource(R.drawable.iv_over_time);
        } else if (type == 2) {
            iv_center.setImageResource(R.drawable.iv_cry);
        }

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueClickListener.onCancel();
                dialog.dismiss();
            }
        });
        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueClickListener.onConfirm(1);
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;
    }

    public Dialog showGrouponShareDialog(final Activity activity, String num) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_groupon_share, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        TextView tv_wechat = view.findViewById(R.id.tv_wechat);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tv_num = view.findViewById(R.id.tv_num);
        tv_num.setText(num);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm();
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;
    }


    public Dialog showGrouponDialog(final Activity activity, List<ProductDetailBean.GroupOrder> groupOrders) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_groupon_add, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);

        LinearLayout ll_groupon_add = view.findViewById(R.id.ll_groupon_add);
        ImageView iv_close = view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll_groupon_add.removeAllViews();
        if (groupOrders != null && groupOrders.size() > 0) {
            for (int i = 0; i < groupOrders.size(); i++) {
                View grouponView = LayoutInflater.from(activity).inflate(R.layout.item_groupon_product, null);
                CommonImage cimage1 = grouponView.findViewById(R.id.cimage1);
                CommonImage cimage2 = grouponView.findViewById(R.id.cimage2);
                TextView tv_name = grouponView.findViewById(R.id.tv_name);
                TextView tv_groupon_order = grouponView.findViewById(R.id.tv_groupon_order);
                List<ProductDetailBean.GroupOrder.User> users = groupOrders.get(i).getUsers();
                ProductDetailBean.GroupOrder groupOrder = groupOrders.get(i);
                String orderId = groupOrder.getId();
                tv_groupon_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        valueClickListener.onConfirm(groupOrder);
                        dialog.dismiss();
                    }
                });

                if (users.size() > 1) {
                    cimage1.setVisibility(View.VISIBLE);
                    cimage2.setVisibility(View.VISIBLE);
                } else {
                    cimage1.setVisibility(View.GONE);
                    cimage2.setVisibility(View.VISIBLE);
                }
                for (int j = 0; j < users.size(); j++) {
                    if (j < 2) {

                        ProductDetailBean.GroupOrder.User user = users.get(j);
                        String userName = user.getUserName();
                        String userImage = user.getUserImage();
                        if (!userImage.contains("http")) {
                            userImage = ApiService.IMAGE + userImage;
                        }
                        if (j == 0) {
                            cimage2.setImageURI(userImage);
                            tv_name.setText(userName);
                        } else{
                            cimage1.setImageURI(userImage);
                            tv_name.setText(users.get(0).getUserName()+" "+userName);
                        }
                    }


                }
                ll_groupon_add.addView(grouponView);
            }

        }
        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;
    }

    public Dialog showLocationDialog(final Activity activity) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_location, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_open = view.findViewById(R.id.tv_open);

        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm();
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;

    }

    public Dialog choosePayType(final Activity activity, int payType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_choose_pay, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, false, false);

        LinearLayout ll_alipay = view.findViewById(R.id.ll_alipay);
        LinearLayout ll_wechat = view.findViewById(R.id.ll_wechat);
        CheckBox cb_alipay = view.findViewById(R.id.cb_alipay);
        CheckBox cb_wechat = view.findViewById(R.id.cb_wechat);
        TextView tv_ok = view.findViewById(R.id.tv_ok);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        if (payType == 0) {
            cb_alipay.setChecked(true);
            cb_wechat.setChecked(false);
        } else {
            cb_alipay.setChecked(false);
            cb_wechat.setChecked(true);
        }


        ll_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_alipay.setChecked(!cb_alipay.isChecked());
                if (cb_alipay.isChecked()) {
                    cb_wechat.setChecked(false);
                }

            }
        });
        ll_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_wechat.setChecked(!cb_wechat.isChecked());
                if (cb_wechat.isChecked()) {
                    cb_alipay.setChecked(false);
                }
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_alipay.isChecked()) {
                    valueClickListener.onConfirm(0);
                } else {
                    valueClickListener.onConfirm(1);

                }
                dialog.dismiss();

            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;

    }


    public Dialog showOrderCouponDialog(final Activity activity, List<CouponBeans> DataBean) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_order_coupon, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, false, false);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        LinearLayout ll_coupons = view.findViewById(R.id.ll_coupons);
        ll_coupons.removeAllViews();
        for (int i = 0; i < DataBean.size(); i++) {
            CouponBeans couponBean = DataBean.get(i);
            CouponBeans.CouponDTO item = DataBean.get(i).getCoupon();
            View item_grade = LayoutInflater.from(activity).inflate(R.layout.item_coupon_shopcar, null);
            TextView tv_money = item_grade.findViewById(R.id.tv_money);
            TextView tv_dis = item_grade.findViewById(R.id.tv_dis);
            TextView tv_deadline = item_grade.findViewById(R.id.tv_deadline);
            TextView tv_type = item_grade.findViewById(R.id.tv_type);
            TextView tv_get = item_grade.findViewById(R.id.tv_get);
            TextView tvinfo = item_grade.findViewById(R.id.tv_info);
            if (couponBean.getShop()==null){
                tvinfo.setVisibility(View.GONE);
            }else {
                tvinfo.setVisibility(View.VISIBLE);
            }
            int type = item.getType();
            if (0 == type) {
                tv_type.setText("店铺满减券");
                tv_dis.setText("满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
                tv_money.setText("¥" + (Double.parseDouble(item.getReduceFee() + "") / 100));
                if (couponBean.getShop()!=null) {
                    tvinfo.setText(couponBean.getShop().getName());
                }
            } else if (1 == type) {
                tv_type.setText("无敌券");
                tv_dis.setText("无门槛红包");
                tv_money.setText("¥" + (Double.parseDouble(item.getReduceFee() + "") / 100) + "");
                tvinfo.setText("无门槛");
            } else if (2 == type) {
                tv_type.setText("全站通用满减券");
                tv_dis.setText("满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
                tv_money.setText("¥" + (Double.parseDouble(item.getReduceFee() + "") / 100) + "");
                tvinfo.setText("全平台通用");
            } else if (3 == type) {
                tv_type.setText("店铺折扣券");
                tv_money.setText((Double.parseDouble(item.getDiscountRate() + "") / 10) + "折");
                if (couponBean.getShop()!=null) {
                    tvinfo.setText(couponBean.getShop().getName());
                }
            }
            tv_deadline.setText("使用期限 " + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getStartTime())) + "-" + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getEndTime())));
            tv_get.setText("立即使用");
            tv_get.setOnClickListener(v -> {
                valueClickListener.onConfirm(couponBean);
                dialog.dismiss();
            });

            ll_coupons.addView(item_grade);
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;

    }


    public Dialog showCouponDialog(final Activity activity, List<CouponBean> DataBean) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_coupon_shopcar, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, false, false);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        LinearLayout ll_coupons = view.findViewById(R.id.ll_coupons);
        TextView tv_ok = view.findViewById(R.id.tv_ok);

        ll_coupons.removeAllViews();
        for (int i = 0; i < DataBean.size(); i++) {
            CouponBean item = DataBean.get(i);
            View item_grade = LayoutInflater.from(activity).inflate(R.layout.item_coupon_shopcar, null);
            TextView tv_money = item_grade.findViewById(R.id.tv_money);
            TextView tv_dis = item_grade.findViewById(R.id.tv_dis);
            TextView tv_deadline = item_grade.findViewById(R.id.tv_deadline);
            TextView tv_type = item_grade.findViewById(R.id.tv_type);
            TextView tv_left = item_grade.findViewById(R.id.tv_left);
            TextView tv_right = item_grade.findViewById(R.id.tv_right);

            int type = item.getType();
            if (0 == type) {
                tv_type.setText("店铺满减券");
                tv_dis.setText("满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
                tv_money.setText((int) (Double.parseDouble(item.getReduceFee() + "") / 100) + "");
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.GONE);

            } else if (1 == type) {
                tv_type.setText("无敌券");
                tv_dis.setText("无门槛红包");
                tv_money.setText((int) (Double.parseDouble(item.getReduceFee() + "") / 100) + "");
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.GONE);
            } else if (2 == type) {
                tv_type.setText("全站通用满减券");
                tv_dis.setText("满" + ((int) (Double.parseDouble(item.getFullFee() + "") / 100)) + "元可用");
                tv_money.setText((int) (Double.parseDouble(item.getReduceFee() + "") / 100) + "");
                tv_left.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.GONE);

            } else if (3 == type) {
                tv_type.setText("店铺折扣券");
                tv_left.setVisibility(View.GONE);
                tv_right.setVisibility(View.VISIBLE);
                tv_money.setText((Double.parseDouble(item.getDiscountRate() + "") / 10) + "");

            }

            tv_deadline.setText("使用期限 " + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getStartTime())) + "-" + TimeUtil.formatMsecConvertTime2(Long.valueOf(item.getEndTime())));
            TextView tv_get = item_grade.findViewById(R.id.tv_get);
            boolean possess = item.isPossess();
            tv_get.setEnabled(!possess);
            tv_get.setText(possess ? "已领取" : "领取");
            int finalI = i;
            if (!possess) {
                tv_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_get.setText("已领取");
                        tv_get.setEnabled(false);
                        valueClickListener.onConfirm(item);
                    }
                });
            }

            ll_coupons.addView(item_grade);
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }

        return dialog;
    }


    public Dialog showReturnDialog(final Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dailog_show_return, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        EditText et_number = view.findViewById(R.id.et_number);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = et_number.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    ToastUtil.showToast("运单号码不能为空", false);
                    return;
                }
                valueClickListener.onConfirm(content);
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;


    }

    public Dialog showRefundDialog(final Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_show_refund, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, false, false);
        Button bt_c = view.findViewById(R.id.bt_c);

        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public Dialog showListDialog(final Activity activity, List<TypeBean.ChildrenBean> childrenList) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_showlist, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        LinearLayout ll_children = view.findViewById(R.id.ll_childrens);

        ll_children.removeAllViews();
        for (int i = 0; i < childrenList.size(); i++) {
            View item_grade = LayoutInflater.from(activity).inflate(R.layout.item_grade, null);
            TextView tv_grade = item_grade.findViewById(R.id.tv_grade);
            TypeBean.ChildrenBean childrenBean = childrenList.get(i);
            tv_grade.setText(childrenBean.getName());
            item_grade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    valueClickListener.onConfirm(childrenBean);
                    dialog.dismiss();
                }
            });
            ll_children.addView(item_grade);
        }

//        for (int i = 0; i < typeBeans.size(); i++) {
//            TypeBean typeBean = typeBeans.get(i);
//            View item_grade = mInflater.inflate(R.layout.item_grade, null);
//            TextView tv_grade = item_grade.findViewById(R.id.tv_grade);
//            tv_grade.setText(typeBean.getName());
//            ll_grade.addView(item_grade);
//        }

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;

    }


    public Dialog showCancelTipDialog(final Activity activity, String title, String content) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_cancel_order, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_content.setText(content);
        tv_title.setText(title);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;


    }

    public Dialog showAlertTipDialog(final Activity activity, String content) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_tip_show, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;


    }


    public Dialog showLogoutDialog(final Activity activity) {


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_logout_tip, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm();
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public static Dialog showParameterDialog(final Activity activity, String publisher, String subject, String coding, String grade, String price, String discount) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_parameter, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, true, true);
        TextView tv_publisher = view.findViewById(R.id.tv_publisher);
        TextView tv_subject = view.findViewById(R.id.tv_subject);
        TextView tv_coding = view.findViewById(R.id.tv_coding);
        TextView tv_grade = view.findViewById(R.id.tv_grade);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_discount = view.findViewById(R.id.tv_discount);

        tv_publisher.setText(publisher);
        tv_subject.setText(subject);
        tv_coding.setText(coding);
        tv_grade.setText(grade);
        tv_price.setText(StringUtils.isNull((Double.parseDouble(price + "") / 100) + "") + "元");
        tv_discount.setText(StringUtils.isNull((Double.parseDouble(discount + "") / 100) + "") + "元");


        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public static Dialog showGuaranteeDialog(final Activity activity) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_guarantee, null);
        final BottomDialogView dialog = new BottomDialogView(activity, view, true, true);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public static Dialog showTipDialog(final Activity activity) {


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_report_show, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public static Dialog showDialog(final Activity activity) {


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_report, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);

        EditText et_content = view.findViewById(R.id.et_content);
        TextView tv_report = view.findViewById(R.id.tv_report);
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    Toast.makeText(activity, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                showConfirmDialog(activity);

            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }

    public static Dialog showConfirmDialog(final Activity activity) {


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_report_confirm, null);
        final CenterDialogView dialog = new CenterDialogView(activity, view, true, true);


        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!activity.isFinishing()) {
            dialog.show();
        }


        return dialog;
    }


}
