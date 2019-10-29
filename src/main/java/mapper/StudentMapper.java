package mapper;

import concurrency.LockManager;
import connection.DBConnection;
import domain.Subject;
import domain.user.Student;
import domain.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Database operations regarding students
 */
public class StudentMapper {
    private static StudentMapper studentMapper;
    private DegreeMapper degreeMapper;
    private SubjectMapper subjectMapper;
    private UserMapper userMapper;

    /**
     * Private constructor to avoid instantiating an object
     */
    private StudentMapper() {
        degreeMapper = DegreeMapper.getInstance();
        subjectMapper = SubjectMapper.getInstance();
        userMapper = UserMapper.getInstance();
    }

    /**
     * Singleton method to get the mapper instance
     *
     * @return a StudentMapper instance
     */
    public static StudentMapper getInstance() {
        if (studentMapper == null) {
            synchronized (UserMapper.class) {
                if (studentMapper == null) {
                    studentMapper = new StudentMapper();
                }
            }
        }
        return studentMapper;
    }

    /**
     * Get a student using id
     * @param id the id of the student
     * @return a student object
     */
    public Student getStudentById(int id){
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "select username,password,role,firstName,lastName,gender,email,phone,address,degreeName from ( user t1 left join user_deg t2 on t1.id=t2.userid) left join degree t3 on t2.degreeid=t3.id where t1.id=?;";
        Student student = null;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            conn.commit();
            rs.next();
            String username = rs.getString("username");
            String password = rs.getString("password");
            int role = rs.getInt("role");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int gender = rs.getInt("gender");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String degreeName = rs.getString("degreeName");
            student= new Student(id,username,password,role,firstName,lastName,gender,email,phone,address,degreeName);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
        } finally {
            DBConnection.closeConnection(conn);
        }

