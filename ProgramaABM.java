/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pedro Narvaez
 */
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProgramaABM extends JFrame {
    private ArrayList<Autor> autores;
    private JTable tablaAutores;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;

    public ProgramaABM() {
        autores = new ArrayList<>();

        setTitle("Programa ABM de Autores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear tabla para mostrar los autores
        String[] columnas = {"ID", "Nombre", "Apellido"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        tablaAutores = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaAutores);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Panel para los campos de texto
        JPanel panelCampos = new JPanel(new GridLayout(3, 2));

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField();

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblApellido);
        panelCampos.add(txtApellido);

        panelPrincipal.add(panelCampos, BorderLayout.SOUTH);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener((ActionEvent e) -> {
            agregarAutor();
        });

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarAutor();
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarAutor();
            }
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirDelPrograma();
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.NORTH);

        tablaAutores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int filaSeleccionada = tablaAutores.getSelectedRow();
                if (filaSeleccionada != -1) {
                    cargarDatosAutor(filaSeleccionada);
                }
            }
        });

        add(panelPrincipal);
    }

    private void agregarAutor() {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        Autor autor = new Autor(id, nombre, apellido);
        autores.add(autor);

        actualizarTabla();
        limpiarCampos();
    }

    private void modificarAutor() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        if (filaSeleccionada != -1) {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();

            Autor autor = autores.get(filaSeleccionada);
            autor.setId(id);
            autor.setNombre(nombre);
            autor.setApellido(apellido);

            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Autor modificado exitosamente.");
        }
    }

    private void eliminarAutor() {
        int filaSeleccionada = tablaAutores.getSelectedRow();
        if (filaSeleccionada != -1) {
            autores.remove(filaSeleccionada);

            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Autor eliminado exitosamente.");
        }
    }

    private void cargarDatosAutor(int fila) {
        Autor autor = autores.get(fila);
        txtId.setText(autor.getId());
        txtNombre.setText(autor.getNombre());
        txtApellido.setText(autor.getApellido());
    }

    private void actualizarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaAutores.getModel();
        modeloTabla.setRowCount(0);

        for (Autor autor : autores) {
            Object[] fila = {autor.getId(), autor.getNombre(), autor.getApellido()};
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
    }

    private void salirDelPrograma() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas salir del programa?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            dispose(); // Cerrar la ventana
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProgramaABM programa = new ProgramaABM();
                programa.setVisible(true);
            }
        });
    }
}

