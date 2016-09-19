package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by yotamc on 01-Sep-16.
 */
public class GifView extends View {

    public Movie mMovie;
    public long movieStart;

    public GifView(Context context) {
        super(context);
        initView();
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        //R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(R.raw.zebra);
        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
            this.invalidate();
        }
    }
    private int gifId;


    public void setGIFResource(int resId) {
        this.gifId = resId;
        initView();
    }
    public int getGIFResource() {
        return this.gifId;
    }
}
