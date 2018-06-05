/*
@describe: Create database and tables for Youcan
@author: Zhang Jiachang
@date: 2018/1/23
@email: 2218982471@qq.com
*/

drop database if exists youcan;
create database youcan;
use youcan;

# id ：Just index for all, can't do anything

create table user(
	id int unsigned primary key not null auto_increment,
	phone char(11) not null,
	password varchar(30) not null,
	school varchar(80), # Can be null
	major varchar(80), # Can be null
	name varchar(80), # Can be null
	sex varchar(5) # man/women/null（Can be null）
);

create table open(
	id int unsigned primary key not null auto_increment,
	phone char(11) not null,
	date varchar(30),
	open char(11)  
);

create table content(
	id int unsigned primary key not null auto_increment,
	phone char(11) not null,
	date varchar(30) not null,
	time varchar(30) not null,
	content text not null,
	priority tinyint not null,  # 0/1/2
	done tinyint not null  # 0/1
);

create table fans(
	id int unsigned primary key not null auto_increment,
	phone char(11) not null,
	fans char(11) not null
);

create table idols(
	id int unsigned primary key not null auto_increment,
	phone char(11) not null,
	idols char(11) not null
);
