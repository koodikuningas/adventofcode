/*
Advent of Code 2017
Day 7
Author: koodikuningas
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class Node implements Comparable<Node>
{
    private String name;
    private int weight;
    private List<Node> children = new ArrayList<Node>();

    public Node(String n, int w, List<Node> c)
    {
        name = n;
        weight = w;
        children = c;
    }

    public String getName()
    {
        return name;
    }
    
    public void addChild(Node node)
    {
        for (int i = 0; i < children.size(); i++)
        {
            Node child = children.get(i);
            if (child.getName().equals(node.getName()))
            {
                if (node.size() > child.size())
                {
                    children.set(i, node);
                }               
            }
            else
            {
                child.addChild(node);
            }
        }
    }

    public int size()
    {
        int size = 1;
        for (Node child : children)
        {
            size += child.size();
        }
        return size;
    }

    public String toString()
    {
        String childStr = "";
        for (Node child : children)
        {
            childStr += child + " ";
        }
        return name + " " + weight + " " + childStr;
    }

    public int compareTo(Node another)
    {
        return this.size() - another.size();
    }
}

class Tree
{
    private List<String> lines;
    private List<Node> nodes = new ArrayList<Node>();

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
        Map<String, Integer> weights = new HashMap<String, Integer>();
        for (String line : lines)
        {
            String left = line;
            String right = "";
            if (line.contains("->"))
            {
                String[] parts = line.split("->");
                left = parts[0].trim();
            }

            String[] properties = left.split(" ");
            String name = properties[0];
            int weight = Integer.parseInt(properties[1].replace("(","").replace(")",""));
            weights.put(name, weight);
        }

        for (String line : lines)
        {
            if (line.contains("->"))
            {
                String[] parts = line.split("->");
                String right = parts[1].trim();
                List<Node> children = new ArrayList<Node>();
                if (!right.equals(""))
                {
                    String[] childNodes = right.split(",");
                    List<String> childStr = new ArrayList<String>();
                    for (int i = 0; i < childNodes.length; i++)
                    {
                        childStr.add(childNodes[i].trim());
                    }

                    for (String child : childStr)
                    {
                        children.add(new Node(child, weights.get(child), new ArrayList<Node>()));
                    }
                }
                String[] properties = line.split(" ");
                String name = properties[0];
                nodes.add(new Node(name, weights.get(name), children));
            }
        }

        while (nodes.size() > 1)
        {
            Collections.sort(nodes);
            
            Node first = nodes.remove(0);
            for (Node node : nodes)
            {
                node.addChild(first);
            }
        }
    }

    public String solvePart1()
    {
        return nodes.get(0).getName();
    }

    public int solvePart2()
    {
        return 0;
    }
}

public class Day7
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Usage: java Day7 fileName");
            System.exit(0);
        }

        String fileName = args[0];      
        Tree tree = new Tree();
        tree.readFromFile(fileName);
        tree.init();
        String solution1 = tree.solvePart1();
        System.out.println("Part 1 solution is " + solution1);
        int solution2 = tree.solvePart2();
        System.out.println("Part 2 solution is " + solution2);
    }
}
