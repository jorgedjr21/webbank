/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge
 */
public class CookieUtilities {
    
    public static boolean findCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies =request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                if(cookieName.equals(c.getName())){
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }
    
    
    public static Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                if(cookieName.equals(c.getName())){
                    return c;
                }
            }
        }else{
            return null;
        }
        return null;
    }
    
    public static void deleteCookies(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
    }
    
}
