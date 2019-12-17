CREATE DATABASE zkeeper;
USE zkeeper;

DROP TABLE IF EXISTS `zookeeper_cluster`;
CREATE TABLE `zookeeper_cluster` (
  `cluster_id` int(11) NOT NULL auto_increment,
  `cluster_name` varchar(255) NOT NULL,
  `server_list` varchar(255) NOT NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`cluster_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf-8;