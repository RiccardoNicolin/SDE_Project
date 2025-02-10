# GARDEN MANAGER
This application was developed as a project for the Software Design Engineering course at University of Trento.\
Garden Manager is a service-oriented application designed to help users gather information about the plants they want to plant, including the expected harvest time and weather conditions for the 10 days from the planting date.\
The system integrates services to provide a user experience which allows users to log in, search for plants and retrieve relevant weather and agricultural data.\
All the information about the API calls for each service can be found in the [wiki](https://github.com/RiccardoNicolin/SDE_Project/wiki) of the repository.
## DEMO
To run a demo of the application in your system do the following steps.
* Clone the repository
* Move to the demo directory
* Run the setup.sh script with your mysql username
  ```shell
    . ./setup.sh <your_sql_username>
    ```
* Run the demo.sh script
  ```shell
    . ./demo.sh
    ```
* Navigate to http://localhost:3000
