
package ui;

public class Main{

    private Menu menu;

    public Main(){
        this.menu = new Menu();
    }
    
    public Menu getMenu() {
        return menu;
    }
    
    public static void main( String[] args ){
        Main main = new Main();
        do{
            main.getMenu().showMenu();
            main.getMenu().readOption();
            main.getMenu().executeOption();
        }while(main.getMenu().isAlive());
    }
}