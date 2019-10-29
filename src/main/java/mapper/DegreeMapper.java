package mapper;

import connection.DBConnection;
import connection.Util;
import domain.Degree;
import domain.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Database operations regarding degrees
 */
public class DegreeMapper {
    private static DegreeMapper degreeMapper;

    /**
     * Private constructor to avoid instantiating an object
     */
    private DegreeMapper() {}

    /**
     * Singleton method to get the mapper instance
     *
     * @return a DegreeMapper instance
     */
    public static DegreeMapper getInstance() {
        if (degreeMapper == null) {
            synchronized (DegreeMapper.class) {
                if (degreeMapper == null) {
                    degreeMapper = new DegreeMapper();
                }
            }
        }
        return degreeMapper;
    }

    /**
     * Get all degrees
     *
     * @return a list that contains all degree objects
     */
    public List<Degree> getAllDegrees() {
        if (!Registry.degreeEmpty()) {
            return Registry.loadAllDegrees();
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM degree";
        ArrayList<Degree> degrees = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Degree degree;
                int id = rs.getInt("id");
                String degreeName = rs.getString("degreename");
                String degreeDesc = rs.getString("degreedesc");
                degree = new Degree(id, degreeName, degreeDesc);
                degrees.add(degree);
                Registry.saveDegree(degree);
            }
            conn.commit();
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

        return degrees;
    }

    /**
     * Delete degrees given ids
     * @param ids an array of degree ids to be deleted
     * @return number of successful deletions
     */
    public int deleteDegrees(int[] ids) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM degree WHERE id IN (");
        for (int i : ids) {
            sb.append(i + ",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        int result =  Util.runUpdate(sb.toString(), conn);
        if (result != 0) {
            for (int i : ids) {
                Registry.deleteDegree(i);
            }
        }

        return result;
    }

    /**
     * Add a degree to the database
     * @param degree A degree object containing information of the degree to be added
     * @return An integer that shows the number of rows affected (1 for success, 0 for failed)
     */
    public int addDegree(Degree degree) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO degree (degreename, degreedesc) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, degree.getDegreeName());
            ps.setString(2, degree.getDegreeDesc());
            int result =  ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                ResultSet key = ps.getGeneratedKeys();
                key.next();
                degree.setId(key.getInt(1));
                Registry.saveDegree(degree);
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
     * Get a degree object given an id
     * @param id the id of the degree
     * @return A degree Object
     */
    public Degree getDegree(int id) {
        Degree cache = Registry.loadDegree(id);
        if (cache != null) {
            return cache;
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM degree WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            conn.commit();

            rs.next();

            Degree degree;
            String degreeName = rs.getString("degreename");
            String degreeDesc = rs.getString("degreedesc");
            degree = new Degree(id, degreeName, degreeDesc);
            return degree;
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
        return null;
    }

    /**
     * Get all subjects in a degree given a degree id
     * @param id The id of the degree
     * @return A list of subjects that's in the degree
     */
    public List<Subject> getSubjectListInDegree(int id) {
        // try to fetch from the identity map
        Degree degree = Registry.loadDegree(id);

        HashMap<Integer, Subject> cache = degree.getSubjects();
        if(cache==null){
            cache=new HashMap<>();
            degree.setSubjects(cache);
        }
        if (!cache.isEmpty()){
            return new ArrayList<>(cache.values());
        }

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT subjectid, subjectcode, subjectname, subjectdesc " +
                "FROM deg_sub a LEFT JOIN subject b ON a.subjectid = b.id " +
                "WHERE a.degreeid = ?";
        ArrayList<Subject> subjects = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            conn.commit();

            while (rs.next()) {
                Subject subject;
                int subjectId = rs.getInt("subjectid");
                String subjectCode = rs.getString("subjectcode");
                String subjectName = rs.getString("subjectname");
                String subjectDesc = rs.getString("subjectdesc");

                subject = new Subject(subjectId, subjectCode, subjectName, subjectDesc);
                subjects.add(subject);
                cache.put(subjectId, subject);
            }
            return subjects;
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
        return null;
    }

    /**
     * Adding subjects into a degree
     * @param subjectIds An array of integers which are the subject ids to be added
     * @param degreeId The id of the degree
     * @return Number of successful additions
     */
    public int addSubjectToDegree(int[] subjectIds, int degreeId) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO deg_sub (degreeid, subjectid) VALUES ");
        for (int subjectId : subjectIds) {
            sb.append("(" + degreeId + ", " + subjectId + "),");
        }
        sb.setLength(sb.length() - 1);

        int result =  Util.runUpdate(sb.toString(), conn);

        // Update the identity map if success
        if (result != 0) {
            Degree degree = Registry.loadDegree(degreeId);
            HashMap<Integer, Subject> cache = degree.getSubjects();
            for (int i : subjectIds) {
                cache.put(i, Registry.loadSubject(i));
            }
        }
        return result;
    }

    /**
     * Update the information of a degree
     * @param degree The updated degree object
     * @return the number of rows affected
     */
    public int updateDegree(Degree degree) {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE degree SET degreename = ?, degreedesc = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, degree.getDegreeName());
            ps.setString(2, degree.getDegreeDesc());
            ps.setInt(3, degree.getId());
            int result = ps.executeUpdate();
            conn.commit();
            if (result != 0) {
                Degree saved = Registry.loadDegree(degree.getId());
                saved.setDegreeDesc(degree.getDegreeDesc());
                saved.setDegreeName(degree.getDegreeName());
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
     * Delete subjects in a degree
     * @param id The id of the degree
     * @param subjectIds An array of integers which are the subjects to be removed from the degree
     * @return The number of rows affected
     */
    public int deleteSubjectInDegree(int id, int[] subjectIds) {
        Connection conn = DBConnection.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM deg_sub WHERE degreeid = " + id + " AND subjectid IN (");
        for (int subjectId : subjectIds) {
            sb.append(subjectId + ",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(");");
        int result =  Util.runUpdate(sb.toString(), conn);

        if (result != 0) {
            Degree degree = Registry.loadDegree(id);
            HashMap<Integer, Subject> cache = degree.getSubjects();
            for (int i : subjectIds) {
                cache.remove(i);
            }
        }
        return result;
    }
}
