package Additional;

import java.util.Stack;

public class generiki {

    public static void main(String[] args) {

    }
}

 class GenericStack<T extends Comparable<T>> {
    private Stack<T> stack;
    private Stack<T> minStack;

    public GenericStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(T item) {
        stack.push(item);

        if (minStack.isEmpty() || item.compareTo(minStack.peek()) <= 0) {
            minStack.push(item);
        }
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("The stack is empty.");
        }

        T item = stack.pop();

        if (item.equals(minStack.peek())) {
            minStack.pop();
        }

        return item;
    }

    public T peek() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("The stack is empty.");
        }

        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public T getMin() {
        if (minStack.isEmpty()) {
            return null;
        }

        return minStack.peek();
    }

    public static void main(String[] args) {
        GenericStack<Integer> stack = new GenericStack<>();
        stack.push(5);
        stack.push(3);
        stack.push(7);
        stack.push(7);
        stack.push(7);
        System.out.println("Min: " + stack.getMin()); // Output: Min: 3

        stack.pop();
        System.out.println("Peek: " + stack.peek()); // Output: Peek: 3
    }
}
