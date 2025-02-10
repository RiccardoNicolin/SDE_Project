#!/bin/bash

if [ $# -ne 1 ]; then
    echo "You should provide your mysql username"
    exit 1
fi

ROOT=$PWD/..
MYSQL_USERNAME=$1

echo ""
echo "Setting up your system, you will be asked for your mysql password..."
echo ""

mysql -u $1 -p < $ROOT/demo/garden_manager.sql
sleep 1

cd $ROOT/frontend
npm install

echo "Your system is now ready, run . ./demo.sh to start the demo"

exit 0