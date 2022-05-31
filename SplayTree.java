/*
	Kelompok 1 :
	- Steve Octodinata 535180054
	- Richard Karsten 535180093
	- Refiando 535180098
	- Jeremie Jessma 535180099
*/
import java.util.Scanner;

public class SplayTree {

	private Node root;
	private String string;

	public class Node {
		public int data;
		public Node left, right;

		public Node(int element) {
			this.data = element;
			left = null;
			right = null;
		}
	}

	public SplayTree() {
		root = null;
	}
	
	//fungsi untuk rotasi kiri.
	private Node leftRotate(Node node) {
		Node rotate = node.left;
		node.left = rotate.right;
		rotate.right = node;
		return rotate;
	}

	//fungsi untuk rotasi kanan.
	private Node rightRotate(Node node) {
		Node rotate = node.right;
		node.right = rotate.left;
		rotate.left = node;
		return rotate;
	}

	// Fungsi Rotasi dan operasi zig - zig, zig-zag Node Pada Splay Tree
	private void newNode(int data) {
		boolean flag = true; // check if satisfed the condition or not.
		Node node = root;
		Node parent, grParent, ggParent; // Need pointer for the parent and the grandparent
		parent = null;
		grParent = null;
		ggParent = null;

		while (true) {
			if (node == null || data == node.data)
				break;
			else if (node.left != null && data < node.data) {
				//jika node merupakan right cild dari parent
				//maka akan di lakukan rotasi kiri.
				//zig
				if (data == node.left.data) {
					node = leftRotate(node);
				}
				// zig-zig
				else if (node.left.left != null && data == node.left.left.data) {
					grParent = node;
					parent = node.left;
					node = leftRotate(grParent);
					node = leftRotate(parent);
					flag = true;
				}
				// zig-zag
				else if (node.left.right != null && data == node.left.right.data) {
					grParent = node; //Untuk mendefinisikan grand parent menjadi node
					parent = node.left; //Untuk mendefinisikan parent menjadi node.left
					grParent.left = rightRotate(parent);
					node = leftRotate(grParent);
					flag = true;
				}

				else if (data < node.data) {
					ggParent = node; // point for the great-grandparent.
					node = node.left;
				}
			} else if (node.right != null && data > node.data) {
				//jika node merupakan left cild dari parent
				//maka akan di lakukan rotasi kanan.
				//zag
				if (data == node.right.data) {
					node = rightRotate(node);
				}
				// zag-zag
				else if (node.right.right != null && data == node.right.right.data) {
					grParent = node;
					parent = node.right;
					node = rightRotate(grParent);
					node = rightRotate(parent);
					flag = true;
				}
				// zig-zag
				else if (node.right.left != null && data == node.right.left.data) {
					grParent = node;
					parent = node.right;
					grParent.right = leftRotate(parent);
					node = rightRotate(grParent);
					flag = true;
				} else if (data > node.data) {
					ggParent = node;
					node = node.right;
				}
			}

			else if ((node.left == null && data < node.data)
					|| (node.right == null && data > node.data)) {
				data = node.data;
				node = root;
				ggParent = null;
			}

			// Link node and its all parent after zig-zig or zig-zag
			// set root to node.
			if (flag && ggParent != null) {
				if (node.data < ggParent.data)
					ggParent.left = node;
				else if (node.data > ggParent.data)
					ggParent.right = node;
				node = root;
				ggParent = null;
				flag = false;
			}
		}
		root = node;
	}

	//Fungsi untuk memeriksa apakah masih
	//ada atau tidak node lain.
	public boolean isNull() {
		return root == null;
	}

	// node yang akan di masukan
	public void insert(int key) {
		root = insert(key, root);
		newNode(key);
	}

	// Fungsi Memasukan Node Pada Splay Tree
	private Node insert(int key, Node node) {
		if (node == null)
			return new Node(key);
		else {
			if (key < node.data)
				node.left = insert(key, node.left);
			else if (key > node.data)
				node.right = insert(key, node.right);
			return node;
		}
	}

	//Fungsi untuk mendapatkan nilai root
	//dan di simpan pada variabel root.data
	public Integer getValue() {
		if (root != null) {
			return root.data;
		} else {
			return null;
		}
	}

	//Fungsi untuk mendapatkan root.
	public Node getRoot() {
		return root;
	}

	//Fungsi untuk menampilkan pohon preorder traversal
	//dari operasi insert, find, dan delete yang dilakukan.
	void preOrder(Node node) {
		if (node == null)
			return;
		string += node.data + ",";
		preOrder(node.left);
		preOrder(node.right);
	}

