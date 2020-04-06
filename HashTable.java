import java.util.Scanner;

public class HashTable
{
   public static void main (String[] args)
   {
      Scanner input = new Scanner(System.in);
      //key table
      int[] keys = {1234, 8234, 7867, 1009, 5438, 4312, 3420, 9487, 5418, 5299, 
                    5078, 8239, 1208, 5098, 5195, 5329, 4543, 3344, 7698, 5412, 
                    5567, 5672, 7934, 1254, 6091, 8732, 3095, 1975, 3843, 5589, 
                    5439, 8907, 4097, 3096, 4310, 5298, 9156, 3895, 6673, 7871, 
                    5787, 9289, 4553, 7822, 8755, 3398, 6774, 8289, 7665, 5523};
      int option = 0;
      int key = 0;
      //creation of hashing table with probes as 2nd dimension
      int[][] Table = new int[50][2];
      
      do {
         for (int i = 0; i < Table.length; i++) {
            for (int m = 0; m < Table[0].length; m++)
               Table[i][m] = 0; //populate hashing table with 0s
         }
         System.out.println("\n-----MAIN MENU-----");
         System.out.println("0 - Exit Program");
         System.out.println("1 - Run HF1 (Division with Linear Probing)");
         System.out.println("2 - Run HF2 (Dvision with Quadratic Probing)");
         System.out.println("3 - Run HF3 (Division with Double Hashing)");
         System.out.println("4 - Run HF4 (Student-Designed Function)");
         System.out.print("Enter menu option: ");
         option = input.nextInt();
         System.out.println();
      
         //menu option switch
         switch (option) {
         
            case 0:
               System.out.println("Program is terminated.");
               break;
            case 1:
               //linear probing method on every key in table
               for (int i = 0; i < keys.length; i++)
                  linearProbing(Table, keys[i], 0, division(keys[i]));
               printTable(option, Table); //print resulting table
               break;
            case 2:
               //quadratic probing method on every key in table
               for (int i = 0; i < keys.length; i++)
                  quadraticProbing(Table, keys[i], 0, division(keys[i]));
               printTable(option, Table); //print resulting table
               break;
            case 3:
               //implementation of provided second hashing method on every key in table
               for (int i = 0; i < keys.length; i++)
                  doubleHashing(Table, keys[i], 0, doubleHashingFunction(keys[i], 1), 1);
               printTable(option, Table); //print resulting table
               break;
            case 4:
               //implementation of own second hashing method on every key in table
               for (int i = 0; i < keys.length; i++)
                  studentProbing(Table, keys[i], 0, studentHashingFunction(keys[i], 1), 1);
               printTable(option, Table); //print resulting table
               break;
            default:
               //print if option < 0 or > 4 and rerun loop
               System.out.println("Option not in list. Re-enter menu option.\n");
            
         }
      } while (option != 0);
   }
   
   private static int division(int key) {
      int divisionHashing = (key % 50); //division hashing
      
      return divisionHashing;
   }
   
   private static int doubleHashingFunction(int key, int count) {
      int hashingFunction = division(key) + count * (30 - key % 25); //second hashing function
      
      return hashingFunction;
   }
   
   private static int studentHashingFunction(int key, int count) {
      int hashingFunction = (division(key) + count * (47 - (key % 47)))*(key%50); //own second hashing function
      
      return hashingFunction;
   }
   
   public static void linearProbing(int[][] Table, int key, int probe, int divisionHashing) {
      
      //division hashing
      if (Table[divisionHashing%50][0] == 0) {
         Table[divisionHashing%50][0] = key;
         Table[divisionHashing%50][1] = probe;
      }
      else {
         linearProbing(Table, key, probe + 1, divisionHashing + 1); //recursive call for collision resolution
      }
   }      
      
   public static void quadraticProbing(int[][] Table, int key, int probe, int divisionHashing) {
    
      //division hashing
      if (Table[divisionHashing%50][0] == 0) {
         Table[divisionHashing%50][0] = key;
         Table[divisionHashing%50][1] = probe;
      }
      else
         quadraticProbing(Table, key, probe + 1, divisionHashing+1^2); //recursion call for collision resolution
   }
      
   public static void doubleHashing(int[][] Table, int key, int probe, int increment, int count) {
      
      //division hashing
      if (Table[division(key)][0] == 0) {
         Table[division(key)][0] = key;
         Table[division(key)][1] = probe;
         count = 1;
      }
      else {
         //implementation of second hashing function to populate table
         if (Table[doubleHashingFunction(key, count)%50][0] == 0) {
            Table[doubleHashingFunction(key, count)%50][0] = key;
            Table[doubleHashingFunction(key, count)%50][1] = probe + 1;
            count++;
         }
         //implementation of second hashing function to populate table until 50 attempts has been reached
         else if (probe < 50)
            doubleHashing(Table, key, probe + 1, doubleHashingFunction(key, count), count + 1);
         else
            System.out.println("Unable to hash key " + key + " to the table");
      }
   }
      
   public static void studentProbing(int[][] Table, int key, int probe, int increment, int count) {
    
      //division hashing
      if (Table[division(key)][0] == 0) {
         Table[division(key)][0] = key;
         Table[division(key)][1] = probe;
         count = 1;
      }
      else {
         //implementation of second hashing function to populate table
         if (Table[studentHashingFunction(key, count)%50][0] == 0) {
            Table[studentHashingFunction(key, count)%50][0] = key;
            Table[studentHashingFunction(key, count)%50][1] = probe + 1;
            count++;
         }
         //implementation of second hashing function to populate table until 50 attempts has been reached
         else if (probe < 50)
            studentProbing(Table, key, probe + 1, studentHashingFunction(key, count), count + 1);
         else
            System.out.println("Unable to hash key " + key + " to the table");
      }
   }
      
    //sum all probes
   private static int sumProbing(int[][] Table) {
      int count = 0;
   
      for (int i = 0; i < Table.length; i++) {
         for (int m = 1; m < Table[0].length; m++) 
            count = count + Table[i][m];
      }
   
      return count;
   }
   
   //print entire resulting table
   public static void printTable(int option, int[][] Tables) {
      System.out.println("\nHash table resulted from HF" + option + ":\n");
      System.out.println("Index\t Key\t Probes");
      System.out.println("---------------------");
      for (int i = 0; i < Tables.length; i++) {
         if (Tables[i][0] == 0)
            System.out.print("  " + i + " \t\t " + Tables[i][0]);
         else 
            System.out.print("  " + i + " \t " + Tables[i][0]);
         for (int m = 1; m < Tables[0].length; m++) {
            System.out.println(" \t " + Tables[i][m]);
         }
      }
      System.out.println("---------------------\n");
      System.out.println("Sum of probe values = " + sumProbing(Tables));
   }
      
      
}
         
         
         
         
