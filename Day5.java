/*
Advent of Code 2017
Day 5
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class CPU
{
    private int[] instructions;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            List<String> lines = Files.readAllLines(path);
            instructions = new int[lines.size()];
            int i = 0;
            for (String line : lines)
            {
                instructions[i] = Integer.parseInt(line);
                i++;
            }
        }
        catch(Exception e) {}
    }

    public int solvePart1()
    {
        int index = 0;
        int steps = 0;
        int length = instructions.length;
        while (index < length)
        {
            int instruction = instructions[index];
            instructions[index] = instruction + 1;
            index += instruction;
            steps++;
        }
        return steps;
    }

    public int solvePart2()
    {
        int index = 0;
        int steps = 0;
        int length = instructions.length;
        while (index < length)
        {
            int instruction = instructions[index];
            if (instruction >= 3)
                instructions[index] = instruction - 1;
            else
                instructions[index] = instruction + 1;
            index += instruction;
            steps++;
        }
        return steps;
    }
}

public class Day5
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day5 fileName");
            System.exit(0);
        }

        String fileName = args[0];
        CPU cpu = new CPU();
        cpu.readFromFile(fileName);
        int solution1 = cpu.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        // Reinitialize from file
        cpu.readFromFile(fileName);
        int solution2 = cpu.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
