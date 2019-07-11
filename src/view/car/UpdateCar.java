package view.car;

import util.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateCar extends JDialog {

    private JLabel carModelLabel = new JLabel("车辆型号");
    private JComboBox<Brand> carBrandBox = new JComboBox<Brand>();
    private JComboBox<Model> carModelBox = new JComboBox<Model>();

    private JLabel carStateLabel = new JLabel("车辆状态");
    private JComboBox<State> carStateBox = new JComboBox<State>();

    private JLabel carLocationLabel = new JLabel("车辆地点");
    private JComboBox<Province> carProvinceBox = new JComboBox<Province>();
    private JComboBox<City> carCityBox = new JComboBox<City>();

    private JLabel carColorLabel = new JLabel("车辆颜色");
    private JTextField carColorField = new JTextField();

    private JLabel carPictureLabel = new JLabel("车辆图片");
    private JTextField carPictureField = new JTextField();

    private JLabel carInfoLabel = new JLabel("车辆备注");
    private JTextArea carInfoArea = new JTextArea();

    private JButton confirmBotton = new JButton("确认修改");

    public UpdateCar(String carID) {

        setTitle("更新车辆");
        setSize(600,700);
        setLocationRelativeTo(null);
        setLayout(null);
        setModal(true);

        carModelLabel.setBounds(50,20,100,30);
        carBrandBox.setBounds(170,20,150,30);
        carModelBox.setBounds(340,20,150,30);

        carStateLabel.setBounds(50,70,100,30);
        carStateBox.setBounds(170,70,320,30);

        carLocationLabel.setBounds(50,120,100,30);
        carProvinceBox.setBounds(170,120,150,30);
        carCityBox.setBounds(340,120,150,30);

        carColorLabel.setBounds(50,170,100,30);
        carColorField.setBounds(170,170,320,30);

        carPictureLabel.setBounds(50,220,100,30);
        carPictureField.setBounds(170,220,320,30);

        carInfoLabel.setBounds(250,280,100,30);
        carInfoArea.setBounds(100,330,400,200);

        confirmBotton.setBounds(380,550,120,40);

        add(carBrandBox);
        add(carModelLabel);
        add(carModelBox);
        add(carStateLabel);
        add(carStateBox);
        add(carLocationLabel);
        add(carProvinceBox);
        add(carCityBox);
        add(carColorLabel);
        add(carColorField);
        add(carPictureLabel);
        add(carPictureField);
        add(carInfoLabel);
        add(carInfoArea);
        add(confirmBotton);

        Connection connection = DButil.getConnection();
        String sql = "select car.*, model.model_brand, city.city_province from car, model, city where car.car_id = ? and model.model_id = car.car_model and city.city_id = car.car_city";
        String sql1 = "select * from brand";
        String sql2 = "select * from model where model_brand = ?";

        String sql3 = "select * from state";
        String sql4 = "select * from province";
        String sql5 = "select * from city where city_province = ?";



        try {
            Car car = new Car();


            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,carID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car.setCarID(rs.getInt(1));
                car.setCarModel(rs.getInt(2));
                car.setCarState(rs.getInt(3));
                car.setCarCity(rs.getInt(4));
                car.setCarPicture(rs.getString(5));
                car.setCarInfo(rs.getString(6));
                car.setCarBrand(rs.getInt(8));
                car.setCarProvince(rs.getInt(9));
            }
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setObject(1,car.getCarBrand());
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            PreparedStatement ps4 = connection.prepareStatement(sql4);
            PreparedStatement ps5 = connection.prepareStatement(sql5);
            ps5.setObject(1,car.getCarProvince());

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();
            ResultSet rs4 = ps4.executeQuery();
            ResultSet rs5 = ps5.executeQuery();


            while (rs1.next()) {
                Brand brand = new Brand();
                brand.setBrandID(rs1.getInt(1));
                brand.setBrandName(rs1.getString(2));
                carBrandBox.addItem(brand);

//                if(brand.getBrandID() == brand1.getBrandID()){
//                    carBrandBox.setSelectedItem(brand1);
//                    System.out.println(brand1.getBrandID());
//                    System.out.println(12314546);
//                }
            }

            Brand brand1 = new Brand();
            brand1 = brand1.searchBrand(car.getCarBrand());
            for (int j =0; j<carBrandBox.getItemCount();j++) {
                Brand brand;
                brand = carBrandBox.getItemAt(j);
                if (brand.getBrandID() == brand1.getBrandID()) {
                    carBrandBox.setSelectedItem(brand);
                    while (rs2.next()){
                        Model model = new Model();
                        model.setModelID(rs2.getInt(1));
                        model.setModelName(rs2.getString(3));
                        carModelBox.addItem(model);
                    }
                    Model model1 = new Model();
                    model1 = model1.searchModel(car.getCarModel());
                    for (int k =0; k<carModelBox.getItemCount();k++) {
                        Model model;
                        model = carModelBox.getItemAt(k);
                        if (model.getModelID() == model1.getModelID()) {
                            carModelBox.setSelectedItem(model);
                        }
                    }
                }
            }


            while (rs3.next()){
                State state = new State();
                state.setStateID(rs3.getInt(1));
                state.setStateName(rs3.getString(2));
                carStateBox.addItem(state);
            }
            while (rs4.next()){
                Province province = new Province();
                province.setProvinceID(rs4.getInt(1));
                province.setProvinceName(rs4.getString(2));
                carProvinceBox.addItem(province);
            }
            Province province1 = new Province();
            province1 = province1.searchProvince(car.getCarProvince());
            for (int j =0; j<carProvinceBox.getItemCount();j++) {
                Province province;
                province = carProvinceBox.getItemAt(j);
                if (province.getProvinceID() == province1.getProvinceID()) {
                    carProvinceBox.setSelectedItem(province1);
                    while (rs5.next()){
                        City city = new City();
                        city.setCityID(rs5.getInt(1));
                        city.setCityName(rs5.getString(3));
                        carCityBox.addItem(city);
                    }
                    City city1 = new City();
                    city1 = city1.searchCity(car.getCarCity());
                    for (int k =0; k<carCityBox.getItemCount();k++) {
                        City city;
                        city = carCityBox.getItemAt(k);
                        if (city.getCityID() == city1.getCityID()) {
                            carModelBox.setSelectedItem(city);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(connection);
        }



        carBrandBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                carModelBox.removeAllItems();

                Brand carBrain = (Brand) carBrandBox.getSelectedItem();
                int carBrandID = carBrain.getBrandID();

                Connection connection1 = DButil.getConnection();
                String sql3 = "select model_id, model_name from model where model_brand = ?";
                try {
                    PreparedStatement ps = connection1.prepareStatement(sql3);
                    ps.setObject(1,carBrandID);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()){
                        Model carModel = new Model();
                        carModel.setModelID(rs.getInt(1));
                        carModel.setModelName(rs.getString(2));
                        carModelBox.addItem(carModel);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    DButil.releaseConnection(connection1);
                }
            }
        });

        carProvinceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carCityBox.removeAllItems();

                Province carProvince = (Province) carProvinceBox.getSelectedItem();
                int carProvinceID = carProvince.getProvinceID();

                Connection connection2 = DButil.getConnection();
                String sql4 = "select city_id, city_name from city where city_province = ?";

                try {
                    PreparedStatement ps = connection2.prepareStatement(sql4);
                    ps.setObject(1,carProvinceID);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        City carCity = new City();
                        carCity.setCityID(rs.getInt(1));
                        carCity.setCityName(rs.getString(2));
                        carCityBox.addItem(carCity);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                } finally {
                    DButil.releaseConnection(connection2);
                }
            }
        });

        confirmBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Model carModel = (Model) carBrandBox.getSelectedItem();
                int modelID = carModel.getModelID();

            }
        });

        setVisible(true);
    }

}
