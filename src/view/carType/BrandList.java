package view.carType;

import util.DButil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BrandList extends JFrame {
    private JTextField searchBrandID = new JTextField("编号关键字");
    private JTextField searchBrandName = new JTextField("名称关键字");
    private JTextField searchBrandInfo = new JTextField("介绍关键字");
    private JButton refreshSearchBotton = new JButton("刷新");
    private JButton searchBrandBotton = new JButton("查询");

    private JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JTable table = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JLabel brandIDLabel = new JLabel("品牌编号");
    private JTextField brandIDField = new JTextField();

    private JLabel brandNameLabel = new JLabel("品牌名称");
    private JTextField brandNameField = new JTextField();

    private JLabel brandInfoLabel = new JLabel("品牌介绍");
    private JTextArea brandInfoArea = new JTextArea();

    private JButton clearBotton = new JButton("清空");
    private JButton resetBotton = new JButton("重置");

    private JButton addBrandBotton = new JButton("新增品牌");

    private JButton editBotton = new JButton("修改品牌信息");

    private JButton deleteBotton = new JButton("删除所选品牌");

    public BrandList() {
        setTitle("品牌列表");
        setSize(950,800);
        setLocationRelativeTo(null);
        setLayout(null);


        searchBrandID.setForeground(Color.gray);
        searchBrandName.setForeground(Color.gray);
        searchBrandInfo.setForeground(Color.gray);

        searchBrandID.setBounds(50,30,150,30);
        searchBrandName.setBounds(220,30,150,30);
        searchBrandInfo.setBounds(390,30,150,30);
        refreshSearchBotton.setBounds(720,30,80,30);
        searchBrandBotton.setBounds(820,30,80,30);
        pane.setBounds(50,100,850,300);

        brandIDLabel.setBounds(80,430,80,30);
        brandIDField.setBounds(180,430,220,30);
        brandNameLabel.setBounds(530,430,80,30);
        brandNameField.setBounds(630,430,220,30);
        brandInfoLabel.setBounds(80,490,80,30);
        brandInfoArea.setBounds(180,490,670,100);
        clearBotton.setBounds(80,640,80,30);
        resetBotton.setBounds(180,640,80,30);
        addBrandBotton.setBounds(450,640,120,30);
        editBotton.setBounds(590,640,120,30);
        deleteBotton.setBounds(730,640,120,30);

        Vector<String> thVector = new Vector<String>();
        thVector.add("编号");
        thVector.add("名称");
        thVector.add("介绍");
        Vector<Vector<String>> dataVector = new Vector<Vector<String>>();

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchBrandID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBrandID.getText().equals("编号关键字") == true) {
                    searchBrandID.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = searchBrandID.getText();
                if (temp.equals("")) {
                    searchBrandID.setText("编号关键字");
                    searchBrandID.setForeground(Color.gray);
                }
            }
        });

        searchBrandName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBrandName.getText().equals("名称关键字") == true) {
                    searchBrandName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = searchBrandName.getText();
                if (temp.equals("")) {
                    searchBrandName.setText("名称关键字");
                    searchBrandName.setForeground(Color.gray);
                }
            }
        });

        searchBrandInfo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBrandInfo.getText().equals("介绍关键字") == true) {
                    searchBrandInfo.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = searchBrandInfo.getText();
                if (temp.equals("")) {
                    searchBrandInfo.setText("介绍关键字");
                    searchBrandInfo.setForeground(Color.gray);
                }
            }
        });

        Connection connection = DButil.getConnection();

        String sql = "select * from brand where brand_recycle_bin = 0";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Vector<String> vector = new Vector<String>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                dataVector.add(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(connection);
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(dataVector, thVector);
        table.setModel(defaultTableModel);
        pane.getViewport().add(table);

        table.getTableHeader().setReorderingAllowed(false);

        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(450);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);

        brandIDField.setEditable(false);

        add(searchBrandID);
        add(searchBrandName);
        add(searchBrandInfo);
        add(refreshSearchBotton);
        add(searchBrandBotton);
        add(pane);
        add(brandIDLabel);
        add(brandIDField);
        add(brandNameLabel);
        add(brandNameField);
        add(brandInfoLabel);
        add(brandInfoArea);
        add(clearBotton);
        add(resetBotton);
        add(addBrandBotton);
        add(editBotton);
        add(deleteBotton);

        searchBrandBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String brandID = searchBrandID.getText();
                String brandName = searchBrandName.getText();
                String brandInfo = searchBrandInfo.getText();

                Connection connection1 = DButil.getConnection();
                StringBuffer stringBuffer = new StringBuffer("select * from brand where brand_recycle_bin = 0 ");

                List list = new ArrayList();

                if (brandID.trim().length() > 0 && brandID.equals("编号关键字") == false) {
                    stringBuffer.append("and brand_id like ? ");
                    list.add("%" + brandID + "%");
                }

                if (brandName.trim().length() > 0 && brandName.equals("名称关键字") == false) {
                    stringBuffer.append("and brand_name like ? ");
                    list.add("%" + brandName + "%");
                }

                if (brandInfo.trim().length() > 0 && brandInfo.equals("介绍关键字") == false) {
                    stringBuffer.append("and brand_info like ? ");
                    list.add("%" + brandInfo + "%");
                }

                try {
                    PreparedStatement ps = connection1.prepareStatement(stringBuffer.toString());

                    for (int i = 0; i < list.size(); i++) {
                        ps.setObject(i + 1, list.get(i));
                        System.out.println(123456);
                        System.out.println(stringBuffer);

                    }

                    ResultSet rs = ps.executeQuery();
                    defaultTableModel.getDataVector().clear();
                    defaultTableModel.fireTableDataChanged();

                    System.out.println(stringBuffer);
                    while (rs.next()) {
                        Vector<String> vector = new Vector<String>();
                        vector.add(rs.getString(1));
                        vector.add(rs.getString(2));
                        vector.add(rs.getString(3));
                        dataVector.add(vector);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    DButil.releaseConnection(connection1);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                String brandID = (String) table.getValueAt(row, 0);
                String brandName = (String) table.getValueAt(row, 1);
                String brandInfo = (String) table.getValueAt(row, 2);
                brandIDField.setText(brandID);
                brandNameField.setText(brandName);
                brandInfoArea.setText(brandInfo);

            }
        });

        clearBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brandNameField.setText("");
                brandInfoArea.setText("");
            }
        });

        resetBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brandNameField.setText((String) table.getValueAt(table.getSelectedRow(), 1));
                brandInfoArea.setText((String)table.getValueAt(table.getSelectedRow(), 2));
            }
        });

        addBrandBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = JOptionPane.showConfirmDialog(null, "确认","确认添加新品牌？",JOptionPane.YES_NO_OPTION);
                if (m == 0) {
                    String brandName = brandNameField.getText();
                    String brandInfo = brandInfoArea.getText();

                    Connection connection = DButil.getConnection();
                    String sql = "insert into brand(brand_name, brand_info, brand_recycle_bin) values(?, ?, ?)";
                    String sql1 = "select * from brand where brand_recycle_bin = 0";
                    try {
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setObject(1, brandName);
                        ps.setObject(2, brandInfo);
                        ps.setObject(3, 0);

                        int n = ps.executeUpdate();

                        if (n>0) {
                            JOptionPane.showMessageDialog(null, "新增成功！");

                            PreparedStatement ps1 = connection.prepareStatement(sql1);
                            ResultSet rs = ps.executeQuery();
                            defaultTableModel.getDataVector().clear();
                            defaultTableModel.fireTableDataChanged();
                            while (rs.next()){
                                Vector<String> vector = new Vector<String>();
                                vector.add(rs.getString(1));
                                vector.add(rs.getString(2));
                                vector.add(rs.getString(3));
                                dataVector.add(vector);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "新增失败！");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        DButil.releaseConnection(connection);
                    }
                }

            }
        });

        editBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中要修改的品牌！");
                } else {
                    int m = JOptionPane.showConfirmDialog(null, "确认","确认修改品牌信息？",JOptionPane.YES_NO_OPTION);
                    if (m == 0) {
                        String brandID = brandIDField.getText();
                        String brandName = brandNameField.getText();
                        String brandInfo = brandInfoArea.getText();

                        Connection connection1 = DButil.getConnection();
                        String sql = "update brand set brand_name = ?, brand_info = ? where brand_id = ?";

                        try {
                            PreparedStatement ps = connection1.prepareStatement(sql);
                            ps.setObject(1, brandName);
                            ps.setObject(2, brandInfo);
                            ps.setObject(3,brandID);
                            int n = ps.executeUpdate();

                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "品牌修改成功！");
                                ((DefaultTableModel)table.getModel()).setValueAt(brandName, row,1);
                                ((DefaultTableModel)table.getModel()).setValueAt(brandInfo, row,2);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            DButil.releaseConnection(connection1);
                        }
                    }
                    
                }
            }
        });

        deleteBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请先选中要删除的品牌！");
                } else {
                    int m = JOptionPane.showConfirmDialog(null, "确认","确认移入回收站？",JOptionPane.YES_NO_OPTION);
                    if (m == 0) {
                        String brandID = brandIDField.getText();

                        Connection connection1 = DButil.getConnection();
                        String sql = "update brand set brand_recycle_bin = 1 where brand_id = ?";

                        try {
                            PreparedStatement ps = connection1.prepareStatement(sql);
                            ps.setObject(1, brandID);
                            int n = ps.executeUpdate();
                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "删除成功！");
                                ((DefaultTableModel)table.getModel()).removeRow(row);
                                brandIDField.setText("");
                                brandNameField.setText("");
                                brandInfoArea.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "删除失败！");
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            DButil.releaseConnection(connection1);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new BrandList();
    }
}
