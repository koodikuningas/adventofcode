/*
Advent of Code 2017
Day 4
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PassPhrase
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

    private boolean isValid(String line)
    {
	String[] parts = line.split(" ");
	for (int i = 0; i < parts.length; i++)
	{
	    String part = parts[i];
	    if (!part.equals(part.toLowerCase()))
		return false;

	    for (int j = 0; j < parts.length; j++)
	    {
		String secondPart = parts[j];
		if (i != j && part.equals(secondPart))
		    return false;
	    }
	}
	return true;
    }

    public int solve()
    {
	int sum = 0;
	for (String line : lines)
	{
	    if (isValid(line))
		sum++;
	}
	return sum;
    }
}

class Day4
{
    public static void main(String[] args)
    {
	if (args.length < 1)
	{
	    System.out.println("Usage: java Day4 fileName");
	    System.exit(0);
	}

	String fileName = args[0];
	PassPhrase passPhrase = new PassPhrase();
	passPhrase.readFromFile(fileName);
	int solution = passPhrase.solve();
	System.out.println("Solution is " + solution);
    }
}
