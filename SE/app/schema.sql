CREATE TABLE department (
    dept varchar(25) primary key,
    hod varchar(20)
);

CREATE TABLE students (
    name varchar(50),
    student_id varchar(20) primary key,
    dept varchar(25),
    year_of_entry int,
    program varchar(5),
    address varchar(100),
    phone varchar(20),
    foreign key (dept) references department(dept)
);

CREATE TABLE courses (
    course_id char(5) primary key,
    dept varchar(25),
    L int,
    T int,
    P int,
    C float,
    pre_req varchar(5)[][],
    foreign key (dept) references department(dept)
);

CREATE TABLE faculty (
    name varchar(50),
    faculty_id varchar(20) primary key,
    dept varchar(25),
    doj date,
    phone varchar(20),
    address varchar(100),
    foreign key (dept) references department(dept)
);

CREATE TABLE offering (
    course_id char(5),
    sem int,
    year int,
    faculty_id varchar(20),
    cg_constraint float,
    primary key(course_id, sem, year),
    foreign key (course_id) references courses(course_id),
    foreign key (faculty_id) references faculty(faculty_id)
);

CREATE TABLE student_course (
    student_id varchar(20),
    course_id char(5),
    sem int,
    year int,
    status char,
    primary key(student_id, course_id, sem, year),
    foreign key (student_id) references students(student_id),
    foreign key (course_id, sem, year) references offering(course_id, sem, year)
);

CREATE TABLE grades (
    student_id varchar(20),
    course_id char(5),
    credits float,
    sem int,
    year int,
    grade int,
    primary key(student_id, course_id, sem, year),
    foreign key (student_id) references students(student_id),
    foreign key (course_id, sem, year) references offering(course_id, sem, year)
);

CREATE TABLE degree_criteria (
    year int,
    dept varchar(25),
    program varchar(5),
    PC varchar(5) [],
    PEmin float,
    HSmin float,
    SCmin float,
    OEmin float,
    BTP varchar(5)[],
    primary key(year, dept,program),
    foreign key (dept) references department(dept)
);

CREATE TABLE users (
    username varchar(20) primary key,
    password varchar(20),
    role int
);
 
CREATE TABLE current_event (
    event varchar(20),
    sem int,
    year int,
    start_date date,
    end_date date,
    PRIMARY KEY (event, sem, year, start_date, end_date)
);

CREATE TABLE log (
    username varchar(20),
    time timestamp,
    event varchar(20),
    primary key(username, time),
    foreign key (username) references users(username)
);

