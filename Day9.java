/*
Advent of Code 2017
Day 9
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Stream
{
    private List<String> lines;
    private int totalScore = 0;
    private int canceledCharacters = 0;
    
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
        String stream = lines.get(0);
        int currentScore = 0;
        boolean garbageMode = false;
        boolean ignoreMode = false;

        for (int i = 0; i < stream.length(); i++)
        {
            char c = stream.charAt(i);

            if (garbageMode)
            {
                if (ignoreMode)
                    ignoreMode = false;
                else
                {
                    if (c == '>')
                    {
                        garbageMode = false;
                        ignoreMode = false;
                    }
                    else if (c == '!')
                    {
                        ignoreMode = true;
                    }
                    else
                    {
                        canceledCharacters++;
                    }
                }
            }
            else
            {
                if (c == '{')
                {
                    currentScore++;
                }
                else if (c == '}')
                {
                    totalScore += currentScore;
                    currentScore--;
                }
                else if (c == '<')
                {
                    garbageMode = true;
                }
            }
        }
    }

    public int solvePart1()
    {
        return totalScore;
    }

    public int solvePart2()
    {
        return canceledCharacters;
    }
}

public class Day9
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day9 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Stream stream = new Stream();
        stream.readFromFile(fileName);
        stream.init();
        int solution1 = stream.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = stream.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
