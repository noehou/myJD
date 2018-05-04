package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RProductComment;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.ui.RatingBar;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class CommentAdapter extends JDBaseAdapter<RProductComment> {

    public int mCurrentTabPosition=-1;

    public CommentAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        SmartImageView userIconIv;
        TextView userNameTv;
        TextView commentTimeTv;
        RatingBar commentLevelRb;
        TextView commentTv;
        TextView buyTimeTv;
        TextView buyVersionTv;
        TextView loveCountTv;
        TextView subCommentTv;
        LinearLayout imageContainerLI;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.comment_item_view,parent,false);
            holder=new ViewHolder();
            holder.userIconIv = (SmartImageView) convertView.findViewById(R.id.icon_iv);
            holder.userNameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.commentTimeTv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.commentLevelRb = (RatingBar) convertView.findViewById(R.id.rating_bar);
            holder.commentTv = (TextView) convertView.findViewById(R.id.content_tv);
            holder.buyTimeTv = (TextView) convertView.findViewById(R.id.buytime_tv);
            holder.buyVersionTv = (TextView) convertView.findViewById(R.id.buyversion_tv);
            holder.loveCountTv = (TextView) convertView.findViewById(R.id.lovecount_tv);
            holder.subCommentTv = (TextView) convertView.findViewById(R.id.subcomment_tv);
            holder.imageContainerLI = (LinearLayout) convertView.findViewById(R.id.iamges_container);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RProductComment bean = mDatas.get(position);
        holder.userIconIv.setImageUrl(NetworkConst.BASE_URL+bean.getUserImg());
        holder.userNameTv.setText(bean.getUserName());
        holder.commentTimeTv.setText(bean.getCommentTime());
        holder.commentLevelRb.setRating(bean.getRate());
        holder.commentTv.setText(bean.getComment());
        holder.buyTimeTv.setText("购买时间："+bean.getBuyTime());
        holder.buyVersionTv.setText("型号："+bean.getProductType());
        initImageContainer(holder.imageContainerLI,bean.getImgUrls());
        holder.loveCountTv.setText("喜欢（"+bean.getLoveCount()+"）");
        holder.subCommentTv.setText("回复（"+bean.getSubComment()+")");
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mDatas!=null?mDatas.get(position):null;
    }

    private void initImageContainer(LinearLayout containerLl, String imageUrlJson) {
        // 1.知道容器 holer.imageContainerLl
        // 2.知道容器里面到底要添加多少内容吗?
        // 如果放回的数据是3: 显示3
        // 如果放回的数据是5: 显示4
        int childCount = containerLl.getChildCount();
        List<String> imgUrls = JSON.parseArray(imageUrlJson, String.class);
        int dataSize = imgUrls.size();
        int realSize = Math.min(childCount, dataSize);
        // 清空老数据
        for (int i = 0; i < childCount; i++) {
            SmartImageView smiv = (SmartImageView) containerLl.getChildAt(i);
            smiv.setImageDrawable(new BitmapDrawable());
        }
        // 设置新的数据
        for (int i = 0; i < realSize; i++) {
            SmartImageView smiv = (SmartImageView) containerLl.getChildAt(i);
            smiv.setImageUrl(NetworkConst.BASE_URL + imgUrls.get(i));
        }
        containerLl.setVisibility(realSize > 0 ? View.VISIBLE
                : View.GONE);
    }
}
