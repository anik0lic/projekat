package raf.sk.projekat1.gui;

import lombok.Getter;
import lombok.Setter;
import raf.sk.projekat1.model.Appointment;
import raf.sk.projekat1.specification.ScheduleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class MainFrame extends JFrame {

    private ScheduleService ss;
    private JComboBox comboBox;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public MainFrame(Frame owner, ScheduleService ss) throws HeadlessException {
        this.ss = ss;

        setTitle("Schedule");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setSize((int) (1500), (int) (700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(owner);

        JPanel panelForTable = new JPanel();

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));

        table.setFont(new Font("Arial", Font.PLAIN, 12));

//
        table.setRowHeight(40);
        table.getColumnModel().setColumnMargin(20);


        scrollPane = new JScrollPane(table);

        model.addColumn("Termini");

        updateTable();

        panelForTable.add(scrollPane);



        JPanel menuRight = new JPanel();
        menuRight.setLayout(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(10, 10, 10, 10);


        JPanel menuLeft = new JPanel();
        menuLeft.setLayout(new GridBagLayout());
        GridBagConstraints gbc1Left = new GridBagConstraints();
        gbcRight.insets = new Insets(10, 10, 10, 10);


        JPanel menu = new JPanel();
        menu.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbcRight.insets = new Insets(20, 10, 20, 10);



        String[] boxItems = {"Add", "Remove", "Update", "Search", "Check"};
        comboBox = new JComboBox(boxItems);
        comboBox.setPreferredSize(new Dimension(150, 50));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        comboBox.setBackground(Color.CYAN);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(550, 100));

        JButton runBtn = new JButton("Run");
        runBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        runBtn.setAction(StartGui.getInstance().getActionManager().getRunAction());
        runBtn.setPreferredSize(new Dimension(100, 50));
        runBtn.setBackground(Color.CYAN);


        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        menuRight.add(comboBox, gbcRight);
        gbcRight.gridy = 1;
        gbcRight.fill = GridBagConstraints.NONE;
        menuRight.add(textArea, gbcRight);
        gbcRight.gridy = 2;
        gbcRight.anchor = GridBagConstraints.NORTHEAST;
        menuRight.add(runBtn, gbcRight);

        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.gridy = 0;
        gbc1.gridx = 0;
        menu.add(panelForTable,gbc1);
        gbc1.gridx = 1;
        menu.add(menuRight,gbc1);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(new Dimension(100, 50));
        JButton exportBtn = new JButton("Export");
        exportBtn.setPreferredSize(new Dimension(100, 50));

        exportBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        exitBtn.setBackground(Color.CYAN);
        exportBtn.setBackground(Color.CYAN);

        exportBtn.setAction(StartGui.getInstance().getActionManager().getExportAction());
        exitBtn.addActionListener (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gbc1.gridy = 1;
        gbc1.gridx = 2;
        gbc1.anchor = GridBagConstraints.SOUTHEAST;
        menu.add(exportBtn,gbc1);
        gbc1.gridy = 2;
        menu.add(exitBtn,gbc1);


        add(menu, BorderLayout.CENTER);
    }


    public void searchUpdate(List<String> appointments){

        if(appointments.isEmpty()){
            model.setRowCount(0);
            return;
        }

        model.getDataVector().removeAllElements();
        for(String s : appointments){
            model.addRow(new Object[]{s});
        }

    }

    public void updateTable(){
        model.getDataVector().removeAllElements();
        for(String s : ss.printAppointments(ss.getSchedule().getAppointments())){
            model.addRow(new Object[]{s});
        }
    }

}
