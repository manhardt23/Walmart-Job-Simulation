public class Main {
    public static void main(String[] args) {
        PowerOfTwoMaxHeap myheap = new PowerOfTwoMaxHeap(3);
        myheap.insert(8);
        myheap.insert(3);
        myheap.insert(6);
        myheap.insert(10);
        myheap.insert(41);
        myheap.insert(61);
        myheap.insert(42);
        myheap.insert(51);
        myheap.insert(44);
        myheap.insert(33);
        myheap.insert(29);
        myheap.insert(67);
        myheap.insert(63);
        myheap.insert(79);
        myheap.insert(16);
        myheap.insert(2);
        myheap.insert(58);
        myheap.insert(25);
        myheap.insert(10);
        myheap.insert(37);
        myheap.insert(18);
        myheap.insert(13);
        myheap.insert(15);
        myheap.insert(1);
        myheap.insert(39);

        System.out.println(myheap);
        int n = myheap.popMax();
        System.out.println(n);
    }
}
