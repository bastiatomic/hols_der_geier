
public class temp {

    public static void main(String[] args) {

        Human oneHuman = new Human();
        Human twoHuman = new Human();

        System.out.println(oneHuman.getVictoryPoints());

        oneHuman.addStarterCards();
        oneHuman.setVictoryPoints(39);

        System.out.println(oneHuman.getUserCards());
        System.out.println(twoHuman.getUserCards());


    }
}
