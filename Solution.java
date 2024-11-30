import java.util.*;

class Solution {
    public int[][] validArrangement(int[][] pairs) {
        // Adjacency list for the graph
        Map<Integer, Deque<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        
        // Build the graph and degree maps
        for (int[] pair : pairs) {
            int start = pair[0], end = pair[1];
            graph.computeIfAbsent(start, k -> new ArrayDeque<>()).add(end);
            outDegree.put(start, outDegree.getOrDefault(start, 0) + 1);
            inDegree.put(end, inDegree.getOrDefault(end, 0) + 1);
        }
        
        // Find the starting node
        int startNode = pairs[0][0]; // Default to the first pair's start
        for (int node : graph.keySet()) {
            if (outDegree.getOrDefault(node, 0) - inDegree.getOrDefault(node, 0) == 1) {
                startNode = node;
                break;
            }
        }
        
        // Perform Hierholzer's algorithm
        LinkedList<int[]> result = new LinkedList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(startNode);
        
        while (!stack.isEmpty()) {
            int node = stack.peek();
            if (graph.containsKey(node) && !graph.get(node).isEmpty()) {
                stack.push(graph.get(node).poll());
            } else {
                stack.pop();
                if (!stack.isEmpty()) {
                    result.addFirst(new int[] { stack.peek(), node });
                }
            }
        }
        
        // Convert result to 2D array
        return result.toArray(new int[result.size()][]);
    }
}
git 