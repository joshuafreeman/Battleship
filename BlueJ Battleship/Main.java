import chn.util.*;
public class Main 
{
    public static void main(String[] args)
    {
        ConsoleIO con = new ConsoleIO();
        PlayerBoard bor = new PlayerBoard(10, 10);
        int xCord,yCord,boatType,rot;
        boolean right = true;
        bor.emptyBoard();
        System.out.print(" ");
         for(int y = 1; y <= bor.getWidth(); y++)
        {
            System.out.print(y + " ");
        }
        System.out.println();
        for(int y = 0; y < bor.getHeight(); y++)
        {
            System.out.print(y+1);
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }
        System.out.println();
        for(int x = 0; x < 5; x++)
        {
            System.out.println("Please type a X cord.");
            xCord = con.readInt();
            while(right)
            {
                if(xCord < 1 || xCord > 10)
                {
                   System.out.println("Please type a X cord between 1 and 10.");
                   xCord = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            System.out.println("Please type a Y cord.");
            yCord = con.readInt();
             while(right)
            {
                if(yCord < 1 || yCord > 10)
                {
                   System.out.println("Please type a Y cord between 1 and 10.");
                   yCord = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            System.out.println("Please type 0 for Horizontal and 1 for Vertical");
            rot = con.readInt();
             while(right)
            {
                if(rot < 0 || rot > 1)
                {
                   System.out.println("Please learn to type.");
                   rot = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            System.out.println("BattleShip:1,Submarine:2,Carrier:3,PatrolBoat:4,Destroyer:5");
            boatType = con.readInt();
             while(right)
            {
                if(boatType < 1 || boatType > 5)
                {
                   System.out.println("Please type between 1 and 5.");
                   boatType = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            switch(boatType)
            {
                case(1):
                    if(bor.setShip(xCord,yCord,rot,"Battleship"))
                    
                    break;
                case(2):
                    if(bor.setShip(xCord,yCord,rot,"Submarine"))
                    break;                
                case(3):
                    if(bor.setShip(xCord,yCord,rot,"Carrier"))
                    break;                
                case(4):
                    if(bor.setShip(xCord,yCord,rot,"Patrol Boat"))
                    break;                
                case(5):
                    if(bor.setShip(xCord,yCord,rot,"Destroyer"))
                    break;              
            }    
            for(int y = 0; y < bor.getHeight(); y++)
            {
                for (int x2 = 0; x2 < bor.getWidth(); x2++)
                    System.out.print(bor.displaySpot(x2, y) + " ");
                System.out.println();
            }
        }
        while(!bor.isEmpty())
        {
            System.out.println("Please type a X cord to Shoot.");
            xCord = con.readInt();
            while(right)
            {
                if(xCord < 1 || xCord > 10)
                {
                   System.out.println("Please type a X cord between 1 and 10.");
                   xCord = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            System.out.println("Please type a Y cord to Shoot.");
            yCord = con.readInt();
             while(right)
            {
                if(yCord < 1 || yCord > 10)
                {
                   System.out.println("Please type a Y cord between 1 and 10.");
                   yCord = con.readInt(); 
                }
                else
                {
                    right = false;
                }    
            }  
            right = true;
            if(bor.receiveAttack(xCord,yCord))
            {
                System.out.println("U SONK MOI BITLESHOP");
            }
            
            for(int y = 0; y < bor.getHeight(); y++)
            {
                for (int x2 = 0; x2 < bor.getWidth(); x2++)
                System.out.print(bor.displaySpot(x2, y) + " ");
                System.out.println();
            }
            
        }    
        System.out.println("hoi tots pritu gud, u won");
    }
}
