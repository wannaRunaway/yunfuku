package com.jiarui.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.jiarui.base.glide.ShapeImageView;
import com.jiarui.base.utils.DisplayUtil;
import com.jiarui.base.utils.StringUtils;

import java.util.List;

/**
 * @author shoyu
 * @ClassName MultiImageView.java
 * @Description: 显示1~N张图片的View
 */

public class MultiImageView extends LinearLayout {
    public static int MAX_WIDTH = 0;
    public static int MAX_HEIGHT = 0;

    // 照片的Url列表
    protected List<String> imagesList;

    private String strImgHeader;

    protected int[] imgUrls;

    public void setStrImgHeader(String strImgHeader) {
        this.strImgHeader = strImgHeader;
    }

    /**
     * 长度 单位为Pixel
     **/
    protected int pxOneMaxWandH; // 单张图最大允许宽高
    protected int pxMoreWandH = 0;// 多张图的宽高
    protected int pxImagePadding = DisplayUtil.dip2px(getContext(), 3);// 图片间的间距

    protected int MAX_PER_ROW_COUNT = 3;// 每行显示最大数

    protected LayoutParams onePicPara;
    protected LayoutParams morePara, moreParaColumnFirst;
    protected LayoutParams rowPara;

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MultiImageView(Context context) {
        super(context);
    }

    public MultiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setList(List<String> lists) throws IllegalArgumentException {
        if (lists == null) {
            throw new IllegalArgumentException("imageList is null...");
        }
        imagesList = lists;

        if (MAX_WIDTH > 0) {
            pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3; // 解决右侧图片和内容对不齐问题
            pxOneMaxWandH = MAX_WIDTH * 2 / 3;
            initImageLayoutParams();
        }

        initView();
    }

    public void setList(int[] imUrls) throws IllegalArgumentException {
        if (imUrls == null) {
            throw new IllegalArgumentException("imageList is null...");
        }
        imgUrls = imUrls;

        if (MAX_WIDTH > 0) {
            pxMoreWandH = (MAX_WIDTH - pxImagePadding * 2) / 3; // 解决右侧图片和内容对不齐问题
            pxOneMaxWandH = MAX_WIDTH * 2 / 3;
            initImageLayoutParams();
        }

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MAX_WIDTH == 0) {
            int width = measureWidth(widthMeasureSpec);
            int height = measureWidth(heightMeasureSpec);

            if (width > 0) {
                MAX_WIDTH = width;
                MAX_HEIGHT = height;
                if (imgUrls != null && imgUrls.length > 0) {
                    //  setList(imagesList);
                    setList(imgUrls);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    protected int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            // result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
            // + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    protected void initImageLayoutParams() {
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;

        onePicPara = new LayoutParams(wrap, wrap);

        moreParaColumnFirst = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara = new LayoutParams(pxMoreWandH, pxMoreWandH);
        morePara.setMargins(pxImagePadding, 0, 0, 0);

        rowPara = new LayoutParams(match, wrap);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    protected void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();
        if (MAX_WIDTH == 0) {
            // 为了触发onMeasure()来测量MultiImageView的最大宽度，MultiImageView的宽设置为match_parent
            addView(new View(getContext()));
            return;
        }

        if (imgUrls == null || imgUrls.length == 0) {
            return;
        }
        int allCount = imgUrls.length;
        if (!StringUtils.isEmpty(strImgHeader)) {
            MAX_PER_ROW_COUNT = 4;
            int rowCount = 1;// 行数
            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLayout = new LinearLayout(getContext());
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                rowLayout.setLayoutParams(rowPara);
                if (rowCursor != 0) {
                    rowLayout.setPadding(0, pxImagePadding, 0, 0);
                }

                int columnCount = 4;// 每行的列数
                if (rowCursor != rowCount - 1) {
                    columnCount = MAX_PER_ROW_COUNT;
                }
                addView(rowLayout);

                int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
                for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                    int position = columnCursor + rowOffset;
                    rowLayout.addView(createImageView(position, true));
                }
            }
        } else {
            if (imgUrls.length == 1) {
                addView(createImageView(0, false));
            } else {

                if (allCount == 4) {
                    MAX_PER_ROW_COUNT = 2;
                } else {
                    MAX_PER_ROW_COUNT = 3;
                }
                int rowCount = allCount / MAX_PER_ROW_COUNT + (allCount % MAX_PER_ROW_COUNT > 0 ? 1 : 0);// 行数
                for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                    LinearLayout rowLayout = new LinearLayout(getContext());
                    rowLayout.setOrientation(LinearLayout.HORIZONTAL);

                    rowLayout.setLayoutParams(rowPara);
                    if (rowCursor != 0) {
                        rowLayout.setPadding(0, pxImagePadding, 0, 0);
                    }

                    int columnCount = allCount % MAX_PER_ROW_COUNT == 0 ? MAX_PER_ROW_COUNT : allCount % MAX_PER_ROW_COUNT;// 每行的列数
                    if (rowCursor != rowCount - 1) {
                        columnCount = MAX_PER_ROW_COUNT;
                    }
                    addView(rowLayout);

                    int rowOffset = rowCursor * MAX_PER_ROW_COUNT;// 行偏移
                    for (int columnCursor = 0; columnCursor < columnCount; columnCursor++) {
                        int position = columnCursor + rowOffset;
                        rowLayout.addView(createImageView(position, true));
                    }
                }
            }
        }
    }

    protected ImageView createImageView(int position, final boolean isMultiImage) {
//        String url = imagesList.get(position);
        int imgurl = imgUrls[position];
        ShapeImageView imageView = new ColorFilterImageView(getContext());
        imageView.setScaleType(ScaleType.CENTER_CROP);
        if (isMultiImage) {
            imageView.setLayoutParams(position % MAX_PER_ROW_COUNT == 0 ? moreParaColumnFirst : morePara);
        } else {
            imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setMaxHeight(pxOneMaxWandH);
            imageView.setLayoutParams(onePicPara);
        }
        imageView.setRadius(5);
        imageView.setId(position);
        imageView.setShapeType(ShapeImageView.ShapeType.RECTANGLE);
        imageView.setImageResource(imgurl);
        imageView.setOnClickListener(new ImageOnClickListener(position));

        return imageView;
    }

    protected class ImageOnClickListener implements OnClickListener {

        protected int position;

        public ImageOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, position);
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}