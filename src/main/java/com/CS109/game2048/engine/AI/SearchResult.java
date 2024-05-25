package com.CS109.game2048.engine.AI;

public class SearchResult {
    public int move;
    public double score;
    public int position;
    public int cutoffs;

    public SearchResult(){

    }

    public SearchResult(int move,double score,int position, int cutoffs){
        this.move=move;
        this.score=score;
        this.position=position;
        this.cutoffs=cutoffs;
    }
}
