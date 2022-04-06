import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime, closingTime;

    List<String> itemsInOrder;
    ByteArrayOutputStream outPutStream;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void beforeEach(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");

        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant = Mockito.spy(restaurant);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        outPutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outPutStream));

        itemsInOrder = new ArrayList<String>();
        itemsInOrder.add("Sweet corn soup");
        itemsInOrder.add("Vegetable lasagne");
    }

    @AfterEach
    public void afterEach()
    {
        System.setOut(System.out);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        LocalTime currentTime = LocalTime.parse("11:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        restaurant.isRestaurantOpen();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        LocalTime currentTime = LocalTime.parse("23:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        restaurant.isRestaurantOpen();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
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

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_total_order_value_equal_to_388(){
        int actualTotalOrderValue = restaurant.displayTotalOrder(itemsInOrder);
        int expectedTotalOrderValue = 388;

        assertEquals(expectedTotalOrderValue,actualTotalOrderValue);
    }

    @Test
    public void display_details_of_the_current_restaurant(){

        String expectedDetails =
                "Restaurant:Amelie's cafe\n"
                        +"Location:Chennai\n"
                        +"Opening time:10:30\n"
                        +"Closing time:22:00\n"
                        +"Menu:"+"\n" + "[Sweet corn soup:119\n, Vegetable lasagne:269\n]\n";

    restaurant.displayDetails();
    assertEquals(expectedDetails,outPutStream.toString());
    }

    @Test
    public void display_total_order_value_with_message(){
        String expectedMessage = "Your order will cost Rs.388\n";
        restaurant.displayTotalOrder(itemsInOrder);

        assertEquals(expectedMessage,outPutStream.toString());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}