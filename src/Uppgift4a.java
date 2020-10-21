import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Uppgift4a extends JFrame implements ActionListener {

    JPanel buttonPanel = new JPanel();
    JLabel label = new JLabel("Filnamn:");
    JTextField searchField = new JTextField(15);
    JButton open = new JButton("Öppna");
    JButton save = new JButton("Spara");
    JButton write = new JButton("Skriv ut");
    JButton exit = new JButton("Avsluta");
    JTextArea textArea = new JTextArea(10,50);
    JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    public Uppgift4a(){
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(label);
        buttonPanel.add(searchField);
        buttonPanel.add(open);
        buttonPanel.add(save);
        buttonPanel.add(write);
        buttonPanel.add(exit);

        searchField.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        write.addActionListener(this);
        exit.addActionListener(this);

        add(buttonPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void readFile(String filnamn) {
        try (BufferedReader buff = new BufferedReader(new FileReader(filnamn))) {
            textArea.read(buff, null);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(String filnamn) {
        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(filnamn)))) {
            textArea.write(printer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == open || e.getSource() == searchField){
            readFile(searchField.getText());
        }

        else if(e.getSource() == save){
            saveFile(searchField.getText());
        }

        else if(e.getSource() == write){
            try {
                textArea.print();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        else if(e.getSource() == exit){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Uppgift4a start = new Uppgift4a();
    }
}

/*
•Använd JLabel , JTextField , JButtons och JTextArea
•När användaren klickar på ”Öppna” ska innehållet i filen med det
filnamn som angavs i textfältet visas i textarean
•När användare klickar ”Spara” ska innehållet i textarean sparas till det
filnamn som finns angivet i textfältet.
•När användaren klickar ”Skriv ut” ska textareans inbyggda print
funktion anropas.
•När användaren klickar ”Avsluta” ska programmet stängas ner.
 */