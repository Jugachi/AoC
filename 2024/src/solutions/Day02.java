import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public static void main(String[] args) {
    Scanner scanner;
    try {
        scanner = new Scanner(new File("/src/input.txt"));
    } catch (FileNotFoundException e) {
        System.out.println("File not found");
        return;
    }

    // Choose part1 or part2 logic (true = part1, false = part2)
    boolean part1 = false;
    String result = solve(part1, scanner);
    System.out.println("Result: " + result);
}

public static String solve(boolean part1, Scanner in) {
    long answer = 0;
    List<String[]> reports = new ArrayList<>();

    // Read input
    while (in.hasNext()) {
        String line = in.nextLine();
        reports.add(line.split(" "));
    }

    if (part1) {
        // Part 1: Check each report directly
        for (String[] report : reports) {
            if (isSafe(report)) {
                answer++;
            }
        }
    } else {
        // Part 2: Check if any subsequence of the report is safe
        for (String[] report : reports) {
            boolean anySafe = false;
            for (int i = 0; i < report.length; i++) {
                // Remove one element to create a new array
                String[] newReport = new String[report.length - 1];
                System.arraycopy(report, 0, newReport, 0, i);
                System.arraycopy(report, i + 1, newReport, i, report.length - i - 1);

                if (isSafe(newReport)) {
                    anySafe = true;
                    break;
                }
            }
            if (anySafe) {
                answer++;
            }
        }
    }

    return String.valueOf(answer);
}

private static boolean isSafe(String[] steps) {
    int[] nums = new int[steps.length];
    for (int i = 0; i < nums.length; i++) {
        nums[i] = Integer.parseInt(steps[i]);
    }

    // Determine trend (increasing or decreasing)
    boolean decreasing = nums[0] > nums[1];
    if (nums[0] == nums[1]) {
        return false;
    }

    for (int i = 1; i < nums.length; i++) {
        // Check adjacent difference range
        if (Math.abs(nums[i] - nums[i - 1]) > 3) {
            return false;
        }

        // Ensure no mixed trends
        if (nums[i] > nums[i - 1] && decreasing) {
            return false;
        }
        if (nums[i] < nums[i - 1] && !decreasing) {
            return false;
        }

        // Ensure no repeated numbers
        if (nums[i] == nums[i - 1]) {
            return false;
        }
    }

    return true;
}
