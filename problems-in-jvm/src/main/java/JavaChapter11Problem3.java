import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

public class JavaChapter11Problem3 {
    public static final class CustomAVLTree {
        private final int key;
        private int size;
        private int height;
        private CustomAVLTree left;
        private CustomAVLTree right;

        public CustomAVLTree(int key) {
            this.key = key;
            this.size = 1;
            this.height = 0;
        }

        public int getKey() {
            return key;
        }

        public int getSize() {
            return size;
        }

        public int getHeight() {
            return height;
        }

        public CustomAVLTree getLeft() {
            return left;
        }

        public CustomAVLTree getRight() {
            return right;
        }

        public CustomAVLTree insert(int value) {
            if (value > key) {
                right = (right != null) ? right.insert(value) : new CustomAVLTree(value);
            } else {
                left = (left != null) ? left.insert(value) : new CustomAVLTree(value);
            }
            size++;
            height = Math.max(
                    (right != null ? right.height : 0),
                    (left != null ? left.height : 0)
            ) + 1;

            int leftHeight = (left != null) ? left.getHeight() : 0;
            int rightHeight = (right != null) ? right.getHeight() : 0;

            if (rightHeight + 1 < leftHeight) {
                CustomAVLTree newRoot = Objects.requireNonNull(left, "Left node is null");
                int newRootLeftHeight = (newRoot.left != null) ? newRoot.left.getHeight() : 0;
                int newRootRightHeight = (newRoot.right != null) ? newRoot.right.getHeight() : 0;
                if (newRootLeftHeight + 1 < newRootRightHeight) {
                    left = newRoot.rotateLeft();
                }

                return rotateRight();
            } else if (leftHeight + 1 < rightHeight) {
                CustomAVLTree newRoot = Objects.requireNonNull(right, "Right node is null");
                int newRootLeftHeight = (newRoot.left != null) ? newRoot.left.getHeight() : 0;
                int newRootRightHeight = (newRoot.right != null) ? newRoot.right.getHeight() : 0;
                if (newRootLeftHeight > newRootRightHeight + 1) {
                    right = newRoot.rotateRight();
                }
                return rotateLeft();
            } else {
                return this;
            }
        }

        private CustomAVLTree rotateLeft() {
            CustomAVLTree newRoot = Objects.requireNonNull(right, "Right node is null.");
            right = newRoot.left;
            newRoot.left = this;

            int leftHeight = (left != null) ? left.getHeight() : 0;
            int rightHeight = (right != null) ? right.getHeight() : 0;
            height = (right == null && left == null) ? 0 : Math.max(leftHeight, rightHeight) + 1;
            int leftSize = (left != null) ? left.size : 0;
            int rightSize = (right != null) ? right.size : 0;
            size = leftSize + rightSize + 1;


            int newLeftHeight = newRoot.left.getHeight();
            int newRightHeight = (newRoot.right != null) ? newRoot.right.getHeight() : 0;
            newRoot.height = Math.max(newLeftHeight, newRightHeight) + 1;

            int newLeftSize = newRoot.left.size;
            int newRightSize = (newRoot.right != null) ? newRoot.right.size : 0;
            newRoot.size = newLeftSize + newRightSize + 1;

            return newRoot;
        }

        private CustomAVLTree rotateRight() {
            if (left == null) {
                throw new IllegalStateException("Left node is null.");
            }
            CustomAVLTree newRoot = left;
            left = newRoot.right;

            int leftHeight = (left != null) ? left.getHeight() : 0;
            int rightHeight = (right != null) ? right.getHeight() : 0;
            height = (right == null && left == null) ? 0 : Math.max(leftHeight, rightHeight) + 1;
            int leftSize = (left != null) ? left.size : 0;
            int rightSize = (right != null) ? right.size : 0;
            size = leftSize + rightSize + 1;

            newRoot.right = this;

            int newLeftHeight = (newRoot.left != null) ? newRoot.left.getHeight() : 0;
            int newRightHeight = newRoot.right.getHeight();
            newRoot.height = Math.max(newLeftHeight, newRightHeight) + 1;

            int newLeftSize = (newRoot.left != null) ? newRoot.left.size : 0;
            int newRightSize = newRoot.right.size;
            newRoot.size = newLeftSize + newRightSize + 1;

            return newRoot;
        }

        public int select(int position) {
            if (position <= 0 || position > size) {
                throw new IllegalArgumentException("Invalid position.");
            }
            int rightSize = (right != null) ? right.size : 0;
            if (position == size - rightSize) {
                return key;
            } else if (position < size - rightSize) {
                // left is not null
                return left.select(position);
            } else {
                int leftSize = (left != null) ? left.size : 0;
                // right is not null
                return right.select(position - leftSize - 1);
            }
        }
    }

    public int[] getHeapKthMedians(int[] numbers) {
        PriorityQueue<Integer> firstHalfHeap = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> secondHalfHeap = new PriorityQueue<>();
        int[] result = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            if (firstHalfHeap.isEmpty()) {
                firstHalfHeap.add(numbers[i]);
            } else if (secondHalfHeap.isEmpty()) {
                if (numbers[i] < firstHalfHeap.peek()) {
                    secondHalfHeap.add(firstHalfHeap.poll());
                    firstHalfHeap.add(numbers[i]);
                } else {
                    secondHalfHeap.add(numbers[i]);
                }
            } else if (numbers[i] > secondHalfHeap.peek()) {
                secondHalfHeap.add(numbers[i]);
            } else {
                firstHalfHeap.add(numbers[i]);
            }

            if (firstHalfHeap.size() < secondHalfHeap.size()) {
                firstHalfHeap.add(secondHalfHeap.poll());
            }
            if (firstHalfHeap.size() > secondHalfHeap.size() + 1) {
                secondHalfHeap.add(firstHalfHeap.poll());
            }

            result[i] = Objects.requireNonNull(firstHalfHeap.peek());
        }

        return result;
    }

    public int[] getSearchTreeKthMedians(int[] numbers) {
        CustomAVLTree customSearchTree = new CustomAVLTree(numbers[0]);
        int[] result = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) {
                customSearchTree = customSearchTree.insert(numbers[i]);
            }
            result[i] = customSearchTree.select((i + 2) / 2);
        }

        return result;
    }
}
