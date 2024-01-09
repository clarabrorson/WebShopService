package com.Molndal.WebShopService.Service;

/**
 * Denna klass används för att ta emot en förfrågan om att lägga till en vara i kundvagnen.
 *
 * @author Fredrik
 */
public class AddToCartRequest {

    private int quantity;

    /**
     * Metod som returnerar antalet varor som ska läggas till i kundvagnen.
     * @return antal varor som ska läggas till i kundvagnen.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metod som sätter antalet varor som ska läggas till i kundvagnen.
     * @param quantity antal varor som ska läggas till i kundvagnen.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