        return student;
    }

    /**
     * Set the degree of a student
     * @param user a user object which is the student
     * @param degreeId the id of the degree to be set
     * @return number of rows affected
     */
    public int setDegreeOfStudent(User user, int degreeId) {
        try {
            LockManager.getInstance().acquireWriteLock(user.getUsername());
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock failed");
        }
        User cache =  Registry.loadUser(user.getUsername());
        if (cache != null
                && cache.getDegrees() != null
                && !cache.getDegrees().isEmpty()) {
            if (cache.getDegrees().get(0) == degreeId) {
                LockManager.getInstance().releaseWriteLock(user.getUsername());
                return 1;
            }
        }
        Connection conn = DBConnection.getInstance().getConnection();
        String delete = "DELETE FROM user_deg WHERE userid = ?";
        String insert = "INSERT INTO user_deg (userid, degreeid) VALUES (?, ?)";
        try {
            PreparedStatement deleteStatement = conn.prepareStatement(delete);
            PreparedStatement insertStatement = conn.prepareStatement(insert);
            deleteStatement.setInt(1, user.getId());
            insertStatement.setInt(1, user.getId());
            insertStatement.setInt(2, degreeId);
            deleteStatement.executeUpdate();
            insertStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            if (cache != null && cache.getDegrees() != null) {
                ArrayList<Integer> degree = cache.getDegrees();
                if (!degree.isEmpty()) {
                    degree.set(0, degreeId);
                } else {
                    degree.add(degreeId);
                }
            }
            LockManager.getInstance().releaseWriteLock(user.getUsername());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
            LockManager.getInstance().releaseWriteLock(user.getUsername());
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Get the degrees of a student
     * @param username the username of the student
     * @return a list of degrees
     */
    public ArrayList<Integer> getDegreesOfStudent(String username) {
        try {
            LockManager.getInstance().acquireReadLock(username);
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock failed");
        }
        // try to fetch from the identity map
        User user = Registry.loadUser(username);
        if (user != null) {
            ArrayList<Integer> cache = user.getDegrees();
            if (cache != null && !cache.isEmpty()) {
                LockManager.getInstance().releaseReadLock(username);
                return cache;
            }
        } else {
            userMapper.getUserList();
            user = Registry.loadUser(username);
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT degreeid FROM user_deg WHERE userid = ?";
        ArrayList<Integer> degreeIds = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            conn.commit();
            while (rs.next()) {
                int id = rs.getInt("degreeid");
                degreeIds.add(id);
            }
            user.setDegrees(degreeIds);
            LockManager.getInstance().releaseReadLock(username);
            return degreeIds;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
            LockManager.getInstance().releaseReadLock(username);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }


    /**
     * Get the subjects that a student has enrolled in
     * @param username the username of the student
     * @return a list of subjects
     */
    public ArrayList<Subject> getSubjectsOfStudent(String username){
        try {
            LockManager.getInstance().acquireReadLock(username);
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock failed");
        }
        User cached = Registry.loadUser(username);
        if (cached != null && cached.getDegrees() != null) {
            ArrayList<Integer> subjectIds = cached.getSubjects();
            ArrayList<Subject> subjects;
            if (subjectIds != null && !subjectIds.isEmpty()) {
                subjects = new ArrayList<>();
                subjects.addAll(subjectMapper.getSubjects(subjectIds));
                LockManager.getInstance().releaseReadLock(username);
                return subjects;
            }
        } else {
            cached = userMapper.getUser(username);
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT subjectid, subjectcode, subjectname, subjectdesc " +
                "FROM user_sub a LEFT JOIN subject b " +
                "ON a.subjectid = b.id where a.userid = ?";
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cached.getId());
            ResultSet rs = ps.executeQuery();
            conn.commit();
            while (rs.next()) {
                int subjectId = rs.getInt("subjectid");
                String subjectCode = rs.getString("subjectcode");
                String subjectName = rs.getString("subjectname");
                String subjectDesc = rs.getString("subjectdesc");

                subjects.add(new Subject(subjectId, subjectCode, subjectName, subjectDesc));
            }

            ArrayList<Integer> subjectIds = new ArrayList<>();
            for (Subject subject : subjects) {
                subjectIds.add(subject.getId());
            }
            cached.setSubjects(subjectIds);
            LockManager.getInstance().releaseReadLock(username);
            return subjects;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
            LockManager.getInstance().releaseReadLock(username);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return null;
    }

    /**
     * Enroll a student in a subject
     * @param username the username of the student
     * @param subjectId the subject to be enrolled
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int studentEnroll(String username, int subjectId) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user_sub (userid, subjectid) VALUES (?, ?)";

        try {
            LockManager.getInstance().acquireWriteLock(username);
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock failed");
        }

        User cached = Registry.loadUser(username);
        if (cached == null) {
            cached = userMapper.getUser(username);
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cached.getId());
            ps.setInt(2, subjectId);

            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                ArrayList<Integer> subjects = cached.getSubjects();
                subjects.add(subjectId);
            }
            LockManager.getInstance().releaseWriteLock(username);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
            LockManager.getInstance().releaseWriteLock(username);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }

    /**
     * Withdraw a student from a subject
     * @param username the username of a student
     * @param subjectId the id of the subject to be withdrawn
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int studentWithdraw(String username, int subjectId) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM user_sub WHERE userid = ? AND subjectId = ?";

        try {
            LockManager.getInstance().acquireWriteLock(username);
        } catch (InterruptedException e) {
            System.out.println("Acquiring write lock failed");
        }

        User cached = Registry.loadUser(username);
        if (cached == null) {
            cached = userMapper.getUser(username);
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cached.getId());
            ps.setInt(2, subjectId);

            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                ArrayList<Integer> subjects = cached.getSubjects();
                subjects.remove(new Integer(subjectId));
            }
            LockManager.getInstance().releaseWriteLock(username);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception rollback) {
                System.out.println("Roll back failed.");
            }
            LockManager.getInstance().releaseWriteLock(username);
        } finally {
            DBConnection.closeConnection(conn);
        }
        return 0;
    }
}
