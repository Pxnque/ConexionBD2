/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

 

public class ConexionBD extends JFrame{
        private  ChartPanel chartPanel;
        private int zona1,zona2,zona3,zona4,zona5,zona6;
    public ConexionBD(){
         setTitle("Gráfico de Datos desde Base de Datos");
        setSize(800, 00);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        // Configuración de la conexión a la base de datos
        String jdbcURL = "jdbc:mysql://localhost:3306/graficacionventa";
        String usuario = "root";
        String contraseña = "";
        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(jdbcURL, usuario, contraseña);
            // Consulta SQL
            String consulta = "SELECT * FROM listadoventas";
            // Preparar la sentencia SQL
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
            // Ejecutar la consulta
            ResultSet resultado = sentencia.executeQuery();
            // Procesar los resultados
            /*while (resultado.next()) {
                int codigo = resultado.getInt("codigo");
                String nombres = resultado.getString("nombres");
                String lugar_nacimiento = resultado.getString("lugar_nacimiento");
                String fechaNacimiento = resultado.getString("fecha_nacimiento");
                String direccion = resultado.getString("direccion");
                String telefono = resultado.getString("telefono");
                String puesto = resultado.getString("puesto");
                int estado = resultado.getInt("estado");
                
                System.out.println("Codigo: " + codigo + ", Nombres: " + nombres + 
                        ", Lugar nacimiento: " + lugar_nacimiento + ", Fecha nacimiento: " + fechaNacimiento + ", direccion: " + direccion + ",telefono: "
                        + telefono + ", Puesto: " + puesto + ", estado: " + estado);
            }*/
            // Crea un gráfico de barras
            

            // Crea un conjunto de datos
             // Crear un mapa para contar las zonas
            HashMap<String, Integer> zonaCount = new HashMap<>();

            while (resultado.next()) {
                String zona = resultado.getString("Zona");
                // Verificar si la zona ya está en el mapa
                if (zonaCount.containsKey(zona)) {
                    int count = zonaCount.get(zona);
                    zonaCount.put(zona, count + 1);
                } else {
                    zonaCount.put(zona, 1);
                }
            }

            // Crear un conjunto de datos
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Agregar los valores al conjunto de datos
            for (String zona : zonaCount.keySet()) {
                int count = zonaCount.get(zona);
                dataset.addValue(count, "UNIDADES", zona);
            }

            // Crea un gráfico de barras
            JFreeChart chart = ChartFactory.createBarChart("Unidades vendidas por zona", "Zona", "Unidades", dataset);

            chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 800));
            getContentPane().add(chartPanel, BorderLayout.CENTER);

            

 

            // Cerrar la conexión
            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            ConexionBD app = new ConexionBD();
            app.setVisible(true);
        });
    }
}