package sorts;

import java.util.Arrays;

public class SORTS {

    public static void main(String[] args) {
        int arr[] = {-1,-4,5,3,2,100,33};



        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void getSort(int arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 -i; j++) {
                if(arr[j] >arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    private static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int current = arr[i];
            int j = i-1;
            while(j>= 0 && arr[j] >current) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = current;
        }
    }

    private static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                if(arr[minIndex] > arr[j]) {
                        minIndex = j;
                }
            }

            getSwap(i, minIndex, arr);
        }
    }

    private static void getSwap(int i, int minIndex, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
}
