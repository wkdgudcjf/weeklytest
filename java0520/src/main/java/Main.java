import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

abstract class Player{
    private String name;
    private String initNumber;
    private List<String> fightList = new LinkedList<String>();
    private Boolean isEnd;
    public Player(String name, String initNumber) {
        this.name = name;
        this.initNumber = initNumber;
        this.isEnd = false;
    }
    public abstract void play(String number) throws Exception;
    public void addFightList(String number) {
        fightList.add(number);
    }
    public Boolean getIsEnd()
    {
        return isEnd;
    }
    public void setIsEnd(Boolean isEnd)
    {
        this.isEnd = isEnd;
    }
    public String getInitNumber()
    {
        return initNumber;
    }
    public String getName() {
        return name;
    }
    public void printFightList() {
        System.out.println(name);
        System.out.println("-------------------------------------");
        int i = 1;
        for(String fight : fightList)
        {
            System.out.println(i + " : " + fight);
            i++;
        }
        System.out.println("-------------------------------------");
    }
}
class PlayerATeam extends Player
{
    public PlayerATeam(String name, String number) {
        super(name, number);
    }
    @Override
    public void play(String number) throws Exception {
        final String REGEX = "[0-9]+";
        for(int i = 0; i < 3; i++) {
            for(int j = i+1 ; j < 4; j++) {
                if(number.charAt(i) == number.charAt(j)) {
                    throw new Exception("Invalid input(same number). The opportunity passes to the next team.");
                }
            }
        }
        if(number.length() != 4 )
        {
            throw new Exception("Invalid input(length 4). The opportunity passes to the next team.");
        } else if(number.matches(REGEX) == false) {
            throw new Exception("Invalid input(Only Numeric). The opportunity passes to the next team.");
        }

        int strike = 0;
        int ball = 0;
        int out = 0;

        for(int i = 0; i < 4; i++){
            if(number.charAt(i) == getInitNumber().charAt(i)) {
                strike += 1;
            } else {
                if(number.indexOf(getInitNumber().charAt(i)) != -1){
                    ball += 1;
                } else {
                    out += 1;
                }
            }
        }

        if(strike == 4) {
            System.out.println("Congratulation!");
            super.setIsEnd(true);
        } else {
            System.out.println("Strike : "+strike+", Ball :"+ball+", Out: "+out);
        }

        super.addFightList(number + " : Strike : "+strike+", Ball :"+ball+", Out: "+out);
    }
}

class PlayerBTeam extends Player
{
    public PlayerBTeam(String name, String number) {
        super(name, number);
    }
    @Override
    public void play(String number) throws Exception {
        final String REGEX = "[0-9]+";
        for(int i = 0; i < 3; i++) {
            for(int j = i+1 ; j < 4; j++) {
                if(number.charAt(i) == number.charAt(j)) {
                    throw new Exception("Invalid input(same number). The opportunity passes to the next team.");
                }
            }
        }
        if(number.length() != 4 )
        {
            throw new Exception("Invalid input(length 4). The opportunity passes to the next team.");
        } else if(number.matches(REGEX) == false) {
            throw new Exception("Invalid input(Only Numeric). The opportunity passes to the next team.");
        }

        int strike = 0;
        int ball = 0;
        int out = 0;

        for(int i = 0; i < 4; i++) {
            if(number.charAt(i) == getInitNumber().charAt(i)) {
                ball += 1;
            } else {
                if(number.indexOf(getInitNumber().charAt(i)) != -1){
                    strike += 1;
                } else {
                    out += 1;
                }
            }
        }

        if(ball == 4) {
            System.out.println("Congratulation!");
            super.setIsEnd(true);
        } else {
            System.out.println("Strike : "+strike+", Ball :"+ball+", Out: "+out);
        }

        super.addFightList(number + " : Strike : "+strike+", Ball :"+ball+", Out: "+out);
    }
}

class PlayerCTeam extends Player
{
    public PlayerCTeam(String name, String number) {
        super(name, number);
    }
    @Override
    public void play(String number) throws Exception {
        final String REGEX = "[0-9]+";
        for(int i = 0; i < 3; i++) {
            for(int j = i+1 ; j < 4; j++) {
                if(number.charAt(i) == number.charAt(j)) {
                    throw new Exception("Invalid input(same number). The opportunity passes to the next team.");
                }
            }
        }
        if(number.length() != 4 )
        {
            throw new Exception("Invalid input(length 4). The opportunity passes to the next team.");
        } else if(number.matches(REGEX) == false) {
            throw new Exception("Invalid input(Only Numeric). The opportunity passes to the next team.");
        }

        int strike = 0;
        int ball = 0;
        int out = 0;

        for(int i = 0; i < 4; i++) {
            if(number.charAt(i) == getInitNumber().charAt(i)) {
                strike += 1;
            } else {
                if(number.indexOf(getInitNumber().charAt(i)) != -1){
                    ball += 1;
                } else {
                    out += 1;
                }
            }
        }

        if(strike >= 3) {
            System.out.println("Congratulation!");
            super.setIsEnd(true);
        } else {
            System.out.println("Strike : "+strike+", Ball :"+ball+", Out: "+out);
        }

        super.addFightList(number + " : Strike : "+strike+", Ball :"+ball+", Out: "+out);
    }
}

public class Main {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Player playerArray[] = {new PlayerATeam("A Team","1234"), new PlayerBTeam("B Team","5678"), new PlayerCTeam("C Team","8910")};
        int checkPlayer = 0;

        while(true) {
            checkPlayer = 0;
            for(Player player : playerArray) {
                if (player.getIsEnd() == false) {
                    System.out.print("Please enter a 4 digit number (" + player.getName() + ") defence : ");
                    String number = scanner.nextLine();
                    try {
                        player.play(number);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    checkPlayer++;
                }
            }
            if(checkPlayer == playerArray.length) break;
        }

        for(Player player : playerArray) {
            player.printFightList();
        }
    }
}
