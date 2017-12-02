/*
Advent of Code 2017
Day 2
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class SpreadSheet
{
	private List<String> lines;

	void readFromFile(String fileName)
	{
		try 
		{
			Path path = Paths.get(".", fileName);
			lines = Files.readAllLines(path);
		}
		catch (Exception e) {}
	}

	int solve()
	{
		int sum = 0;
		for (String line : lines)		
		{
			String[] parts = line.split("\t");
			int max = 0;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < parts.length; i++)
			{
				int number = Integer.parseInt(parts[i]);
				if (number > max)
					max = number;
				if (number < min)
					min = number;
			}
			int difference = max - min;
			sum += difference;

		}
		return sum;
	}
}

class Day2
{
	public static void main(String[] args)
	{
		if (args.length < 1) 
		{
			System.out.println("Usage: java Day2 fileName");
			System.exit(0);
		}

		String fileName = args[0]; 
		SpreadSheet spreadSheet = new SpreadSheet();
		spreadSheet.readFromFile(fileName);
		int solution = spreadSheet.solve();
		System.out.println("Solution is " + solution);		
	}
}
