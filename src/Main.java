import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    JTextField txtId, txtName, txtDept, txtCourse, txtPhone;

    JButton btnAdd, btnView, btnDelete, btnClear, btnUpdate;
    JButton btnSearch;

    Connection conn;

    JTable table;

    JLabel lblCount;

    DefaultTableModel model;

    public Main() {

        setTitle("Student Management System");

        setSize(900,600);

        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout(10,10));

        JPanel formPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel topPanel = new JPanel();

        formPanel.setLayout(new GridLayout(5,2,10,10));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20));

        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20));

        buttonPanel.setLayout(new GridLayout(2,3,10,10));

        tablePanel.setLayout(new BorderLayout());

        topPanel.setLayout(new BorderLayout());

        String[] columns = {

                "ID",

                "Name",

                "Department",

                "Course",

                "Phone"

        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(52,152,219));
        table.setSelectionForeground(Color.WHITE);

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = table.getSelectedRow();

                if (row != -1) {

                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtName.setText(model.getValueAt(row, 1).toString());
                    txtDept.setText(model.getValueAt(row, 2).toString());
                    txtCourse.setText(model.getValueAt(row, 3).toString());
                    txtPhone.setText(model.getValueAt(row, 4).toString());

                }

            }

        });

        JScrollPane scrollPane =
                new JScrollPane(table);

        lblCount = new JLabel("Total Students : 0");
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablePanel.add(lblCount, BorderLayout.NORTH);

        tablePanel.add(scrollPane,BorderLayout.CENTER);

        getContentPane().setBackground(new Color(245,245,245));

        JLabel lblId = new JLabel("Student ID");
        txtId = new JTextField(20);

        JLabel lblName = new JLabel("Name");
        txtName = new JTextField(20);

        JLabel lblDept = new JLabel("Department");
        txtDept = new JTextField(20);

        JLabel lblCourse = new JLabel("Course");
        txtCourse = new JTextField(20);

        JLabel lblPhone = new JLabel("Phone");
        txtPhone = new JTextField(20);

        btnAdd = new JButton("Add Student");
        btnView = new JButton("View Students");
        btnDelete = new JButton("Delete Student");
        btnUpdate = new JButton("Update Student");
        btnUpdate.setBackground(new Color(255,165,0));
        btnClear = new JButton("Clear Fields");
        btnSearch = new JButton("Search Student");

        JLabel heading = new JLabel("Student Management System");

        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));

        heading.setHorizontalAlignment(JLabel.CENTER);

        heading.setBorder(
                BorderFactory.createEmptyBorder(15,10,15,10));


        Font font = new Font("Segoe UI", Font.PLAIN, 15);

        lblId.setFont(font);
        lblName.setFont(font);
        lblDept.setFont(font);
        lblCourse.setFont(font);
        lblPhone.setFont(font);

        txtId.setFont(font);
        txtName.setFont(font);
        txtDept.setFont(font);
        txtCourse.setFont(font);
        txtPhone.setFont(font);

        btnAdd.setFont(font);
        btnView.setFont(font);
        btnUpdate.setFont(font);
        btnDelete.setFont(font);
        btnSearch.setFont(font);
        btnClear.setFont(font);


        btnAdd.setBackground(new Color(46,204,113));
        btnAdd.setForeground(Color.WHITE);

        btnUpdate.setBackground(new Color(243,156,18));
        btnUpdate.setForeground(Color.WHITE);

        btnDelete.setBackground(new Color(231,76,60));
        btnDelete.setForeground(Color.WHITE);

        btnSearch.setBackground(new Color(52,152,219));
        btnSearch.setForeground(Color.WHITE);

        btnView.setBackground(new Color(155,89,182));
        btnView.setForeground(Color.WHITE);

        btnClear.setBackground(new Color(149,165,166));
        btnClear.setForeground(Color.WHITE);

        btnAdd.setFocusPainted(false);
        btnUpdate.setFocusPainted(false);
        btnDelete.setFocusPainted(false);
        btnSearch.setFocusPainted(false);
        btnView.setFocusPainted(false);
        btnClear.setFocusPainted(false);

        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnView.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(lblId);
        formPanel.add(txtId);

        formPanel.add(lblName);
        formPanel.add(txtName);

        formPanel.add(lblDept);
        formPanel.add(txtDept);

        formPanel.add(lblCourse);
        formPanel.add(txtCourse);

        formPanel.add(lblPhone);
        formPanel.add(txtPhone);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        buttonPanel.add(btnSearch);
        buttonPanel.add(btnView);
        buttonPanel.add(btnClear);

        connectDatabase();

        createTable();

        btnAdd.addActionListener(e -> addStudent());

        btnView.addActionListener(e -> viewStudents());

        btnDelete.addActionListener(e -> deleteStudent());

        btnUpdate.addActionListener(e -> updateStudent());

        btnClear.addActionListener(e -> clearFields());

        btnSearch.addActionListener(e -> searchStudent());

        JPanel centerPanel = new JPanel(new BorderLayout());

        centerPanel.add(buttonPanel, BorderLayout.NORTH);

        centerPanel.add(tablePanel, BorderLayout.CENTER);

        topPanel.add(heading, BorderLayout.NORTH);

        topPanel.add(formPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        add(centerPanel, BorderLayout.CENTER);

        viewStudents();

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void connectDatabase() {

        try {

            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection("jdbc:sqlite:students.db");

            System.out.println("Database Connected");

        } catch(Exception e) {

            System.out.println(e);

        }
    }

    void createTable() {

        try {

            Statement stmt = conn.createStatement();

            String sql =
                    "CREATE TABLE IF NOT EXISTS students(" +
                            "id INTEGER PRIMARY KEY," +
                            "name TEXT," +
                            "department TEXT," +
                            "course TEXT," +
                            "phone TEXT)";

            stmt.execute(sql);

        } catch(Exception e) {

            System.out.println(e);

        }
    }

    boolean validateFields(){

        if(txtId.getText().trim().isEmpty() ||
                txtName.getText().trim().isEmpty() ||
                txtDept.getText().trim().isEmpty() ||
                txtCourse.getText().trim().isEmpty() ||
                txtPhone.getText().trim().isEmpty()){

            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return false;
        }

        if(!txtPhone.getText().matches("\\d{10}")){

            JOptionPane.showMessageDialog(this,
                    "Phone must contain exactly 10 digits");
            return false;
        }

        return true;
    }

    boolean studentExists(int id) {

        try {

            String sql = "SELECT * FROM students WHERE id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch(Exception e) {

            return false;

        }

    }

    void addStudent() {

        if(!validateFields())
            return;

        int id = Integer.parseInt(txtId.getText());

        if(studentExists(id)) {

            JOptionPane.showMessageDialog(this,
                    "Student ID already exists");

            return;
        }

        try {

            String sql =
                    "INSERT INTO students VALUES(?,?,?,?,?)";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(txtId.getText())
            );

            pst.setString(
                    2,
                    txtName.getText()
            );

            pst.setString(
                    3,
                    txtDept.getText()
            );

            pst.setString(
                    4,
                    txtCourse.getText()
            );

            pst.setString(
                    5,
                    txtPhone.getText()
            );

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Student Added Successfully"
            );

            viewStudents();

            clearFields();

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e
            );

        }
    }

    void viewStudents() {

        try {

            model.setRowCount(0);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {

                Object[] row = {

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("course"),
                        rs.getString("phone")

                };

                model.addRow(row);

            }

            lblCount.setText("Total Students : " + model.getRowCount());

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);

        }

    }

    void deleteStudent() {

        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (option != JOptionPane.YES_OPTION)
            return;

        try {

            String sql =
                    "DELETE FROM students WHERE id=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(
                    1,
                    Integer.parseInt(txtId.getText())
            );

            int rows = pst.executeUpdate();

            if(rows > 0){

                JOptionPane.showMessageDialog(
                        this,
                        "Student Deleted Successfully");

                viewStudents();

                clearFields();

            }else{

                JOptionPane.showMessageDialog(
                        this,
                        "Student Not Found");

            }

        } catch(Exception e) {

            System.out.println(e);

        }
    }

    void updateStudent() {

        if(!validateFields())
            return;

        try {

            String sql = "UPDATE students SET name=?, department=?, course=?, phone=? WHERE id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, txtName.getText());
            pst.setString(2, txtDept.getText());
            pst.setString(3, txtCourse.getText());
            pst.setString(4, txtPhone.getText());
            pst.setInt(5, Integer.parseInt(txtId.getText()));

            int rows = pst.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(this, "Student Updated Successfully");

                viewStudents();

                clearFields();

            } else {

                JOptionPane.showMessageDialog(this, "Student Not Found");

            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(this, e);

        }
    }

    void clearFields() {

        txtId.setText("");

        txtName.setText("");

        txtDept.setText("");

        txtCourse.setText("");

        txtPhone.setText("");

    }

    void searchStudent(){

        try{

            String sql = "SELECT * FROM students WHERE id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, Integer.parseInt(txtId.getText()));

            ResultSet rs = pst.executeQuery();

            if(rs.next()){

                txtName.setText(rs.getString("name"));
                txtDept.setText(rs.getString("department"));
                txtCourse.setText(rs.getString("course"));
                txtPhone.setText(rs.getString("phone"));

            } else {

                JOptionPane.showMessageDialog(this,"Student Not Found");

            }

        } catch(Exception e){

            JOptionPane.showMessageDialog(this,e);

        }
    }

    public static void main(String[] args) {

        new Main();

    }
}