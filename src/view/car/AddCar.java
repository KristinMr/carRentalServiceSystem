package view.car;

import util.*;
import view.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddCar extends JPanel {

    private JLabel carNumberLabel = new JLabel("车牌号");
    private JTextField carNumberField = new JTextField();
    
    private JLabel carTypeLabel = new JLabel("车辆类型");
    private JComboBox<Brand> carBrandBox = new JComboBox<Brand>();
    private JComboBox<Model> carModelBox = new JComboBox<Model>();

    private JLabel carStateLabel = new JLabel("车辆状态");
    private JComboBox<State> carStateBox = new JComboBox<State>();

//    private JLabel carLocationLabel = new JLabel("车辆地点");
//    private JComboBox<Province> carProvinceBox = new JComboBox<Province>();
//    private JComboBox<City> carCityBox = new JComboBox<City>();

    private JLabel carColorLabel = new JLabel("车辆颜色");
    private JTextField carColorField = new JTextField();

    private JLabel carRentLabel = new JLabel("车辆租金");
    private JTextField carRentField = new JTextField();

//    private JLabel carPictureLabel = new JLabel("车辆图片");
//    private JTextField carPictureField = new JTextField();

    private JLabel carInfoLabel = new JLabel("车辆备注");
    private JTextArea carInfoArea = new JTextArea();

    private JButton resetButton = new JButton("重置");
    private JButton addButton = new JButton("添加");

    public AddCar(Admin admin) {
//        setTitle("新增车辆");
        setSize(1350,750);
//        setLocationRelativeTo(null);
        setLayout(null);
//        setModal(true);

//        setBackground(Color.LIGHT_GRAY);

        carNumberLabel.setBounds(80,50,120,30);
        carNumberField.setBounds(230,50,570,30);

        carTypeLabel.setFont(new java.awt.Font("楷体", 1, 15));
        carTypeLabel.setBounds(80,100,120,30);
        carBrandBox.setBounds(230,100,275,30);
        carModelBox.setBounds(525,100,275,30);

        carStateLabel.setBounds(80,150,120,30);
        carStateBox.setBounds(230,150,570,30);

        carColorLabel.setBounds(80,200,120,30);
        carColorField.setBounds(230,200,570,30);

        carRentLabel.setBounds(80,250,120,30);
        carRentField.setBounds(230,250,570,30);

        carInfoLabel.setBounds(80,310,120,30);
        carInfoArea.setBounds(230,310,570,200);

        resetButton.setBounds(80,540,80,30);
        addButton.setBounds(680,540,120,40);


        carColorField.setEditable(false);
        carRentField.setEditable(false);

        add(carNumberLabel);
        add(carNumberField);
        add(carTypeLabel);
        add(carBrandBox);
        add(carModelBox);
        add(carStateLabel);
        add(carStateBox);
//        add(carLocationLabel);
//        add(carProvinceBox);
//        add(carCityBox);
        add(carColorLabel);
        add(carColorField);
        add(carRentLabel);
        add(carRentField);
//        add(carPictureLabel);
//        add(carPictureField);
        add(carInfoLabel);
        add(carInfoArea);
        add(resetButton);
        add(addButton);

        Connection connection = DButil.getConnection();
        String sql = "select * from brand where brand_recycle_bin = 0";
        String sql1 = "select * from brand where brand_recycle_bin = 0 order by brand_id limit 1";
        String sql2 = "select * from model where model_brand = ? and model_recycle_bin = 0";
        String sql3 = "select * from state where state_type = 1 and state_recycle_bin = 0";
//        String sql4 = "select * from province where province_recycle_bin = 0";
//        String sql5 = "select * from province where province_recycle_bin = 0 order by province_id limit 1";
//        String sql6 = "select * from city where city_province = ? and city_recycle_bin = 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            PreparedStatement ps3 = connection.prepareStatement(sql3);
//            PreparedStatement ps4 = connection.prepareStatement(sql4);
//            PreparedStatement ps5 = connection.prepareStatement(sql5);
//            PreparedStatement ps6 = connection.prepareStatement(sql6);

            ResultSet rs = ps.executeQuery();
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs3 = ps3.executeQuery();
//            ResultSet rs4 = ps4.executeQuery();
//            ResultSet rs5 = ps5.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setBrandID(rs.getString(1));
                brand.setBrandName(rs.getString(2));
                carBrandBox.addItem(brand);
            }

            if (rs1.next()) {
                int brandID = rs1.getInt(1);
                ps2.setObject(1, brandID);
                ResultSet rs2 = ps2.executeQuery();
                int i = 0;
                while (rs2.next()) {
                    Model carModel = new Model();
                    carModel.setModelID(rs2.getString(1));
                    carModel.setModelName(rs2.getString(3));
                    carModel.setModelColor(rs2.getString(4));
                    carModel.setModelRent(rs2.getString(5));
                    carModelBox.addItem(carModel);

                    if (i == 0) {
                        carColorField.setText(rs2.getString(4));
                        carRentField.setText(rs2.getString(5));
                        i++;
                    }
                }
            }

            while (rs3.next()){
                State state = new State();
                state.setStateID(rs3.getString(1));
                state.setStateName(rs3.getString(2));
                carStateBox.addItem(state);
            }
//            while (rs4.next()){
//                Province province = new Province();
//                province.setProvinceID(rs4.getInt(1));
//                province.setProvinceName(rs4.getString(2));
//                carProvinceBox.addItem(province);
//            }

//            if (rs5.next()) {
//                int provinceID = rs5.getInt(1);
//                ps6.setObject(1, provinceID);
//                ResultSet rs6 = ps6.executeQuery();
//                while (rs6.next()) {
//                    City carCity = new City();
//                    carCity.setCityID(rs6.getInt(1));
//                    carCity.setCityName(rs6.getString(3));
//                    carCityBox.addItem(carCity);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(connection);
        }

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.main.removeAll();
                Main.main.repaint();
                Main.main.updateUI();

                Main.main.add(new AddCar(admin));
            }
        });

        carBrandBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                carModelBox.removeAllItems();

                Brand carBrain = (Brand) carBrandBox.getSelectedItem();
                String carBrandID = carBrain.getBrandID();

                Connection connection1 = DButil.getConnection();
                String sql3 = "select * from model where model_brand = ? and model_recycle_bin = 0";
                try {
                    PreparedStatement ps = connection1.prepareStatement(sql3);
                    ps.setObject(1,carBrandID);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        Model carModel = new Model();
                        carModel.setModelID(rs.getString(1));
                        carModel.setModelName(rs.getString(3));
                        carModel.setModelColor(rs.getString(4));
                        carModel.setModelRent(rs.getString(5));
                        carModelBox.addItem(carModel);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    DButil.releaseConnection(connection1);
                }
            }
        });

        carModelBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Model carModel = (Model) carModelBox.getSelectedItem();

                if (carModel != null) {
                    carColorField.setText(carModel.getModelColor());
                    carRentField.setText(carModel.getModelRent());
                }
            }
        });

