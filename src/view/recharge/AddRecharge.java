package view.recharge;


import util.Admin;
import util.DButil;
import util.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AddRecharge extends JPanel {
    private JTextField searchUserID = new JTextField("编号关键字");
    private JTextField searchUserName = new JTextField("名称关键字");
    private JTextField searchUserInfo = new JTextField("介绍关键字");
    private JButton refreshSearchButton = new JButton("刷新");
    private JButton searchUserButton = new JButton("查询");

    //    private JButton editButton = new JButton("修改所选用户");
    private JButton addRechargeButton = new JButton("给所选用户充值");

    private JScrollPane jScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };


    public AddRecharge(Admin admin) {
//        setTitle("用户列表");
        setSize(1350, 800);
//        setLocationRelativeTo(null);
        setLayout(null);

        searchUserID.setForeground(Color.gray);
        searchUserName.setForeground(Color.gray);
        searchUserInfo.setForeground(Color.gray);

        searchUserID.setBounds(15, 40, 150, 30);
        searchUserName.setBounds(185, 40, 150, 30);
        searchUserInfo.setBounds(355, 40, 150, 30);
        refreshSearchButton.setBounds(720, 40, 80, 30);
        searchUserButton.setBounds(820, 40, 80, 30);

//        editButton.setBounds(1000,40,150,40);
        addRechargeButton.setBounds(1170, 40, 150, 40);

        jScrollPane.setBounds(15, 100, 1310, 700);


        ImageIcon imageIcon = new ImageIcon("C:\\Users\\mrcap\\IdeaProjects\\UserRentalServiceSystem\\src\\source\\main.jpg");
        JLabel bgLabel = new JLabel(imageIcon);
//        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
//        bgLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
//        this.getContentPane().add(new JLabel());
//        ((JPanel) getContentPane()).setOpaque(false);

        add(searchUserID);
        add(searchUserName);
        add(searchUserInfo);
        add(refreshSearchButton);
        add(searchUserButton);
//        add(editButton);
        add(addRechargeButton);
        add(jScrollPane);
        add(bgLabel);

        Vector<String> userTHVector = new Vector<String>();
        userTHVector.add("编号");
        userTHVector.add("姓名");
        userTHVector.add("性别");
        userTHVector.add("身份证号");
        userTHVector.add("联系电话");
        userTHVector.add("电子邮箱");
        userTHVector.add("驾照编号");
        userTHVector.add("驾驶年龄");
        userTHVector.add("年龄");
        userTHVector.add("联系地址");
        userTHVector.add("剩余金额");
        userTHVector.add("用户备注");

        Vector<Vector<String>> userDataVector = new Vector<Vector<String>>();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Connection collection = DButil.getConnection();
        String sql = "select * from user where user_recycle_bin = 0";

        try {
            PreparedStatement ps = collection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> vector = new Vector<String>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(5));
                vector.add(rs.getString(6));
                vector.add(rs.getString(7));
                vector.add(rs.getString(8));
                vector.add(rs.getString(9));
                vector.add(rs.getString(10));
                vector.add(rs.getString(11));
                vector.add(rs.getString(12));
                vector.add(rs.getString(13));
                vector.add(rs.getString(14));

                userDataVector.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(collection);
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(userDataVector, userTHVector);
        table.setModel(defaultTableModel);
        jScrollPane.getViewport().add(table);

        table.getTableHeader().setReorderingAllowed(false);


        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);

//        editButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int row = table.getSelectedRow();
//                if (row==-1){
//                    JOptionPane.showMessageDialog(null, "请先选中要修改的用户");
//                    return;
//                } else {
//                    String userID = (String)table.getValueAt(row,0);
//                    new UpdateUser();
//                }
//            }
//        });

        addRechargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中要充值的用户");
                    return;
                } else {
                    String userID = (String) table.getValueAt(row, 0);

                    User user = new User();

                    Connection connection1 = DButil.getConnection();
                    String sql1 = "select user_id, user_name, user_money from user where user_id = ?";
                    try {
                        PreparedStatement ps = connection1.prepareStatement(sql1);
                        ps.setObject(1, userID);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            user.setUserID(rs.getString(1));
                            user.setUserName(rs.getString(2));
                            user.setUserMoney(rs.getString(3));
                            new Recharging(admin, user);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        DButil.releaseConnection(connection1);
                    }
                }
            }
        });

        setVisible(true);
    }

//    public static void main(String[] args) {
//        try {
//            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.frameBorderStyle.translucencyAppleLike;
////            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.frameBorderStyle.generalNoTranslucencyShadow;
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//        } catch (Exception e) {
//
//        }
//        new UserList();
//    }

}
