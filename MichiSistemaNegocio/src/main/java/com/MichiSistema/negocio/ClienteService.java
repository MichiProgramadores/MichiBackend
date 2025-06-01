/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.MichiSistema.negocio;

import com.MichiSistema.dominio.Cliente;
import java.util.ArrayList;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public interface ClienteService {
    void registrarCliente(Cliente cliente) throws Exception;
    void actualizarCliente(Cliente cliente) throws Exception;
    void eliminarCliente(int idCliente) throws Exception;
    Cliente obtenerCliente(int idCliente) throws Exception;
    ArrayList<Cliente> listarClientes() throws Exception;
}
