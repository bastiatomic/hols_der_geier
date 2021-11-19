public class scribble {

    public static void main(String[] args) {

        // -1 1 -2 2 -3 3
        int swapper = 1;
        int centerCardChoice = 20;
        for (int i = 0; i < 9; i++) {

            System.out.println("Swapper: " + swapper);
            System.out.println(centerCardChoice += swapper);

            if (swapper >0){
                swapper += 1;
            } else {
                swapper -= 1;
            }
            swapper *=-1;

            //System.out.println(swapper);
        }

        }

    }

