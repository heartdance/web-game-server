package cn.flypigeon.springbootdemo.snake.entity;

import lombok.Data;

@Data
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
