import React from 'react';

const PlantInfo = (info) => {
    let json = null
    if (info === null) {
        return <p>No data</p>;
    } else {
        let stringy = JSON.stringify(info)
        stringy = stringy.replace(/info/g, 'info')
        let obj = JSON.parse(stringy)
        json = obj.info
        for (let i in json) {
            console.log(json[i])
            if (json[i] === null) {
                json[i] = "Not Available/Indifferent"
            }
        }
    }
    const Dime = {
        width: '200px',
    };

    return (
        <div>
            <img src={json.image} alt={json.name} style={Dime} />
            <p>INFO ABOUT {json.name}</p>
            <p>Watering: {json.watering}</p>
            <p>Sun requirement: {json.sun_requirements}</p>
            <p>Cycle: {json.cycle}</p>
            <p>Sowing method: {json.sowing_method}</p>
            <p>Harvest time: {json.harvest_time}</p>
            <p>Average time between sowing and harvesting: {json.growing_degree_days}</p>
        </div>
    );
};

export default PlantInfo;