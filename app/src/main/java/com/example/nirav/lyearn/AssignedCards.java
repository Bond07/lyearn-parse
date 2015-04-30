package com.example.nirav.lyearn;

/**
 * Created by nirav on 4/27/15.
 */
public class AssignedCards {

    String cardTitle;
    String cardAssigner;

    public AssignedCards(String title, String assigner) {
        this.cardTitle = title;
        this.cardAssigner = assigner;
     }


    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardAssigner() {
        return cardAssigner;
    }
}
