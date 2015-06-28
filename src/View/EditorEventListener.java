/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.EventListener;

/**
 *
 * @author Lilium Aikia
 */
public interface EditorEventListener extends EventListener
{
    public void authorizeSucess();
    public void authorizeFailed();
}
