import java.util.*;
class Mystack{
    private ArrayList<Integer> arr;
    private int top;
    public Mystack(){
        arr=new ArrayList<>();
        top=-1;
    }
    public void push(Integer item){
        arr.add(item);
    }
    public Integer pop(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            return -1;
        }
        int res=arr.get(arr.size()-1);
        arr.remove(arr.size()-1);
        return res;
    }
    public Integer peek(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            return -1;
        }
        return arr.get(arr.size()-1);
    }
    public boolean isEmpty(){
        return arr.isEmpty();
    }
}
public class StackMain{
    public static void main(String[] args){
        Mystack s=new Mystack();
        System.out.println(s.pop());
        s.push(10);
        s.push(20);
        s.push(30);
        System.out.println(s.peek());
        System.out.println(s.pop());
        System.out.println(s.peek());
        s.push(40);
        System.out.println(s.peek());
        System.out.println(s.isEmpty());
    }
}