//        carProvinceBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                carCityBox.removeAllItems();
//
//                Province carProvince = (Province) carProvinceBox.getSelectedItem();
//                int carProvinceID = carProvince.getProvinceID();
//
//                Connection connection2 = DButil.getConnection();
//                String sql4 = "select city_id, city_name from city where city_province = ?";
//
//                try {
//                    PreparedStatement ps = connection2.prepareStatement(sql4);
//                    ps.setObject(1,carProvinceID);
//                    ResultSet rs = ps.executeQuery();
//
//                    while (rs.next()) {
//                        City carCity = new City();
//                        carCity.setCityID(rs.getInt(1));
//                        carCity.setCityName(rs.getString(2));
//                        carCityBox.addItem(carCity);
//                    }
//                } catch (Exception e2) {
//                    e2.printStackTrace();
//                } finally {
//                    DButil.releaseConnection(connection2);
//                }
//            }
//        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.main.removeAll();
                Main.main.repaint();
                Main.main.updateUI();

                Main.main.add(new AddCar(admin));
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String carNumber = carNumberField.getText();
                Model carModel = (Model) carModelBox.getSelectedItem();
                String carModelID = carModel.getModelID();

                State carState = (State) carStateBox.getSelectedItem();
                String carStateID = carState.getStateID();

//                City carCity = (City) carCityBox.getSelectedItem();
//                int carCityID = carCity.getCityID();

                String carColor = carColorField.getText();

                String carInfo = carInfoArea.getText();

                if (carNumber.equals("")) {
                    JOptionPane.showMessageDialog(null, "车牌号不能为空！请输入车牌号。");
                } else {
                    int m = JOptionPane.showConfirmDialog(null, "添加","确认无误？",JOptionPane.YES_NO_OPTION);
                    if (m == 0) {
                        Connection connection3 = DButil.getConnection();
                        String sql5 = "insert into car (car_number, car_model, car_state, car_info, car_recycle_bin) values(?, ?, ?, ?, 0)";
                        try{
                            PreparedStatement ps = connection3.prepareStatement(sql5);
                            ps.setObject(1, carNumber);
                            ps.setObject(2,carModelID);
                            ps.setObject(3,carStateID);
//                        ps.setObject(3,carCityID);
                            ps.setObject(4,carInfo);

                            int n = ps.executeUpdate();

                            if (n>0) {
                                JOptionPane.showMessageDialog(null, "添加成功！");
                                Main.main.removeAll();
                                Main.main.repaint();
                                Main.main.updateUI();

                                Main.main.add(new CarList(admin));
                            } else {
                                JOptionPane.showMessageDialog(null, "添加失败！");
                            }
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        } finally {
                            DButil.releaseConnection(connection3);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

//    public static void main(String[] args) {
//        new AddCar();
//    }

}
