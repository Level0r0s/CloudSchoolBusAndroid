package com.guokrspace.cloudschoolbus.parents.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexafree.materialList.model.CardItemView;
import com.guokrspace.cloudschoolbus.parents.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * Created by Yang Kai on 15/7/14.
 */
public class TeacherListCardItemView extends CardItemView<NoticeCard> {

    public TeacherListCardItemView(Context context) {
        super(context);
    }

    public TeacherListCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TeacherListCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(NoticeCard card) {
        super.build(card);

        /*
         * Header
         */
        //Teacher Head
        ImageView teacherHead = (ImageView) findViewById(R.id.teacher_avatar);
        if (teacherHead != null) {
            if(card.getTeacherAvatarUrl() == null || card.getTeacherAvatarUrl().isEmpty()) {
                teacherHead.setImageDrawable(card.getDrawable());
            } else {
                Picasso.with(getContext()).load(card.getTeacherAvatarUrl()).into(teacherHead);
            }
        }
        //Teacher Name
        TextView teacherName = (TextView) findViewById(R.id.teacher_name);
        teacherName.setText(card.getTeacherName());
        if (card.getDescriptionColor() != -1) {
            teacherName.setTextColor(card.getDescriptionColor());
        }

        //Classname
        TextView kindergarten = (TextView) findViewById(R.id.classname);
        kindergarten.setText(card.getKindergarten());
        if (card.getDescriptionColor() != -1) {
            kindergarten.setTextColor(card.getDescriptionColor());
        }
    }
}