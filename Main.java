public class Main {
    public static void main(String[] args) {
        HuffmanInterface huffman = new HuffmanCoding();

        String message = "Katanu Mwendwa";

        String encoded = huffman.encode(message);
        System.out.println("Encoded: " + encoded);

        String decoded = huffman.decode(encoded);
        System.out.println("Decoded: " + decoded);

        System.out.println("Original size (bits): " + (message.length() * 16));
        System.out.println("Compressed size (bits): " + encoded.length());
    }
}