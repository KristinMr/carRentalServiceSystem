package view.car;

import util.DButil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class CarsInfo extends JDialog {
    private JScrollPane jScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JTable table = new JTable() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JButton editBotton = new JButton("修改所选车辆");

    private JButton deleteBotton = new JButton("删除所选车辆");

    public CarsInfo() {
        setTitle("车辆列表");
        setSize(1500,1000);
        setLocationRelativeTo(null);
        setLayout(null);
        setModal(true);

        editBotton.setBounds(1000,20,150,40);
        deleteBotton.setBounds(1200,20,150,40);

        jScrollPane.setBounds(20,100,1460,800);
        add(editBotton);
        add(deleteBotton);
        add(jScrollPane);

        Vector<String> carTHVector = new Vector<String>();
        carTHVector.add("编号");
        carTHVector.add("品牌");
        carTHVector.add("型号");
        carTHVector.add("颜色");
        carTHVector.add("租金/（每天）");
        carTHVector.add("图片");
        carTHVector.add("状态");
        carTHVector.add("所在省份");
        carTHVector.add("所在城市");
        carTHVector.add("备注");

        Vector<Vector<String>> carDataVector = new Vector<Vector<String>>();

        Connection collection = DButil.getConnection();
        String sql = "select car.car_id, brand.brand_name, model.model_name, model.model_color, model.model_rent, car.car_picture, state.state_name, province.province_name, city.city_name, car.car_info from car, model, brand, state, city, province where car.car_model = model.model_id and model.model_brand = brand.brand_id and car.car_state = state.state_id and car.car_city = city.city_id and city.city_province = province.province_id and car.car_recycle_bin = 0";

        try {
            PreparedStatement ps = collection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> vector = new Vector<String>();
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                vector.add(rs.getString(6));
                vector.add(rs.getString(7));
                vector.add(rs.getString(8));
                vector.add(rs.getString(9));
                vector.add(rs.getString(10));

                carDataVector.add(vector);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(collection);
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(carDataVector, carTHVector);
        table.setModel(defaultTableModel);
        jScrollPane.getViewport().add(table);

        table.getTableHeader().setReorderingAllowed(false);


        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);

        editBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null, "请先选中要修改的车辆");
                    return;
                } else {
                    String carID = (String)table.getValueAt(row,0);
                    new UpdateCar(carID);
                }
            }
        });

        deleteBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null, "请先选中要删除的车辆");
                    return;
                } else {
                    String carID = (String)table.getValueAt(row,0);
                    int m = JOptionPane.showConfirmDialog(null, "确定","将所选车辆移入回收站？",JOptionPane.YES_NO_OPTION);
                    if (m == 0) {
                        Connection connection1 = DButil.getConnection();
                        String sql1 = "update car set car_recycle_bin = 1 where car_id = ?";
                        try{
                            PreparedStatement ps = connection1.prepareStatement(sql1);
                            ps.setObject(1, carID);
                            int n = ps.executeUpdate();
                            if (n>0){
                                ((DefaultTableModel)table.getModel()).removeRow(row);
                                JOptionPane.showMessageDialog(null, "车辆删除成功");
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
        new CarsInfo();
    }
}