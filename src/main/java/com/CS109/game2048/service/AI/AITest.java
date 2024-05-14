package com.CS109.game2048.service.AI;

import com.CS109.game2048.service.Grid;

public class AITest {
    public static void main(String[] args) {
        Grid grid=new Grid();
        grid.generateRandomNumber();
        for (int i = 0; i <8; i++) {
            grid.up();
        }
        AI ai = new AI(grid);
        System.out.println(ai.getBestMove(5));
    }
}
