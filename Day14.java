/*
Advent of Code 2017
Day 14
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;

class KnotHashUtil
{
    private List<Integer> marks = new ArrayList<Integer>();

    public String hash(String input)
    {
        int maxSize = 256;
        int skipSize = 0;
        int currentPosition = 0;

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

class DiskFragmenter
{   
    private List<String> lines;
    private int[][] grid;

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
        String puzzle = lines.get(0);
        grid = new int[128][128];

        for (int i = 0; i < 128; i++)
        {
            String row = puzzle + "-" + i;
            String hash = (new KnotHashUtil()).hash(row);
            String hashBinary = "";
            for (int j = 0; j < hash.length(); j++)
            {
                int value = Integer.parseInt(hash.substring(j,j+1), 16);
                String result = Integer.toBinaryString(value);
                String formatted = ("0000" + result).substring(result.length());
                hashBinary += formatted;
            }

            for (int j = 0; j < hashBinary.length(); j++)
            {
                grid[i][j] = Integer.parseInt(hashBinary.substring(j,j+1));
            }
        }
    }

    public int solvePart1()
    {
        int usedSquares = 0;

        for (int i = 0; i < 128; i++)
        {
            for (int j = 0; j < 128; j++)
            {
                if (grid[i][j] == 1)
                    usedSquares++;
            }
        }

        return usedSquares;
    }
    
    private void markRegion(int x, int y, int regionNumber)
    {
        if (x < 0 || y < 0 || x > 127 || y > 127)
            return;

        if (grid[x][y] != 1)
            return;

        grid[x][y] = regionNumber;

        markRegion(x - 1, y, regionNumber);
        markRegion(x + 1, y, regionNumber);
        markRegion(x, y - 1, regionNumber);
        markRegion(x, y + 1, regionNumber);
    }

    public int solvePart2()
    {
        int currentRegionNumber = 1;
        for (int i = 0; i < 128; i++)
        {
            for (int j = 0; j < 128; j++)
            {
                if (grid[i][j] == 1)
                {
                    currentRegionNumber++;
                    markRegion(i, j, currentRegionNumber);
                }
            }
        }
        return currentRegionNumber - 1;
    }
}

public class Day14
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day14 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        DiskFragmenter diskFragmenter = new DiskFragmenter();
        diskFragmenter.readFromFile(fileName);
        diskFragmenter.init();
        int solution1 = diskFragmenter.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = diskFragmenter.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
