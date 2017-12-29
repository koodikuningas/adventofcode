/*
Advent of Code 2017
Day 16
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

class Dancing
{   
    private List<String> lines;
    private String[] parts;
    private String programs;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    private void changeChars(int first, int second)
    {
        char[] chars = programs.toCharArray();
        char temp = chars[first];
        chars[first] = chars[second];
        chars[second] = temp;
        programs = String.valueOf(chars);
    }

    private void spinChars(int amount)
    {
        String head = programs.substring(0, programs.length() - amount);
        String tail = programs.substring(programs.length() - amount);
        programs = tail + head;
    }

    public void init()
    {
        programs = "abcdefghijklmnop";
        parts = lines.get(0).split(",");
    }

    public void runPrograms()
    {
        for (String part : parts)
        {
            if (part.startsWith("s"))
            {
                int spin = Integer.parseInt(part.substring(1));
                spinChars(spin);
            }
            else if (part.startsWith("x"))
            {
                String[] arguments = part.substring(1).split("/");
                int first = Integer.parseInt(arguments[0]);
                int second = Integer.parseInt(arguments[1]);
                changeChars(first, second);
            }
            else if (part.startsWith("p"))
            {
                String[] arguments = part.substring(1).split("/");
                String firstString = arguments[0];
                String secondString = arguments[1];
                int first = programs.indexOf(firstString);
                int second = programs.indexOf(secondString);
                changeChars(first, second);
            }
        }
    } 

    public String solvePart1()
    {
        runPrograms();
        return programs;
    }
    
    public String solvePart2()
    {
        int rounds = 1000000000;

        List<String> allPrograms = new ArrayList<String>();
        for (int i = 0; i < rounds; i++)
        {
            if (allPrograms.contains(programs))
            {
                return allPrograms.get(rounds % i);
            }

            allPrograms.add(programs);
            runPrograms();
        }

        return null;
    }
}

public class Day16
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day16 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Dancing dancing = new Dancing();
        dancing.readFromFile(fileName);
        dancing.init();
        String solution1 = dancing.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        dancing.init();
        String solution2 = dancing.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
