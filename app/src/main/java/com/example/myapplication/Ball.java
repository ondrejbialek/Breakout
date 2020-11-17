package com.example.myapplication;

import java.util.Random;

public class Ball{
    private int posX, posY;
    private int dirX, dirY;
    private int size;
    private int [] speedY = {-10, -11, -12, -13, -14, -15, -16, -17, -18, -19, -20};
    private int [] speedX = {-5, -7, -9, -11, -13, -15, -17, 5, 7, 9, 11, 13, 15, 17};

    public Ball(int x, int y, int ballSize)
    {
        this.posX = x;
        this.posY = y;
        this.dirX = -14;
        this.dirY = -27;
        this.size = ballSize;
    }

    public int getSize(){
        return this.size;
    }

    public void setPosX(int pos){
        this.posX += pos;
    }

    public void setPosY(int pos){
        this.posY += pos;
    }

    public int getposX()
    {
        return this.posX;
    }

    public int getposY()
    {
        return this.posY;
    }

    public int getdirX()
        {
            return this.dirX;
        }

    public int getdirY()
    {
        return this.dirY;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public void reverseX()
    {
        this.dirX = -(this.dirX);
    }

    public void reverseY()
    {
        this.dirY = -(this.dirY);
    }

    public void setDirYRandom()
    {
        Random r = new Random();
        this.dirY = speedY[r.nextInt(this.speedY.length)];
    }

    public void setDirXRandom()
    {
        Random r = new Random();
        this.dirX = speedX[r.nextInt(this.speedX.length)];
    }
}