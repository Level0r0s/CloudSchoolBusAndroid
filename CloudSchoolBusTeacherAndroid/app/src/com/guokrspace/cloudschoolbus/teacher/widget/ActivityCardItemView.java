package com.guokrspace.cloudschoolbus.teacher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.support.utils.DateUtils;
import com.dexafree.materialList.model.CardItemView;
import com.guokrspace.cloudschoolbus.teacher.R;
import com.guokrspace.cloudschoolbus.teacher.base.include.Version;
import com.squareup.picasso.Picasso;

/**
 * Created by Yang Kai on 15/7/14.
 */
public class ActivityCardItemView extends CardItemView<ActivityCard> {

    public ActivityCardItemView(Context context) {
        super(context);
    }

    public ActivityCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(ActivityCard card) {
        super.build(card);

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

        //Class Name
        TextView kindergarten = (TextView) findViewById(R.id.classname);
        kindergarten.setText(card.getClassName());
        if (card.getDescriptionColor() != -1) {
            kindergarten.setTextColor(card.getDescriptionColor());
        }

        //Timestamp
        String   sendTimeStr = card.getSentTime();
        TextView sentTimeTextView    = (TextView) findViewById(R.id.timestamp);
        sentTimeTextView.setText(DateUtils.timelineTimestamp(sendTimeStr,card.getContext()));
        if (card.getDescriptionColor() != -1) {
            sentTimeTextView.setTextColor(card.getDescriptionColor());
        }

        //Card Type
        String cardType = card.getCardType();
        TextView cardTextView = (TextView) findViewById(R.id.card_type);
        cardTextView.setText(cardType);

        // Title
        TextView title = (TextView) findViewById(R.id.titleTextView);
        title.setText(card.getTitle());
        if (card.getTitleColor() != -1) {
            title.setTextColor(card.getTitleColor());
        }

        // Description
        TextView description = (TextView) findViewById(R.id.text_content);
        description.setText(card.getDescription());
        if (card.getDescriptionColor() != -1) {
            description.setTextColor(card.getDescriptionColor());
        }

        // ImageView
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        if (imageView != null) {
            if(card.getUrlImage() == null || card.getUrlImage().isEmpty()) {
                imageView.setImageDrawable(card.getDrawable());
            } else {
                Picasso.with(getContext()).load(card.getUrlImage()).into(imageView);
            }
        }

        /*
         * Card Bottom
         */
        Button confirmButton = (Button) findViewById(R.id.confirm);
        //1: need user confirm 2: already confirmed 3: no user ops need
        if(Version.PARENT) {
            if (card.getIsNeedConfirm().equals("1")) {
                confirmButton.invalidate();
                confirmButton.setOnClickListener(card.getmConfirmButtonClickListener());
                confirmButton.setText(getResources().getString(R.string.confirm_activity));
                confirmButton.setBackgroundColor(getResources().getColor(R.color.button_enable));
                confirmButton.setVisibility(View.VISIBLE);
                confirmButton.setEnabled(true);
            } else if (card.getIsNeedConfirm().equals("2")) {
                confirmButton.invalidate();
                confirmButton.setText(getResources().getString(R.string.confirmed_notice));
                confirmButton.setBackgroundColor(getResources().getColor(R.color.button_disable));
                confirmButton.setEnabled(false);
                confirmButton.setVisibility(View.VISIBLE);
            } else {
                confirmButton.invalidate();
                confirmButton.setVisibility(View.INVISIBLE);
            }
        }else{ //Teacher App do not show activity
            confirmButton.setVisibility(View.INVISIBLE);
        }
    }
}
