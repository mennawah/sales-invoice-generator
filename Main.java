import com.sun.jdi.IntegerType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;


class Main extends JFrame implements ActionListener {
    private JTable table;
    private String[] cols={"ID","date" ,"Customer","Total"};
    private String[][] data=readCSV();
    private JButton btn;
    private JButton btn2;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn)) {

            addInvoice();
            Main.this.dispose();
            try {
                main(new String[0]);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } // when i click on delete button "pop up message appears" and delete the selected invoice
        if (e.getSource().equals(btn2)) {
            JOptionPane.showMessageDialog(null,"Are you sure you want to Delete the selected invoice?","Egypt",JOptionPane.ERROR_MESSAGE);
            System.out.println(table.getSelectedRow());
            try {
                delInvoice(table.getSelectedRow());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            Main.this.dispose();
            try {
                main(new String[0]);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }




    public Main() throws IOException {
        super("invoices");

        table= new JTable(data,cols);

        add( new JScrollPane(table));
        setSize(400,500);
        setLocation(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        btn = new JButton("create new invoice");
        add(btn);
        btn.addActionListener(this);



        btn2 = new JButton("Delete invoice");
        add(btn2);
        btn2.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    public String[][] readCSV() throws IOException {


        Scanner scan = new Scanner(new File("C:\\Users\\mennatallahw\\Desktop\\invoices.csv"));
        ArrayList<String[]> records = new ArrayList<String[]>();
        String[] record = new String[4];
        while(scan.hasNext())
        {
            record = scan.nextLine().split(",");
            records.add(record);
        }

        for(String[] temp : records)
        {
            for(String temp1 : temp)
            {
                System.out.print(temp1 + " ");
            }
            System.out.print("\n");
        }




        //return new String[][]{{"122", "menna", "Excellent", ""}};
        return records.toArray(new String[0][]);

    }

    public static void addInvoice() {
        try
        {
            String filename= "C:\\Users\\mennatallahw\\Desktop\\invoices.csv";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write("##,dd-mm-yyyy,customer,amount\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }


    public void delInvoice(int row) throws FileNotFoundException {

        Scanner scan = new Scanner(new File("C:\\Users\\mennatallahw\\Desktop\\invoices.csv"));
        ArrayList<String> records = new ArrayList<String>();
        while(scan.hasNext())
        {
            records.add(scan.nextLine());
        }
        records.remove(row);

        try
        {
            String filename= "C:\\Users\\mennatallahw\\Desktop\\invoices.csv";
            FileWriter fw = new FileWriter(filename,false); //the true will append the new data

            int count = 1;
            String str = records.get(0) + "\n";
            while (records.size() > count) {
                str = str + records.get(count) + "\n";
                count++;
            }
            fw.write(str);
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }



    }





    public static void main(String[] args) throws IOException {
        new Main().setVisible(true);
    }
}
