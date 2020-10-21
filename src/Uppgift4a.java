import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Uppgift4a extends JFrame implements ActionListener {

    private final String fileNameCachePath = "Files.txt";
    private JPanel p = new JPanel();
    Vector<String> fileCache = new Vector<>();
    private JComboBox<String> fileName;

    JPanel buttonPanel = new JPanel();
    JLabel label = new JLabel("Filnamn:");
    JButton open = new JButton("Öppna");
    JButton save = new JButton("Spara");
    JButton write = new JButton("Skriv ut");
    JButton exit = new JButton("Avsluta");
    JTextArea textArea = new JTextArea(10,60);
    JScrollPane scrollPane = new JScrollPane(textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    public Uppgift4a(){

        loadFileName();
        fileName = new JComboBox(fileCache);
        fileName.setSelectedIndex(-1);
        fileName.setEditable(true);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(label);
        buttonPanel.add(fileName);
        buttonPanel.add(open);
        buttonPanel.add(save);
        buttonPanel.add(write);
        buttonPanel.add(exit);

        fileName.addActionListener(this);
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

    private void loadFileName() {
        try (Scanner sc = new Scanner(new FileReader(fileNameCachePath))){
            while(sc.hasNextLine()){
                fileCache.add(sc.nextLine().trim());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile(String filnamn) {
        try (BufferedReader buff = new BufferedReader(new FileReader(filnamn))) {
            textArea.read(buff, null);
        }
        catch (IOException e) {
            System.out.println("Filen fanns inte på disken än.");
        }
    }

    private void saveFile(String filnamn) {
        try (PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(filnamn)))) {
            textArea.write(printer);
        }
        catch (IOException e) {
            System.out.println("Gick inte att spara filen.");
        }
    }

    private void saveFileNames() {
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(Paths.get(fileNameCachePath)));) {
            for (int i=0; i < 5 && i<fileName.getItemCount(); i++){
                w.println((String) fileName.getItemAt(i));
            }
        }
        catch (IOException e) {
            System.out.println("Gick inte att spara ner filerna till textfilen. " + fileNameCachePath.toString());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileName || e.getSource() == open){
            readFile((String)fileName.getSelectedItem());
            if(fileCache.indexOf((String)fileName.getSelectedItem()) == -1){
                fileName.addItem((String)fileName.getSelectedItem());
            }
        }

        else if(e.getSource() == save){
            saveFile((String)fileName.getSelectedItem());
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
            saveFileNames();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Uppgift4a start = new Uppgift4a();
    }
}

/*
Bygg ut din Texteditor.
• Gör textfältet, där man skriver in filnamn, till en editerbar combobox
• När en användare har öppnat en fil, spara sökvägen i comboboxens dropdown fält
• Låt de 5 senast öppnade fil sökvägarna visas i comboboxen
• För att vi inte ska tappa bort filsökvägarna mellan programkörningar
• Spara ner dem i en fil när programmet stänger ner
• Läst upp dem från fil när programmet startar upp
 */