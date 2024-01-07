package com.example.tictactoeapp;

import javafx.geometry.Pos;

public class Tree {
    public TreeNode root;

    public Tree(){
        this.root = null;
    }

    public void addNodeToRoot(Possibility data){
        TreeNode newNode = new TreeNode(data);
        if(this.root == null){
            this.root = newNode;
        } else {
            this.root.addBranch(newNode);
        }
    }

    public boolean addNodeToWhere(Object where, Possibility value){
        TreeNode curr = root;
        for(int i = 0; i < curr.branches.size(); i++){
            TreeNode currentBranch = curr.branches.get(i);
            if(currentBranch.data == where){
                currentBranch.addBranch(new TreeNode(value));
                return true;
            } else {
                if(!currentBranch.branches.isEmpty()){
                    boolean isSuccess = addNodeToWhere(where, value, currentBranch);
                    if(isSuccess){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean addNodeToWhere(Object where, Possibility value, TreeNode node){
        for(int i = 0; i < node.branches.size(); i++){
            TreeNode currentBranch = node.branches.get(i);
            if(currentBranch.data == where){
                currentBranch.addBranch(new TreeNode(value));
                return true;
            } else {
                if(!currentBranch.branches.isEmpty()){
                    boolean isSuccess = addNodeToWhere(where, value, currentBranch);
                    if(isSuccess){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void displayTree(){
        TreeNode curr = root;
        System.out.println(curr.data);
        for(int i = 0; i < curr.branches.size(); i++){
            TreeNode currentBranch = curr.branches.get(i);
            System.out.println("- " + currentBranch.data);
            if(!currentBranch.branches.isEmpty()){
                displayTree(currentBranch);
            }
        }
    }
    public void displayTree(TreeNode branch){
        for(int i = 0; i < branch.branches.size(); i++){
            TreeNode currentBranch = branch.branches.get(i);
            System.out.println("- " + currentBranch.data);
            if(!currentBranch.branches.isEmpty()){
                displayTree(currentBranch);
            }
        }
    }
}
