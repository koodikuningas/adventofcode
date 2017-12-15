/*
Advent of Code 2017
Day 12
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

class Pipes
{
    private List<String> lines;
    private Map<String, List<String>> pipes = new HashMap<String, List<String>>();
    private List<String> foundPipes = new ArrayList<String>();
    private List<String> resultPipes = new ArrayList<String>();

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    public void findPipe(String programToFind)
    {
        for (String line : lines)
        {
            String[] parts = line.split("<->");
            String left = parts[0].trim();
            String right = parts[1];
            parts = right.split(",");
            List<String> programs = new ArrayList<String>();
            for (String part : parts)
            {
                programs.add(part.trim());   
            }
            pipes.put(left, programs);
        }
        
        for (Map.Entry<String, List<String>> entry : pipes.entrySet())
        {
            String mainProgram = entry.getKey();
            List<String> subPrograms = entry.getValue();

            if (mainProgram.equals(programToFind))
                addPipe(foundPipes, mainProgram);
            else
            {
                for (String sub : subPrograms)
                {
                    if (sub.equals(programToFind))
                        addPipe(foundPipes, mainProgram);                
                }
            }
        }
        while (foundPipes.size() > 0)
        {
            List<String> pipesToCheck = new ArrayList<String>();
            for (String pipe : foundPipes)
            {
                addPipe(resultPipes, pipe);
                addPipe(pipesToCheck, pipe);
            }
            foundPipes.clear(); 

            for (String pipe : pipesToCheck)
            {
                List<String> subPrograms = pipes.get(pipe);
                for (String sub : subPrograms)
                {
                    if (!resultPipes.contains(sub))
                        addPipe(foundPipes, sub);
                }
            }         
        }
    }

    private void addPipe(List<String> list, String pipe)
    {
        if (!list.contains(pipe))
            list.add(pipe);
    }

    public int solvePart1()
    {
        return resultPipes.size();
    }
    
    public int solvePart2()
    {
        int groups = 1;
        List<String> notFoundPipes = new ArrayList<String>();
        for (String key : pipes.keySet())
        {
            if (!resultPipes.contains(key))
                notFoundPipes.add(key);
        }

        while (notFoundPipes.size() > 0)
        {
            findPipe(notFoundPipes.get(0));
            notFoundPipes.clear();
            for (String key : pipes.keySet())
            {
                if (!resultPipes.contains(key))
                    notFoundPipes.add(key);
            }
            groups++;
        }

        return groups;
    }
}

public class Day12
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day12 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Pipes pipes = new Pipes();
        pipes.readFromFile(fileName);
        pipes.findPipe("0");
        int solution1 = pipes.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = pipes.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
