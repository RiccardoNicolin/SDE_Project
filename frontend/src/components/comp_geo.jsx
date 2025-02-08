import React from 'react';


    const geolocation = () => {
        let res = "";
        console.log("getLocation");
        if (navigator.geolocation) {
            res = navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            res = "Geolocation is not supported by this browser.";
        }
        return res;
    }

    function showPosition(position) {
        return "Latitude: " + position.coords.latitude +
            "<br>Longitude: " + position.coords.longitude;
    }


const CompGeo = () => {
   geolocation();

    return (
        <div>
            <div>AAA</div>
        </div>
    );
};

export default CompGeo;