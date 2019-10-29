package mapper;

import connection.DBConnection;
import domain.Subject;
import connection.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database operations regarding subjects
 */
public class SubjectMapper {

    private static SubjectMapper instance;

    /**
     * Private constructor to avoid instantiating an object
     */
    private SubjectMapper() {}

    /**
     * Singleton method to get the mapper instance
     *
     * @return a SubjectMapper instance
     */
    public static SubjectMapper getInstance() {
        if (instance == null) {
            synchronized (SubjectMapper.class) {
                if (instance == null) {
                    instance = new SubjectMapper();
                }
            }
        }
        return instance;
    }

    /**
     * Get all subjects
     * @return a list of subjects
     */
    public List<Subject> getAllSubjects() {
        if (!Registry.subjectEmpty()) {
            return Registry.loadAllSubjects();
        }
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM subject";
        ArrayList<Subject> subjects = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                Subject subject = mapSubject(rs);
                subjects.add(subject);
                Registry.saveSubject(subject);
            }
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

        return subjects;
    }

    /**
     * Get a list of subjects given ids
     * @param subjectIds a list of integers which are subject ids
     * @return a list of subject objects
     */
    public List<Subject> getSubjects(List<Integer> subjectIds) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder("SELECT * FROM subject WHERE id IN (");
        for (int id : subjectIds){
            sb.append(id + ",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        ArrayList<Subject> subjects = new ArrayList<>();

        try {
            String sql = sb.toString();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            conn.commit();
            while (rs.next()) {
                Subject subject = mapSubject(rs);
                subjects.add(subject);
                Registry.saveSubject(subject);
            }
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
        return subjects;
    }

    /**
     * Get a subject out of the result set from the database
     * @param rs the resultset from database
     * @return a subject object
     */
    private Subject mapSubject(ResultSet rs) {
        Subject subject = null;
        try {
            int id = rs.getInt("id");
            String subjectCode = rs.getString("subjectcode");
            String subjectName = rs.getString("subjectname");
            String subjectDesc = rs.getString("subjectdesc");
            subject = new Subject(id, subjectCode, subjectName, subjectDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    /**
     * Add a subject to the database
     * @param subject the subject to be added
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int addSubject(Subject subject) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO subject (subjectcode, subjectname, subjectdesc) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subject.getSubjectCode());
            ps.setString(2, subject.getSubjectName());
            ps.setString(3, subject.getSubjectDesc());

            int result =  ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                ResultSet key = ps.getGeneratedKeys();
                key.next();
                subject.setId(key.getInt(1));
                Registry.saveSubject(subject);
            }
            return result;
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
        return 0;
    }

    /**
     * Update a subject in the database
     * @param subject the updated subject object
     * @return number of rows affected (1 for success, 0 for failure)
     */
    public int updateSubject(Subject subject) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE subject SET subjectcode = ?, subjectname = ?, subjectdesc = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subject.getSubjectCode());
            ps.setString(2, subject.getSubjectName());
            ps.setString(3, subject.getSubjectDesc());
            ps.setInt(4, subject.getId());
            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                Registry.saveSubject(subject);
            }
            return result;
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
        return 0;
    }

    /**
     * Delete a subject in the database
     * @param ids an array of integers which are ids of subjects to be deleted
     * @return number of rows affected
     */
    public int deleteSubject(int[] ids) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM subject WHERE id IN (");
        for (int i : ids) {
            sb.append(i + ",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");

        int result =  Util.runUpdate(sb.toString(), conn);
        if (result != 0) {
            for (int i : ids) {
                Registry.deleteSubject(i);
            }
        }

        return result;
    }
}
