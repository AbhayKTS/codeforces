import java.util.*;
class Mystack{
    private ArrayList<Integer> arr;
    private int top;
    int capacity;
    public Mystack(int capacity){
        this.capacity=capacity;
        arr=new ArrayList<>(capacity);
        top=-1;
    }
    public void push(int item){
        if(top==capacity-1){
            System.out.println("Stack is full");
            return;
        }
        top++;
        arr.add(item);
    }
    public int pop(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            return -1;
        }
        int res=arr.get(top);
        top--;
        return res;
    }
    public int peek(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            return -1;
        }
        return arr.get(top);
    }
    public boolean isEmpty(){
        return top==-1;
    }
}
public class StackMain{
    public static void main(String[] args){
        Mystack s=new Mystack(5);
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