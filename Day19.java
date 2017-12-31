/*
Advent of Code 2017
Day 19
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

class SeriesOfTubes
{   
    private List<String> lines;
    private char[][] grid;
    private String track = "";
    private int DOWN = 0;
    private int UP = 1;
    private int LEFT = 2;
    private int RIGHT = 3;
    private int currentDirection = DOWN;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }

    private void setDirection(int direction)
    {
        currentDirection = direction;
    }

    public void init()
    {
        int height = lines.size();
        int width = 0;
        for (String line : lines)
        {
            if (line.length() > width)
                width = line.length();
        }

        grid = new char[width][height];

        for (int i = 0; i < lines.size(); i++)
        {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++)
            {
                grid[j][i] = line.charAt(j);
            }
        }

        int currentX = 0;
        int currentY = 0;

        for (int i = 0; i < width; i++)
        {
            if (grid[i][0] == '|')
            {
                currentX = i;
                break;
            }
        }

        while (true)
        {
            if (grid[currentX][currentY] == ' ')
                break;

            if (currentDirection == DOWN)
                currentY++;
            else if (currentDirection == UP)
                currentY--;
            else if (currentDirection == RIGHT)
                currentX++;
            else if (currentDirection == LEFT)
                currentX--;

            if (grid[currentX][currentY] == '+')
            {
                if (currentDirection == UP || currentDirection == DOWN)
                {
                    if (grid[currentX + 1][currentY] == ' ')
                        setDirection(LEFT);
                    else
                        setDirection(RIGHT);
                }
                else
                {
                    if (grid[currentX][currentY + 1] == ' ')
                        setDirection(UP);
                    else
                        setDirection(DOWN);
                }
            }
            else if (grid[currentX][currentY] != '-' && grid[currentX][currentY] != '|')
            {
                track += grid[currentX][currentY];
            }
        }
    }

    public String solvePart1()
    {
        return track;
    }
    
    public int solvePart2()
    {
        return 0;
    }
}

public class Day19
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day19 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        SeriesOfTubes seriesOfTubes = new SeriesOfTubes();
        seriesOfTubes.readFromFile(fileName);
        seriesOfTubes.init();
        String solution1 = seriesOfTubes.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = seriesOfTubes.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
