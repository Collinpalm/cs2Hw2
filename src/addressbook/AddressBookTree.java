package addressbook;

public class AddressBookTree<T extends Comparable<T>, U> {
    //saves the root of the tree
    //cannot be accessed directly to avoid
    //idiots trying to do something stupid
    private Node root;
    //constructor that creates a null node as the root
    public AddressBookTree(){root = new Node();}
    public Node getRoot(){
        return this.root;
    }
    //insert method from the pseudo code from professor Steinburg
    //if it doesnt work its his fault
    public void insert(T nameInput, U officeInput) {
        Node z = new Node(nameInput, officeInput, null, null, null, 0);
            Node x = root;
            Node y = null;
            while (x != null) {
                y = x;
                if (x.getName() != null || z.getName().compareTo(x.getName()) < 0) {
                    x = x.getRightkid();
                } else {
                    x = x.getLeftkid();
                }
            }
            z.setParent(y);
            if(z.getParent() != null) {
                T yname = (T) y.getName();
                T zname = (T) z.getName();
                if ((yname).compareTo(zname) < 0) {
                    y.setRightkid(z);
                } else {
                    y.setLeftkid(z);
                }
            }
            z.setLeftkid(null);
            z.setRightkid(null);
            z.setColor(1);
            if(z.getParent() != null || z.getParent().getParent() != null ||z.getParent().getParent().getRightkid() != null || z.getParent().getParent().getLeftkid() != null){}
                //insert_fix(z);


    }
    //fixing the insert, also from professor Steinburg's pseudo code
    //again, his fault if it doesnt work
    public void insert_fix(Node z){
        System.out.println(z.getParent().getColor());
       while(z.getParent().getColor() == 1){
           if(z.getParent().getName().compareTo(z.getParent().getParent().getLeftkid().getName()) ==0){
               Node y = z.getParent().getParent().getRightkid();
               System.out.println(y.getColor());
               if(y.getColor() == 1){
                   z.getParent().setColor(0);
                   y.setColor(0);
                   z.getParent().getParent().setColor(1);
                   z = z.getParent().getParent();
               }else{
                   if(z.getName().compareTo(z.getParent().getRightkid().getName()) == 0) {
                        z = z.getParent();
                        rotationLeft(z);
                   }
                   z.getParent().setColor(0);
                   z.getParent().getParent().setColor(1);
                   rotationRight(z.getParent().getParent());
               }
           }else{
               Node y = z.getParent().getParent().getLeftkid();
               if(y.getColor() == 1){
                   z.getParent().setColor(0);
                   y.setColor(0);
                   z.getParent().getParent().setColor(1);
                   z = z.getParent().getParent();
               }else{
                   if(z.getName().compareTo(z.getParent().getLeftkid().getName()) == 0) {
                       z = z.getParent();
                       rotationRight(z);
                   }
                   z.getParent().setColor(0);
                   z.getParent().getParent().setColor(1);
                   rotationLeft(z.getParent().getParent());
               }
           }
       }
    }
    //delete method also from the pseudocode
    //I think I understand the pseudocode, I could be very wrong
    public void deleteNode(T nameInput){
        Node z = this.root;
        while(z.getName().compareTo(nameInput) != 0 && (z.getLeftkid() != null || z.getRightkid() != null)){
            if(z.getName().compareTo(nameInput) > 0){
                z = z.getRightkid();
            }else{
                z = z.getLeftkid();
            }
        }
        Node y = z;
        Node x = null;
        int yOriginalColor = y.getColor();
        if(z.getLeftkid() == null){
            x = z.getRightkid();
            rb_transplant(z, z.getRightkid());
        }else if(z.getRightkid() == null){
            x = z.getLeftkid();
            rb_transplant(z, z.getLeftkid());
        }else{
            y = minTree(z.getRightkid());
            yOriginalColor = y.getColor();
            x = y.getRightkid();
            if(y.getParent().getName().compareTo(z.getName()) == 0){
                x.setParent(y);
            }else{
                rb_transplant(x, y);
                y.setRightkid(z.getRightkid());
                y.getRightkid().setParent(y);
            }
            rb_transplant(x, y);
            y.setLeftkid(z.getLeftkid());
            y.getLeftkid().setParent(y);
            y.setColor(0);
        }
        if(yOriginalColor == 0){
            delete_fix(x);
        }
    }
    //this is the method I think is being called by the delete methdod
    //It's recursive to find the minimum value in the tree
    private Node minTree(Node<T, U> node){
        if(node.getLeftkid() != null){
            return minTree(node.getLeftkid());
        }
        return node;
    }
    //fixing eh delete so its still a red/black tree
    //again pseudocode
    public void delete_fix(Node<T,U> x){
        Node w = null;
        while(x.getName().compareTo((T)this.root.getName())==0 && x.getColor() == 0){
            if(x.getName().compareTo((T)x.getParent().getLeftkid().getName()) == 0){
                w = x.getParent().getRightkid();
                if(w.getColor() == 1){
                    w.setColor(0);
                    x.getParent().setColor(1);
                    rotationLeft(x.getParent());
                }
                if(w.getLeftkid().getColor() == 0 && w.getRightkid().getColor() == 0){
                    w.setColor(1);
                    x = x.getParent();
                }else{
                    if(w.getRightkid().getColor() == 0) {
                        w.getLeftkid().setColor(0);
                        w.setColor(1);
                        rotationRight(w);
                        w = x.getParent().getRightkid();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(0);
                    w.getRightkid().setColor(0);
                    rotationLeft(x.getParent());
                    x = this.root;
                }
            }else{
                    w = x.getParent().getLeftkid();
                    if(w.getColor() == 1){
                        w.setColor(0);
                        x.getParent().setColor(1);
                        rotationRight(x.getParent());
                    }
                    if(w.getLeftkid().getColor() == 0 && w.getRightkid().getColor() == 0){
                        w.setColor(1);
                        x = x.getParent();
                    }else{
                        if(w.getLeftkid().getColor() == 0) {
                            w.getRightkid().setColor(0);
                            w.setColor(1);
                            rotationLeft(w);
                            w = x.getParent().getLeftkid();
                        }
                        w.setColor(x.getParent().getColor());
                        x.getParent().setColor(0);
                        w.getLeftkid().setColor(0);
                        rotationRight(x.getParent());
                        x = this.root;
                }
            }
        }
        x.setColor(0);
    }
    //left rotation of the tree
    //again pseudocode
    public void rotationLeft(Node<T,U> x){
        Node y = x;
        x.setRightkid(y.getLeftkid());
        if(y.getLeftkid() != null){
            y.getLeftkid().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent() == null){
            this.root = y;
        }else if(x.getName().compareTo((T)x.getParent().getLeftkid().getName()) == 0){
            x.getParent().setLeftkid(y);
        }else{
            x.getParent().setRightkid(y);
        }
        y.setLeftkid(x);
        x.setParent(y);
    }
    //right rotation of the tree
    //this is what I think is the correct mirror of the
    //pseudocode provided by professor Steinburg
    public void rotationRight(Node<T,U> x){
        Node y = x;
        x.setLeftkid(y.getRightkid());
        if(y.getRightkid() == null){
            y.getRightkid().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent() == null){
            this.root = y;
        }else if(x.getName().compareTo((T)x.getParent().getRightkid().getName()) == 0){
            x.getParent().setRightkid(y);
        }else{
            x.getParent().setLeftkid(y);
        }
        y.setRightkid(x);
        x.setParent(y);
    }
    //transplant method, this is to assist deletes
    public void rb_transplant(Node<T,U> nodeOne, Node<T,U> nodeTwo){
        if(nodeOne == null){
            this.root = nodeTwo;
        }else if(nodeOne.getName().compareTo((T)nodeOne.getParent().getLeftkid().getName()) == 0){
            nodeOne.getParent().setLeftkid(nodeTwo);
        }else{
            nodeOne.getParent().setRightkid(nodeTwo);
        }
        nodeTwo.setParent(nodeOne.getParent());
    }
    //calls the recusive function bc thats better
    public void display(){displayRecurse(this.root);}
    //method to recursivly print out the inorder traversal
    public void displayRecurse(Node <T, U> root){
        if(root == null){
            return;
        }
        displayRecurse(root.getLeftkid());
        System.out.println(root.getName() + " - " + root.getOffice());
        displayRecurse(root.getRightkid());
    }
    //recursively travers the tree and count the black nodes
    public int countBlack(Node<T,U> selectedRoot){
        if(selectedRoot == null){
            return 0;
        }
        if(selectedRoot.hasNoKids()){
            if(selectedRoot.getColor() == 0){
                return 1;
            }
                return 0;
        }
        if(selectedRoot.getLeftkid() != null){
            if(selectedRoot.getColor() == 0){
                return countBlack(selectedRoot.getLeftkid()) + 1;
            }
            return countBlack(selectedRoot.getLeftkid());
        }
        if(selectedRoot.getRightkid() != null){
            if(selectedRoot.getColor() == 0){
                return countBlack(selectedRoot.getLeftkid()) + 1;
            }
            return countBlack(selectedRoot.getLeftkid());
        }
        return 0;
    }
    //recursively traverse the tree and count the red nodes
    public int countRed(Node<T,U> selectedRoot){
        if(selectedRoot.hasNoKids()){
            if(selectedRoot.getColor() == 1){
                return 1;
            }
            return 0;
        }
        if(selectedRoot.getLeftkid() != null){
            if(selectedRoot.getColor() == 1){
                return countRed(selectedRoot.getLeftkid()) + 1;
            }
            return countRed(selectedRoot.getLeftkid());
        }
        if(selectedRoot.getRightkid() != null){
            if(selectedRoot.getColor() == 1){
                return countRed(selectedRoot.getLeftkid()) + 1;
            }
            return countRed(selectedRoot.getLeftkid());
        }
        return 0;
    }

}






//addressbook.Node class
class Node<T extends Comparable<T>, U> {
    //variable names... pretty self-explanatory
    private T name;
    private U office;
    private Node parent;
    private Node leftkid;
    private Node rightkid;
    private int color;
    //constructors
    //default
    public Node(){
        name = null;
        office = null;
        parent = null;
        leftkid = null;
        rightkid = null;
        color = 0;
    }
    //with variables
    public Node(T nameInput, U officeInput, Node parentInput, Node leftkidInput, Node rightkidInput, int colorInput){
        name = nameInput;
        office = officeInput;
        parent = parentInput;
        leftkid = leftkidInput;
        rightkid = rightkidInput;
        color = colorInput;
    }
    //class to check if the node has any children, not necessary but will cut down the
    //number of lines of code I will have to write, just quality of life things
    //RETURNS: boolean true if node has no children, otherwise false

    //OK im an idiot and this doesnt work for some reason that I dont entirely understand
    public boolean hasNoKids(){
        if(this.leftkid == null && this.rightkid == null){
            return true;
        }
        return false;
    }
    //getter methods
    public int getColor() {
        return color;
    }
    public T getName() {
        return name;
    }
    public U getOffice() {
        return office;
    }
    public Node getLeftkid() {
        return leftkid;
    }
    public Node getRightkid() {
        return rightkid;
    }
    public Node getParent() {
        return parent;
    }
    //setter methods
    public void setName(T name) {
        this.name = name;
    }
    public void setOffice(U office) {
        this.office = office;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public void setLeftkid(Node leftkid) {
        this.leftkid = leftkid;
    }
    public void setRightkid(Node rightkid) {
        this.rightkid = rightkid;
    }
}
