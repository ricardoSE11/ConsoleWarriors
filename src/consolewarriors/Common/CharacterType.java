/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Common;

import java.io.Serializable;

/**
 *
 * @author rshum
 */
public enum CharacterType implements Serializable {
    
    FIRE , AIR , WATER , WHITE_MAGIC , BLACK_MAGIC , ELECTRICITY , ICE , ACID , SPIRITUALITY , IRON;
    
    public static CharacterType getCharacterTypeValue(String string){
        switch(string){
            case "WHITE MAGIC":{
                return CharacterType.WHITE_MAGIC;
            }
            
            case "BLACK MAGIC":{
                return CharacterType.BLACK_MAGIC;
            }
            
            default:{
                return valueOf(string);
            }
        }
    }
    
    public String stringRepresentationOfCharacterType(){
        switch(this){
            case WHITE_MAGIC: {
                return "WHITE MAGIC";
            }

            case BLACK_MAGIC: {
                return "BLACK MAGIC";
            }

            default: {
                return this.toString();
            }
        }
    }

}
