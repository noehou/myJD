package com.fwy.tommaso.jdmall.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fwy.tommaso.jdmall.R;
import com.fwy.tommaso.jdmall.bean.RGoodComment;
import com.fwy.tommaso.jdmall.cons.NetworkConst;
import com.fwy.tommaso.jdmall.ui.RatingBar;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Tommaso on 2018/1/3.
 */

public class GoodCommentAdapter extends JDBaseAdapter<RGoodComment> {

    public GoodCommentAdapter(Context c) {
        super(c);
    }

    class ViewHolder{
        RatingBar commentLevelRb;
        TextView nameTv;
        TextView commentContentTv;
        LinearLayout imageContainerLI;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.good_comment_item_view,parent,false);
            holder=new ViewHolder();
            holder.commentLevelRb = (RatingBar)convertView.findViewById(R.id.rating_bar);
            holder.nameTv = (TextView)convertView.findViewById(R.id.name_tv);
            holder.commentContentTv = (TextView) convertView.findViewById(R.id.content_tv);
            holder.imageContainerLI = (LinearLayout)convertView.findViewById(R.id.iamges_container);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        RGoodComment bean = mDatas.get(position);
        holder.commentLevelRb.setRating(bean.getRate());
        holder.nameTv.setText(bean.getUserName());
        holder.commentContentTv.setText(bean.getComment());

        int childCount = holder.imageContainerLI.getChildCount();
        List<String> imageUrls = JSON.parseArray(bean.getImgUrls(),String.class);
        int dataSize = imageUrls.size();
        int realSize=Math.min(childCount,dataSize);
        for (int i = 0;i < childCount;i++){
            SmartImageView smiv = (SmartImageView) holder.imageContainerLI.getChildAt(i);
            smiv.setImageDrawable(new BitmapDrawable());
        }


        for (int i = 0;i<realSize;i++){
            SmartImageView smiv = (SmartImageView) holder.imageContainerLI.getChildAt(i);
            smiv.setImageUrl(NetworkConst.BASE_URL+imageUrls.get(i));
        }

        holder.imageContainerLI.setVisibility(realSize>0?View.VISIBLE:View.GONE);

        return convertView;
    }
}
