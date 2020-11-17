package com.example.myapplication;

import android.graphics.RectF;

public class Paddle{

    private int width;
    private int height;
    RectF rect;
    final int stopped = 0;
    final int left = -1;
    final int right = 1;
    private int moving = stopped;

    public Paddle(int x, int y)
    {
        this.height = 20;
        this.width = 400;
        this.rect = new RectF((x/2)-(width/2), (y*9)/10, (x/2)+(width/2), (y*9)/10 + height);
    }

    public RectF GetRectangle()
    {
        return this.rect;
    }

    public int getWidth()
    {
        return this.width;
    }

    public void setpaddleX(int position)
    {
        this.rect.left = position;
        this.rect.right = position + this.width;
    }

    void setDirection(int dir){
        moving = dir;
    }

    void move(){
        if(moving == left){
            rect.left -= 15;
            rect.right -= 15;
        }

        if(moving == right){
            rect.left += 15;
            rect.right += 15;
        }

    }
}

