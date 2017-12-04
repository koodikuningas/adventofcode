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

    public int solvePart1(int n)
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
        directions[1] = UP;
        directions[2] = LEFT;
        directions[3] = DOWN;

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

    public int solvePart2(int n)
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
        directions[1] = UP;
        directions[2] = LEFT;
        directions[3] = DOWN;

        currentDirectionIndex = 0;
        currentDirection = directions[currentDirectionIndex];

        int i = 1;
        currentX = accessPointX;
        currentY = accessPointY;
        while (i < n)
        {
            currentX = getNextX(currentDirection);
            currentY = getNextY(currentDirection);
            i = getAdjacentSum();
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
        return i;
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

    private int getAdjacentSum()
    {
        int sum =
            grid[currentX-1][currentY-1] +
            grid[currentX][currentY-1] +
            grid[currentX+1][currentY-1] +
            grid[currentX+1][currentY] +
            grid[currentX+1][currentY+1] +
            grid[currentX][currentY+1] +
            grid[currentX-1][currentY+1] +
            grid[currentX-1][currentY];
        return sum;
    }
}

public class Day3
{
    public static void main(String[] args)
    {
        int n = 368078;
        SpiralMemory spiralMemory = new SpiralMemory();
        int solution1 = spiralMemory.solvePart1(n);
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = spiralMemory.solvePart2(n);
        System.out.println("Part 2 solution is " + solution2);
    }
}
