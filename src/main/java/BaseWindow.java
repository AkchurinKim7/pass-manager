import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

public class BaseWindow {

    public static JFrame jf = new JFrame("StorPass");
    public static JButton addEntry;
    public static JButton deleteEntry;
    public static JButton masterPass;
    public static JTable dbTable = new JTable();
    public static DefaultTableModel dtm;
    public static String filePath = "passwords.txt";

    public static void baseWindow() throws Exception {

        mainPanel();

        addEntry.addActionListener(e -> addNewEntry());

        deleteEntry.addActionListener(e -> deleteThisEntry());

        masterPass.addActionListener(e -> changeMasterPass());

    }

    /// Создание главного окна
    public static void mainPanel() throws Exception {
        jf.setSize(800, 365);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        jf.add(panel);

        /// Создание кнопок
        addEntry = new JButton("Add New Entry");
        addEntry.setBounds(20, 20, 150, 40);
        panel.add(addEntry);

        deleteEntry = new JButton("Delete Entry");
        deleteEntry.setBounds(20, 75, 150, 40);
        panel.add(deleteEntry);

        masterPass = new JButton("Change MasterPass");
        masterPass.setBounds(20, 279, 150, 40);
        panel.add(masterPass);

        buildTable(panel);
    }


    /// Добавление новой записи
    public static void addNewEntry() {
        JFrame jf = new JFrame("Add New Entry");
        jf.setSize(370, 190);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(null);
        jf.getContentPane().add(addPanel);

        JLabel resLabel = new JLabel("Resource:");
        resLabel.setBounds(40, -70, 200, 200);
        addPanel.add(resLabel);
        JTextField resource = new JTextField(15);
        resource.setBounds(130, 20, 200, 20);
        addPanel.add(resource);
        ///Ограничение на ввод
        resource.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 48 || kchr > 122 || (kchr > 90 && kchr < 97)) {
                    ke.consume();
                }

            }
        });

        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setBounds(40, -40, 200, 200);
        addPanel.add(loginLabel);
        JTextField login = new JTextField(15);
        login.setBounds(130, 50, 200, 20);
        addPanel.add(login);
        ///Ограничение на ввод
        login.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 48 || kchr > 122 || (kchr > 90 && kchr < 97)) {
                    ke.consume();
                }

            }
        });

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, -10, 200, 200);
        addPanel.add(passwordLabel);
        JTextField password = new JTextField(15);
        password.setBounds(130, 80, 200, 20);
        addPanel.add(password);
        ///Ограничение на ввод
        password.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 48 || kchr > 122 || (kchr > 90 && kchr < 97)) {
                    ke.consume();
                }

            }
        });

        JButton addButton = new JButton("Add");
        addButton.setBounds(130, 115, 100, 30);
        addPanel.add(addButton);

        /// Добавление строчки в файл
        addButton.addActionListener(e -> {
            jf.setVisible(false);
            if (!(password.getText().equals("")) && (!login.getText().equals("")) && (!resource.getText().equals(""))) {
                writeString(resource.getText() + " " + login.getText() + " "
                        + password.getText() + System.getProperty("line.separator"));
                String[] newRow = {resource.getText(), login.getText(), password.getText()};
                dtm.addRow(newRow);
            }
        });
    }

    ///запись в файл
    public static void writeString(String string){
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            bufferWriter.write(string + System.getProperty("line.separator"));
            bufferWriter.close();
        } catch (IOException e1) {
            System.out.println(e1);
        }

    }


    /// Удаление выбранной записи
    public static void deleteThisEntry() {
        int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to " +
                "delete?", "Delete Entry", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {
            deleteEntry();
        }
    }

    public static void deleteEntry() {

        int sr = dbTable.getSelectedRow();
        System.out.println(sr);

        if (sr != -1) {
            List<String> entry = new ArrayList<>();

            try (Scanner scan = new Scanner(new File(filePath))) {
                while (scan.hasNextLine()) {
                    entry.add(scan.nextLine());
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            entry.remove(sr);
            dtm.removeRow(sr);
            dbTable.setModel(dtm);

            Writer writer = null;
            try {
                writer = new FileWriter(filePath, false);
                for (String line : entry) {
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
            } catch (Exception e2) {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } else {
            JOptionPane pane = new JOptionPane();
            JOptionPane.showMessageDialog(pane, "Please, select an entry to delete");
            pane.setSize(100, 100);
            pane.setVisible(false);
        }
    }


    /// Смена МастерПароля
    public static void changeMasterPass() {
        JFrame jf = new JFrame("Change MasterPass");
        jf.setSize(340, 130);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel changePanel = new JPanel();
        changePanel.setLayout(null);
        jf.getContentPane().add(changePanel);

        JLabel resLabel = new JLabel("Enter Old MasterPass:");
        resLabel.setBounds(40, -70, 200, 200);
        changePanel.add(resLabel);

        JTextField password = new JTextField(15);
        password.setBounds(180, 20, 140, 20);
        changePanel.add(password);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(140, 55, 50, 30);
        changePanel.add(okButton);

        okButton.addActionListener(e1 -> {
            if (Verify.verify(password.getText())) {
                jf.setVisible(false);
                enterNewMasterPass();
            } else {
                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog(pane, "MasterPass invalid");
                pane.setSize(100, 100);
                pane.setVisible(false);
            }
        });
    }


    /// Ввод нового МастерПароля
    public static void enterNewMasterPass() {
        JFrame jf = new JFrame("Change MasterPass");
        jf.setSize(340, 130);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel changePanel1 = new JPanel();
        changePanel1.setLayout(null);
        jf.getContentPane().add(changePanel1);

        JLabel resLabel = new JLabel("Enter new MasterPass:");
        resLabel.setBounds(40, -70, 200, 200);
        changePanel1.add(resLabel);

        JTextField newPass = new JTextField(15);
        newPass.setBounds(180, 20, 140, 20);
        changePanel1.add(newPass);
        ///Ограничение на ввод
        newPass.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                char kchr = ke.getKeyChar();
                if (kchr < 48 || kchr > 122 || (kchr > 90 && kchr < 97)) {
                    ke.consume();
                }

            }
        });

        JButton okButton = new JButton("Save");
        okButton.setBounds(120, 55, 100, 30);
        changePanel1.add(okButton);

        okButton.addActionListener(e -> {
            if (Verify.verify(newPass.getText())) {
                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog(pane, "The new MasterPass mustn`t " +
                        "be the same as the old MasterPass");
                pane.setSize(100, 100);
                pane.setVisible(false);
                newPass.setText("");
            } else {
                jf.setVisible(false);

                File file = new File("storpass.txt");
                try {
                    new Scanner(file);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(file);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                assert writer != null;
                writer.print(Verify.getHash(newPass.getText()));
                writer.close();

                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog(pane, "MasterPass changed successfully");
                pane.setSize(100, 100);
                pane.setVisible(false);
            }
        });
    }


    /// Создание таблицы
    public static void buildTable(JPanel panel) throws Exception {
        JScrollPane pane = new JScrollPane(dbTable);
        pane.setBounds(190, 20, 585, 300);

        panel.add(pane);

        Vector<String> header = new Vector<>();

        header.add("Resource");
        header.add("Login");
        header.add("Password");

        dtm = (DefaultTableModel) dbTable.getModel();
        dtm.setColumnIdentifiers(header);

        ///заполнение таблицы из файла
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            /// чтение построчно
            String stringConfig;
            while ((stringConfig = br.readLine()) != null) {
                dtm.addRow(ReadWriteModule.parsString(stringConfig));
            }
        }
    }

}



//// Не реализована в полной мере потому что не нашли решения для ограничение на ввод в JTable
    /* Сохранение изменений записи
    public static void saveChangingEntry() {
        int selection = JOptionPane.showConfirmDialog(null, "Save changes?",
                "Saving changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (selection == JOptionPane.YES_OPTION) {

            List<String[]> data = new ArrayList<>();

            for (int i = 0; i < dbTable.getRowCount(); i++) {
                String resource = dbTable.getModel().getValueAt(i, 0).toString();
                String login = dbTable.getModel().getValueAt(i, 1).toString();
                String password = dbTable.getModel().getValueAt(i, 2).toString();
               data.add(new String[]{encryption(resource).toString() + " " + encryption(login).toString() + " "
                        + encryption(password).toString()});
            }

            Writer writer = null;
            try {
                writer = new FileWriter(filePath, false);
                for (String[] line : data) {
                    writer.write(line[0] + " " + line[1] + " " + line[2]);
                    writer.write(System.getProperty("line.separator"));
                }
                writer.flush();
            } catch (Exception e2) {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
*/