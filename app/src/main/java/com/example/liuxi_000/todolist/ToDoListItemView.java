package com.example.liuxi_000.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by liuxi_000 on 2014/7/14.
 */
public class ToDoListItemView extends TextView {
    public ToDoListItemView(Context context, AttributeSet set, int ds) {
        super(context, set, ds);
        init();
    }

    public ToDoListItemView(Context context) {
        super(context);
        init();
    }
    public ToDoListItemView(Context context,  AttributeSet set) {
        super(context, set);
        init();
    }

    private Paint marginPaint;
    private Paint linePaint;
    private int paperColor;
    private float margin;
    private void init() {
        Resources resources = getResources();

        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(resources.getColor(R.color.notepad_margin));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(resources.getColor(R.color.notepad_lines));

        paperColor = resources.getColor(R.color.notepad_papers);
        margin = resources.getDimension(R.dimen.notepad_margin);
    }

    @Override
    public void  onDraw(Canvas canvas) {
        canvas.drawColor(paperColor);

        canvas.drawLine(0, 0, 0, getMeasuredHeight(), linePaint);
        canvas.drawLine(0, getMeasuredHeight(),
                getMeasuredWidth(), getMeasuredHeight(), linePaint);

        canvas.drawLine(margin, 0, margin, getMeasuredHeight(),
                marginPaint);
        canvas.save();
        canvas.translate(margin, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
}
