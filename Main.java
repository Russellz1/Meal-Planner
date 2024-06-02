package mealplanner;

import jdk.jfr.Category;
import mealplanner.Enum.DaysOfTheWeek;
import mealplanner.Enum.MealCategories;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        //database.deleteTables();

        boolean continueInput = true; //continues the loop as long as the user does not want to exit
        while (continueInput) {
            Scanner sc = new Scanner(System.in);
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            String userCommand  = sc.nextLine();
            switch (userCommand) {
                //Adds Meal to the menu
                case "add":
                    Meal newMeal = new Meal();
                    newMeal.addInput();
                    database.addMeal(newMeal);
                    break;
                case "show":
                    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
                    //Checks user input to make sure it is part of the available options
                    String catergroy = sc.nextLine();
                    while (!MealCategories.isCategory(catergroy)) {
                        System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                        catergroy = sc.nextLine();
                    }
                    List<Meal> meals = database.getMealsByCategory(catergroy, "meal_id");
                    if (meals.size() == 0) {
                        System.out.println("No meals found.");
                    } else {
                        System.out.println("Category: " + catergroy + "\n");
                    }
                    for (Meal meal : meals) {
                        System.out.println(meal.toStringWOCategory());
                        System.out.println();
                    }
                    break;
                case "plan":
                    List<Plan> plans = Plan.createPlan();
                    Plan.printPlans(plans);
                    database.insertCompletePlan(plans);
                    break;
                case "save":
                    List<Plan> plansToSave = database.getAllPlans();
                    if (plansToSave.size() == 0) {
                        System.out.println("Unable to save. Plan your meals first.");
                        break;
                    }
                    System.out.println("Input a filename.");
                    String filename = sc.nextLine();
                    File exportedFile = new File(filename);
                    try {
                        FileWriter writer = new FileWriter(exportedFile);
                        writer.write("test string");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case "exit":
                    continueInput = false;
                    break;
                default:
                    //System.out.println("Invalid command");
            }
        }
        System.out.println("Bye!");
    }
}