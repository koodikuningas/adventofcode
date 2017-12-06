/*
Advent of Code 2017
Day 6
Author: koodikuningas
*/

import java.util.ArrayList;
import java.util.List;

class Debugger
{
    private int[] data;

    public void init(String input)
    {
        String[] parts = input.split("\t");
        data = new int[parts.length];
        for (int i = 0; i < parts.length; i++)
        {
            data[i] = Integer.parseInt(parts[i]);
        }
    }

    public int solvePart1()
    {
        boolean similarFound = false;
        int cycles = 0;
        List<String> oldCycles = new ArrayList<String>();
        while (!similarFound)
        {
            int index = findMaxIndex();
            int number = data[index];
            int divisor = data.length;
            int quotient = (int)Math.round((float)number / divisor);                  
            oldCycles.add(this.toString());
            cycles++;
    
            data[index] = 0;
            for (int i = 0; i < data.length; i++)
            {
                index++;
                if (index == data.length)
                    index = 0;
                int distributable = quotient;
                if (number < quotient)
                    distributable = number;
                number -= distributable;
                data[index] = data[index] + distributable;
            }

            if (oldCycles.contains(this.toString()))
                similarFound = true;
        }
        return cycles;
    }

    public int solvePart2()
    {
        return 0;
    }

    private int findMaxIndex()
    {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < data.length; i++)
        {
            if (data[i] > max)
            {
                max = data[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public String toString()
    {
        String ret = "";
        for (int i = 0; i < data.length; i++)
        {
            ret += data[i] + " ";
        }
        return ret;
    }
}

public class Day6
{
    public static void main(String[] args)
    {
        String input = "11	11	13	7	0	15	5	5	4	4	1	1	7	1	15	11";
        Debugger debugger = new Debugger();
        debugger.init(input);
        int solution1 = debugger.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        // Reinitialize
        debugger.init(input);
        int solution2 = debugger.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
