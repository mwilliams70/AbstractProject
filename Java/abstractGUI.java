import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class abstractGUI {
    public boolean connected;
    public boolean loggedIn = false;
    public String role;
    public Object[] userInfo;
    private JFrame gui;
    public databaseFunctions abstractDB;

    public abstractGUI() {
        // connects to the database
        abstractDB = new databaseFunctions();
        abstractDB.connect("abstract_project", "root", "student");
        

        // creates the gui
        gui = new JFrame("Abstract Project");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(800, 600);
        gui.setLocation(500, 100);

        // login/launch page for application
        JPanel app = new JPanel();
        app.setLayout(new GridLayout(4, 0));
        JButton studentLogin = new JButton("Student Login");
        JButton facultyLogin = new JButton("Faculty Login");
        JButton publicUserLogin = new JButton("Public User Login");
        JButton createAccount = new JButton("Create Account");

        // allows a student to login to the db application
        studentLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel sl = new JPanel();
                sl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("msw7476");
                JTextField pwdText = new JTextField("mypassword");
                sl.add(un);
                sl.add(unText);
                sl.add(pwd);
                sl.add(pwdText);
                JOptionPane.showMessageDialog(null, sl, "Student Login", JOptionPane.QUESTION_MESSAGE);

                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();

                // stores if the user is successfully logged in
                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "student");
                
                
                if (loggedIn) {
                    // stores the logged in student's basic information to be displayed
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "student");
                    role="student";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    
                    gui.dispose(); // closes the login page
                    
                    // GUI is created for students to interact with
                    // students are able to search for abstracts by interest and by their chosen keywords
                    JFrame studentGUI = new JFrame("Student Dashboard");
                    studentGUI.setSize(400, 300);
                    studentGUI.setLocation(500, 100);
                    studentGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    JPanel studentApp = new JPanel();
                    studentApp.setLayout(new BoxLayout(studentApp, BoxLayout.Y_AXIS));
                    JButton interests = new JButton("View or Change My Interests");
                    styleButton(interests);
                    interests.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            modifyBtn("student");
                        }
                    });

                    displayUserInformation("student", studentApp);
                    studentApp.add(Box.createVerticalStrut(10));
                    studentApp.add(wrapButton(interests));
                    studentApp.add(Box.createVerticalStrut(10));
                    studentApp.add(wrapButton(searchAbstractsButton()));
                    studentApp.add(Box.createVerticalStrut(10));
                    studentApp.add(wrapButton(searchAbstractsByInfo()));
                    studentGUI.add(studentApp);
                    studentGUI.setVisible(true);
                }
                
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        // button that lets a faculty member login to the db application
        facultyLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel fl = new JPanel();
                fl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("username");
                JTextField pwdText = new JTextField("password");
                fl.add(un);
                fl.add(unText);
                fl.add(pwd);
                fl.add(pwdText);
                JOptionPane.showMessageDialog(null, fl, "Faculty Login", JOptionPane.QUESTION_MESSAGE);

                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();


                // stores the faculty members basic information to be displayed to them
                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "faculty");

                // if successfully logged in, the faculty is displayed their own GUI
                // a faculty is allowed to search for students by name and their interest
                // and add a new abstract
                if (loggedIn) {
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "faculty");
                    role="faculty";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();

                    JFrame facultyGUI = new JFrame("Faculty Dashboard");
                    facultyGUI.setSize(400, 500);
                    facultyGUI.setLocation(500, 100);
                    facultyGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel facultyApp = new JPanel();
                    facultyApp.setLayout(new BoxLayout(facultyApp, BoxLayout.Y_AXIS));
                    JButton interests = new JButton("View or Change My Interests");
                    styleButton(interests);
                    interests.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            modifyBtn("faculty");
                        }
                    });
                    displayUserInformation("faculty", facultyApp);
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(interests));
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(searchStudentsButton()));
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(searchStudentsByName()));
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(addAbstractButton()));
                    
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(searchAbstractsButton()));
                    facultyApp.add(Box.createVerticalStrut(10));
                    facultyApp.add(wrapButton(searchAbstractsByInfo()));
                    

                    facultyGUI.add(facultyApp);
                    facultyGUI.setVisible(true);
                }
                
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        // allows a public user to login to the db application
        publicUserLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel pl = new JPanel();
                pl.setLayout(new GridLayout(2, 2));
                JLabel un = new JLabel("Username: ");
                JLabel pwd = new JLabel("Password");
                JTextField unText = new JTextField("public");
                JTextField pwdText = new JTextField("user");
                pl.add(un);
                pl.add(unText);
                pl.add(pwd);
                pl.add(pwdText);
                JOptionPane.showMessageDialog(null, pl, "Public User Login", JOptionPane.QUESTION_MESSAGE);
        
                String inputUn = unText.getText();
                String inputPwd = pwdText.getText();
        
                loggedIn = abstractDB.loggedIn(inputUn, inputPwd, "publicUser");

                // if publicUser is successfully logged in, buttons for them to search for 
                // abstracts based on an interest or keyword are displayed
                if (loggedIn) {
                    userInfo = abstractDB.getBasicInformation(inputUn, inputPwd, "publicUser");
                    role="publicUser";
                    JOptionPane.showMessageDialog(null, "Logged In Successfully", "Logged In", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();
        
                    // Setup Public User Dashboard
                    JFrame publicGUI = new JFrame("Public User Dashboard");
                    publicGUI.setSize(400, 300);
                    publicGUI.setLocation(500, 100);
                    publicGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                    JPanel publicApp = new JPanel();
                    publicApp.setLayout(new BoxLayout(publicApp, BoxLayout.Y_AXIS));
        
                    displayUserInformation("publicUser", publicApp);
                    publicApp.add(Box.createVerticalStrut(10));
                    publicApp.add(wrapButton(searchAbstractsByInfo()));
                    publicApp.add(Box.createVerticalStrut(10));
                    publicApp.add(wrapButton(searchAbstractsButton()));
        
                    publicGUI.add(publicApp);
                    publicGUI.setVisible(true);
                }
                System.out.println("userInfo: " + Arrays.toString(userInfo));
            }
        });

        // if a user does not have an account, they can create one through the 'Create Account' button
        createAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFrame frame = new JFrame("Create Account");
                frame.setSize(300, 400);
                frame.setLocation(500, 400);
                JPanel ca = new JPanel();
                ca.setLayout(new GridLayout(4, 0));
                JLabel areYou = new JLabel("What type of user are you: ");
                JButton student = new JButton("Student");
                styleButton(student);
                JButton faculty = new JButton("Faculty");
                styleButton(faculty);
                JButton pub = new JButton("Public User");
                styleButton(pub);
            
                ca.add(areYou);
                ca.add(student);
                ca.add(faculty);
                ca.add(pub);
                
                frame.add(ca);
                frame.setVisible(true);

                // if a user makes a student account they enter their information that is asked of them
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

                        // creates a student account
                        abstractDB.createStudentAccount(unInput.getText(), pwdInput.getText(),
                         fnInput.getText(), lnInput.getText(), emailInput.getText(), 
                         Integer.parseInt(collegeIDInput.getText()), majorInput.getText());
                    }
                });
            
                // creates a faculty account based on provided information
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
                        
                        // creates a new faculty account
                        abstractDB.createFacultyAccount(unInput.getText(),
                         pwdInput.getText(), fnInput.getText(), lnInput.getText(),
                         emailInput.getText(), Integer.parseInt(officeInput.getText()), collegeIDArray,
                         buildingInput.getText()
                        );
                        
                        
                    }
                });

                // creates a new public user account
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
                        
                        // inserts a new public user account
                        abstractDB.createPublicUserAccount(unInput.getText(), pwdInput.getText(),
                         orgInput.getText(), contactInput.getText());
                    }
                });
            }
        });
        
        Color ritOrange = new Color(255, 102, 0);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
        
         
        studentLogin.setBackground(ritOrange);
        studentLogin.setForeground(Color.WHITE); 
        studentLogin.setFont(buttonFont);
        studentLogin.setBorder(new LineBorder(Color.BLACK, 2));


        
        facultyLogin.setBackground(ritOrange);
        facultyLogin.setForeground(Color.WHITE);
        facultyLogin.setFont(buttonFont);
        facultyLogin.setBorder(new LineBorder(Color.BLACK, 2));


        publicUserLogin.setBackground(ritOrange);
        publicUserLogin.setForeground(Color.WHITE);
        publicUserLogin.setFont(buttonFont);
        publicUserLogin.setBorder(new LineBorder(Color.BLACK, 2));
  
        
        createAccount.setBackground(ritOrange);
        createAccount.setForeground(Color.WHITE);
        createAccount.setFont(buttonFont);
        createAccount.setBorder(new LineBorder(Color.BLACK, 2));
 


        app.add(studentLogin);
        app.add(facultyLogin);
        app.add(publicUserLogin);
        app.add(createAccount);

        
        ImageIcon icon = new ImageIcon("ritLogo.png");
        JLabel imageLabel = new JLabel(icon);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.WEST);
        mainPanel.add(app, BorderLayout.CENTER); 
        app.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        gui.add(mainPanel);
        gui.setVisible(true); 
    }

    // creates a format for a JPanel to be displayed horizontally
    private JPanel createFieldRow(JLabel label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    // allows users to view and modify their current interests
    private void modifyBtn(String role) {
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(3, 2));
        JLabel int1 = new JLabel("Interest 1: ");
        JLabel int2 = new JLabel("Interest 2: ");
        JLabel int3 = new JLabel("Interest 3: ");
    
        String[] interests = abstractDB.getInterest(Integer.parseInt(userInfo[0].toString()), role);
        System.out.println(Arrays.toString(interests));
    
        // ensures that if a value is blank it does not crash the code
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
    
        // inserts the users interests
        abstractDB.insertInterests(intArr, role, Integer.parseInt(userInfo[0].toString()));
    
        JOptionPane.showMessageDialog(null, "Interests Updated!", "Updated", JOptionPane.INFORMATION_MESSAGE);
    }

    // creates a panel to display a users basic information such as names, emails, etc.
    public void displayUserInformation(String role, JPanel panel) {
        String id;
        String fullName;
        String email;
        if (role.equals("student")) {
            id = userInfo[0].toString();
            fullName = userInfo[1].toString() + " " + userInfo[2].toString();
            email = userInfo[3].toString();

            JLabel studentLabel = new JLabel("<html><b>ID: </b>" + id + "<br><b>Name: </b> " + fullName + "<br><b>Email: </b>" + email + "</html>");
            
            studentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);           
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
            labelPanel.add(studentLabel);
            panel.add(labelPanel);
           
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
        
                                            
            facultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
            labelPanel.add(facultyLabel);
            panel.add(labelPanel);
        } else if (role.equals("publicUser")) {
            id = userInfo[0].toString();
            fullName = userInfo[1].toString(); // this is org name
            email = userInfo[2].toString(); // contact info
            JLabel publicLabel = new JLabel("<html><b>ID: </b>" + id +
                                           "<br><b>Organization: </b> " + fullName +
                                           "<br><b>Contact Info: </b> " + email + "</html>");
            panel.add(publicLabel);
        }
    }
    
    
    // Allows faculty to search for students by interests
    private JButton searchStudentsButton(){
         JButton searchStudents = new JButton("Search students by interest");
         styleButton(searchStudents);
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
    
    // Searches for abstracts based on a users inputted interest
    // if a user enters 'python' abstracts written by faculty members with an interest of python will be displayed
    private JButton searchAbstractsButton(){
         JButton searchAbstracts = new JButton("Search Abstract By Interest");
         styleButton(searchAbstracts);
         searchAbstracts.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               String topic = JOptionPane.showInputDialog(null, "Enter your interest to search for matching Abstracts:", "Search Abstracts", JOptionPane.QUESTION_MESSAGE);
               
               if (topic != null && !topic.trim().isEmpty()) {
                  String[][] abstracts = abstractDB.studentSearchAbstract(topic.trim().toLowerCase());
                  
                  if (abstracts.length >0){
                     StringBuilder output = new StringBuilder("Matching Abstracts:\n\n");
                     for (String[] abs : abstracts) {
                        output.append("Title: ").append(abs[0]).append("\n");
                        output.append("Authors: ").append(abs[1]).append("\n");
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
    
    // searches abstracts based on keywords of an abstract such as (title, author, content)
    private JButton searchAbstractsByInfo() {
        JButton search = new JButton("Search Abstract");
        styleButton(search);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String line = JOptionPane.showInputDialog(null, "Enter a keyword about the abstract you are looking for (title, author, content of abstract, etc.)", "Search Abstracts", JOptionPane.QUESTION_MESSAGE);

                if (line != null && !line.trim().isEmpty()) {
                    String[][] abstracts = abstractDB.searchAbstractsTarget(line.trim());
                    if (abstracts.length > 0) {
                        StringBuilder output = new StringBuilder("Results Matching '" + line.trim() + "'' Found: \n\n");
                        for (String[] abs : abstracts) {
                            output.append("Title: ").append(abs[0]).append("\n");
                            output.append("Authors: ").append(abs[1]).append("\n");
                            output.append("Content: ").append(abs[2]).append("\n");
                            output.append("--------------------------\n");
                         }
                         JOptionPane.showMessageDialog(null, output.toString(), "Abstract Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No abstracts found for: " + line, "No Results", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        return search;
    }
    
    // Allows Faculty to add abstracts
    private JButton addAbstractButton() {
         JButton addAbstract = new JButton("Add New Abstract");
         styleButton(addAbstract);
         addAbstract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
               JPanel form = new JPanel();
               form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

               JLabel titleLabel = new JLabel("Title:");
               JTextField titleField = new JTextField();

               JLabel authorsLabel = new JLabel("Authors:");
               JTextField authorsField = new JTextField(userInfo[1] + " " + userInfo[2]); // Default to logged-in faculty name

               JLabel contentLabel = new JLabel("Content:");
               JTextArea contentArea = new JTextArea(5, 20);
               contentArea.setLineWrap(true);
               contentArea.setWrapStyleWord(true);
               JScrollPane contentScroll = new JScrollPane(contentArea);

               form.add(titleLabel);
               form.add(titleField);
               form.add(Box.createVerticalStrut(10));
               form.add(authorsLabel);
               form.add(authorsField);
               form.add(Box.createVerticalStrut(10));
               form.add(contentLabel);
               form.add(contentScroll);

               int result = JOptionPane.showConfirmDialog(null, form, "Add Abstract", JOptionPane.OK_CANCEL_OPTION);
   
               if (result == JOptionPane.OK_OPTION) {
                   String title = titleField.getText().trim();
                   String authors = authorsField.getText().trim();
                   String content = contentArea.getText().trim();
   
                   if (!title.isEmpty() && !authors.isEmpty() && !content.isEmpty()) {
                       int facultyID = Integer.parseInt(userInfo[0].toString());
                       abstractDB.insertFacultyAbstract(title, authors, content, facultyID);
                       JOptionPane.showMessageDialog(null, "Abstract added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                   } else {
                       JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                   }
               }
        }
    });
    return addAbstract;
}

    // allows faculty to search for a student based on their first and last name
    private JButton searchStudentsByName() {
        JButton search = new JButton("Search Students By Name");
        styleButton(search);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JPanel form = new JPanel();
                form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

                JLabel fName = new JLabel("First Name: ");
                JTextField fNameField = new JTextField();

                JLabel lName = new JLabel("Last Name: ");
                JTextField lNameField = new JTextField();

                form.add(fName);
                form.add(fNameField);
                form.add(Box.createHorizontalStrut(10));
                form.add(lName);
                form.add(lNameField);
                form.add(Box.createHorizontalStrut(10));

                JOptionPane.showMessageDialog(null, form, "Search For Student", JOptionPane.QUESTION_MESSAGE);

                String[][] students = abstractDB.searchStudentsByName(fNameField.getText(), lNameField.getText());
                if (students.length > 0) {
                    StringBuilder output = new StringBuilder("Students Found: \n\n");
                    for (String[] student : students) {
                        output.append("Name: ").append(student[0]).append("\n");
                        output.append("eMail: ").append(student[1]).append("\n");
                        output.append("------------------------------\n");
                    }
                    JOptionPane.showMessageDialog(null, output.toString(), "Student Results", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(null, "No Students Found For: " + fNameField.getText() + " " + lNameField.getText(), "No Results Found", JOptionPane.WARNING_MESSAGE);

                }
            }
        });
        return search;
    }

        // sets styles of buttons
       private void styleButton(JButton button) {
          Color ritOrange = new Color(255, 102, 0);
          Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
          button.setBackground(ritOrange);
          button.setForeground(Color.WHITE);
          button.setFont(buttonFont);
          button.setBorder(new LineBorder(Color.BLACK, 2));
        }
   
        // sets styles of buttons
       private JPanel wrapButton(JButton button) {
          JPanel wrapper = new JPanel();
          wrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
          button.setPreferredSize(new Dimension(300, 40)); 
          wrapper.add(button);
          return wrapper;
        }

    
    public static void main(String[] args) {
        new abstractGUI();
    }
}
