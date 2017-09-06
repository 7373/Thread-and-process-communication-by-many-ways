package threadMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import threadMessage.Helper;

/**
 * 
 * @author Administrator
 *共享变量来做控制；

	a. 利用最基本的synchronized、notify、wait：
 */
public class list {

    private final ThreadToGo threadToGo = new ThreadToGo();

    public Runnable newThreadOne() {
        final String[] inputArr = Helper.buildNoArr(52);
        return new Runnable() {
            private String[] arr = inputArr;
            public void run() {
                try {
                   while(true){
                        synchronized (threadToGo) {
                            while (threadToGo.list.isEmpty())
                                threadToGo.wait();
                           // Helper.print(arr[i], arr[i + 1]);
//                            for(int i1=0;i1<threadToGo.list.size();i1++)
//                            System.out.println(threadToGo.list.get(i1));
                            System.out.println(Arrays.toString( threadToGo.list.toArray()));
                            threadToGo.list.removeAll(threadToGo.list);
                            //threadToGo.list.toArray();
                            threadToGo.notify();
                        }
                   }
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }

    public Runnable newThreadTwo() {
        final String[] inputArr = Helper.buildCharArr(26);
        return new Runnable() {
            private String[] arr = inputArr;

            public void run() {
                try {
                    while(true){
                        synchronized (threadToGo) {
                            while (!threadToGo.list.isEmpty())
                                threadToGo.wait();
                        //    Helper.print(arr[i]);
                         //   threadToGo.value = 1;
                            //for(String i=1;i<5;i++)
//                            threadToGo.list.add("123");
//                            threadToGo.list.add("234");
                            threadToGo.notify();
                        }
                }
                    
                } catch (InterruptedException e) {
                    System.out.println("Oops...");
                }
            }
        };
    }

    class ThreadToGo {
    	private final List<String> list = Collections.synchronizedList(new ArrayList<String>());

		public ThreadToGo() {
			super();
			// TODO Auto-generated constructor stub
			 this.list.add("123");
	         this.list.add("234");
		}
    	
    	
    }

    public static void main(String args[]) throws InterruptedException {
        list one = new list();
        Helper.instance.run(one.newThreadOne());
        Helper.instance.run(one.newThreadTwo());
        Helper.instance.shutdown();
    }
}
