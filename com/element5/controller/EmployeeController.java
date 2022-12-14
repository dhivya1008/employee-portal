package com.element5.controller;

import com.element5.service.EmployeeService;
import com.element5.service.impl.EmployeeServiceImpl;
import com.element5.util.EmployeeUtils;
import com.element5.exception.InvalidException;
import com.element5.model.Trainer;
import com.element5.model.Trainee;

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.Map;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * <h3>EmployeeController</h3>
 * This class is used to get inputs from the user and stored them in the database and display the output to the user
 *
 * @author Dhivya
 * @version 1.1
 * @since 2022
 *
 */

public class EmployeeController {

    static Scanner scanner = new Scanner(System.in); 
    static private EmployeeService employeeservice = new EmployeeServiceImpl();
    static private Logger logger = LoggerFactory.getLogger(EmployeeController.class);
  
    public static void menu() {  
        String role;
        int inputs = 0;
        
        while(inputs!=9) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HI!!CHOOSE AN OPTION FROM THE FOLLOWING:\n")
                         .append("1. Add an Trainer Details\n")
                         .append("2. Add an Trainee Details\n")
                         .append("3. View an Employee Details\n")
                         .append("4. Show all Employee Details\n")
                         .append("5. Update an Employee Details\n")
                         .append("6. Remove an Employee details\n")
                         .append("7. Assign trainers to trainee\n")
                         .append("8. Assign trainees to trainer\n")
                         .append("9. Stop");
            System.out.println(stringBuilder);                
            inputs = scanner.nextInt();

            switch(inputs) { 
            case 1 :
                setTrainerDetails();
                break;              
            case 2 :
                setTraineeDetails();
                break;
            case 3:            
                System.out.println("1. Trainer Details\n2. Trainee Details\n3. Exit");
                int choice = scanner.nextInt();

                switch(choice) {      
                case 1 :
                     logger.info("Enter the Trainer Id:");
                     int trainerId = scanner.nextInt();
                     Trainer trainer = employeeservice.viewTrainerDetailsById(trainerId);
                     if (trainer != null ) {
                         System.out.println("The Trainer Details:" +"\n"+ employeeservice.viewTrainerDetailsById(trainerId));     
                     } else {
                         logger.info("No trainer available");
                     }                                      
                     break;
                case 2 :
                     StringBuilder stringBuild = new StringBuilder();
                     logger.info("Enter the Trainee Id:");
                     int traineeId = scanner.nextInt();
                     System.out.println("The Trainee Details:" +"\n"+ employeeservice.viewTraineeDetailsById(traineeId)); 
                     Trainee trainee = employeeservice.viewTraineeDetailsById(traineeId);
                     for(int i = 0; i < trainee.getTrainerDetails().size() ; i++) {
                         stringBuild.append("Trainer Name: " + trainee.getTrainerDetails().get(i).getName() +"\n")
                                    .append("Trainer Id:" + trainee.getTrainerDetails().get(i).getId() +"\n")
                                    .append("Trainer Email:" + trainee.getTrainerDetails().get(i).getEmail());
                                     System.out.println(stringBuild);
                }    
                break;
                case 3 :
                    logger.info("you exited the portal");
                    break;
                }             
                break;
             case 4:             
                 System.out.println("1. Trainer Details\n2. Trainee Details\n 3.Exit");
                 choice = scanner.nextInt();

                 switch(choice) {      
                 case 1 :
                    logger.info("The Trainers Details:" +"\n"+ employeeservice.viewAllTrainerDetails()); 
                    break;
                 case 2 :
                    logger.info("The Trainee Details:" + employeeservice.viewAllTraineeDetails());
                    break;
                 case 3 :
                    logger.info("you exited the portal");
                    break;                  
                 }
                 break;
             case 5 :
                 System.out.println("whom you want to update:\n1. Trainer Details\n2. Trainee Details\n3. Exit");
                 choice = scanner.nextInt();

                 switch(choice) {             
                 case 1 :   
                     System.out.println("Enter the ID of an Employee You want to update:");
                     int trainerId = scanner.nextInt();
                     Trainer trainerDetails = employeeservice.viewTrainerDetailsById(trainerId);
                     Trainer updatedTrainerDetails  = updateTrainerDetails(trainerDetails);
                     employeeservice.updateTrainerDetailsById(trainerId, updatedTrainerDetails);
                     break;
                 case 2 :     
                     System.out.println("Enter the ID of an Employee You want to update :");
                     int traineeId = scanner.nextInt();
                     Trainee traineeDetails = employeeservice.viewTraineeDetailsById(traineeId);
                     Trainee updatedTraineeDetails = updateTraineeDetails(traineeDetails);
                     employeeservice.updateTraineeDetailsById(traineeId, updatedTraineeDetails);
                     break; 
                 case 3 :
                     logger.info("you exited the portal");
                     break;
                 }
                 break;             
            case 6 : 
                System.out.println("1. Trainer Details\n2. Trainee Details\n3. Exit");
                choice = scanner.nextInt();

                switch(choice) {     
                case 1 :
                     logger.info("Enter the Trainer Id:");
                     int trainerId = scanner.nextInt();
                     employeeservice.deleteTrainerDetailsById(trainerId); 
                     break;
                case 2 :
                     logger.info("Enter the Trainee Id:");
                     int traineeId = scanner.nextInt();
                     employeeservice.deleteTraineeDetailsById(traineeId);
                     break;
                case 3 :
                     logger.info("you exited the portal");
                     break;
                }
                break; 
             case 7 :                  
                  logger.info("Enter the TraineeId you want to assign trainer");
                  int traineeId = scanner.nextInt();
                  assignTrainersToTrainee(traineeId);
                  break;
             case 8 :                 
                  logger.info("Enter the TrainerId you want to assign trainees");
                  int trainerId = scanner.nextInt();
                  assignTraineesToTrainer(trainerId);
                  break;                                                      
             case 9 :                  
                  logger.info("You Exited the portal");
                  break;             
             }
        } 
    }
  
    /**
     * This method is used to set details about the trainer
     *
     */
    public static void setTrainerDetails() {

        scanner.nextLine();
        logger.info("Enter the EmployeeName:");
        String name = getName();
          
        int id = 0;

        logger.info("Enter the Email Address");
        String email = getEmail();
           
        logger.info("Enter the Designation:");
        String designation = scanner.nextLine();
                     
        logger.info("Enter the Contact Number:");
        String contactNumber = getContact();
        long mobileNumber = Long.parseLong(contactNumber);
  
        logger.info("Enter Your Date Of Birth in dd-mm-yyyy format:");
        String dateOfBirth = getBirthDate();

        logger.info("Enter the Experience:");
        int experience = scanner.nextInt();
        scanner.nextLine();

        logger.info("Enter the Date Of Joining in dd-mm-yyyy format:");
        String dateOfJoining = getDate();

        logger.info("Enter the Project Name:"); 
        String projectName = scanner.nextLine();
                
        employeeservice.addTrainer(id, name, mobileNumber, email, designation,
                                   dateOfBirth, projectName, dateOfJoining, experience);
                           
        System.out.println("*****************************************************");
        logger.info("Trainer Details are successfully added");
        System.out.println("*******************************************************");              
    } 

    /**
     * This method is used to set trainee details
     *
     */
    public static void setTraineeDetails() {
         
        scanner.nextLine();
        logger.info("Enter the EmployeeName:");
        String name = getName();

        int id = 0;

        logger.info("Enter the Email Address");
        String email = getEmail();
           
        logger.info("Enter the Designation:");
        String designation = scanner.nextLine();
                     
        logger.info("Enter the Contact Number:");
        String contactNumber = getContact();
        long mobileNumber = Long.parseLong(contactNumber);
  
        logger.info("Enter Your Date Of Birth in dd-mm-yyyy format:");
        String dateOfBirth = getBirthDate();
           
        logger.info("Enter the pass out year:");
        String passOutYear = scanner.nextLine();

        logger.info("Enter the Task Name:"); 
        String taskName = scanner.nextLine();

        logger.info("Enter your college name:");
        String collegeName = scanner.nextLine();
                        
        employeeservice.addTrainee(id, name, mobileNumber, email, designation,
                                   dateOfBirth, taskName, passOutYear, collegeName);
                           
        System.out.println("*****************************************************");
        logger.info("Trainee Details are successfully added");
        System.out.println("*******************************************************");
    }
                    
    /**
     * This method is used to get informations from the user and update informations about trainer
     *
     * @return updated trainer details 
     */
    public static Trainer updateTrainerDetails(Trainer trainerDetails) {

        logger.info("Enter the value you want to update otherwise press enter to skip"); 
        scanner.nextLine();        
        logger.info("Enter the update Name:");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            trainerDetails.setName(name);
        }
        logger.info("Enter the update Designation:");
        String designation = scanner.nextLine();
        if (!designation.isEmpty()) {
            trainerDetails.setDesignation(designation);
        }
        logger.info("Enter the update ContactNumber:");
        String contactNumber = scanner.nextLine();
        if (!contactNumber.isEmpty()) {
            long mobileNumber = Long.parseLong(contactNumber); 
              trainerDetails.setMobileNumber(mobileNumber);
        }
        logger.info("Enter the update Experience:");
        String experience = scanner.nextLine();
        if (!experience.isEmpty()) {
            int updatedExperience = Integer.valueOf(experience);
            trainerDetails.setExperience(updatedExperience);
        }
        logger.info("Enter the update Project Name:");
        String projectName = scanner.nextLine();
        if (!projectName.isEmpty()) {
            trainerDetails.setProjectName(projectName);
        }
        logger.info("Enter the update DateOfBirth:");
        String dateOfBirth = scanner.nextLine();
        if (!dateOfBirth.isEmpty()) {
            trainerDetails.setBirthDate(dateOfBirth);
        }
        logger.info("Enter the update DateOfJoining:");
        String dateOfJoining = scanner.nextLine();
        if (!dateOfJoining.isEmpty()) {
            trainerDetails.setJoiningDate(dateOfJoining);
        }
        logger.info("Enter the update Email");
        String email = scanner.nextLine();
        if(!email.isEmpty()) {
           trainerDetails.setEmail(email);
        }
        return trainerDetails;
     } 

     /**
      * This method is used to get informations from the user and update informations about trainee
      *
      * @return updated trainee details 
      */
     public static Trainee updateTraineeDetails(Trainee traineeDetails) {

         logger.info("Enter the value you want to update otherwise press enter to skip"); 
         scanner.nextLine();        
         logger.info("Enter the update Name:");
         String name = scanner.nextLine();
         if (!name.isEmpty()) {
             traineeDetails.setName(name);
         }
         logger.info("Enter the update Designation:");
         String designation = scanner.nextLine();
         if (!designation.isEmpty()) {
             traineeDetails.setDesignation(designation);
         }
         logger.info("Enter the update ContactNumber:");
         String contactNumber = scanner.nextLine();
         if (!contactNumber.isEmpty()) {
             long mobileNumber = Long.parseLong(contactNumber); 
             traineeDetails.setMobileNumber(mobileNumber);
         }
         logger.info("Enter the update Task Name:");
         String taskName = scanner.nextLine();
         if (!taskName.isEmpty()) {
             traineeDetails.setTaskName(taskName);
         }
         logger.info("Enter the update DateOfBirth:");
         String dateOfBirth = scanner.nextLine();
         if (!dateOfBirth.isEmpty()) {
             traineeDetails.setBirthDate(dateOfBirth);
         }
         logger.info("Enter the update PassOutYear:");
         String passOutYear = scanner.nextLine();
         if (!passOutYear.isEmpty()) {
             traineeDetails.setPassOutYear(passOutYear);
         }
         logger.info("Enter the update Email");
         String email = scanner.nextLine();
         if (!email.isEmpty()) {
             traineeDetails.setEmail(email);
         }
         logger.info("Enter the update College Name:");
         String collegeName = scanner.nextLine();
         if (!collegeName.isEmpty()) {
             traineeDetails.setCollegeName(collegeName);
         } 
         return traineeDetails;
    }

    /**
     * This method is used to assign trainer to trainee
     *
     */
    public static void assignTrainersToTrainee(int traineeId) {

        Trainee trainee = employeeservice.viewTraineeDetailsById(traineeId);
        if (trainee!=null) {
            logger.info("how many trainers you want t assign");
            List<Trainer> trainerDetails = new ArrayList<>();
            int noOfTrainers = scanner.nextInt();

             while(noOfTrainers > 0) {
                 logger.info("Enter the TrainerId you want to assign to this Trainer:");
                 int trainerId = scanner.nextInt();
                 Trainer trainer = employeeservice.viewTrainerDetailsById(trainerId);
                 if (trainer!=null) {
                     trainerDetails.add(trainer);
                     trainee.setTrainerDetails(trainerDetails);
                     employeeservice.changeTraineeDetailsById(traineeId, trainee);
                     noOfTrainers--;
                 } else {
                     logger.error("No trainee Details found");
                     noOfTrainers--;
                 }
              }
          } else {
              logger.error("No trainee details found");
           }
    }

    /**
     * This method is used to assign trainees to trainer
     *
     */
    public static void assignTraineesToTrainer(int trainerId) {

        Trainer trainer = employeeservice.viewTrainerDetailsById(trainerId);
        if (trainer!=null) {
            logger.info("how many trainees you want to add");
            List<Trainee> traineeDetails = new ArrayList<>();
            int noOfTrainees = scanner.nextInt();

            while(noOfTrainees > 0) {
                logger.info("Enter the TraineeId you want to assign to this Trainer:");
                int traineeId = scanner.nextInt();
                Trainee trainee = employeeservice.viewTraineeDetailsById(traineeId);
                if (trainee!=null) {
                    traineeDetails.add(trainee);
                    trainer.setTraineeDetails(traineeDetails);
                    employeeservice.changeTrainerDetailsById(trainerId, trainer);
                    noOfTrainees--;
                } else {
                    logger.error("No trainee Details found");
                    noOfTrainees--;
                }
             }
          } else {
               logger.error("No trainer details found");
          }
    }

    /**
     * This method is used to validate name
     *
     * @return name
     */               
    public static String getName() { 
        boolean isValidName = true;
        String name = null;

        while(isValidName) {
            name = scanner.nextLine();
            boolean isValidFormat = EmployeeUtils.isNameValidated(name);
            if (isValidFormat) {
                isValidName = false;
            } else {
                isValidName = true;
                logger.info("Numbers, special characters are not allowed");
                System.out.println("Please enter the name correctly");
            }
         }
          return name;
    }

    /**
     * This method is used to validate contact number
     *
     * @return contact number
     *
     */
    public static String getContact() { 
        boolean isValidContact = true;
        String contactNumber = null;

        while(isValidContact) {
            contactNumber = scanner.nextLine();
            boolean isValidFormat = EmployeeUtils.isNumberValidated(contactNumber);
            if (isValidFormat) {
                isValidContact = false;
            } else {
                isValidContact = true;
                logger.error("Contact Number should only contain numbers from 0-9:");
                System.out.println("Please enter valid input");
            }
         }          
          return contactNumber;
    }
      
    /**
      * This method is used to validate date
      *
      * @return date
      *
      */ 
    public static String getDate() { 
        boolean isValidDate = true;
        String date = null;
        while(isValidDate) {
            date = scanner.nextLine();
            boolean isValidFormat = EmployeeUtils.isDateValidated(date); 
            if (isValidFormat) {
                isValidDate = false;
            } else {
                isValidDate = true;
                System.out.println("Please enter the date of Joining in this dd-mm-yyyy");
            }
        }
        return date;
    }

    /**
      * This method is used to validate Birthdate
      *
      * @return Birthdate
      *
      */ 
    public static String getBirthDate() { 
        boolean isValidDate = true;
        String birthDate = null;
        while(isValidDate) {
            birthDate= scanner.nextLine();
            boolean isValidFormat = EmployeeUtils.isBirthDateValidated(birthDate); 
            if (isValidFormat) {
                isValidDate = false;
            } else {
                isValidDate = true;
                System.out.println("please enter birth date in this format: dd-mm-yyyy");
            }
        }
        return birthDate;
    }

    /**
      * This method is used to validate email
      *
      * @return email
      *
      */   
     public static String getEmail() { 
         boolean isValidEmail = true;
         String email = null;
         while(isValidEmail) {
             email = scanner.nextLine();
             boolean isValidFormat = EmployeeUtils.isEmailValidated(email); 
             if (isValidFormat) {
                 isValidEmail = false;
             } else {
                 isValidEmail= true;
                 logger.error("Email Address should be in this format: eg:abc@def.com");
                 System.out.println("Please enter valid email");
             }
         }
         return email;
      }                               
} 
          

  
             
             
       
  