package com.example.myapplication;

import android.graphics.RectF;

public class Brick{

    private int visible[][];
    private RectF bricks[][];
    private int brickHeight;
    private int brickWidth;
    private final int offset = 10;
    private final int offsetYpos = 150;

    public Brick(int row, int col, int width, int height)
    {
        brickWidth = width / col;
        brickHeight = height / row / 3;
        visible = new int[row][col];
        bricks = new RectF[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                bricks[i][j] = new RectF(j * brickWidth + offset, i * brickHeight + offset + offsetYpos, (j * brickWidth) + brickWidth, (i * brickHeight) + brickHeight + offsetYpos);
                visible[i][j] = 1;
            }
        }
    }

    public RectF GetBrick(int i, int j)
    {
        return this.bricks[i][j];
    }

    public void setBrickValue(int value, int row, int col)
    {
        visible[row][col] = value;
    }

    public int getMap(int i, int j)
    {
        return this.visible[i][j];
    }
}
