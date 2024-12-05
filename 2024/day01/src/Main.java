import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


// https://adventofcode.com/2024/day/1
public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> locationsLeft = new ArrayList<>();
        ArrayList<Integer> locationsRight = new ArrayList<>();

       try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
           String line;
           while ((line = br.readLine()) != null) {
               String[] numbers = line.split("\\s+");
               if (numbers.length == 2) {
                   locationsLeft.add(Integer.parseInt(numbers[0]));
                   locationsRight.add(Integer.parseInt(numbers[1]));
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       int[] leftArray = locationsLeft.stream().mapToInt(i -> i).toArray();
       int[] rightArray = locationsRight.stream().mapToInt(i -> i).toArray();

       Arrays.sort(leftArray);
       Arrays.sort(rightArray);

       int totalDistance = 0;
       for (int i = 0; i < leftArray.length; i++) {
           totalDistance += Math.abs(leftArray[i] - rightArray[i]);
       }

       System.out.println("Total distance: " + totalDistance);

       int similarityScore = calculateSimilarityScore(locationsLeft, locationsRight);
       System.out.println("Similarity Score: " + similarityScore);
    }

    private static int calculateSimilarityScore(ArrayList<Integer> locationsLeft, ArrayList<Integer> locationsRight) {
        Map<Integer, Integer> rightCountMap = new HashMap<>();

        for (int number : locationsRight) {
            rightCountMap.put(number, rightCountMap.getOrDefault(number, 0) + 1);
        }

        int similarityScore = 0;

        for (int number : locationsLeft) {
            int countInRight = rightCountMap.getOrDefault(number, 0);
            similarityScore += (number * countInRight);
        }

        return similarityScore;
    }
}