/*
Advent of Code 2017
Day 8
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

class Registers
{
    private Map<String, Integer> registerValues = new HashMap<String, Integer>();
    private List<String> lines;
    private Comparator<Map.Entry<String, Integer>> comparator;
    private int allTimeHigh = 0;

    public Registers()
    {
        comparator = new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        };
    }
    
    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }

    public void storeRegister(String name, int value)
    {
        registerValues.put(name, value);
    }

    private void printRegisters()
    {
        for (Map.Entry<String, Integer> entry : registerValues.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }       
    }

    private boolean testCondition(String register, String operator, int value)
    {
        int registerValue = registerValues.get(register);
        if (operator.equals(">"))
            return registerValue > value;
        else if (operator.equals("<"))
            return registerValue < value;
        else if (operator.equals(">="))
            return registerValue >= value;
        else if (operator.equals("<="))
            return registerValue <= value;
        else if (operator.equals("=="))
            return registerValue == value;
        else if (operator.equals("!="))
            return registerValue != value;
        return false;
    }

    private void runCommand(String register, String command, int argument)
    {
        int registerValue = registerValues.get(register);
        if (command.equals("inc"))
            registerValue += argument;
        else if (command.equals("dec"))
            registerValue -= argument;

        storeRegister(register, registerValue);
    }

    private int findMax()
    {
        return Collections.max(registerValues.entrySet(), comparator).getValue();
    }
    
    public void init()
    {
        for (String line : lines)
        {
            String[] parts = line.split(" ");
            String commandRegister = parts[0];
            String conditionRegister = parts[4];

            storeRegister(commandRegister, 0);
            storeRegister(conditionRegister, 0);
        }
        
        for (String line : lines)
        {
            String[] parts = line.split(" ");
            String commandRegister = parts[0];
            String command = parts[1];
            int commandArgument = Integer.parseInt(parts[2]);
            String conditionRegister = parts[4];
            String conditionOperator = parts[5];
            int conditionValue = Integer.parseInt(parts[6]);

            if (testCondition(conditionRegister, conditionOperator, conditionValue))
                runCommand(commandRegister, command, commandArgument);

            int max = findMax();
            if (max > allTimeHigh)
                allTimeHigh = max;
        }       
    }

    public int solvePart1()
    {
        return findMax();
    }

    public int solvePart2()
    {
        return allTimeHigh;
    }
}

public class Day8
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day8 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Registers registers = new Registers();
        registers.readFromFile(fileName);
        registers.init();
        int solution1 = registers.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = registers.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
