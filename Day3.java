/*
Advent of Code 2017
Day 3
Author: koodikuningas
*/

class SpiralMemory
{
	private int[][] grid;
	private int accessPointX;
	private int accessPointY;
	private int MAX_VALUE = 10000;
	private int RIGHT = 0;
	private int DOWN = 1;
	private int LEFT = 2;
	private int UP = 3;
	private int[] directions;
	private int currentDirectionIndex;
	private int currentDirection;
	private int currentX;
	private int currentY;

	public int solve(int n)
	{
		grid = new int[MAX_VALUE][MAX_VALUE];
		for (int i = 0; i < MAX_VALUE; i++)
		{
			for (int j = 0; j < MAX_VALUE; j++)
			{
				grid[i][j] = 0;
			}
		}
		accessPointX = MAX_VALUE / 2;
		accessPointY = MAX_VALUE / 2;
		grid[accessPointX][accessPointY] = 1;

		directions = new int[4];
		directions[0] = RIGHT;
		directions[1] = DOWN;
		directions[2] = LEFT;
		directions[3] = UP;

		currentDirectionIndex = 0;
		currentDirection = directions[currentDirectionIndex];

		int i = 1;
		currentX = accessPointX;
		currentY = accessPointY;
		while (i < n)
		{
			i++;
			currentX = getNextX(currentDirection);
			currentY = getNextY(currentDirection);
			grid[currentX][currentY] = i;

			int nextDirectionIndex = getNextDirectionIndex(currentDirectionIndex);
			int nextDirection = directions[nextDirectionIndex];
			int nextX = getNextX(nextDirection);
			int nextY = getNextY(nextDirection);
			if (grid[nextX][nextY] == 0)
			{
				currentDirectionIndex = nextDirectionIndex;
				currentDirection = directions[currentDirectionIndex];
			}
			
		}
		System.out.println("X coordinate is " + currentX);
		System.out.println("Y coordinate is " + currentY);
		int solution = Math.abs(currentX - accessPointX) + Math.abs(currentY - accessPointY);
		return solution;
	}

	private int getNextDirectionIndex(int index)
	{
		index++;
		if (index == directions.length)
			index = 0;
		return index;
	}

	private int getNextX(int direction)
	{
		if (direction == RIGHT)
			return currentX + 1;
		if (direction == LEFT)
			return currentX - 1;
		return currentX;
	}

	private int getNextY(int direction)
	{
		if (direction == UP)
			return currentY - 1;
		if (direction == DOWN)
			return currentY + 1;
		return currentY;
	}
}

public class Day3
{
	public static void main(String[] args)
	{
		int n = 368078;
		SpiralMemory spiralMemory = new SpiralMemory();
		int solution = spiralMemory.solve(n);
		System.out.println("Solution is " + solution);
	}
}
