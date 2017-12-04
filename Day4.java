/*
Advent of Code 2017
Day 4
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class PassPhrase
{
    private List<String> lines;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }

    private boolean isValid(String line, boolean anagramsAllowed)
    {
        String[] parts = line.split(" ");
        for (int i = 0; i < parts.length; i++)
        {
            String part = parts[i];
            if (!part.equals(part.toLowerCase()))
                return false;

            for (int j = 0; j < parts.length; j++)
            {
                String secondPart = parts[j];
                if (i != j && part.equals(secondPart))
                    return false;
                if (!anagramsAllowed)
                {
                    String firstSorted = sortString(part);
                    String secondSorted = sortString(secondPart);
                    if (i != j && firstSorted.equals(secondSorted))
                        return false;
                }
            }
        }
        return true;
    }

    private String sortString(String str)
    {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public int solvePart1()
    {
        int sum = 0;
        for (String line : lines)
        {
            if (isValid(line, true))
                sum++;
        }
        return sum;
    }

    public int solvePart2()
    {
        int sum = 0;
        for (String line : lines)
        {
            if (isValid(line, false))
                sum++;
        }
        return sum;
    }
}

class Day4
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day4 fileName");
            System.exit(0);
        }

        String fileName = args[0];
        PassPhrase passPhrase = new PassPhrase();
        passPhrase.readFromFile(fileName);
        int solution1 = passPhrase.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = passPhrase.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
