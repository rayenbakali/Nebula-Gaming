/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import tn.nebulagaming.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.nebulagaming.entities.User;

/**
 *
 * @author ibeno
 */
public class ServiceUser {

    public static ServiceUser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public String resultAdd;
    public String loginResult;

    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }
    
    

    public String checkUserUnique(User u) {
        String url = Statics.BASE_URL + "/checkUserUnique?email=" + u.getEmail();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultAdd = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultAdd;
    }

    public String loginCheck(String email, String password) {
        String url = Statics.BASE_URL + "/loginCheck?email=" + email + "&password=" + password;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                loginResult = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(loginResult + " login test");
        return loginResult;
    }
    
    public boolean modifierUserMotDePasse(String email,String password) {

        String url = Statics.BASE_URL + "/modifyPasswordUser?email=" + email + "&password=" + password;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
     public String forgetPasswordCheck(String email) {
        String url = Statics.BASE_URL + "/getCodeM?email=" + email ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                loginResult = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return loginResult;
    }

}
