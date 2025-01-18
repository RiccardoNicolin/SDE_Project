import React from 'react';
import { Navigate } from 'react-router-dom';
import {jwtDecode} from "jwt-decode";

function validJwt() {
    let flag = false;

    //check user has JWT token
    let jwt = sessionStorage.getItem("token")
    sessionStorage.getItem("token") ? flag=true : flag=false

    if(flag){
        let jwtData = jwtDecode(jwt)
        let expirationDate = jwtData.exp * 1000
        let current_time = Date.now()
        if(current_time > expirationDate){
            console.log("Token expired")
            flag = false
        }
    }
   
    return flag
}


export default function RouteGuard({ children }) {

  const isAuthenticated = validJwt();

  return isAuthenticated ? children : <Navigate to="/login" />;

}
