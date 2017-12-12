/*
Advent of Code 2017
Day 11
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class HexGrid
{
    private List<String> lines;
    private int startX = 0;
    private int startY = 0;    
    private int currentX = startX;
    private int currentY = startY;
    private int distanceMax = 0;

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
        String input = lines.get(0);
        String[] parts = input.split(",");

        for (String part : parts)
        {
            if (part.equals("n"))
            {
                currentY--;
            }
            else if (part.equals("s"))
            {
                currentY++;
            }
            else if (part.equals("nw"))
            {
                currentX--;
                currentY--;
            }
            else if (part.equals("sw"))
            {
                currentX--;
                currentY++;
            }
            else if (part.equals("ne"))
            {
                currentX++;
                currentY--;
            }
            else if (part.equals("se"))
            {
                currentX++;
                currentY++;
            }

            int currentDistance = distanceFromCurrentCoordinates();
            if (currentDistance > distanceMax)
            {
                distanceMax = currentDistance;
            }
        }
        
    }

    private int distanceFromCurrentCoordinates()
    {
        int x1 = startX;
        int z1 = startY - (startX - (startX&1)) / 2;
        int y1 = -x1-z1;

        int x2 = currentX;
        int z2 = currentY - (currentX - (currentX&1)) / 2;
        int y2 = -x2-z2;

        int result = Math.max(Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1)), Math.abs(z2 - z1));
        return result;
    }

    public int solvePart1()
    {
        return distanceFromCurrentCoordinates();
    }
    
    public int solvePart2()
    {
        return distanceMax;
    }
}

public class Day11
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day11 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        HexGrid hexGrid = new HexGrid();
        hexGrid.readFromFile(fileName);
        hexGrid.init();
        int solution1 = hexGrid.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = hexGrid.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
