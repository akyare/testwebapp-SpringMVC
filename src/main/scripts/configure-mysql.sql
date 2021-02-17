# connect to mysql and run as root user
#Create Databases
CREATE DATABASE web_dev;
CREATE DATABASE web_prod;

#Create database service accounts
CREATE USER 'web_dev_user'@'localhost' IDENTIFIED BY 'intec';
CREATE USER 'web_dev_user'@'%' IDENTIFIED BY 'intec';

#Database grants
GRANT SELECT ON web_dev.* to 'web_dev_user'@'localhost';
GRANT INSERT ON web_dev.* to 'web_dev_user'@'localhost';
GRANT DELETE ON web_dev.* to 'web_dev_user'@'localhost';
GRANT UPDATE ON web_dev.* to 'web_dev_user'@'localhost';
GRANT SELECT ON web_dev.* to 'web_dev_user'@'%';
GRANT INSERT ON web_dev.* to 'web_dev_user'@'%';
GRANT DELETE ON web_dev.* to 'web_dev_user'@'%';
GRANT UPDATE ON web_dev.* to 'web_dev_user'@'%';