/*
Advent of Code 2017
Day 18
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

class Duet
{   
    private List<String> lines;
    private long frequency = 0;
    private Map<String, Long> registers;
    private int currentCommandIndex;
    private long recoveredFrequency;
    private long part1Solution = 0;
    private boolean part1SolutionFound = false;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    private void executeNext()
    {
        currentCommandIndex++;
        if (currentCommandIndex < lines.size())
            executeCommand(lines.get(currentCommandIndex));
    }

    private boolean isNumeric(String value)
    {
        return value.startsWith("-") || value.chars().allMatch(Character::isDigit);
    }

    private long getRegister(String register)
    {
        if (isNumeric(register))
            return Long.parseLong(register);

        Long value = registers.get(register);
        if (value == null)
            return 0;
        return value;
    }

    private void setRegister(String register, long value)
    {
        registers.put(register, value);
    }

    private void executeCommand(String line)
    {
        if (part1SolutionFound)
            return;

        String[] parts = line.split(" ");
        String command = parts[0];
        if (command.equals("snd"))
        {
            frequency = getRegister(parts[1]);
        }
        else if (command.equals("rcv"))
        {
            long registerValue = getRegister(parts[1]);
            if (registerValue > 0)
            {
                recoveredFrequency = frequency;

                if (!part1SolutionFound)
                {
                    part1Solution = recoveredFrequency;
                    part1SolutionFound = true;
                }
            }
            
        }
        else if (command.equals("jgz"))
        {
            long x = getRegister(parts[1]);
            long y = getRegister(parts[2]);
            if (x > 0)
            {
                currentCommandIndex += y;
                if (currentCommandIndex > 0 && currentCommandIndex < lines.size())
                {
                    executeCommand(lines.get(currentCommandIndex));
                    return;
                }
            }
        }
        else if (command.equals("set"))
        {
            String register = parts[1];
            long value = getRegister(parts[2]);
            setRegister(register, value);
        }
        else
        {
            String register = parts[1];
            long value = getRegister(parts[2]);
            long currentValue = getRegister(register);

            if (command.equals("add"))
            {
                setRegister(register, value + currentValue);
            }
            else if (command.equals("mul"))
            {
                setRegister(register, value * currentValue);
            }
            else if (command.equals("mod"))
            {
                setRegister(register, Math.floorMod(currentValue, value));
            }
        }

        executeNext();
    }

    public void init()
    {
        registers = new HashMap<String, Long>();
        currentCommandIndex = 0;
    }

    private void start()
    {
        executeCommand(lines.get(0));
    }

    public long solvePart1()
    {
        start();
        return part1Solution;
    }
    
    public int solvePart2()
    {
        return 0;
    }
}

public class Day18
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day18 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Duet duet = new Duet();
        duet.readFromFile(fileName);
        duet.init();
        long solution1 = duet.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        duet.init();
        int solution2 = duet.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
