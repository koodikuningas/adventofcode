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
}

class Program
{
    Program otherProgram;
    private int sendCount = 0;
    private List<Long> sendQueue = new ArrayList<Long>();
    private Map<String, Long> registers = new HashMap<String, Long>();
    private int currentCommandIndex = 0;
    private boolean terminated = false;
    private List<String> lines;
    private int programId;

    public Program(int pValue, List<String> l)
    {
        programId = pValue;
        setRegister("p", pValue);
        lines = l;
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

    public void setOtherProgram(Program program)
    {
        otherProgram = program;
    }

    public boolean messageReady()
    {
        return sendQueue.size() > 0;
    }

    public long readMessage()
    {
        sendCount++;
        return sendQueue.remove(0);
    }

    public int getSendCount()
    {
        return sendCount;
    }

    public boolean isTerminated()
    {
        return terminated;
    }

    private void setTerminated(boolean t)
    {
        terminated = t;
    }

    public void executeCommand()
    {
        if (currentCommandIndex < 0 || currentCommandIndex > lines.size() - 1)
        {
            setTerminated(true);
            return;
        }

        String line = lines.get(currentCommandIndex);
        String[] parts = line.split(" ");
        String command = parts[0];
        if (command.equals("snd"))
        {
            long registerValue = getRegister(parts[1]);
            sendQueue.add(registerValue);
        }
        else if (command.equals("rcv"))
        {
            String register = parts[1];
            setTerminated(true);
            if (otherProgram.messageReady())
            {
                setTerminated(false);
                long receivedValue = otherProgram.readMessage();
                setRegister(register, receivedValue);
            }
            else
                return;
        }
        else if (command.equals("jgz"))
        {
            long x = getRegister(parts[1]);
            long y = getRegister(parts[2]);
            if (x > 0)
            {
                currentCommandIndex += y;
                return;
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

        currentCommandIndex++;
    }
}

class ProgramUtil
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

    public int solvePart2()
    {
        Program program0 = new Program(0, lines);
        Program program1 = new Program(1, lines);

        program0.setOtherProgram(program1);
        program1.setOtherProgram(program0);

        while (!program0.isTerminated() || !program1.isTerminated())
        {
            program0.executeCommand();
            program1.executeCommand();
        }

        return program1.getSendCount();
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
        ProgramUtil programUtil = new ProgramUtil();
        programUtil.readFromFile(fileName);
        int solution2 = programUtil.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
