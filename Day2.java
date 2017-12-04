/*
Advent of Code 2017
Day 2
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class SpreadSheet
{
    private List<String> lines;

    void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }

    int solvePart1()
    {
        int sum = 0;
        for (String line : lines)
        {
            String[] parts = line.split("\t");
            int max = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < parts.length; i++)
            {
                int number = Integer.parseInt(parts[i]);
                if (number > max)
                    max = number;
                if (number < min)
                    min = number;
            }
            int difference = max - min;
            sum += difference;
        }
        return sum;
    }

    int solvePart2()
    {
        int sum = 0;
        for (String line : lines)
        {
            String[] parts = line.split("\t");
            int solution = 0;
            for (int i = 0; i < parts.length; i++)
            {
                int first = Integer.parseInt(parts[i]);
                for (int j = 0; j < parts.length; j++)
                {
                    int second = Integer.parseInt(parts[j]);
                    if (first != second)
                    {
                        int bigger = Math.max(first, second);
                        int smaller = Math.min(first, second);
                        if (bigger % smaller == 0)
                            solution = bigger / smaller;
                    }
                }
            }
            sum += solution;
        }
        return sum;
    }
}

class Day2
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day2 fileName");
            System.exit(0);
        }

        String fileName = args[0];
        SpreadSheet spreadSheet = new SpreadSheet();
        spreadSheet.readFromFile(fileName);
        int solution1 = spreadSheet.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = spreadSheet.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
