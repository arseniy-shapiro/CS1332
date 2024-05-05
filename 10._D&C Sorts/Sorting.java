import java.util.Comparator;
import java.util.LinkedList;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class Sorting {

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    @SuppressWarnings("unchecked")
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // Base case
        int length = arr.length;
        if (length <= 1) {
            return;
        }
        // Divide array into two subarrays
        int middleIndex = length / 2;
        int remainder = length - middleIndex;
        T[] leftArray = (T[]) new Object[middleIndex];
        T[] rightArray = (T[]) new Object[remainder];
        for (int i = 0; i < middleIndex; i++) {
            leftArray[i] = arr[i];
        }
        for (int i = 0; i < remainder; i++) {
            rightArray[i] = arr[i + middleIndex];
        }
        // Recursive calls
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        // Merging step
        mergeTwoSubarrays(arr, leftArray, rightArray, comparator);
    }

    private static <T> void mergeTwoSubarrays(T[] arr, T[] leftArray, T[] rightArray, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        int leftLength = leftArray.length;
        int rightLength = rightArray.length;
        while (i < leftLength && j < rightLength) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[i + j] = leftArray[i];
                i++;
            }
            else {
                arr[i + j] = rightArray[j];
                j++;
            }
        }
        while (i < leftLength) {
            arr[i + j] = leftArray[i];
            j++; 
        }
        while (j < rightLength) {
            arr[i + j] = rightArray[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    @SuppressWarnings("unchecked")
    public static void lsdRadixSort(int[] arr) {
        int numberOfBuckets = 19;
        LinkedList<Integer>[] buckets = new LinkedList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }

        int k = findMaxDigitNumber(arr);
        int divider = 1;
        for (int i = 0; i < k; i++) {
            int bucketNumber;
            for (int j = 0, length = arr.length; j < length; j++) {
                bucketNumber = (arr[j] / divider) % 10 + 9;
                buckets[bucketNumber].add(arr[j]);
            }
            int index = 0;
            for (LinkedList<Integer> bucket: buckets) {
                if (bucket != null) {
                    for (int number: bucket) {
                        arr[index] = number;
                        index++;
                    }
                    bucket.clear();
                }
            }
            divider *= 10;
        }
    }

    private static int findMaxDigitNumber(int[] arr) {
        int k = 0;
        int digit;
        int max = 0;
        for (int i = 0, n = arr.length; i < n; i++) {
            if (arr[i] < 0) {
                digit = arr[i] * -1;
            }
            else {
                digit = arr[i];
            }
            if (digit > max) {
                max = digit;
            }
        }
        while (max > 0) {
            max /= 10;
            k++;
        }
        return k;
    }
}
