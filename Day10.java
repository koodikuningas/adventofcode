/*
Advent of Code 2017
Day 10
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class KnotHash
{
    private List<String> lines;
    private List<Integer> marks = new ArrayList<Integer>();
    
    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    public void init()
    {
        int maxSize = 256;
        int skipSize = 0;
        int currentPosition = 0;
        String input = lines.get(0);
        String[] parts = input.split(",");

        for (int i = 0; i < maxSize; i++)
        {
            marks.add(i);
        }

        for (String part : parts)
        {
            int length = Integer.parseInt(part);

            List<Integer> reverse = new ArrayList<Integer>();
            int position = currentPosition;
            for (int i = 0; i < length; i++)
            {
                reverse.add(marks.get(position));

                position++;
                if (position > maxSize - 1)
                    position = 0;        
            }

            Collections.reverse(reverse);
            position = currentPosition;
            for (Integer r : reverse)
            {
                marks.set(position, r);
                position++;
                if (position > maxSize - 1)
                    position = 0;
            }

            currentPosition += length + skipSize;
            while (currentPosition > marks.size() - 1)
            {
                currentPosition = currentPosition - marks.size();
            }

            skipSize++;
        }
    }

    public int solvePart1()
    {
        return marks.get(0) * marks.get(1);
    }

    public String solvePart2()
    {
        int maxSize = 256;
        int skipSize = 0;
        int currentPosition = 0;

        String input = lines.get(0);
        List<Integer> lengths = new ArrayList<Integer>();
        marks = new ArrayList<Integer>();
        for (int i = 0; i < maxSize; i++)
        {
            marks.add(i);
        }

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            lengths.add((int)c);
        }
        lengths.add(17);
        lengths.add(31);
        lengths.add(73);
        lengths.add(47);
        lengths.add(23);

        for (int j = 0; j < 64; j++)
        {
            for (int length : lengths)
            {
                List<Integer> reverse = new ArrayList<Integer>();
                int position = currentPosition;
                for (int i = 0; i < length; i++)
                {
                    reverse.add(marks.get(position));

                    position++;
                    if (position > maxSize - 1)
                        position = 0;
                }

                Collections.reverse(reverse);
                position = currentPosition;
                for (Integer r : reverse)
                {
                    marks.set(position, r);
                    position++;
                    if (position > maxSize - 1)
                        position = 0;
                }

                currentPosition += length + skipSize;
                while (currentPosition > marks.size() - 1)
                {
                    currentPosition = currentPosition - marks.size();
                }

                skipSize++;
            }
        }

        String result = "";

        for (int i = 0; i < 16; i++)
        {
            int startIndex = i*16;
            int number = marks.get(startIndex);
            for (int j = 1; j < 16; j++)
            {
                number = number ^ marks.get(startIndex + j);
            }
            result += String.format("%02x", number);
        }

        return result;
    }
}

public class Day10
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day10 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        KnotHash knotHash = new KnotHash();
        knotHash.readFromFile(fileName);
        knotHash.init();
        int solution1 = knotHash.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        String solution2 = knotHash.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
