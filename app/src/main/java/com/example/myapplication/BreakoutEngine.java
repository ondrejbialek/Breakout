package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class BreakoutEngine extends SurfaceView implements Runnable{

    private Thread gameThread = null;

    private SurfaceHolder ourHolder;

    private volatile boolean playing;
    private boolean completed;

    private boolean paused = true;

    private Canvas canvas;
    private Paint paint;

    private int screenX;
    private int screenY;

    Paddle paddle;

    boolean pom = false;
    boolean pom2 = false;

    Ball ball;
    int ballSize = 50;

    int row, col;
    Brick bricks;

    int bricksCount;

    double sumY = 0;

    public BreakoutEngine(Context context, int x, int y) {

        super(context);

        screenX = x;
        screenY = y;

        row = 6;
        col = 7;
        bricksCount = row * col;
        ourHolder = getHolder();
        paint = new Paint();

        completed = false;

        restart();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (playing) {
            if(!paused){
                update();
            }
            if(!paused || !completed)
                draw();
        }
    }

    private void update() {

        paddle.move();

        for(int k = 0; k < Math.abs(ball.getdirX()); k++) {
            if(ball.getdirX() < 0) {
                ball.setPosX(-1);
            }
            else {
                ball.setPosX(1);
            }

            if(ball.getdirY() < 0){
                sumY = Math.abs((double)ball.getdirY() / (double)ball.getdirX());
                ball.setPosY((int)-Math.round(sumY));
            }
            else{
                sumY = Math.abs((double)ball.getdirY() / (double)ball.getdirX());
                ball.setPosY((int)Math.round(sumY));
            }

            if(RectF.intersects(new RectF(ball.getposX(), ball.getposY(), ball.getposX() + ball.getSize(), ball.getposY() + ball.getSize()),paddle.GetRectangle())){

                if(paddle.GetRectangle().left >= ball.getposX() + ball.getSize() - 1 && ball.getdirX() < 0){
                    ball.setDirY(Math.abs(ball.getdirY()));
                }
                else if(paddle.GetRectangle().left >= ball.getposX() + ball.getSize() - 1 && ball.getdirX() > 0)
                {
                    ball.setDirX(-ball.getdirX());
                    ball.setDirY(Math.abs(ball.getdirY()));
                }
                else if(paddle.GetRectangle().left + paddle.getWidth() <= ball.getposX() + 1 && ball.getdirX() < 0)
                {
                    ball.setDirX(Math.abs(ball.getdirX()));
                    ball.setDirY(Math.abs(ball.getdirY()));
                }
                else if(paddle.GetRectangle().left + paddle.getWidth() <= ball.getposX() + 1 && ball.getdirX() > 0)
                {
                    ball.setDirY(Math.abs(ball.getdirY()));
                }
                else {
                    ball.setDirYRandom();
                    ball.setDirXRandom();
                }
            }

            if (ball.getposX() < 0) {
                ball.reverseX();
            }

            if (ball.getposY() < 150) {
                ball.reverseY();
            }

            if (ball.getposX() + ball.getSize() >= screenX) {
                ball.reverseX();
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {


                    if (bricks.getMap(i, j) == 1) {
                        RectF rectF;
                        rectF = bricks.GetBrick(i, j);

                        RectF ballRect = new RectF(ball.getposX(), ball.getposY(), ball.getposX() + ball.getSize(), ball.getposY() + ball.getSize());
                        RectF brickRect = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);

                        if (RectF.intersects(ballRect, brickRect)) {
                            bricksCount--;

                            if (ballRect.left + ballSize - 1 <= brickRect.left) {
                                ball.reverseX();
                            } else if (ballRect.left + 1 >= brickRect.left + (brickRect.right - brickRect.left)) {

                                ball.reverseX();
                            } else {
                                ball.reverseY();
                            }

                            bricks.setBrickValue(0, i, j);
                            pom = true;
                            pom2 = true;
                            break;
                        }
                    }
                }
                if (pom) {
                    pom = false;
                    break;
                }
            }
            if(pom2){
                pom2 = false;
                break;
            }
        }

        sumY = 0;

        if (paddle.GetRectangle().left <= 0) {
            paddle.setpaddleX(0);
        }

        if (paddle.GetRectangle().right >= screenX) {
            paddle.setpaddleX(screenX - paddle.getWidth());
        }

        if (bricksCount <= 0 || ball.getposY() > screenY)
        {
            ball.setDirX(0);
            ball.setDirY(0);
        }
    }

    void restart(){
        paddle = new Paddle(screenX, screenY);
        ball = new Ball((int) ((paddle.GetRectangle().left + paddle.getWidth() / 2) - 50 / 2),(int) paddle.GetRectangle().top - 80, ballSize);
        bricks = new Brick(row, col, screenX, screenY);
        bricksCount = row * col;
    }

    private void draw(){

        if (ourHolder.getSurface().isValid()) {

            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(0,  0, 0, 0));

            paint.setColor(Color.argb(255,0,0,0));
            canvas.drawRect(new RectF(0,0,screenX,screenY), paint);

            paint.setColor(Color.argb(255,  255, 255, 255));
            canvas.drawRect(new RectF(0,130,screenX,150), paint);

            paint.setColor(Color.argb(255,  255, 255, 255));
            canvas.drawRect(paddle.GetRectangle(), paint);

            RectF rec = new RectF(ball.getposX(),ball.getposY(),ball.getposX() + ball.getSize(),ball.getposY() + ball.getSize());
            canvas.drawRect(rec, paint);

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(bricks.getMap(i,j) == 1)
                        canvas.drawRect(bricks.GetBrick(i,j), paint);
                }
            }

            if (bricksCount <= 0)
            {
                paint.setColor(Color.argb(255,  255, 255, 255));
                paint.setTextSize(100);
                String text = "You won!";
                Rect bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, screenX/2 - bounds.width()/2,(int)(screenY*(0.6)), paint);
                paint.setTextSize(65);
                text = "Tap to start new game";
                bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, screenX/2 - bounds.width()/2,(int)(screenY*(0.65)), paint);
                completed = true;
                paused = true;
                restart();
            }

            if (ball.getposY() > screenY)
            {
                paint.setColor(Color.argb(255,  255, 255, 255));
                paint.setTextSize(100);
                String text = "You lost!";
                Rect bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, screenX/2 - bounds.width()/2,(int)(screenY*(0.6)), paint);
                paint.setTextSize(65);
                text = "Tap to start new game";
                bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, screenX/2 - bounds.width()/2,(int)(screenY*(0.65)), paint);
                completed = true;
                paused = true;
                restart();
            }

            if(paused && !completed)
            {
                paint.setTextSize(65);
                String text = "Tap to start game";
                Rect bounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), bounds);
                canvas.drawText(text, screenX/2 - bounds.width()/2,(int)(screenY*(0.6)), paint);
            }

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if(completed)
                {
                    completed = false;
                    restart();
                }
                else {
                    paused = false;
                }

                if (motionEvent.getX() > screenX / 2) {

                    paddle.setDirection(paddle.right);
                } else {
                    paddle.setDirection(paddle.left);
                }

                break;

            case MotionEvent.ACTION_UP:

                paddle.setDirection(paddle.stopped);
                break;
        }

        return true;
    }
}


