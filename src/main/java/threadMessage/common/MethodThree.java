package threadMessage.common;
import threadMessage.Helper;

/**
 * 
 * @author Administrator
 *c. 利用volatile：
 */
public class MethodThree {

    private volatile ThreadToGo threadToGo = new ThreadToGo();

    class ThreadToGo {
        int value = 1;
    }

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                for (int i = 0; i < arr.length; i=i+2) {
                    while(threadToGo.value==2){}
                    Helper.print(arr[i], arr[i + 1]);
                    threadToGo.value=2;
                }
            }
        };
    }

    public Runnable newThreadTwo() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    while(threadToGo.value==1){}
                    Helper.print(arr[i]);
                    threadToGo.value=1;
                }
            }
        };
    }

    public static void main(String args[]) throws InterruptedException {
        MethodThree three = new MethodThree();
        Helper.instance.run(three.newThreadOne());
        Helper.instance.run(three.newThreadTwo());
        Helper.instance.shutdown();
    }
}
