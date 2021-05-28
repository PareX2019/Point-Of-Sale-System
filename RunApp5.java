import java.util.Date;
import java.text.DecimalFormat;

public class RunApp5
{
    public static String latestReciept ,loggedInUser,loggedInPassword;
    public static int recieptCounter = 1;
    
    //Stock List
    public static String[] items = {"Printer", "Monitor", "Keyboard", "Graphics Card", "Soundbar", "Hard Disk", "Headset", "Smartwatch", "Camcorder","Drone"};
    public static double[] prices = {67.99,138.00,12.50,114.99,249.00,66.95,17.55,135.00,329.00,449.99};

    public static void main(String args[])
    {
        //Log in screen variables
        String choiceLogInSreen;

        System.out.println("1. Login\n2. Exit");
        choiceLogInSreen = Keyboard2.readString();
        if(choiceLogInSreen.equals("1"))
        {
            String[] data = LogIn();
            if(data != null)
            {
                loggedInUser = data[0];
                loggedInPassword = data[1];
                System.out.println("Welcome to the Main Menu " + loggedInUser);
                mainMenu();
            }
            else
                System.out.println("Invalid login credentials."); main(null);

        }else if(choiceLogInSreen.equals("2")){
            System.out.println("Exitting.");
            System.exit(0);
        }
        else{
            System.out.println("Invaid Choice");
        }
    }
    
    public static String pad(String str , int num)
    {
        return String.format("%-" + num + "s", str);   
    }

    public static void mainMenu()
    {
        String mainMenuChoice;

        System.out.println("1.Enter new transaction\n2.Issue Receipt\n3.Display Stock List\n4.Cashier Sign out");
        mainMenuChoice = Keyboard2.readString();
        switch(mainMenuChoice)
        {
            case "1" :
            String[] itemsWithIndex = new String[items.length];
            for(int i =0; i < itemsWithIndex.length; ++i)
                itemsWithIndex[i] = i + ". Item: " + items[i] + " and its price is €" + prices[i];
            
            System.out.println(String.join("\n",itemsWithIndex));
            System.out.println("Type the item's index to enter it to your transaction, Type End to finish your transaction");

            String[] finalItems = new String[1];
            String current;
            double total = 0;
            
            do{
                current = Keyboard2.readString().toLowerCase();
                for(int x=0; x < items.length; ++x)
                {
                    if(current.equals(Integer.toString(x)))
                    {
                         System.out.println("Added Item: " + x + " to your transactiton.");
                         total += prices[x];
                         finalItems = push(finalItems, pad(" " + items[x],60) + "Eur " + prices[x]);
                    }                      
                }     
            }
            while(!current.equals("end"));
            DecimalFormat df=  new DecimalFormat("###.##");
            finalItems = push(finalItems, "\n\n");
            finalItems = push(finalItems, pad(" SUBTOTAL:",60) + "EUR " + df.format(total));
            finalItems = push(finalItems, pad(" VAT:",60) + "EUR " + df.format(total-(total * 0.8)));
            finalItems = push(finalItems, pad(" TOTAL: ",60) +"EUR " + df.format((total +(total-(total * 0.8)))));
            System.out.println("Transaction finished");
            latestReciept = String.join("\n", createReciept(finalItems, loggedInUser)).replace("null","");
            System.out.println(latestReciept);
            mainMenu();
            break;
            case "2":
            if(latestReciept == null)
                System.out.println("No transactions carried out."); 
            else
                System.out.println(latestReciept); 
            mainMenu();
            break;
            case "3":
            for(int i = 0; i < items.length; ++i)
                System.out.println("Item: " + items[i] + " and its price is €" + prices[i]); 
            mainMenu();
            break;
            case "4" :
            loggedInUser = null;
            loggedInPassword = null;
            main(null);
            break;

            default: System.out.println("Invalid choice"); mainMenu();
        }
    }

    public static String[] LogIn()
    {
        //Registered Cashiers
        String[] usernames = {"borg.steve", "zammit.rita", "agius.john", "vella.carlos02", "mallia.amy"};
        String[] passwords = {"Borg87max","RitPopSing!", "ToyotaBeSt","Rock!n!Roll", "$Gaga$Lady$"};

        boolean usernameExists = false, passwordIsCorrect;
        String[] data = new String[2];//index 0 = username , index 1 = password
        int index = 0;

        System.out.println("Enter your username");

        data[0] = Keyboard2.readString().toLowerCase();
        for(int x = 0; x < usernames.length; ++x)
        {
            if(usernames[x].equals(data[0])){
                usernameExists = true;
                index = x;
            }
        }

        if(!usernameExists){
            System.out.println("Invalid user!");
            LogIn();
        }   
        else{
            System.out.println("Enter password for " + usernames[index]);
            data[1] = Keyboard2.readString();
            if(data[1].equals(passwords[index]))
                return data;
            else{
                System.out.println("Invalid password for " + usernames[index]);
                return null;
            }

        }
        return data;
    }

    public static String[] createReciept(String[] data, String cashier)
    {
        String[] reciept = {
                "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -",
                "<< EASY-SAVE SUPERMARKET >>",
                "102, Flower Street, Filfla",
                "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *",
                new Date().toString(),
                "Receipt: " + recieptCounter,
                "Cashier: " + cashier,
                "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *"};
        int arLength = data.length;
        for(int x =0; x < arLength; ++x)
            reciept = push(reciept, data[x]);

        reciept = push(reciept, "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        reciept = push(reciept, "                             FISCAL RECEIPT                            ");
        reciept = push(reciept, "                               THANK  YOU                              ");
        reciept = push(reciept, "--------------------------------CUT-HERE-------------------------------");
        recieptCounter++;
        return reciept;
    }

    public static String[] push(String[] data, String input)
    {
        int i = 0;
        String[] returnArr = new String[data.length + 1];
        for(int x =0; x < data.length; x++){
            returnArr[x] = data[x];
            i = x;
        }
        returnArr[i + 1] = input;
        return returnArr; 
    }
}
