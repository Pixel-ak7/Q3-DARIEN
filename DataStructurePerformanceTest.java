import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//Darien Golfin Tencio 2023152232 quiz 3 :D
class BinarySearchTree {
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    BinarySearchTree() {
        root = null;
    }

    void insert(int key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;
    }

    boolean search(Node root, int key) {
        if (root == null) {
            return false;
        }
        if (key == root.key) {
            return true;
        }
        return key < root.key ? search(root.left, key) : search(root.right, key);
    }

    boolean contains(int key) {
        return search(root, key);
    }
}

class LinkedListStructure {
    List<Integer> linkedList = new LinkedList<>();

    void insert(int value) {
        linkedList.add(value);
    }

    boolean contains(int value) {
        return linkedList.contains(value);
    }
}

public class DataStructurePerformanceTest {
    private static List<Integer> generateRandomData(int n) {
        List<Integer> data = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            data.add(random.nextInt());
        }
        return data;
    }

    // La siguiente función ejecutará la prueba y medirá el tiempo.
    private static long measurePerformance(Runnable action) {
        long startTime = System.nanoTime(); // tiemstamp inicial
        action.run();
        return System.nanoTime() - startTime;// tiemstamp final - timestapm inicial
    }

    public static void main(String[] args) {
        final int NUM_ELEMENTS = 10000;
        BinarySearchTree bst = new BinarySearchTree();
        LinkedListStructure list = new LinkedListStructure();
        List<Integer> elements = generateRandomData(NUM_ELEMENTS);
        Random random = new Random();

        // Insertando elementos
        for (int element : elements) {
            bst.insert(element);
            list.insert(element);
        }

        // Búsqueda y medición de tiempos
        long bstTime = 0;
        long listTime = 0;
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            int elementToSearch = elements.get(random.nextInt(NUM_ELEMENTS));

            bstTime += measurePerformance(() -> bst.contains(elementToSearch));
            listTime += measurePerformance(() -> list.contains(elementToSearch));
        }

        // Dividimos por NUM_ELEMENTS para obtener el tiempo promedio por búsqueda
        bstTime /= NUM_ELEMENTS;
        listTime /= NUM_ELEMENTS;

        // Resultados a CSV para Excel
        try (PrintWriter out = new PrintWriter("performance.csv")) {
            out.println("BST;LinkedList");
            out.printf("%d;%d\n", bstTime, listTime);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Pruebas completadas. Revisa performance.csv para los resultados.");
    }
}