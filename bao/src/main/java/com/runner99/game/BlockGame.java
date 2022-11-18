package com.runner99.game;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
1. 如何判断某一行是否被填满
# 使用熟悉的编程语言替换该片段
# board 棋盘
# row 某一行
def is_full(board, row):
pass
1
2
3
4
5
2. 如何判断dot形状(即占据一个格子的块)是否可以放置在某个位置
# 使用熟悉的编程语言替换该片段
# board: 棋盘
# pos: <row, col>
def lock_dot(board, pos):
pass
1
2
3
4
5
3. 如何判断某个形状(例如L)的块是否可以放置在某个位置
# 使用熟悉的编程语言替换该片段
# board: 棋盘
# pos: <row, col>
# piece: 形状
def lock_piece(board, pos, piece):
pass
 */
public class BlockGame {

    public BlockGame() {
    }


    public boolean isFull(int[][] board,int row){
        for (int i=0 ;i<board[row-1].length;i++){
            if (board[row-1][i]==0){
                return false;
            }
        }
        return true;
    }

    public boolean lockLot(int[][] board, Point pos){
        return board[(int) pos.getX()-1][(int) pos.getY()-1]==1;
    }


    public static void main(String[] args) {
        int[][] board = new int[9][9];
        BlockGame game = new BlockGame();

//        boolean isFull = game.isFull(board, 2);
//        System.out.println(full);

        Point point = new Point(1,2);
        board[0][1] = 1;
        boolean isLock = game.lockLot(board, point);
        System.out.println(isLock);

    }
}
class piece{

}
