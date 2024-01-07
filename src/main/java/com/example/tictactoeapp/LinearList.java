package com.example.tictactoeapp;

public class LinearList {
    private ListNode head;
    private int totalNodes;
    public LinearList(){
        this.head = null;
    }

    public boolean isEmpty(){
        return this.head == null;
    }

    public void insertNodeAtIndex(int index, Possibility x){
        if (isEmpty()){
            this.head = new ListNode(x);
            this.totalNodes++;
        } else {
            if(index > totalNodes){
                System.out.println("Invalid List Index");
            } else {
                ListNode current = this.head;
                ListNode prev = this.head;
                int i = 0;
                while(current != null){
                    if(index == i){
                        break;
                    }
                    prev = current;
                    current = current.next;
                    i++;
                }

                ListNode newNode = new ListNode(x);
                newNode.next = current;
                prev.next = newNode;
                this.totalNodes++;
            }
        }
    }

    public void insertLast(Possibility value){
        ListNode newNode = new ListNode(value);

        if(isEmpty()){
            this.head = newNode;
        } else {
            ListNode current = this.head;

            while(current.next != null){
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void deleteLastNode(){
        if(isEmpty()){
            System.out.println("List is Empty");
        } else if(this.head.next == null){
            this.head = null;
        } else {
            ListNode current = this.head;
            ListNode prev = null;
            while(current.next != null){
                prev = current;
                current = current.next;
            }

            prev.next = null;
        }
    }

    public void displayList(){
        if(isEmpty()){
            System.out.println("List is Empty");
        } else {
            ListNode current = this.head;
            while(current != null){
                System.out.println(current.data);
                current = current.next;
            }
        }
    }
}
