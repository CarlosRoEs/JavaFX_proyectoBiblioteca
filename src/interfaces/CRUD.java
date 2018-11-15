/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;

/**
 * 
 * @author Carlos
 * @param <T> Depende del tipo de objeto que se le pase
 */
public interface CRUD<T> {
    
    public boolean add(T t);
    public boolean update(T t);
    public boolean remove(T t);
    public List<T> getList();
}
