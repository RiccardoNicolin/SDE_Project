#!/bin/bash

ROOT=$PWD/..

stty -echoctl # hide ^C
trap ctrl_c INT

ctrl_c() {
    echo "Exiting, killing other processes..."
    
    if [ -n "$coordinator_service_pid" ]; then
        sudo kill $coordinator_service_pid
    fi
    
    if [ -n "$weather_service_pid" ]; then
        sudo kill $weather_service_pid
    fi
    
    if [ -n "$green_service_pid" ]; then
        sudo kill $green_service_pid
    fi

    if [ -n "$database_service_pid" ]; then
        sudo kill $database_service_pid
    fi

    if [ -n "$frontend_service_pid" ]; then
        sudo kill $frontend_service_pid
    fi

    echo "done."
    exit 0
}

echo "----------------------------"
echo "Starting database service..."

cd $ROOT/database_service
mvn spring-boot:run &> log_database_service.log &
database_service_pid=$!
sleep 1

echo "----------------------------"
echo "Starting green service..."

cd $ROOT/green_service
mvn spring-boot:run &> log_green_service.log &
green_service_pid=$!
sleep 1

echo "----------------------------"
echo "Starting weather service..."

cd $ROOT/weather_service
mvn spring-boot:run &> log_weather_service.log &
weather_service_pid=$!
sleep 1

echo "----------------------------"
echo "Starting coordinator service..."

cd $ROOT/Coordinator
mvn spring-boot:run &> log_coordinator_service.log &
coordinator_service_pid=$!
sleep 1

echo "----------------------------"
echo "Starting frontend service..."

cd $ROOT/frontend
npm start | tee log_frontend_service.log &
frontend_service_pid=$!

wait $frontend_service_pid


