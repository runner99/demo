package com.runner99.java8.game;

import java.lang.reflect.Type;
import java.util.Arrays;
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

    private int[][] board;
    public BlockGame(int a,int b) {
        this.board=new int[a][b];
        this.board[0][1]=1;
    }

    public boolean isFull(int a){
        for (int i=0 ;i<board[a-1].length;i++){
            if (board[a-1][i]==0){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "BlockGame{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    public static void main(String[] args) {
        BlockGame game = new BlockGame(9,9);
        boolean full = game.isFull(1);
        System.out.println(full);

    }
}