	// Fungsi Menghapus Node Pada Splay Tree
	public boolean delete(int key) {
		if (!isNull()) {
			newNode(key);
			if (root != null && root.data == key) {
				if (root.left != null) {
					Node tmp = root.right;
					root = root.left;
					newNode(key);
					root.right = tmp;
				} else
					root = root.right;
				return true;
			}
		}
		return false;
	}

	// Fungsi Mencari Node Pada Splay Tree
	public boolean find(int key) {
		if (isNull())
			return false;
			newNode(key);
		if (root.data == key) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		string = "";
		preOrder(getRoot());
		return string;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		SplayTree Node = new SplayTree();
		int NodeTotal;
		char ch;
        
        do    
        {
            System.out.println("\nSplay Tree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. delete ");
            System.out.println("3. search");
			System.out.println("Inputkan pilihan : ");
 
            int choice = scan.nextInt();            
            switch (choice)
            {
				case 1 :
				System.out.println("Jumlah node: ");
				NodeTotal = scan.nextInt();
				int i=0;
				do{
				System.out.println("Node setelah input: ");
                Node.insert( scan.nextInt() );                     
                i ++;
				}while (NodeTotal > i);
				System.out.println("\nPreorder Node: "+Node);
				break;
            case 2 : 
                System.out.println("Node yang akan di hapus: ");
				System.out.println("\nStatus Node yang akan di hapus: "+Node.delete(scan.nextInt()));
				System.out.println("\nPreorder Node setelah delete: "+Node);                     
                break;                          
            case 3 : 
                System.out.println("Node yang akan di cari: ");
				System.out.println("\nStatus Node yang akan di cari: "+Node.find(scan.nextInt()));
                System.out.println("Preorder Node yang di cari: "+ Node);
                break;  
            default : 
                System.out.println("Input salah \n ");
                break;   
            } 
 
            System.out.println("\nMau melanjutkan? (ketik y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');               
    }
}

/*
	(77) -> (77) -> (49)     ->         insert 26                      insert 42
	         /        \            (49) (zig right) (26)            (26)   (zig-zag    (42)
		  (49)       (77)           / \     ->        \               \     left)      /  \
		      (zig right)        (26) (77)           (49)   ->       (49)     ->     (26)(49)
								                       \              / \                  \
												      (77)          (42)(77)              (77)

	insert 71                                                 insert 56
	(42)                  (42)  (zig left)  (71)                (71)   (zig-zig   (71)  (zig     (56)
	/  \  (zig-zag left)  /  \              /  \                /  \     left)    /  \   right)   / \
  (26)(49)      ->      (26)(71)     ->   (42)(77)     ->     (42)(77)   ->     (56)(77)   ->   (49)(71)
		\                    / \           / \                 / \               /               /    \
	   (77)                (49)(77)      (26)(49)            (26)(49)          (49)            (42)  (77)
		 \                                                         \            /               /
		(71)                                                      (56)        (42)            (26)
																			   /
																			 (26)

	insert 46                                                         insert 88
        (56)  (zig-zag    (56)     (zig right)   (46)                   (46)                     (46)
	    / \     right)    /  \                   /  \                   /  \                     /  \
      (49)(71)    ->    (46) (71)     ->       (42) (56)     ->       (42) (56)       ->       (42) (56)
	   /    \           / \    \                /    / \               /    / \                 /    / \
	 (42)  (77)       (42)(49) (77)           (26) (49)(71)           (26) (49)(71)           (26) (49)(88) (zig-zig
	  / \              /                                 \                      \                       /   left)
	(26)(46)         (26)                               (77)                   (77)                   (77)
	                                                                             \                     /
																				(88)			     (71)

	                                                insert 32
	        (88)                                       (88)                                           (88)
	         /                                          /                                              /
	       (56)                                       (56)                                           (56)
	     /     \                                     /     \                                       /     \
       (46)    (77) (zig-zig left)                (46)    (77)     (zig-zig right)               (46)    (77)   
	    / \     /                                  / \     /                                     /  \     /
	 (42)(49) (71)                               (42)(49) (71)                                 (32)(49) (71)
	  /                                           /                                            / \
	(26)                                        (26)                                          (26) (42)
												  \
												 (32)


	(zig-zig right)		   (88)            (zig right)        (32)
                            /                                /    \
						  (32)						       (26)  (88)
						  /  \						              /
						(26) (46)						        (46)
						     /  \						        /  \
						   (42) (56)					     (42) (56)
						        /  \						      /  \
							 (49) (77)					        (49) (77)
							       /							      /
							     (71)							    (71)
*/