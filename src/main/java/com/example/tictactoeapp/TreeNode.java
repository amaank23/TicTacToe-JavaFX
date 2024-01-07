package com.example.tictactoeapp;

import java.util.ArrayList;

public class TreeNode {
    public Possibility data;
    public ArrayList<TreeNode> branches;
    public TreeNode(Possibility data){
        this.data = data;
        branches = new ArrayList<TreeNode>();
    }

    public void addBranch(TreeNode node){
        branches.add(node);
    }
}
