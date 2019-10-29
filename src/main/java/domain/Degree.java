package domain;

import mapper.DegreeMapper;

import java.util.HashMap;
import java.util.List;

public class Degree {
    private int id;
    private String degreeName;
    private String degreeDesc;
    private HashMap<Integer, Subject> subjects;
    private static DegreeMapper degreeMapper;

    static {
        degreeMapper = DegreeMapper.getInstance();
    }

    public Degree(int id, String degreeName, String degreeDesc) {
        this.id = id;
        this.degreeName = degreeName;
        this.degreeDesc = degreeDesc;
        subjects = new HashMap<>();
    }

    public static List<Degree> getDegreeList() {
        DegreeMapper degreeMapper = DegreeMapper.getInstance();
        return degreeMapper.getAllDegrees();
    }

    public static int deleteDegrees(int[] list) {
        return degreeMapper.deleteDegrees(list);
    }

    public int addDegree() {
        return degreeMapper.addDegree(this);
    }

    public static int addSubjectToDegree(int[] subjectIds, int degreeId) {
        return degreeMapper.addSubjectToDegree(subjectIds, degreeId);
    }

    public static List<Subject> getSubjectListInDegree(int id) {
        return degreeMapper.getSubjectListInDegree(id);
    }

    public int updateDegree() {
        return degreeMapper.updateDegree(this);
    }

    public static int deleteSubjectInDegree(int id, int[] subjectIds) {
        return degreeMapper.deleteSubjectInDegree(id, subjectIds);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getDegreeDesc() {
        return degreeDesc;
    }

    public void setDegreeDesc(String degreeDesc) {
        this.degreeDesc = degreeDesc;
    }

    public HashMap<Integer, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(HashMap<Integer, Subject> subjects) {
        this.subjects = subjects;
    }
}
