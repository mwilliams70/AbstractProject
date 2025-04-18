import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class abstractGUI {
    public boolean connected;
    public boolean loggedIn = false;
    public String role;
    public Object[] userInfo;
    private JFrame gui;
    public accountGeneration abstractDB;

    public abstractGUI() {
        abstractDB = new accountGeneration();
        abstractDB.connect("abstract_project", "root", "student");
        
        gui = new JFrame("Abstract Project");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(800, 600);
        gui.setLocation(500, 100);

        JPanel app = new JPanel();
        app.setLayout(new GridLayout(4, 0));
        JButton studentLogin = new JButton("Student Login");
        JButton facultyLogin = new JButton("Faculty Login");
        JButton publicUserLogin = new JButton("Public User Login");
        JButton createAccount = new JButton("Create Account");

        studentLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel sl = new JPanel();
                sl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("msw7476");
                JTextField pwdText = new JTextField("studentpassword");
                sl.add(un);
                sl.add(unText);
                sl.add(pwd);
                sl.add(pwdText);
                JOptionPane.showMessageDialog(null, sl, "Student Login", JOptionPane.QUESTION_MESSAGE);

                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();

                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "student");
                
                if (loggedIn) {
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "student");
                    role="student";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();
                    
                    JFrame studentGUI = new JFrame("Student Dashboard");
                    studentGUI.setSize(400, 300);
                    studentGUI.setLocation(500, 100);
                    studentGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    JPanel studentApp = new JPanel();
                    studentApp.setLayout(new GridLayout(3, 0));
                    JButton interests = new JButton("View or Change My Interests");
                    interests.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            modifyBtn("student");
                        }
                    });

                    displayUserInformation("student", studentApp);
                    studentApp.add(interests);
                    studentApp.add(searchAbstractsButton());
                    studentGUI.add(studentApp);
                    studentGUI.setVisible(true);
                }
                
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        facultyLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel fl = new JPanel();
                fl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("professor");
                JTextField pwdText = new JTextField("password");
                fl.add(un);
                fl.add(unText);
                fl.add(pwd);
                fl.add(pwdText);
                JOptionPane.showMessageDialog(null, fl, "Faculty Login", JOptionPane.QUESTION_MESSAGE);

                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();

                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "faculty");
                if (loggedIn) {
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "faculty");
                    role="faculty";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();

                    JFrame facultyGUI = new JFrame("Faculty Dashboard");
                    facultyGUI.setSize(400, 300);
                    facultyGUI.setLocation(500, 100);
                    facultyGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel facultyApp = new JPanel();
                    facultyApp.setLayout(new GridLayout(3, 0));
                    JButton interests = new JButton("View or Change My Interests");
                    interests.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            modifyBtn("faculty");
                        }
                    });
                    displayUserInformation("faculty", facultyApp);
                    facultyApp.add(interests);
                    facultyApp.add(searchStudentsButton());
                    facultyGUI.add(facultyApp);
                    facultyGUI.setVisible(true);
                }
                
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        publicUserLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel pl = new JPanel();
                pl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("msw7476");
                JTextField pwdText = new JTextField("studentpassword");
                pl.add(un);
                pl.add(unText);
                pl.add(pwd);
                pl.add(pwdText);
                JOptionPane.showMessageDialog(null, pl, "Public User Login", JOptionPane.QUESTION_MESSAGE);

                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();

                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "publicUser");
                if (loggedIn) {
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "publicUser");
                    role="publicUser";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();
                }
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        createAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFrame frame = new JFrame("Create Account");
                frame.setSize(300, 400);
                frame.setLocation(500, 400);
                JPanel ca = new JPanel();
                ca.setLayout(new GridLayout(4, 0));
                JLabel areYou = new JLabel("What type of user are you: ");
                JButton student = new JButton("Student");
                JButton faculty = new JButton("Faculty");
                JButton pub = new JButton("Public User");
            
                ca.add(areYou);
                ca.add(student);
                ca.add(faculty);
                ca.add(pub);
                
                frame.add(ca);
                frame.setVisible(true);

                student.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        JPanel studentCreate = new JPanel();
                        
                        studentCreate.setLayout(new BoxLayout(studentCreate, BoxLayout.Y_AXIS));
                        JLabel un = new JLabel("Enter Username: ");
                        JLabel pwd = new JLabel("Enter Password: ");
                        JLabel firstName = new JLabel("Enter First Name: ");
                        JLabel lastName = new JLabel("Enter Last Name: ");
                        JLabel email = new JLabel("Enter eMail: ");
                        

                        String[] ids = abstractDB.getCollegeIDs();
                        StringBuilder finalIDs = new StringBuilder();
                        for (String id : ids){
                            finalIDs.append(id).append("\n");
                        }
                        finalIDs.append("Enter # ID's of Your College (Ex: 1 2): ");
                        JTextArea idsLabel = new JTextArea(finalIDs.toString());
                        idsLabel.setEditable(false);
                        idsLabel.setOpaque(false);
                        
                        JLabel major = new JLabel("Enter Your Major: ");

                        JTextField unInput = new JTextField();
                        JTextField pwdInput = new JTextField();
                        JTextField fnInput = new JTextField();
                        JTextField lnInput = new JTextField();
                        JTextField emailInput = new JTextField();
                        JTextField collegeIDInput = new JTextField();
                        JTextField majorInput = new JTextField();
                        
                        studentCreate.add(createFieldRow(un, unInput));
                        studentCreate.add(createFieldRow(pwd, pwdInput));
                        studentCreate.add(createFieldRow(firstName, fnInput));
                        studentCreate.add(createFieldRow(lastName, lnInput));
                        studentCreate.add(createFieldRow(email, emailInput));

                        studentCreate.add(idsLabel);
                        studentCreate.add(collegeIDInput);
                        
                        studentCreate.add(createFieldRow(major, majorInput));


                        JOptionPane.showMessageDialog(null, studentCreate, "Create Student Account", JOptionPane.QUESTION_MESSAGE);

                        abstractDB.createStudentAccount(unInput.getText(), pwdInput.getText(),
                         fnInput.getText(), lnInput.getText(), emailInput.getText(), 
                         Integer.parseInt(collegeIDInput.getText()), majorInput.getText());
                    }
                });
            
                faculty.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        JPanel facultyCreate = new JPanel();
                        facultyCreate.setLayout(new BoxLayout(facultyCreate, BoxLayout.Y_AXIS));
                        JLabel un = new JLabel("Enter Username: ");
                        JLabel pwd = new JLabel("Enter Password: ");
                        JLabel firstName = new JLabel("Enter First Name: ");
                        JLabel lastName = new JLabel("Enter Last Name: ");
                        JLabel email = new JLabel("Enter eMail: ");
                        JLabel officeNum = new JLabel("Enter Office Number: ");
                        JLabel building = new JLabel("Enter What Building Your Office Is In: ");
                        String[] ids = abstractDB.getCollegeIDs();
                        StringBuilder finalIDs = new StringBuilder();
                        for (String id : ids){
                            finalIDs.append(id).append("\n");
                        }
                        finalIDs.append("Enter # ID's of Your College (Ex: 1 2): ");
                        JTextArea idsLabel = new JTextArea(finalIDs.toString());
                        idsLabel.setEditable(false);
                        idsLabel.setOpaque(false);
                        JTextField unInput = new JTextField();
                        JTextField pwdInput = new JTextField();
                        JTextField fnInput = new JTextField();
                        JTextField lnInput = new JTextField();
                        JTextField emailInput = new JTextField();
                        JTextField collegeIDInput = new JTextField();
                    
                        JTextField officeInput = new JTextField();
                        JTextField buildingInput = new JTextField();

                        facultyCreate.add(createFieldRow(un, unInput));
                        facultyCreate.add(createFieldRow(pwd, pwdInput));
                        facultyCreate.add(createFieldRow(firstName, fnInput));
                        facultyCreate.add(createFieldRow(lastName, lnInput));
                        facultyCreate.add(createFieldRow(email, emailInput));

                        facultyCreate.add(idsLabel);
                        facultyCreate.add(collegeIDInput);

                        facultyCreate.add(createFieldRow(officeNum, officeInput));
                        facultyCreate.add(createFieldRow(building, buildingInput));

                        JOptionPane.showMessageDialog(null, facultyCreate, "Create Faculty Account", JOptionPane.QUESTION_MESSAGE);
                        
                        String[] collegeIDArray = collegeIDInput.getText().split(" ");
                        
                        abstractDB.createFacultyAccount(unInput.getText(),
                         pwdInput.getText(), fnInput.getText(), lnInput.getText(),
                         emailInput.getText(), Integer.parseInt(officeInput.getText()), collegeIDArray,
                         buildingInput.getText()
                        );
                        
                        
                    }
                });
                pub.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        JPanel publicCreate = new JPanel();
                        publicCreate.setLayout(new GridLayout(4, 2));
                        JLabel un = new JLabel("Enter Username: ");
                        JLabel pwd = new JLabel("Enter Password: ");
                        JLabel orgName = new JLabel("Enter Organization Name (Ex: Public Library): ");
                        JLabel contactInfo = new JLabel("Enter Contact Information (Phone Number, eMail, etc.): ");

                        JTextField unInput = new JTextField();
                        JTextField pwdInput = new JTextField();
                        JTextField orgInput = new JTextField();
                        JTextField contactInput = new JTextField();

                        publicCreate.add(un);
                        publicCreate.add(unInput);
                        publicCreate.add(pwd);
                        publicCreate.add(pwdInput);
                        publicCreate.add(orgName);
                        publicCreate.add(orgInput);
                        publicCreate.add(contactInfo);
                        publicCreate.add(contactInput);

                        JOptionPane.showMessageDialog(null, publicCreate, "Public User Creation", JOptionPane.QUESTION_MESSAGE);
                        abstractDB.createPublicUserAccount(unInput.getText(), pwdInput.getText(),
                         orgInput.getText(), contactInput.getText());
                    }
                });
            }
        });


        app.add(studentLogin);
        app.add(facultyLogin);
        app.add(publicUserLogin);
        app.add(createAccount);
        gui.add(app);
        gui.setVisible(true); 
    }

    private JPanel createFieldRow(JLabel label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    private void modifyBtn(String role) {
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(3, 2));
        JLabel int1 = new JLabel("Interest 1: ");
        JLabel int2 = new JLabel("Interest 2: ");
        JLabel int3 = new JLabel("Interest 3: ");
    
        String[] interests = abstractDB.getInterest(Integer.parseInt(userInfo[0].toString()), role);
        System.out.println(Arrays.toString(interests));
    
        if (interests == null || interests.length < 3) {
            interests = new String[] {"", "", ""};
        } else if (interests.length == 1) {
            interests = new String[] {interests[0], "", ""};
        } else if (interests.length==2) {
            interests = new String[] {interests[0], interests[1], ""};
        }
        
        JTextField userInt1 = new JTextField(interests[0]);
        JTextField userInt2 = new JTextField(interests[1]);
        JTextField userInt3 = new JTextField(interests[2]);
    
        main.add(int1);
        main.add(userInt1);
        main.add(int2);
        main.add(userInt2);
        main.add(int3);
        main.add(userInt3);
    
        JOptionPane.showMessageDialog(null, main, "Modify", JOptionPane.QUESTION_MESSAGE);
        
        String inputtedInt1 = userInt1.getText();
        String inputtedInt2 = userInt2.getText();
        String inputtedInt3 = userInt3.getText();
        
        String[] intArr = new String[] {inputtedInt1, inputtedInt2, inputtedInt3};
        System.out.println(Arrays.toString(intArr));
    
        abstractDB.insertInterests(intArr, role, Integer.parseInt(userInfo[0].toString()));
    
        JOptionPane.showMessageDialog(null, "Interests Updated!", "Updated", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayUserInformation(String role, JPanel panel) {
        String id;
        String fullName;
        String email;
        if (role.equals("student")) {
            id = userInfo[0].toString();
            fullName = userInfo[1].toString() + " " + userInfo[2].toString();
            email = userInfo[3].toString();

            JLabel studentLabel = new JLabel("<html><b>ID: </b>" + id + "<br><b>Name: </b> " + fullName + "<br><b>Email: </b>" + email + "</html>");
            panel.add(studentLabel);
           
        } else if (role.equals("faculty")) {
            id = userInfo[0].toString();
            fullName = userInfo[1].toString() + " " + userInfo[2].toString();
            email = userInfo[3].toString();
            String building = userInfo[5].toString();
            String room = userInfo[4].toString();

            JLabel facultyLabel = new JLabel("<html><b>ID: </b>" + id +
                                            "<br><b>Name: </b> " + fullName +
                                            "<br><b>eMail: </b> " + email +
                                            "<br><b>Building: </b> " + building +
                                            "<br><b>Room Number: </b> " + room);
            panel.add(facultyLabel);
        }
    }
    
    private JButton searchStudentsButton(){
         JButton searchStudents = new JButton("Search students by interest");
         searchStudents.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               String topic = JOptionPane.showInputDialog(null, "Enter an interest to search for:", "Find Students", JOptionPane.QUESTION_MESSAGE);
                           
               if (topic !=null &&!topic.trim().isEmpty()){
                  String[][] studentResults = abstractDB.facultySearchStudents(topic.trim().toLowerCase());
                              
                  if (studentResults.length > 0) {
                     StringBuilder output = new StringBuilder("Matching Students:\n\n");
                     for (String[] student : studentResults){
                        output.append("Name: ").append(student[0]).append("\n");
                        output.append("Email: ").append(student[1]).append("\n");
                        output.append("-----------------------------------------------\n");
                        }
                        JOptionPane.showMessageDialog(null, output.toString(), "Students Found", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No students found with entered interest: " + topic, "No Results", JOptionPane.WARNING_MESSAGE);
                    }
                 }
                           
               }
        });
                    
         return searchStudents;
    }
    
    private JButton searchAbstractsButton(){
         JButton searchAbstracts = new JButton("Search Faculty by Abstracts");
         searchAbstracts.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               String topic = JOptionPane.showInputDialog(null, "Enter an abstract to search for:", "Search Abstracts", JOptionPane.QUESTION_MESSAGE);
               
               if (topic != null && !topic.trim().isEmpty()) {
                  String[][] abstracts = abstractDB.studentSearchAbstract(topic.trim().toLowerCase());
                  
                  if (abstracts.length >0){
                     StringBuilder output = new StringBuilder("Matching Abstracts:\n\n");
                     for (String[] abs : abstracts) {
                        output.append("Title: ").append(abs[0]).append("\n");
                        output.append("Authors: ").append(abs[1]).append("\\n");
                        output.append("Content: ").append(abs[2]).append("\n");
                        output.append("--------------------------\n");
                     }
                     JOptionPane.showMessageDialog(null, output.toString(), "Abstract Results", JOptionPane.INFORMATION_MESSAGE);
                     
                  } else {
                     JOptionPane.showMessageDialog(null, "No abstracts found for: " + topic, "No Results", JOptionPane.WARNING_MESSAGE);
                  }
               }
            }
         });
         
         return searchAbstracts;
    }
    
    public static void main(String[] args) {
        new abstractGUI();
    }
}
