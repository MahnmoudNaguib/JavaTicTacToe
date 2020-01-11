/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepadcontroller;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;
import net.java.games.input.Event;

/**
 *
 * @author Khaled
 */
public class GamePadController {

    /**
     * @param args the command line arguments
     */
    
    public GamePadController(){
        
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        // First controller of the desired type.
        Controller firstController = null;
        Controller secondController = null;
        for(int i=0; i < controllers.length && firstController == null; i++) {
            if(controllers[i].getType() == Controller.Type.STICK) {
                // Found a controller
                firstController = controllers[i];
                secondController = controllers[i+1];
                break;
            }
        }
        
        if(firstController == null || secondController == null) {
            // Couldn't find a controller
            System.out.println("Found no desired controller!");
            //System.exit(0);
        }else{

        System.out.println("First controller of a desired type is: " + firstController.getName());
        System.out.println("First controller of a desired type is: " + secondController.getName());
        }
       
    }
    
    public char getPressedButton(){
        char pressedButton=0;
        
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Controller firstController = null;
        
        for(int i=0; i < controllers.length && firstController == null; i++) {
            if(controllers[i].getType() == Controller.Type.STICK) {
                // Found a controller
                firstController = controllers[i];
                break;
            }
        }
        if(firstController != null){
            //while(true){
        firstController.poll();
        Component[] components = firstController.getComponents();
        for(int i=0;i<components.length;i++) {
                //System.out.println(components[i].getName());
                if(components[i].isAnalog()) {
                    if(components[i].getName().equalsIgnoreCase("x axis")){
                        if(components[i].getPollData() <=-0.5){
                            pressedButton='l';
                        }else{
                            if(components[i].getPollData() >= 0.5){
                            pressedButton='r';
                            }
                        }
                    }else{
                        if(components[i].getPollData() <=-0.5){
                            pressedButton='u';
                        }else{
                            if(components[i].getPollData() >= 0.5){
                            pressedButton='d';
                            }
                        }
                    }
                    //System.out.println(components[i].getPollData());
                } else {
                    if(components[i].getPollData()==1.0f) {
                        if(components[i].getName().equalsIgnoreCase("button 0")){
                            pressedButton='t';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 1")){
                            pressedButton='c';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 2")){
                            pressedButton='x';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 3")){
                            pressedButton='s';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 4")){
                            pressedButton='a';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 5")){
                            pressedButton='b';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 6")){
                            pressedButton='d';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 7")){
                            pressedButton='e';
                        }
                            }
                            }
                            }
                            }
                            }
                            }
                        }
                    }
//                        System.out.println("On");
//                    } else {
//                        System.out.println("Off");
//                    }
                }
            }
       // System.out.println(pressedButton);
       // }
        }
        return pressedButton;
    }
    
     public char getPressedButton2(){
        char pressedButton=0;
        
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Controller secondController = null;
        
        for(int i=0; i < controllers.length && secondController == null; i++) {
            if(controllers[i].getType() == Controller.Type.STICK) {
                // Found a controller
                secondController = controllers[i+1];
                break;
            }
        }
        if(secondController != null){
            //while(true){
        secondController.poll();
        Component[] components = secondController.getComponents();
        for(int i=0;i<components.length;i++) {
                //System.out.println(components[i].getName());
                if(components[i].isAnalog()) {
                    if(components[i].getName().equalsIgnoreCase("x axis")){
                        if(components[i].getPollData() <=-0.5){
                            pressedButton='l';
                        }else{
                            if(components[i].getPollData() >= 0.5){
                            pressedButton='r';
                            }
                        }
                    }else{
                        if(components[i].getPollData() <=-0.5){
                            pressedButton='u';
                        }else{
                            if(components[i].getPollData() >= 0.5){
                            pressedButton='d';
                            }
                        }
                    }
                    //System.out.println(components[i].getPollData());
                } else {
                    if(components[i].getPollData()==1.0f) {
                        if(components[i].getName().equalsIgnoreCase("button 0")){
                            pressedButton='t';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 1")){
                            pressedButton='c';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 2")){
                            pressedButton='x';
                        }else{
                            if(components[i].getName().equalsIgnoreCase("button 3")){
                            pressedButton='s';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 4")){
                            pressedButton='a';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 5")){
                            pressedButton='b';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 6")){
                            pressedButton='d';
                        }else{
                                if(components[i].getName().equalsIgnoreCase("button 7")){
                            pressedButton='e';
                        }
                            }
                            }
                            }
                            }
                            }
                            }
                        }
                    }
//                        System.out.println("On");
//                    } else {
//                        System.out.println("Off");
//                    }
                }
            }
       // System.out.println(pressedButton);
       // }
        }
        return pressedButton;
    }
     
     public boolean isControllerConnected(){
         boolean connected=false;
         Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
         for(int i=0; i < controllers.length && connected==false; i++) {
            if(controllers[i].getType() == Controller.Type.STICK) {
                // Found a controller
                connected = true;
                break;
            }
        }
         return connected;
     }
    
    
//    public static void main(String[] args) {
//        // TODO code application logic here
//        GamePadController c=new GamePadController();
//        char b1,b2;
//        while(true){
////            if(c.isControllerConnected()){
////                System.out.println("Connected");
////            }else{
////                System.out.println("Disconnected");
////            }
//             b1=c.getPressedButton();
//             b2=c.getPressedButton2();
//             if(b1!=0){
//             System.out.println(b1);
//             while(c.getPressedButton()!=0);
//             }
//             if(b2!=0){
//             System.out.println(b2);
//             while(c.getPressedButton2()!=0);
//             }
//        }
//       
//    }
    
}
