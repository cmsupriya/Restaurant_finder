import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void mockingRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = Mockito.spy(new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime));
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    private void mockCurrentTime(LocalTime testTime) {
        when(restaurant.getCurrentTime()).thenReturn(testTime);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime testTime = LocalTime.parse("12:30:00");
        mockCurrentTime(testTime);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime testTime = LocalTime.parse("23:00:00");
        mockCurrentTime(testTime);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void calculate_order_total_should_return_0_for_empty_item_names() {
        double total = restaurant.calculateOrderTotal();
        assertEquals(0, total); // This test should fail initially
    }

    @Test
    public void calculate_order_total_should_return_price_of_single_item() {
        double total = restaurant.calculateOrderTotal("Sweet corn soup");
        assertEquals(119, total); // This test should fail initially
    }

    @Test
    public void calculate_order_total_should_return_sum_of_prices_for_multiple_items() {
        double total = restaurant.calculateOrderTotal("Sweet corn soup", "Vegetable lasagne");
        assertEquals(388, total); // This test should fail initially
    }
}