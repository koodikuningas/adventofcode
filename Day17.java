/*
Advent of Code 2017
Day 17
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

class SpinLock
{   
    private List<String> lines;
    private int ROUNDS_PART1 = 2017;
    private int ROUNDS_PART2 = 50000000;
    private List<Integer> buffer;
    private int steps;
    private int currentPosition;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    private void advance()
    {
        for (int i = 0; i < steps; i++)
        {
            currentPosition++;
            if (currentPosition == buffer.size())
                currentPosition = 0;
        }
    }

    public void init()
    {
        buffer = new ArrayList<Integer>();
        buffer.add(0);
        currentPosition = 0;
        steps = Integer.parseInt(lines.get(0));
    }

    public void spin(int rounds)
    {
        for (int i = 1; i <= rounds; i++)
        {
            advance();
            currentPosition++;
            buffer.add(currentPosition, i);
            
        }
    }

    public int solvePart1()
    {
        spin(ROUNDS_PART1);

        int index = buffer.indexOf(ROUNDS_PART1);
        if (index == buffer.size() - 1)
            return buffer.get(0);
        return buffer.get(index + 1);
    }
    
    public int solvePart2()
    {
        int index = 0;
        int result = 0;

        for (int i = 1; i <= ROUNDS_PART2; i++)
        {
            index = (steps + index) % i + 1;
            if (index == 1)
                result = i;
        }

        return result;
    }
}

public class Day17
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day17 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        SpinLock spinLock = new SpinLock();
        spinLock.readFromFile(fileName);
        spinLock.init();
        int solution1 = spinLock.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        spinLock.init();
        int solution2 = spinLock.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
