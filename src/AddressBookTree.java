public class AddressBookTree<T extends Comparable, U> {
    private Node root;
    public AddressBookTree(){root = new Node();}
    public void insert(T nameInput, U officeInput) {
        Node newKidOnTheBlock = new Node(nameInput, officeInput, null, null, null, 0);
        Node posRoot = this.root;
        if(posRoot == null){
            root = newKidOnTheBlock;
            return;
        }
        while(!posRoot.hasNoKids()){
            if((posRoot.getName().compareTo(newKidOnTheBlock.getName()))> 0){
                if(posRoot.getRightkid() != null)
                    posRoot = posRoot.getRightkid();
                else
                    break;
            }else {
                if(posRoot.getLeftkid() != null)
                    posRoot = posRoot.getLeftkid();
                else
                    break;
            }
        }
        newKidOnTheBlock.setParent(posRoot);
        if((posRoot.getName().compareTo(newKidOnTheBlock.getName()))> 0 ){
            posRoot.setRightkid(newKidOnTheBlock);
        }else {
            posRoot.setLeftkid(newKidOnTheBlock);
        }
        newKidOnTheBlock.setColor(1);
        insert_fix(newKidOnTheBlock);
    }
    public void insert_fix(Node node){
        while(node.getParent().getColor() == 1){
            if((node.getParent().getName().compareTo(node.getParent().getParent().getLeftkid().getName()))== 0){
                Node y = node.getParent().getParent().getRightkid();
                if(y.getColor() == 1){
                    node.getParent().setColor(0);
                    y.setColor(0);
                    node.getParent().getParent().setColor(1);
                    node = node.getParent().getParent();
                }else{
                    if((node.getParent().getRightkid().getName().compareTo(node.getName()))== 0) {
                        node = node.getParent();
                        rotationLeft(node);
                    }
                    node.getParent().setColor(0);
                    node.getParent().getParent().setColor(1);
                    rotationRight(node.getParent().getParent());
                }
            }else{
                Node y = node.getParent().getParent().getLeftkid();
                if(y.getColor() == 1){
                    node.getParent().setColor(0);
                    y.setColor(0);
                    node.getParent().getParent().setColor(1);
                    node = node.getParent().getParent();
                }else{
                    if(node.getName() == node.getParent().getLeftkid()) {
                        node = node.getParent();
                        rotationRight(node);
                    }
                    node.getParent().setColor(0);
                    node.getParent().getParent().setColor(1);
                    rotationLeft(node.getParent().getParent());
                }
            }
        }
        this.root.setColor(0);
    }
    public void delete(T nameInput){

    }
    public void delete_fix(Node<T,U> node){

    }
    public void rotationLeft(Node<T,U> node){

    }
    public void rotationRight(Node<T,U> node){

    }
    public void rb_transplant(Node<T,U> nodeOne, Node<T,U> nodeTwo){
        if(nodeOne == null){
            this.root = nodeTwo;
        }else if(nodeOne.getName().compareTo(nodeOne.getParent().getLeftkid().getName()) == 0){
            nodeOne.getParent().setLeftkid(nodeTwo);
        }else{
            nodeOne.getParent().setRightkid(nodeTwo);
        }
        nodeTwo.setParent(nodeOne.getParent());
    }
    public void display(){

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






//Node class
class Node<T extends Comparable, U> {
    //variable names... pretty self-explanatory
    private T name;
    private U office;
    private Node parent;
    private Node leftkid;
    private Node rightkid;
    private int color;
    //constructors
    public Node(){
        name = null;
        office = null;
        parent = null;
        leftkid = null;
        rightkid = null;
        color = 0;
    }
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
    public boolean hasNoKids(){
        if(this.leftkid == null && this.rightkid == null){
            return true;
        }
        return false;
    }
    public String checkKids(){
        if(this.leftkid == null)
            return "left";
        return "right";
    }
    public boolean missingKids(){
        if(this.leftkid == null || this.rightkid == null){
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
