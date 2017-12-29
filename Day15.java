/*
Advent of Code 2017
Day 15
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

class Generator
{
    private long start = 0;
    private long factor = 0;
    private long DIVIDER = 2147483647;

    public Generator(long s, long f)
    {
        start = s;
        factor = f;
    }

    public long next()
    {
        long x = start * factor;
        long y = x % DIVIDER;
        start = y;
        return y;
    }
}

class GeneratorUtil
{   
    private List<String> lines;
    private int GEN_A_FACTOR = 16807;
    private int GEN_B_FACTOR = 48271;
    private int rounds = 40000000;
    private int matches = 0;

    public void readFromFile(String fileName)
    {
        try
        {
            Path path = Paths.get(".", fileName);
            lines = Files.readAllLines(path);
        }
        catch (Exception e) {}
    }
    
    private int parseGeneratorStart(String line)
    {
        String[] parts = line.split(" ");
        String start = parts[parts.length - 1].trim();
        return Integer.parseInt(start);
    }

    private String getBinaryPart(long value)
    {
        String binary = Long.toBinaryString(value);
        if (binary.length() < 16)
            return binary;

        return binary.substring(binary.length()-16);
    }

    public void init()
    {
        int generatorAStart = parseGeneratorStart(lines.get(0));
        int generatorBStart = parseGeneratorStart(lines.get(1));

        Generator generatorA = new Generator(generatorAStart, GEN_A_FACTOR);
        Generator generatorB = new Generator(generatorBStart, GEN_B_FACTOR);

        for (int i = 0; i < rounds; i++)
        {
            long a = generatorA.next();
            long b = generatorB.next();
            if (getBinaryPart(a).equals(getBinaryPart(b)))
                matches++;
        }
    }

    public int solvePart1()
    {
        return matches;
    }
    
    public int solvePart2()
    {
        return 0;
    }
}

public class Day15
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day15 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        GeneratorUtil generatorUtil = new GeneratorUtil();
        generatorUtil.readFromFile(fileName);
        generatorUtil.init();
        int solution1 = generatorUtil.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = generatorUtil.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
