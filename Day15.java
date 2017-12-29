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
    private int criteria = 0;

    public Generator(long s, long f, int c)
    {
        start = s;
        factor = f;
        criteria = c;
    }

    public long next()
    {
        long x = start * factor;
        long y = x % DIVIDER;
        start = y;
        return y;
    }

    public long nextValid()
    {
        long next = 0;
        while (true)
        {
            next = next();
            if (next % criteria == 0)
                break;
        }

        return next;
    }
}

class GeneratorUtil
{   
    private List<String> lines;
    private int GEN_A_FACTOR = 16807;
    private int GEN_B_FACTOR = 48271;
    private int GEN_A_CRITERIA = 4;
    private int GEN_B_CRITERIA = 8;

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

    public int solvePart1()
    {
        int rounds = 40000000;
        int matches = 0;

        int generatorAStart = parseGeneratorStart(lines.get(0));
        int generatorBStart = parseGeneratorStart(lines.get(1));

        Generator generatorA = new Generator(generatorAStart, GEN_A_FACTOR, GEN_A_CRITERIA);
        Generator generatorB = new Generator(generatorBStart, GEN_B_FACTOR, GEN_B_CRITERIA);

        for (int i = 0; i < rounds; i++)
        {
            long a = generatorA.next();
            long b = generatorB.next();
            if (getBinaryPart(a).equals(getBinaryPart(b)))
                matches++;
        }

        return matches;
    }
    
    public int solvePart2()
    {
        int rounds = 5000000;
        int matches = 0;

        int generatorAStart = parseGeneratorStart(lines.get(0));
        int generatorBStart = parseGeneratorStart(lines.get(1));

        Generator generatorA = new Generator(generatorAStart, GEN_A_FACTOR, GEN_A_CRITERIA);
        Generator generatorB = new Generator(generatorBStart, GEN_B_FACTOR, GEN_B_CRITERIA);

        for (int i = 0; i < rounds; i++)
        {
            long a = generatorA.nextValid();
            long b = generatorB.nextValid();
            if (getBinaryPart(a).equals(getBinaryPart(b)))
                matches++;
        }

        return matches;
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
        int solution1 = generatorUtil.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = generatorUtil.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
