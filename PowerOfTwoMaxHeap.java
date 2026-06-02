import java.util.ArrayList;

public class PowerOfTwoMaxHeap {
    private ArrayList<Integer> heap;
    private Integer exponent;

    public PowerOfTwoMaxHeap(Integer exp) {
        heap = new ArrayList<>();
        exponent = exp;
    }

    private int getPower() {
        return Math.powExact(2, exponent);
    }

    private int getParent(int i) {
        int d = getPower();
        return (i - 1) / d;
    }

    private ArrayList<Integer> getChildren(int i) {
        int d = getPower();
        ArrayList<Integer> children = new ArrayList<>();
        for (int j = 1; j < d + 1; j++) {
            children.add(d * i + j);
        }
        return children;
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    public void insert(int value) {
        heap.add(value);

        int currentIndex = heap.size() - 1;

        while (currentIndex > 0 && heap.get(currentIndex) > heap.get(getParent(currentIndex))) {
            swap(currentIndex, getParent(currentIndex));

            currentIndex = getParent(currentIndex);
        }
    }

    public int popMax() {
        if (heap.isEmpty()) {
            throw new RuntimeException("heap is empty");
        }
        int max = heap.get(0);

        int lastElement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {

            heap.set(0, lastElement);

            int currentIndex = 0;
            while (true) {
                ArrayList<Integer> children = getChildren(currentIndex);

                int tempMax = currentIndex;

                for (int num : children) {
                    if (num < heap.size() - 1 && heap.get(num) > heap.get(tempMax)) {
                        tempMax = num;
                    }
                }

                if (tempMax == currentIndex) {
                    break;
                }

                swap(currentIndex, tempMax);
                currentIndex = tempMax;
            }

        }

        return max;
    }
}
