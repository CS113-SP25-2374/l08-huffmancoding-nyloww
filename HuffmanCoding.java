import java.util.*;

public class HuffmanCoding implements HuffmanInterface {
    private Node root;
    private Map<Character, String> huffmanCodes;

    // Internal Node class
    private static class Node implements Comparable<Node> {
        char character;
        int frequency;
        Node left, right;

        Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        Node(Node left, Node right) {
            this.character = '\0'; // Internal node
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency;
        }
    }

    @Override
    public String encode(String message) {
        // Step 1: Count character frequencies
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : message.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Build priority queue
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        // Step 3: Build Huffman tree
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            queue.add(new Node(left, right));
        }
        root = queue.poll(); // Root of tree

        // Step 4: Generate codes from tree
        huffmanCodes = new HashMap<>();
        buildCodeMap(root, "");

        // Step 5: Encode the message
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toCharArray()) {
            encoded.append(huffmanCodes.get(c));
        }

        return encoded.toString();
    }

    @Override
    public String decode(String codedMessage) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (char bit : codedMessage.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.isLeaf()) {
                decoded.append(current.character);
                current = root; // reset
            }
        }
        return decoded.toString();
    }

    // Recursive method to generate Huffman codes
    private void buildCodeMap(Node node, String code) {
        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
            return;
        }
        buildCodeMap(node.left, code + "0");
        buildCodeMap(node.right, code + "1");
    }
}
