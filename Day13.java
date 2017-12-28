/*
Advent of Code 2017
Day 13
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

class Layer
{
    private int depth;
    private int range;
    private int scannerLocation = 0;
    private int UP = 0;
    private int DOWN = 1;
    private int scannerDirection = DOWN;
    private boolean caught = false;

    public Layer(int d, int r)
    {
        depth = d;
        range = r;
    }

    public int getDepth()
    {
        return depth;
    }

    public int getRange()
    {
        return range;
    }

    public int getScannerLocation()
    {
        return scannerLocation;
    }

    public boolean getCaught()
    {
        return caught;
    }

    public void advanceScanner()
    {
        if (scannerDirection == DOWN)
        {
            scannerLocation++;
            if (scannerLocation == range - 1)
                scannerDirection = UP;
        }
        else
        {
            scannerLocation--;
            if (scannerLocation == 0)
                scannerDirection = DOWN;

        }
    }

    public void tryToEnter()
    {
        if (scannerLocation == 0)
            caught = true;
    }

    public int getSeverity()
    {
        return depth * range;
    }
}

class FireWall
{
    private List<String> lines;
    private Map<Integer, Layer> layers;
    private int totalSeverity = 0;

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
        layers = new HashMap<Integer, Layer>();
        int maxDepth = 0;
        for (String line : lines)
        {
            String[] parts = line.split(":");
            int depth = Integer.parseInt(parts[0].trim());
            int range = Integer.parseInt(parts[1].trim());
            Layer layer = new Layer(depth, range);
            layers.put(depth, layer);
            if (depth > maxDepth)
                maxDepth = depth;
        }

        for (int i = 0; i <= maxDepth; i++)
        {
            Layer currentLayer = layers.get(i);
            if (currentLayer != null)
                currentLayer.tryToEnter();

            for (Map.Entry<Integer, Layer> entry : layers.entrySet())
            {
                Layer layer = entry.getValue();
                layer.advanceScanner();
            }
        }

        for (Map.Entry<Integer, Layer> entry : layers.entrySet())
        {
            Layer layer = entry.getValue();
            if (layer.getCaught())
                totalSeverity += layer.getSeverity();
        }
    }

    public int solvePart1()
    {
        return totalSeverity;
    }
    
    public int solvePart2()
    {
        return 0;
    }
}

public class Day13
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day13 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        FireWall fireWall = new FireWall();
        fireWall.readFromFile(fileName);
        fireWall.init();
        int solution1 = fireWall.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = fireWall.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
