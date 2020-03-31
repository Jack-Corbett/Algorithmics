import java.util.*;

public class BinarySearchTree<T> extends AbstractSet<T>
{
    private Node<T> root;
    private int size;

    static class Node<T>
    {
        private T element;
        private Node<T> left = null;
        private Node<T> right = null;
        private Node<T> parent;

        private Node(T element, Node<T> parent)
        {
            this.element = element;
            this.parent = parent;
        }
    }

    public BinarySearchTree()
    {
        root = null;
        size = 0;
    }

    public BinarySearchTree(BinarySearchTree<T> other)
    {
        root = null;
        size = 0;
        for (T element: other)
            add(element);
    }

    double averageDepth(Node node, int depth) {
        if (node == root && node != null) {
            return (averageDepth(node.left, depth+1) +
                    averageDepth(node.right, depth+1) + depth)/size();
        } else if (node != null) {
            return averageDepth(node.left, depth+1) +
                    averageDepth(node.right, depth+1) + depth;
        } else {
            return 0;
        }
    }

    Node<T> getRoot() {
        return root;
    }

    /*
    double averageDepth() {
        if (size() == 0) return 0;
        return sumDepth(root, 0) / size();
    }

    private double sumDepth(Node node, int depth)
    {
        if (node == null) return 0;
        return depth + sumDepth(node.left, depth + 1) + sumDepth(node.right, depth + 1);
    }
*/
    public int size()
    {
        return size;
    }

    public Iterator<T> iterator()
    {
        return new TreeIterator();
    }

    public boolean add(T element)
    {
        if (root == null) {
            root = new Node<T>(element, null);
            size++;
            return true;
        } else {
            Node temp = root;
            int comp;
            while (true) {
                comp = ((Comparable)(element)).compareTo(temp.element);
                if (comp == 0)
                    return false;
                if (comp<0) {
                    if (temp.left != null)
                        temp = temp.left;
                    else {
                        temp.left = new Node<T>(element, temp);
                        size++;
                        return true;
                    }
                } else {
                    if (temp.right != null)
                        temp = temp.right;
                    else {
                        temp.right = new Node<T>(element, temp);
                        size++;
                        return true;
                    }
                }
            }
        }
    }

    public boolean remove(Object obj)
    {
        Node<T> e = getNode(obj);
        if (e == null)
            return false;
        deleteNode(e);
        return true;
    }

    private Node<T> getNode(Object obj)
    {
        int comp;
        Node<T> e = root;
        while (e != null) {
            comp = ((Comparable)(obj)).compareTo(e.element);
            if (comp == 0)
                return e;
            else if (comp < 0)
                e = e.left;
            else
                e = e.right;
        }
        return null;
    }

    public T mapAdd(T obj)
    {
        if (root == null) {
            root = new Node<T>(obj, null);
            size++;
            return root.element;
        }
        int comp;
        Node<T> e = root;
        Node<T> p = null;
        boolean left = true;
        while (e != null) {
            p = e;
            comp = ((Comparable)(obj)).compareTo(e.element);
            if (comp == 0)
                return e.element;
            else if (comp < 0) {
                left = true;
                e = e.left;
            } else {
                e = e.right;
                left = false;
            }
        }
        e = new Node<T>(obj, p);
        if (left)
            p.left = e;
        else
            p.right = e;
        size++;
        return e.element;
    }

    public boolean contains(Object obj)
    {
        return getNode(obj) != null;
    }

    private Node<T> deleteNode(Node<T> p)
    {
        size--;
        if (p.left != null && p.right != null) {
            Node<T> s = successor(p);
            p.element = s.element;
            p = s;
        }

        Node<T> replacement;
        if (p.left != null)
            replacement = p.left;
        else
            replacement = p.right;

        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left = replacement;
            else
                p.parent.right = replacement;
        } else if (p.parent == null) {
            root = null;
        } else {
            if (p == p.parent.left)
                p.parent.left = null;
            else
                p.parent.right = null;
        }
        return p;
    }

    private Node<T> successor(Node<T> e)
    {
        if (e == null) {
            return null;
        } else if (e.right != null) {
            Node<T> p = e.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<T> p = e.parent;
            Node<T> child = e;
            while (p != null && child == p.right) {
                child = p;
                p = p.parent;
            }
            return p;
        }
    }

    private class TreeIterator implements Iterator<T>
    {
        private Node<T> lastReturned = null;
        private Node<T> next;

        private TreeIterator()
        {
            next = root;
            if (next != null)
                while (next.left != null)
                    next = next.left;
        }

        public boolean hasNext()
        {
            return next != null;
        }

        public T next()
        {
            if (next == null)
                throw new NoSuchElementException();
            lastReturned = next;
            next = successor(next);
            return lastReturned.element;
        }

        public void remove()
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            if (lastReturned.left != null && lastReturned.right != null)
                next = lastReturned;
            deleteNode(lastReturned);
            lastReturned = null;
        }
    }

}
