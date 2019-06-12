/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Client;

/**
 *
 * @author rshum
 */
public interface IObservable {
    
    public void addObserver(IObserver observer);
    
    public void removeObserver(IObserver observer);
    
    public void notify(Object object);
    
}
