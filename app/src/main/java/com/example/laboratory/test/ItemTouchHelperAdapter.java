package com.example.laboratory.test;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);



    void removeItem(int position);
}
