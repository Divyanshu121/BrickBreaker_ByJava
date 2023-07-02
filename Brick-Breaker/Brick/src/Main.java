import javax.swing.*;//for creating a frame
public class Main {
    public static void main (String[]args){
        JFrame obj= new JFrame();
        Gameplay gameplay=new Gameplay();//Object
         obj.setBounds(10,10,700,600);
         obj.setTitle("Breakout Ball");
         obj.setResizable(false);//user can't change the size of Jframe.if set to false
         obj.setVisible(true);//because the frame is defaultly at hidden.
         //below line is for terminate the program when exit by user.
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         obj.add(gameplay);
    }
}
