





// CREATE TABLE addresses (
//     address_id INT AUTO_INCREMENT PRIMARY KEY,
//     user_id INT NOT NULL,
//     user_type ENUM('student', 'teacher') NOT NULL,
//     province VARCHAR(50) NOT NULL,
//     city VARCHAR(50) NOT NULL,
//     district VARCHAR(50) NOT NULL,
//     detailed_address TEXT,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//     FOREIGN KEY (user_id) REFERENCES students(student_id) ON DELETE CASCADE,
//     FOREIGN KEY (user_id) REFERENCES teachers(teacher_id) ON DELETE CASCADE
// );
