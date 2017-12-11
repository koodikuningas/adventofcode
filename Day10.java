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

    public int solvePart2()
    {
        return 0;
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
        int solution2 = knotHash.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
